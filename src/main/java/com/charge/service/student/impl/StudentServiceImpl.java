package com.charge.service.student.impl;

import com.charge.mapper.student.StudentInfoMapper;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.student.StudentInfo;
import com.charge.service.student.StudentService;
import com.charge.util.RequestParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    public int countStudent(StudentSearchParam studentSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(studentSearchParam.getCurrentPage(), studentSearchParam.getPageSize(), studentSearchParam);
        return studentInfoMapper.countStudentInfo(paramMap);
    }

    public List<StudentInfo> queryStudentList(StudentSearchParam studentSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(studentSearchParam.getCurrentPage(), studentSearchParam.getPageSize(), studentSearchParam);
        List<StudentInfo> resultList =  studentInfoMapper.queryStudentInfoPageList(paramMap);
        return resultList;
    }

}