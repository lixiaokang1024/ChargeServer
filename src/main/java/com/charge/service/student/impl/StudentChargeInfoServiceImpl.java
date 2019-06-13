package com.charge.service.student.impl;

import com.charge.mapper.student.StudentChargeInfoMapper;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.pojo.student.StudentChargeInfo;
import com.charge.service.student.StudentChargeInfoService;
import com.charge.util.RequestParamUtil;
import com.charge.vo.student.StudentChargeInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentChargeInfoServiceImpl implements StudentChargeInfoService {


    @Autowired
    private StudentChargeInfoMapper studentChargeInfoMapper;

    public void insertSelective(StudentChargeInfo studentChargeInfo) {
        studentChargeInfoMapper.insertSelective(studentChargeInfo);
    }

    public int countStudentChargeInfo(StudentChargeInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentChargeInfoMapper.countStudentChargeInfo(paramMap);
    }

    public List<StudentChargeInfoVo> queryStudentChargeInfoList(StudentChargeInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentChargeInfoMapper.queryStudentChargeInfoPageList(paramMap);
    }
}