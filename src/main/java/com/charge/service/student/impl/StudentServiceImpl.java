package com.charge.service.student.impl;

import com.charge.Exception.BusinessException;
import com.charge.mapper.student.StudentExtInfoMapper;
import com.charge.mapper.student.StudentInfoMapper;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.student.StudentExtInfo;
import com.charge.pojo.student.StudentInfo;
import com.charge.service.student.StudentService;
import com.charge.util.DateUtil;
import com.charge.util.RequestParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private StudentExtInfoMapper studentExtInfoMapper;

    public void insertSelective(StudentInfo studentInfo, StudentExtInfo studentExtInfo) {
        if(studentInfo.getIdCardNumber() == null){
            throw new BusinessException("身份证号不能为空，学生："+studentInfo.getName());
        }
        if(studentInfoMapper.getStudentInfoByIdCardNumber(studentInfo.getIdCardNumber()) != null){
            throw new BusinessException("已存在此学生，不可重复导入，身份证号为"+studentInfo.getIdCardNumber());
        }
        studentInfo.setCreatTime(DateUtil.getCurrentTimespan());
        studentInfoMapper.insertSelective(studentInfo);
        studentExtInfo.setStudentId(studentInfo.getId());
        studentExtInfo.setCreateTime(DateUtil.getCurrentTimespan());
        studentExtInfoMapper.insertSelective(studentExtInfo);
    }

    public void updateSelective(StudentInfo studentInfo) {
        if(studentInfoMapper.getStudentInfoByIdCardNumber(studentInfo.getIdCardNumber()) != null){
            throw new BusinessException("身份证号不可重复，身份证号为"+studentInfo.getIdCardNumber());
        }
        studentInfoMapper.updateByPrimaryKeySelective(studentInfo);
    }

    public int countStudent(StudentSearchParam studentSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(studentSearchParam.getCurrentPage(), studentSearchParam.getPageSize(), studentSearchParam);
        return studentInfoMapper.countStudentInfo(paramMap);
    }

    public List<StudentInfo> queryStudentList(StudentSearchParam studentSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(studentSearchParam.getCurrentPage(), studentSearchParam.getPageSize(), studentSearchParam);
        List<StudentInfo> resultList =  studentInfoMapper.queryStudentInfoPageList(paramMap);
        return resultList;
    }

    public List<StudentInfo> getStudentInfoByName(String studentName) {
        return studentInfoMapper.getStudentInfoByName(studentName);
    }

    public StudentInfo getStudentInfoById(Integer studentId) {
        return studentInfoMapper.getStudentInfoById(studentId);
    }

    public void saveOrUpdateStudentInfo(StudentInfo studentInfo) {

    }

}