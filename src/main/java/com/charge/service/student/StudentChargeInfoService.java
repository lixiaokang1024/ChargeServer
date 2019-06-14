package com.charge.service.student;

import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.pojo.student.StudentChargeInfo;
import com.charge.vo.student.StudentChargeInfoDetailVo;
import com.charge.vo.student.StudentChargeInfoVo;

import java.util.List;

public interface StudentChargeInfoService {

    void insertSelective(StudentChargeInfo studentChargeInfo);

    int countStudentChargeInfo(StudentChargeInfoSearchParam searchParam);
    List<StudentChargeInfoVo> queryStudentChargeInfoList(StudentChargeInfoSearchParam searchParam);

    List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetail(Integer studentId, Integer chargeStatus);
}