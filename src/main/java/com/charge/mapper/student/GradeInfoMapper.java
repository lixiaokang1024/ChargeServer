package com.charge.mapper.student;

import com.charge.pojo.student.GradeInfo;

public interface GradeInfoMapper {
    int insert(GradeInfo record);

    int insertSelective(GradeInfo record);

    int updateByPrimaryKeySelective(GradeInfo record);

    int updateByPrimaryKey(GradeInfo record);
}