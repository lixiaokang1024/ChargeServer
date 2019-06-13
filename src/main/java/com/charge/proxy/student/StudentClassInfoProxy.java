package com.charge.proxy.student;

import com.charge.param.student.StudentClassInfoSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.service.student.StudentClassInfoService;
import com.charge.util.JsonUtil;
import com.charge.vo.student.StudentClassInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentClassInfoProxy {

    private static final Logger logger = LoggerFactory.getLogger(StudentClassInfoProxy.class);

    @Autowired
    private StudentClassInfoService studentClassInfoService;

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
}
