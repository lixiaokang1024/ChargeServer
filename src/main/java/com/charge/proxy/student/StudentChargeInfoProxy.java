package com.charge.proxy.student;

import com.charge.Exception.BusinessException;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.param.student.StudentChargeParam;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.student.StudentChargeInfo;
import com.charge.pojo.student.StudentClassInfo;
import com.charge.pojo.student.StudentExtInfo;
import com.charge.pojo.student.StudentInfo;
import com.charge.service.charge.ChargeService;
import com.charge.service.school.SchoolService;
import com.charge.service.student.StudentChargeInfoService;
import com.charge.service.student.StudentClassInfoService;
import com.charge.service.student.StudentService;
import com.charge.util.DateUtil;
import com.charge.util.ExcelUtil;
import com.charge.util.JsonUtil;
import com.charge.vo.student.StudentChargeInfoDetailVo;
import com.charge.vo.student.StudentChargeInfoVo;
import com.charge.vo.student.StudentChargeIoVo;
import com.charge.vo.student.StudentInfoVo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentChargeInfoProxy {

    private static final Logger logger = LoggerFactory.getLogger(StudentChargeInfoProxy.class);

    @Autowired
    private StudentChargeInfoService studentChargeInfoService;

    @Autowired
    private StudentClassInfoService studentClassInfoService;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private SchoolService schoolService;

    public PageResultDTO<List<StudentChargeInfoVo>> queryStudentChargeInfo(StudentChargeInfoSearchParam searchParam){
        logger.info("查询学生应缴费信息搜索参数：{}", JsonUtil.toJson(searchParam));
        PageResultDTO<List<StudentChargeInfoVo>> pageResultDTO = new PageResultDTO<List<StudentChargeInfoVo>>();
        try {

            int count = studentChargeInfoService.countStudentChargeInfo(searchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(searchParam.getCurrentPage());
            pageResultDTO.setPageSize(searchParam.getPageSize());
            List<StudentChargeInfoVo> studentChargeInfoVoList = studentChargeInfoService.queryStudentChargeInfoList(searchParam);
            pageResultDTO.setData(studentChargeInfoVoList);
        } catch (Exception e){
            logger.error("查询学生应缴费信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

    public List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetail(Integer studentId, List<Integer> chargeStatus, String payTimeBegin, String payTimeEnd){
        logger.info("查询学生应缴费信息详情搜索参数：{}", studentId);
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = new ArrayList<StudentChargeInfoDetailVo>();
        try {
            studentChargeInfoDetailVoList = studentChargeInfoService.queryStudentChargeInfoDetail(studentId, chargeStatus, payTimeBegin, payTimeEnd);
        } catch (Exception e){
            logger.error("查询学生应缴费信息详情出错,msg={}",e.getMessage(),e);
        }
        return studentChargeInfoDetailVoList;
    }

    public void importStudentChargeInfo(InputStream is) throws IOException {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {//到所有工作簿
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            final int totalRow = xssfSheet.getLastRowNum();//取的所有行
            // Read the Row
            for (int rowNum = 1; rowNum <= totalRow; rowNum++) {
                StudentChargeInfo studentChargeInfo = new StudentChargeInfo();
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    XSSFCell studentId = xssfRow.getCell(0);
                    Integer studentIdInt = Integer.parseInt(ExcelUtil.getValue(studentId));
                    XSSFCell studentName = xssfRow.getCell(1);
                    String studentNameStr = ExcelUtil.getValue(studentName);
                    XSSFCell chargeProjectName = xssfRow.getCell(2);
                    String chargeProjectNameStr = ExcelUtil.getValue(chargeProjectName);
                    XSSFCell chargeTime = xssfRow.getCell(3);
                    String chargeTimeStr = ExcelUtil.getValue(chargeTime);
                    XSSFCell chargeCoefficient = xssfRow.getCell(4);
                    Double chargeCoefficientDouble = Double.parseDouble(ExcelUtil.getValue(chargeCoefficient));
                    StudentClassInfo studentClassInfo = studentClassInfoService.getByStudentId(studentIdInt);
                    ClassInfo classInfo = schoolService.getClassInfoById(studentClassInfo.getClassId());
                    if(studentClassInfo == null){
                        throw new BusinessException("无【"+studentNameStr+"】学生");
                    }
                    studentChargeInfo.setStudentId(studentIdInt);
                    ChargeProject chargeProject = chargeService.getChargeProjectByName(chargeProjectNameStr, classInfo.getGradeId());
                    if(chargeProject == null){
                        throw new BusinessException("无【"+chargeProjectNameStr+"】收费项目");
                    }
                    if(studentChargeInfoService.queryByUniqueKey(studentIdInt, chargeProject.getId(), DateUtil.getTimespan2(chargeTimeStr)) != null){
                        throw new BusinessException("学生【"+studentNameStr+"】已导入收费项目【"+chargeProjectNameStr+"】,不能重复导入");
                    }
                    studentChargeInfo.setChargeProjectId(chargeProject.getId());
                    if(chargeProject.getProjectName().equals("保育费")){
                        if(chargeCoefficientDouble <= 0){
                            studentChargeInfo.setChargeAmount(0.00);
                            studentChargeInfo.setChargeCoefficient(0.0);
                        }else if(chargeCoefficientDouble <= 7){
                            studentChargeInfo.setChargeAmount(chargeProject.getAmount() * 0.5);
                            studentChargeInfo.setChargeCoefficient(0.5);
                        }else{
                            studentChargeInfo.setChargeAmount(chargeProject.getAmount());
                            studentChargeInfo.setChargeCoefficient(1.0);
                        }
                    }else{
                        studentChargeInfo.setChargeAmount(chargeProject.getAmount() * chargeCoefficientDouble);
                        studentChargeInfo.setChargeCoefficient(chargeCoefficientDouble);
                    }
                    studentChargeInfo.setChargeTime(DateUtil.getTimespan2(chargeTimeStr));
                    studentChargeInfoService.insertSelective(studentChargeInfo);
                }
            }
        }
    }

    public void doAddCharge(StudentChargeInfo studentChargeInfo){
        Double chargeCoefficientDouble = studentChargeInfo.getChargeCoefficient();
        ChargeProject chargeProjectDB = chargeService.getChargeProjectById(studentChargeInfo.getChargeProjectId());
        if(chargeProjectDB.getProjectName().equals("保育费")){
            if(chargeCoefficientDouble <= 0){
                studentChargeInfo.setChargeAmount(0.00);
                studentChargeInfo.setChargeCoefficient(0.0);
            }else if(chargeCoefficientDouble <= 7){
                studentChargeInfo.setChargeAmount(chargeProjectDB.getAmount() * 0.5);
                studentChargeInfo.setChargeCoefficient(0.5);
            }else{
                studentChargeInfo.setChargeAmount(chargeProjectDB.getAmount());
                studentChargeInfo.setChargeCoefficient(1.0);
            }
        }else{
            studentChargeInfo.setChargeAmount(chargeProjectDB.getAmount() * chargeCoefficientDouble);
            studentChargeInfo.setChargeCoefficient(chargeCoefficientDouble);
        }
        studentChargeInfo.setChargeTime(DateUtil.getTimespan2(studentChargeInfo.getChargeTimeStr()));
        studentChargeInfoService.insertSelective(studentChargeInfo);
    }

    public void doCharge(StudentChargeParam chargeParam){
        studentChargeInfoService.doCharge(chargeParam);
    }

    public List<StudentChargeInfoDetailVo> doDepositCharge(StudentChargeParam chargeParam){
        String receiptId = studentChargeInfoService.addPrepaymentAmount(chargeParam);
        StudentChargeInfoSearchParam studentChargeInfoSearchParam = new StudentChargeInfoSearchParam();
        studentChargeInfoSearchParam.setReceiptId(receiptId);
        studentChargeInfoSearchParam.setCurrentPage(1);
        studentChargeInfoSearchParam.setPageSize(10);
        return studentChargeInfoService.queryStudentChargeInfoDetailPageList(studentChargeInfoSearchParam);
    }

    public List<StudentChargeInfoDetailVo> doProjectCharge(StudentChargeParam chargeParam) {
        return studentChargeInfoService.doProjectCharge(chargeParam);
    }

    public PageResultDTO<List<StudentChargeInfoDetailVo>> queryStudentChargeInfoDetailPageList(StudentChargeInfoSearchParam searchParam) {
        logger.info("统计学生应缴费信息搜索参数：{}", JsonUtil.toJson(searchParam));
        PageResultDTO<List<StudentChargeInfoDetailVo>> pageResultDTO = new PageResultDTO<List<StudentChargeInfoDetailVo>>();
        try {

            int count = studentChargeInfoService.countStudentChargeDetail(searchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(searchParam.getCurrentPage());
            pageResultDTO.setPageSize(searchParam.getPageSize());
            List<StudentChargeInfoDetailVo> studentChargeInfoVoList = studentChargeInfoService.queryStudentChargeInfoDetailPageList(searchParam);
            pageResultDTO.setData(studentChargeInfoVoList);
        } catch (Exception e){
            logger.error("统计学生应缴费信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

  public PageResultDTO<List<StudentChargeIoVo>> getReceiptList(StudentChargeInfoSearchParam searchParam) {
    PageResultDTO<List<StudentChargeIoVo>> pageResultDTO = new PageResultDTO<List<StudentChargeIoVo>>();
    try {

      int count = studentChargeInfoService.countReceiptList(searchParam);
      pageResultDTO.setTotalRecord(count);
      if(count == 0){
        return pageResultDTO;
      }
      pageResultDTO.setCurrentPage(searchParam.getCurrentPage());
      pageResultDTO.setPageSize(searchParam.getPageSize());
        List<StudentChargeIoVo> studentChargeIoVoList = studentChargeInfoService.queryReceiptPageList(searchParam);
      pageResultDTO.setData(studentChargeIoVoList);
    } catch (Exception e){
      logger.error("补打小票列表信息出错,msg={}",e.getMessage(),e);
    }
    return pageResultDTO;
  }
}
