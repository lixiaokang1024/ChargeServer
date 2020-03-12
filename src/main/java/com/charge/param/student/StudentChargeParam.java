package com.charge.param.student;


import java.io.Serializable;
import java.util.List;

/**
 * 学生缴费参数
 */
public class StudentChargeParam implements Serializable {

    private Integer studentId;

    private Double chargeAmount;//缴费金额

    private Integer chargeType;//1:预交费 2:押金 3:项目缴费

    private Integer payType;//0:现金 1：非现金

    private Integer isUseDeposit;//是否使用预缴费金额

    private Double customOfferAmount;//用户自定义优惠金额

    private List<ProjectChargeParam> projectChargeParamList;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getIsUseDeposit() {
        return isUseDeposit;
    }

    public void setIsUseDeposit(Integer isUseDeposit) {
        this.isUseDeposit = isUseDeposit;
    }

    public Double getCustomOfferAmount() {
        return customOfferAmount;
    }

    public void setCustomOfferAmount(Double customOfferAmount) {
        this.customOfferAmount = customOfferAmount;
    }

    public List<ProjectChargeParam> getProjectChargeParamList() {
        return projectChargeParamList;
    }

    public void setProjectChargeParamList(List<ProjectChargeParam> projectChargeParamList) {
        this.projectChargeParamList = projectChargeParamList;
    }
}