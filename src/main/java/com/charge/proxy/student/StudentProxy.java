package com.charge.proxy.student;

import com.alibaba.druid.support.json.JSONUtils;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.student.StudentInfo;
import com.charge.service.student.StudentService;
import com.charge.vo.student.StudentInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentProxy {

    private static final Logger logger = LoggerFactory.getLogger(StudentProxy.class);

    @Autowired
    private StudentService studentService;

    public PageResultDTO<List<StudentInfoVo>> queryStudentInfo(StudentSearchParam searchParam){
        logger.info("查询学生信息搜索参数：{}", JSONUtils.toJSONString(searchParam));
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
}
