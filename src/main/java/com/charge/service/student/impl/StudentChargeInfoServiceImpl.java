package com.charge.service.student.impl;

import com.charge.enums.charge.ChargeStatus;
import com.charge.enums.charge.ChargeType;
import com.charge.mapper.student.StudentChargeInfoMapper;
import com.charge.mapper.student.StudentExtInfoMapper;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.param.student.StudentChargeParam;
import com.charge.pojo.student.StudentChargeInfo;
import com.charge.service.student.StudentChargeInfoService;
import com.charge.util.DateUtil;
import com.charge.util.RequestParamUtil;
import com.charge.vo.student.StudentChargeInfoDetailVo;
import com.charge.vo.student.StudentChargeInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StudentChargeInfoServiceImpl implements StudentChargeInfoService {


    @Autowired
    private StudentChargeInfoMapper studentChargeInfoMapper;

    @Autowired
    private StudentExtInfoMapper studentExtInfoMapper;

    public void insertSelective(StudentChargeInfo studentChargeInfo) {
        studentChargeInfo.setCreateTime(DateUtil.getCurrentTimespan());
        studentChargeInfoMapper.insertSelective(studentChargeInfo);
    }

    public int countStudentChargeInfo(StudentChargeInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentChargeInfoMapper.countStudentChargeInfo(paramMap);
    }

    public List<StudentChargeInfoVo> queryStudentChargeInfoList(StudentChargeInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentChargeInfoMapper.queryStudentChargeInfoPageList(paramMap);
    }

    public List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetail(Integer studentId, List<Integer> chargeStatus) {
        return studentChargeInfoMapper.queryStudentChargeInfoDetail(studentId, chargeStatus);
    }

    public void addPrepaymentAmount(StudentChargeParam chargeParam) {
        studentExtInfoMapper.updatePrepaymentAmount(chargeParam.getStudentId(), chargeParam.getChargeAmount());
    }

    public void doCharge(StudentChargeParam chargeParam) {
        List<Integer> chargeStatus = new ArrayList<Integer>();
        chargeStatus.add(0);
        chargeStatus.add(1);
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = studentChargeInfoMapper.queryStudentChargeInfoDetail(chargeParam.getStudentId(), chargeStatus);
        if(CollectionUtils.isEmpty(studentChargeInfoDetailVoList)){
            return;
        }
        Double chargeAmount = chargeParam.getChargeAmount();//缴费金额
        Boolean useDeposit = chargeParam.getIsUseDeposit() == 1;
        Double prepaymentAmount = 0.00; //学生预缴费金额
        Double changePrepaymentAmount = 0.00; //使用预缴费金额
        if(useDeposit){
            prepaymentAmount = studentExtInfoMapper.getByStudentId(chargeParam.getStudentId()).getPrepaymentAmount();
        }
        for(StudentChargeInfoDetailVo studentChargeInfoDetailVo:studentChargeInfoDetailVoList){
            Double needChargeAmount = studentChargeInfoDetailVo.getChargeAmount() - studentChargeInfoDetailVo.getActualChargeAmount() - studentChargeInfoDetailVo.getUseDepositAmount();
            if(needChargeAmount <= 0){
                continue;
            }
            if(prepaymentAmount <= 0 && chargeAmount <= 0){
                break;
            }
            StudentChargeInfo studentChargeInfo = new StudentChargeInfo();
            studentChargeInfo.setId(studentChargeInfoDetailVo.getId());
            studentChargeInfo.setActualChargeAmount(0.00);
            studentChargeInfo.setUseDepositAmount(0.00);
            studentChargeInfo.setActualChargeTime(DateUtil.getCurrentTimespan());
            if(prepaymentAmount > 0){
                studentChargeInfo.setPayType(ChargeType.ADVANCE_CHARGE.getCode());
                studentChargeInfo.setStatus(ChargeStatus.PART_CHARGED.getCode());
                if(prepaymentAmount >= needChargeAmount){
                    studentChargeInfo.setUseDepositAmount(needChargeAmount);
                    studentChargeInfo.setStatus(ChargeStatus.CHARGED.getCode());
                }else{
                    studentChargeInfo.setUseDepositAmount(prepaymentAmount);
                    needChargeAmount -= prepaymentAmount;
                    if(chargeAmount >= needChargeAmount){
                        studentChargeInfo.setActualChargeAmount(needChargeAmount);
                        studentChargeInfo.setStatus(ChargeStatus.CHARGED.getCode());
                    }else{
                        studentChargeInfo.setActualChargeAmount(chargeAmount);
                    }
                }
            }else{
                if(chargeAmount >= needChargeAmount){
                    studentChargeInfo.setActualChargeAmount(needChargeAmount);
                    studentChargeInfo.setStatus(ChargeStatus.CHARGED.getCode());
                }else{
                    studentChargeInfo.setActualChargeAmount(chargeAmount);
                    studentChargeInfo.setStatus(ChargeStatus.PART_CHARGED.getCode());
                }
            }
            prepaymentAmount -= studentChargeInfo.getUseDepositAmount();
            changePrepaymentAmount += studentChargeInfo.getUseDepositAmount();
            chargeAmount -= studentChargeInfo.getActualChargeAmount();

            studentChargeInfoMapper.updateStudentChargeInfo(studentChargeInfo);
        }
        studentExtInfoMapper.updatePrepaymentAmount(chargeParam.getStudentId(), -changePrepaymentAmount);
    }
}