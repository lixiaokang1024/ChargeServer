package com.charge.mapper.student;

import com.charge.pojo.student.ClassInfo;

public interface ClassInfoMapper {
    int insert(ClassInfo record);

    int insertSelective(ClassInfo record);

    int updateByPrimaryKeySelective(ClassInfo record);

    int updateByPrimaryKey(ClassInfo record);
}