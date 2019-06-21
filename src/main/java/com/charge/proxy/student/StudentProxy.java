package com.charge.proxy.student;

import com.charge.Exception.BusinessException;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.student.StudentExtInfo;
import com.charge.pojo.student.StudentInfo;
import com.charge.service.student.StudentService;
import com.charge.util.DateUtil;
import com.charge.util.ExcelUtil;
import com.charge.util.JsonUtil;
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
public class StudentProxy {

    private static final Logger logger = LoggerFactory.getLogger(StudentProxy.class);

    @Autowired
    private StudentService studentService;

    public PageResultDTO<List<StudentInfoVo>> queryStudentInfo(StudentSearchParam searchParam){
        logger.info("查询学生信息搜索参数：{}", JsonUtil.toJson(searchParam));
        PageResultDTO<List<StudentInfoVo>> pageResultDTO = new PageResultDTO<List<StudentInfoVo>>();
        try {

            int count = studentService.countStudent(searchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(searchParam.getCurrentPage());
            pageResultDTO.setPageSize(searchParam.getPageSize());
            List<StudentInfo> studentInfoList = studentService.queryStudentList(searchParam);
            if(!CollectionUtils.isEmpty(studentInfoList)){
                List<StudentInfoVo> studentInfoVoList = transferToVo(studentInfoList);
                pageResultDTO.setData(studentInfoVoList);
            }
        }catch (Exception e){
            logger.error("查询学生信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

    private List<StudentInfoVo> transferToVo(List<StudentInfo> studentInfoList) {
        List<StudentInfoVo> studentInfoVoList = new ArrayList<StudentInfoVo>();
        if(CollectionUtils.isEmpty(studentInfoList)){
            return studentInfoVoList;
        }
        for(StudentInfo studentInfo:studentInfoList){
            StudentInfoVo studentInfoVo = new StudentInfoVo();
            BeanUtils.copyProperties(studentInfo, studentInfoVo);
            studentInfoVoList.add(studentInfoVo);
        }
        return studentInfoVoList;
    }

    public void importStudentInfo(InputStream is) throws IOException, ParseException {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {//到所有工作簿
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            final int totalRow = xssfSheet.getLastRowNum();//取的所有行
            // Read the Row
            for (int rowNum = 1; rowNum <= totalRow; rowNum++) {
                StudentInfo studentInfo = new StudentInfo();
                StudentExtInfo studentExtInfo = new StudentExtInfo();
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    XSSFCell name = xssfRow.getCell(0);
                    XSSFCell sex = xssfRow.getCell(1);
                    XSSFCell bornDate = xssfRow.getCell(2);
                    XSSFCell parentName = xssfRow.getCell(3);
                    XSSFCell relation = xssfRow.getCell(4);
                    XSSFCell mobile = xssfRow.getCell(5);
                    XSSFCell address = xssfRow.getCell(6);
                    XSSFCell admissionTime = xssfRow.getCell(7);
                    studentInfo.setName(ExcelUtil.getValue(name).replace("\t", ""));
                    String sexStr = ExcelUtil.getValue(sex);
                    studentInfo.setSex(sexStr.equals("男")?0:1);
                    String bornDateStr = ExcelUtil.getValue(bornDate);
                    Date born = DateUtil.getDateByString(bornDateStr);
                    studentInfo.setYear(DateUtil.getYear(born));
                    studentInfo.setMonth(DateUtil.getMonth(born));
                    studentInfo.setDay(DateUtil.getDay(born));
                    studentInfo.setAge(DateUtil.getAge(born));
                    studentInfo.setParentName(ExcelUtil.getValue(parentName));
                    studentInfo.setRelation(ExcelUtil.getValue(relation));
                    studentInfo.setMobile(ExcelUtil.getValue(mobile));
                    studentInfo.setAddress(ExcelUtil.getValue(address));
                    studentExtInfo.setAdmissionTime(DateUtil.getTimespan(ExcelUtil.getValue(admissionTime)));
                    studentService.insertSelective(studentInfo, studentExtInfo);
                }
            }
        }
    }

    public void saveOrUpdateStudentInfo(StudentInfo studentInfo) throws ParseException {
        logger.info("学生信息保存参数：{}", JsonUtil.toJson(studentInfo));
        String bornDateStr = studentInfo.getYear()+"-"+studentInfo.getMonth()+"-"+studentInfo.getDay();
        Date born = DateUtil.getDateByString(bornDateStr);
        studentInfo.setAge(DateUtil.getAge(born));
        if(studentInfo.getId() != null){
            StudentInfo studentInfoIndb = studentService.getStudentInfoById(studentInfo.getId());
            if(studentInfoIndb == null){
                throw new BusinessException("记录不存在！");
            }
            studentService.updateSelective(studentInfo);
        }else{
            StudentExtInfo studentExtInfo = new StudentExtInfo();
            studentService.insertSelective(studentInfo, studentExtInfo);
        }
    }
}
