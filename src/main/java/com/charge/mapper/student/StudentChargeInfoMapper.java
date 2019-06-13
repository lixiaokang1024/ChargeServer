package com.charge.mapper.student;

import com.charge.pojo.student.StudentChargeInfo;

public interface StudentChargeInfoMapper {
    int insert(StudentChargeInfo record);

    int insertSelective(StudentChargeInfo record);

    int updateByPrimaryKeySelective(StudentChargeInfo record);

    int updateByPrimaryKey(StudentChargeInfo record);
}