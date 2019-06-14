package com.charge.proxy.student;

import com.charge.Exception.BusinessException;
import com.charge.param.student.StudentClassInfoSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.school.GradeInfo;
import com.charge.pojo.student.StudentChargeInfo;
import com.charge.pojo.student.StudentClassInfo;
import com.charge.service.school.SchoolService;
import com.charge.service.student.StudentClassInfoService;
import com.charge.util.ExcelUtil;
import com.charge.util.JsonUtil;
import com.charge.vo.student.StudentClassInfoVo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

@Service
public class StudentClassInfoProxy {

    private static final Logger logger = LoggerFactory.getLogger(StudentClassInfoProxy.class);

    @Autowired
    private StudentClassInfoService studentClassInfoService;

    @Autowired
    private SchoolService schoolService;

    public PageResultDTO<List<StudentClassInfoVo>> queryStudentClassInfo(StudentClassInfoSearchParam searchParam){
        logger.info("查询学生班级信息搜索参数：{}", JsonUtil.toJson(searchParam));
        PageResultDTO<List<StudentClassInfoVo>> pageResultDTO = new PageResultDTO<List<StudentClassInfoVo>>();
        try {

            int count = studentClassInfoService.countStudentClassInfo(searchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(searchParam.getCurrentPage());
            pageResultDTO.setPageSize(searchParam.getPageSize());
            List<StudentClassInfoVo> studentClassInfoVoList = studentClassInfoService.queryStudentClassInfoList(searchParam);
            pageResultDTO.setData(studentClassInfoVoList);
        } catch (Exception e){
            logger.error("查询学生班级信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

    public void importStudentClassInfo(InputStream is) throws IOException, ParseException {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {//到所有工作簿
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            final int totalRow = xssfSheet.getLastRowNum();//取的所有行
            // Read the Row
            for (int rowNum = 1; rowNum <= totalRow; rowNum++) {
                StudentClassInfo studentClassInfo = new StudentClassInfo();
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    XSSFCell studentId = xssfRow.getCell(0);
                    Integer studentIdInt = Integer.parseInt(ExcelUtil.getValue(studentId));
                    XSSFCell studentName = xssfRow.getCell(1);
                    String studentNameStr = ExcelUtil.getValue(studentName);
                    XSSFCell className = xssfRow.getCell(2);
                    String classNameStr = ExcelUtil.getValue(className);
                    XSSFCell gradeName = xssfRow.getCell(3);
                    String gradeNameStr = ExcelUtil.getValue(gradeName);
                    GradeInfo gradeInfo = schoolService.getGradeInfoByName(gradeNameStr);
                    if(gradeInfo == null){
                        throw new BusinessException("无【"+gradeNameStr+"】年级");
                    }
                    ClassInfo classInfo = schoolService.getByClassNameGradeId(classNameStr, gradeInfo.getId());
                    if(classInfo == null){
                        throw new BusinessException("无【"+className+"】班级");
                    }
                    studentClassInfo.setStudentId(studentIdInt);
                    studentClassInfo.setClassId(classInfo.getId());
                    studentClassInfo.setGradeId(classInfo.getGradeId());
                    studentClassInfoService.insertSelective(studentClassInfo);
                }
            }
        }
    }
}
