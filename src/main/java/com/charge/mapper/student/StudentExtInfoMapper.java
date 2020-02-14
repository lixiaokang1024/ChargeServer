package com.charge.mapper.student;

import com.charge.pojo.student.StudentExtInfo;
import org.apache.ibatis.annotations.Param;

public interface StudentExtInfoMapper {
    int insert(StudentExtInfo record);

    int insertSelective(StudentExtInfo record);

    int updateByPrimaryKeySelective(StudentExtInfo record);
    int updateByStudentIdSelective(StudentExtInfo record);

    int updateByPrimaryKey(StudentExtInfo record);

    void updatePrepaymentAmount(@Param("studentId") Integer studentId, @Param("prepaymentAmount") Double prepaymentAmount, @Param("chargeType")Integer chargeType);

    StudentExtInfo getByStudentId(Integer studentId);
}