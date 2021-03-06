package com.charge.vo.student;

import com.charge.enums.charge.ChargeStatus;
import com.charge.enums.charge.ChargeType;
import com.charge.util.DateUtil;

import java.io.Serializable;

public class StudentChargeInfoDetailVo implements Serializable {

    private Integer id;
    private Integer studentId;
    private String studentName;
    private String className;
    private String gradeName;
    private String receiptId;
    private Integer chargeProjectId;
    private String chargeProjectName;
    private Double chargeAmount;
    private Double chargeCoefficient;
    private Double actualChargeAmount;//实际缴费金额
    private Double discount;//折扣
    private Double useDepositAmount;//使用预缴费金额
    private Double leftDepositAmount;
    private Double customOfferAmount;
    private Integer chargeTime;//应缴费时间
    private String chargeTimeStr;
    private Integer actualChargeTime;//实际缴费时间
    private String actualChargeTimeStr;
    private Integer chargeType;
    private Integer payType;
    private String payTypeStr;//缴费方式(0:现金 1:预缴费扣除 2:其他)
    private Integer status;
    private String statusStr;//缴费状态(0:未缴费 1:已缴费)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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

    public Double getChargeCoefficient() {
        return chargeCoefficient;
    }

    public void setChargeCoefficient(Double chargeCoefficient) {
        this.chargeCoefficient = chargeCoefficient;
    }

    public Double getLeftDepositAmount() {
        return leftDepositAmount;
    }

    public void setLeftDepositAmount(Double leftDepositAmount) {
        this.leftDepositAmount = leftDepositAmount;
    }

    public Integer getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Integer chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getChargeTimeStr() {
        return DateUtil.getDatetime1(chargeTime);
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

    public Double getActualChargeAmount() {
        return actualChargeAmount;
    }

    public void setActualChargeAmount(Double actualChargeAmount) {
        this.actualChargeAmount = actualChargeAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getUseDepositAmount() {
        return useDepositAmount;
    }

    public void setUseDepositAmount(Double useDepositAmount) {
        this.useDepositAmount = useDepositAmount;
    }

    public Double getCustomOfferAmount() {
        return customOfferAmount;
    }

    public void setCustomOfferAmount(Double customOfferAmount) {
        this.customOfferAmount = customOfferAmount;
    }

    public Integer getActualChargeTime() {
        return actualChargeTime;
    }

    public void setActualChargeTime(Integer actualChargeTime) {
        this.actualChargeTime = actualChargeTime;
    }

    public String getActualChargeTimeStr() {
        return DateUtil.getDatetime(actualChargeTime);
    }

    public void setActualChargeTimeStr(String actualChargeTimeStr) {
        this.actualChargeTimeStr = actualChargeTimeStr;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }
}