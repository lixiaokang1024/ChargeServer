package com.charge.vo.student;

import com.charge.enums.charge.ChargeStatus;
import com.charge.enums.charge.ChargeType;

import java.io.Serializable;

public class StudentChargeInfoVo implements Serializable {

    private Integer id;
    private Integer studentId;
    private String studentName;
    private Integer chargeProjectId;
    private String chargeProjectName;
    private Double chargeAmount;
    private Integer chargeTime;
    private String chargeTimeStr;
    private Integer payType;
    private String payTypeStr;//缴费方式(0:现金 1:预缴费扣除 2:其他)
    private Integer status;
    private String statusStr;//缴费状态(0:未缴费 1:已缴费)
    private Double prepaymentAmount;//预缴费剩余金额
    private Double deposit;//押金

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getChargeProjectId() {
        return chargeProjectId;
    }

    public void setChargeProjectId(Integer chargeProjectId) {
        this.chargeProjectId = chargeProjectId;
    }

    public String getChargeProjectName() {
        return chargeProjectName;
    }

    public void setChargeProjectName(String chargeProjectName) {
        this.chargeProjectName = chargeProjectName;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Integer getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Integer chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getChargeTimeStr() {
        return chargeTimeStr;
    }

    public void setChargeTimeStr(String chargeTimeStr) {
        this.chargeTimeStr = chargeTimeStr;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeStr() {
        return ChargeType.getEnum(payType).getValue();
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        return ChargeStatus.getEnum(status).getValue();
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
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