package com.charge.proxy.student;

import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.student.StudentExtInfo;
import com.charge.pojo.student.StudentInfo;
import com.charge.service.student.StudentChargeInfoService;
import com.charge.service.student.StudentService;
import com.charge.util.DateUtil;
import com.charge.util.ExcelUtil;
import com.charge.util.JsonUtil;
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

    public PageResultDTO<List<StudentChargeInfoVo>> queryStudentChargeInfo(StudentChargeInfoSearchParam searchParam){
        logger.info("查询学生缴费信息搜索参数：{}", JsonUtil.toJson(searchParam));
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
            logger.error("查询学生缴费信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }
}
