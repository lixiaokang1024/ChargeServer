package com.charge.mapper.student;

import com.charge.pojo.student.StudentChargeInfo;
import com.charge.vo.student.StudentChargeInfoVo;

import java.util.List;
import java.util.Map;

public interface StudentChargeInfoMapper {
    int insert(StudentChargeInfo record);

    int insertSelective(StudentChargeInfo record);

    int updateByPrimaryKeySelective(StudentChargeInfo record);

    int updateByPrimaryKey(StudentChargeInfo record);

    int countStudentChargeInfo(Map<String, Object> searchParam);
    List<StudentChargeInfoVo> queryStudentChargeInfoPageList(Map<String, Object> searchParam);
}