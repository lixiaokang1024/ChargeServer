package com.charge.proxy.student;

import com.charge.Exception.BusinessException;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.student.StudentChargeInfo;
import com.charge.pojo.student.StudentClassInfo;
import com.charge.pojo.student.StudentExtInfo;
import com.charge.pojo.student.StudentInfo;
import com.charge.service.charge.ChargeService;
import com.charge.service.student.StudentChargeInfoService;
import com.charge.service.student.StudentClassInfoService;
import com.charge.service.student.StudentService;
import com.charge.util.DateUtil;
import com.charge.util.ExcelUtil;
import com.charge.util.JsonUtil;
import com.charge.vo.student.StudentChargeInfoDetailVo;
import com.charge.vo.student.StudentChargeInfoVo;
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

    public List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetail(Integer studentId, Integer chargeStatus){
        logger.info("查询学生应缴费信息详情搜索参数：{}", studentId);
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = new ArrayList<StudentChargeInfoDetailVo>();
        try {
            studentChargeInfoDetailVoList = studentChargeInfoService.queryStudentChargeInfoDetail(studentId, chargeStatus);
        } catch (Exception e){
            logger.error("查询学生应缴费信息详情出错,msg={}",e.getMessage(),e);
        }
        return studentChargeInfoDetailVoList;
    }

    public void importStudentChargeInfo(InputStream is) throws IOException, ParseException {

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
                    StudentClassInfo studentClassInfo = studentClassInfoService.getByStudentId(studentIdInt);
                    if(studentClassInfo == null){
                        throw new BusinessException("无【"+studentNameStr+"】学生");
                    }
                    studentChargeInfo.setStudentId(studentIdInt);
                    ChargeProject chargeProject = chargeService.getChargeProjectByName(chargeProjectNameStr, studentClassInfo.getGradeId());
                    if(chargeProject == null){
                        throw new BusinessException("无【"+chargeProjectNameStr+"】收费项目");
                    }
                    studentChargeInfo.setChargeProjectId(chargeProject.getId());
                    studentChargeInfo.setChargeAmount(chargeProject.getAmount());
                    studentChargeInfoService.insertSelective(studentChargeInfo);
                }
            }
        }
    }
}
