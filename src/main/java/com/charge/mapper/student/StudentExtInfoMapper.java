package com.charge.mapper.student;

import com.charge.pojo.student.StudentExtInfo;

public interface StudentExtInfoMapper {
    int insert(StudentExtInfo record);

    int insertSelective(StudentExtInfo record);

    int updateByPrimaryKeySelective(StudentExtInfo record);
    int updateByStudentIdSelective(StudentExtInfo record);

    int updateByPrimaryKey(StudentExtInfo record);
}