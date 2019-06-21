package com.charge.param.student;


import java.io.Serializable;

/**
 * 学生缴费参数
 */
public class StudentChargeParam implements Serializable {

    private Integer studentId;

    private Double chargeAmount;//缴费金额

    private Integer isUseDeposit;//是否使用预缴费金额

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

    public Integer getIsUseDeposit() {
        return isUseDeposit;
    }

    public void setIsUseDeposit(Integer isUseDeposit) {
        this.isUseDeposit = isUseDeposit;
    }
}