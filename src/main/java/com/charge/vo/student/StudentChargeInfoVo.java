package com.charge.vo.student;

import java.io.Serializable;

public class StudentChargeInfoVo implements Serializable {

    private Integer studentId;
    private String studentName;
    private Double chargeAmount;//应缴费金额
    private Double actualChargeAmount;//实际缴费金额
    private Double overDueAmount;//欠费金额
    private Double prepaymentAmount;//预缴费剩余金额
    private Double deposit;//押金

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Double getActualChargeAmount() {
        return actualChargeAmount;
    }

    public void setActualChargeAmount(Double actualChargeAmount) {
        this.actualChargeAmount = actualChargeAmount;
    }

    public Double getOverDueAmount() {
        return actualChargeAmount - chargeAmount;
    }

    public void setOverDueAmount(Double overDueAmount) {
        this.overDueAmount = overDueAmount;
    }

    public Double getPrepaymentAmount() {
        return prepaymentAmount;
    }

    public void setPrepaymentAmount(Double prepaymentAmount) {
        this.prepaymentAmount = prepaymentAmount;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
}