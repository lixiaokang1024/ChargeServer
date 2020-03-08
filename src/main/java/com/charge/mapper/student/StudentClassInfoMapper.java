package com.charge.mapper.student;

import com.charge.pojo.student.StudentClassInfo;
import com.charge.vo.student.StudentClassInfoVo;

import java.util.List;
import java.util.Map;

public interface StudentClassInfoMapper {
    int insert(StudentClassInfo record);

    int insertSelective(StudentClassInfo record);

    int updateByPrimaryKeySelective(StudentClassInfo record);

    int updateByPrimaryKey(StudentClassInfo record);

    int countStudentClassInfo(Map<String, Object> searchParam);
    List<StudentClassInfoVo> queryStudentClassInfoPageList(Map<String, Object> searchParam);

    StudentClassInfo getByStudentId(Integer studentId);

    int updateByStudentId(StudentClassInfo studentClassInfo);
}