package com.charge.service.student;

import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.param.student.StudentChargeParam;
import com.charge.pojo.student.StudentChargeInfo;
import com.charge.vo.student.StudentChargeInfoDetailVo;
import com.charge.vo.student.StudentChargeInfoVo;

import java.util.List;

public interface StudentChargeInfoService {

    void insertSelective(StudentChargeInfo studentChargeInfo);

    int countStudentChargeInfo(StudentChargeInfoSearchParam searchParam);
    List<StudentChargeInfoVo> queryStudentChargeInfoList(StudentChargeInfoSearchParam searchParam);

    List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetail(Integer studentId, List<Integer> chargeStatus, String payTimeBegin, String payTimeEnd);

    StudentChargeInfo queryByUniqueKey(Integer studentId, Integer chargeProjectId, Integer chargeTime);

    void addPrepaymentAmount(StudentChargeParam chargeParam);

    void doCharge(StudentChargeParam chargeParam);

    List<StudentChargeInfoDetailVo> doProjectCharge(StudentChargeParam chargeParam);

    int countStudentChargeDetail(StudentChargeInfoSearchParam searchParam);
    List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetailPageList(StudentChargeInfoSearchParam searchParam);

  void checkChargeExpireStudent();
}