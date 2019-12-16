package com.charge.mapper.student;

import com.charge.pojo.student.StudentChargeInfo;
import com.charge.vo.student.StudentChargeInfoDetailVo;
import com.charge.vo.student.StudentChargeInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentChargeInfoMapper {
    int insert(StudentChargeInfo record);

    int insertSelective(StudentChargeInfo record);

    int updateByPrimaryKeySelective(StudentChargeInfo record);

    int updateByPrimaryKey(StudentChargeInfo record);

    StudentChargeInfo queryByUniqueKey(@Param("studentId") Integer studentId, @Param("chargeProjectId")Integer chargeProjectId, @Param("chargeTime")Integer chargeTime);

    int countStudentChargeInfo(Map<String, Object> searchParam);
    List<StudentChargeInfoVo> queryStudentChargeInfoPageList(Map<String, Object> searchParam);

    List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetail(@Param("studentId") Integer studentId, @Param("chargeStatusList") List<Integer> chargeStatus);

    void updateStudentChargeInfo(StudentChargeInfo record);

    int countStudentChargeDetail(Map<String, Object> searchParam);
    List<StudentChargeInfoDetailVo> queryStudentChargeDetailPageList(Map<String, Object> searchParam);

  void checkChargeExpireStudent();
}