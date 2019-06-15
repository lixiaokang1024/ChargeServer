package com.charge.service.student;

import com.charge.param.student.StudentClassInfoSearchParam;
import com.charge.pojo.student.StudentClassInfo;
import com.charge.vo.student.StudentClassInfoVo;

import java.util.List;

public interface StudentClassInfoService {

    void insertSelective(StudentClassInfo studentClassInfo);

    int countStudentClassInfo(StudentClassInfoSearchParam searchParam);
    List<StudentClassInfoVo> queryStudentClassInfoList(StudentClassInfoSearchParam searchParam);

    StudentClassInfo getByStudentId(Integer studentId);

    void upStudentClass(List<StudentClassInfoVo> studentClassInfoVoList);

}