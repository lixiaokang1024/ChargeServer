package com.charge.service.student.impl;

import com.charge.mapper.student.StudentClassInfoMapper;
import com.charge.param.student.StudentClassInfoSearchParam;
import com.charge.pojo.student.StudentClassInfo;
import com.charge.service.student.StudentClassInfoService;
import com.charge.util.DateUtil;
import com.charge.util.RequestParamUtil;
import com.charge.vo.student.StudentClassInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class StudentClassInfoServiceImpl implements StudentClassInfoService {

    @Autowired
    private StudentClassInfoMapper studentClassInfoMapper;

    public void insertSelective(StudentClassInfo studentClassInfo) {
        studentClassInfo.setCreateTime(DateUtil.getCurrentTimespan());
        studentClassInfoMapper.insertSelective(studentClassInfo);
    }

    public int countStudentClassInfo(StudentClassInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentClassInfoMapper.countStudentClassInfo(paramMap);
    }

    public List<StudentClassInfoVo> queryStudentClassInfoList(StudentClassInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentClassInfoMapper.queryStudentClassInfoPageList(paramMap);
    }

    public StudentClassInfo getByStudentId(Integer studentId) {
        return studentClassInfoMapper.getByStudentId(studentId);
    }
}