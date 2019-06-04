package com.charge.mapper.student;

import com.charge.pojo.student.StudentInfo;

import java.util.List;
import java.util.Map;

public interface StudentInfoMapper {
    int insert(StudentInfo record);

    int insertSelective(StudentInfo record);

    int updateByPrimaryKeySelective(StudentInfo record);

    int updateByPrimaryKey(StudentInfo record);

    int countStudentInfo(Map<String, Object> param);
    List<StudentInfo> queryStudentInfoPageList(Map<String, Object> param);
}