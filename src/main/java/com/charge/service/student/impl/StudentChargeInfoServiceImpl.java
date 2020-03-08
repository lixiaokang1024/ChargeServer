package com.charge.service.student.impl;

import com.charge.Exception.BusinessException;
import com.charge.enums.charge.ChargeStatus;
import com.charge.enums.charge.ChargeType;
import com.charge.mapper.student.StudentChargeInfoMapper;
import com.charge.mapper.student.StudentExtInfoMapper;
import com.charge.param.student.ProjectChargeParam;
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
import java.util.HashMap;
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

    public List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetail(Integer studentId, List<Integer> chargeStatus, String payTimeBegin, String payTimeEnd) {
        return studentChargeInfoMapper.queryStudentChargeInfoDetail(studentId, chargeStatus, payTimeBegin, payTimeEnd);
    }

    @Override
    public StudentChargeInfo queryByUniqueKey(Integer studentId, Integer chargeProjectId, Integer chargeTime) {
        return studentChargeInfoMapper.queryByUniqueKey(studentId, chargeProjectId, chargeTime);
    }

    public int countStudentChargeDetail(StudentChargeInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentChargeInfoMapper.countStudentChargeDetail(paramMap);
    }
    public List<StudentChargeInfoDetailVo> queryStudentChargeInfoDetailPageList(StudentChargeInfoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return studentChargeInfoMapper.queryStudentChargeDetailPageList(paramMap);
    }

    @Override
    public void checkChargeExpireStudent() {
        studentChargeInfoMapper.checkChargeExpireStudent();
    }

    public void addPrepaymentAmount(StudentChargeParam chargeParam) {
        studentExtInfoMapper.updatePrepaymentAmount(chargeParam.getStudentId(), chargeParam.getChargeAmount(), chargeParam.getChargeType());
    }

    public void doCharge(StudentChargeParam chargeParam) {
        List<Integer> chargeStatus = new ArrayList<Integer>();
        chargeStatus.add(0);
        chargeStatus.add(1);
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = studentChargeInfoMapper.queryStudentChargeInfoDetail(chargeParam.getStudentId(), chargeStatus, null, null);
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
        studentExtInfoMapper.updatePrepaymentAmount(chargeParam.getStudentId(), -changePrepaymentAmount, 1);
    }

    public List<StudentChargeInfoDetailVo> doProjectCharge(StudentChargeParam chargeParam) {
        List<Integer> chargeStatus = new ArrayList<Integer>();
        chargeStatus.add(0);
        chargeStatus.add(1);
        chargeStatus.add(3);
        List<StudentChargeInfoDetailVo> result = new ArrayList<StudentChargeInfoDetailVo>();
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = studentChargeInfoMapper.queryStudentChargeInfoDetail(chargeParam.getStudentId(), chargeStatus, null, null);
        if(CollectionUtils.isEmpty(studentChargeInfoDetailVoList)){
            return result;
        }
        List<ProjectChargeParam> projectChargeParamList = chargeParam.getProjectChargeParamList();
        Map<Integer, ProjectChargeParam> projectAmountMap = new HashMap<>();
        for(ProjectChargeParam projectChargeParam:projectChargeParamList){
            projectAmountMap.put(projectChargeParam.getStudentProjectId(), projectChargeParam);
        }
        Boolean useDeposit = chargeParam.getIsUseDeposit() == 1;
        Double prepaymentAmount = 0.00; //学生预缴费金额
        Double changePrepaymentAmount = 0.00; //使用预缴费金额
        Double customOfferAmount = chargeParam.getCustomOfferAmount() == null ? 0 : chargeParam.getCustomOfferAmount();
        if(useDeposit){
            prepaymentAmount = studentExtInfoMapper.getByStudentId(chargeParam.getStudentId()).getPrepaymentAmount();
        }
        for(StudentChargeInfoDetailVo vo:studentChargeInfoDetailVoList){
            Double discount = projectAmountMap.get(vo.getId()).getDiscount();
            if(discount == null){
                discount = 1.0;
            }
            Double chargeAmount = vo.getChargeAmount() * discount - vo.getActualChargeAmount() - vo.getUseDepositAmount();
            Double actureChargeAmount = projectAmountMap.get(vo.getId()).getProjectAmount();
            if(actureChargeAmount > chargeAmount){
                throw new BusinessException("实际支付金额大于应支付金额,请核对后再支付");
            }
            Double useCustomOfferAmount = 0.00;
            if(actureChargeAmount < chargeAmount){
                useCustomOfferAmount = chargeAmount - actureChargeAmount;
                if(customOfferAmount < useCustomOfferAmount){
                    useCustomOfferAmount = customOfferAmount;
                }
                customOfferAmount -= useCustomOfferAmount;
            }
            Double useDepositAmount = 0.00;
            if((actureChargeAmount + useCustomOfferAmount) < chargeAmount){
                useDepositAmount = chargeAmount - actureChargeAmount - useCustomOfferAmount;
                if(prepaymentAmount < useDepositAmount){
                    useDepositAmount = prepaymentAmount;
                }
                prepaymentAmount -= useDepositAmount;
                changePrepaymentAmount += useDepositAmount;
            }
            StudentChargeInfo studentChargeInfo = new StudentChargeInfo();
            studentChargeInfo.setId(vo.getId());
            studentChargeInfo.setActualChargeAmount(actureChargeAmount);
            studentChargeInfo.setUseDepositAmount(useDepositAmount);
            studentChargeInfo.setActualChargeTime(DateUtil.getCurrentTimespan());
            studentChargeInfo.setStatus(vo.getStatus());
            studentChargeInfo.setDiscount(discount);
            studentChargeInfo.setCustomOfferAmount(useCustomOfferAmount);
            if((actureChargeAmount + useDepositAmount) < chargeAmount){
                if((actureChargeAmount + useDepositAmount) > 0.00){
                    if(vo.getStatus() != ChargeStatus.EXPIRED.getCode()){
                        studentChargeInfo.setStatus(ChargeStatus.PART_CHARGED.getCode());
                    }
                }
            }else{
                studentChargeInfo.setStatus(ChargeStatus.CHARGED.getCode());
            }
            studentChargeInfo.setPayType(chargeParam.getPayType());
            studentChargeInfoMapper.updateStudentChargeInfo(studentChargeInfo);
            if((actureChargeAmount + useDepositAmount) > 0.00){
                vo.setActualChargeAmount(actureChargeAmount);
                vo.setActualChargeTime(studentChargeInfo.getActualChargeTime());
                vo.setUseDepositAmount(studentChargeInfo.getUseDepositAmount());
                vo.setPayType(studentChargeInfo.getPayType());
                vo.setStatus(studentChargeInfo.getStatus());
                result.add(vo);
            }
        }
        studentExtInfoMapper.updatePrepaymentAmount(chargeParam.getStudentId(), -changePrepaymentAmount, 1);
        return result;
    }
}