package com.charge.service.student;

import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.param.student.StudentChargeParam;
import com.charge.pojo.student.StudentChargeInfo;
import com.charge.vo.student.StudentChargeInfoDetailVo;
import com.charge.vo.student.StudentChargeInfoVo;

import com.charge.vo.student.StudentChargeIoVo;
import java.util.List;

public interface StudentChargeInfoService {

    void insertSelective(StudentChargeInfo studentChargeInfo);

    int countStudentChargeInfo(StudentChargeInfoSearchParam searchParam);
    List<StudentChargeInfoVo> queryStudentChargeInfoList(StudentChargeInfoSearchParam searchParam);

    List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetail(Integer studentId, List<Integer> chargeStatus, String payTimeBegin, String payTimeEnd);

    StudentChargeInfo queryByUniqueKey(Integer studentId, Integer chargeProjectId, Integer chargeTime);

    String addPrepaymentAmount(StudentChargeParam chargeParam);

    void doCharge(StudentChargeParam chargeParam);

    List<StudentChargeInfoDetailVo> doProjectCharge(StudentChargeParam chargeParam);

    int countStudentChargeDetail(StudentChargeInfoSearchParam searchParam);
    List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetailPageList(StudentChargeInfoSearchParam searchParam);

    void checkChargeExpireStudent();

    int countReceiptList(StudentChargeInfoSearchParam searchParam);

    List<StudentChargeIoVo> queryReceiptPageList(StudentChargeInfoSearchParam searchParam);
}