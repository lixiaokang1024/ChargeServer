package com.charge.mapper.student;

import com.charge.pojo.student.StudentClassInfo;

public interface StudentClassInfoMapper {
    int insert(StudentClassInfo record);

    int insertSelective(StudentClassInfo record);

    int updateByPrimaryKeySelective(StudentClassInfo record);

    int updateByPrimaryKey(StudentClassInfo record);
}