package com.charge.pojo.student;

import java.math.BigDecimal;
import java.util.Date;

public class StudentChargeIo {
    private Integer id;

    private Integer studentChargeInfoId;

    private Integer studentId;

    private String receiptId;

    private BigDecimal chargeAmount;

    private BigDecimal actualChargeAmount;

    private BigDecimal useDepositAmount;

    private BigDecimal customOfferAmount;

    private Integer actualChargeTime;

    private Integer chargeType;

    private Integer payType;

    private Integer createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentChargeInfoId() {
        return studentChargeInfoId;
    }

    public void setStudentChargeInfoId(Integer studentChargeInfoId) {
        this.studentChargeInfoId = studentChargeInfoId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId == null ? null : receiptId.trim();
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public BigDecimal getActualChargeAmount() {
        return actualChargeAmount;
    }

    public void setActualChargeAmount(BigDecimal actualChargeAmount) {
        this.actualChargeAmount = actualChargeAmount;
    }

    public BigDecimal getUseDepositAmount() {
        return useDepositAmount;
    }

    public void setUseDepositAmount(BigDecimal useDepositAmount) {
        this.useDepositAmount = useDepositAmount;
    }

    public BigDecimal getCustomOfferAmount() {
        return customOfferAmount;
    }

    public void setCustomOfferAmount(BigDecimal customOfferAmount) {
        this.customOfferAmount = customOfferAmount;
    }

    public Integer getActualChargeTime() {
        return actualChargeTime;
    }

    public void setActualChargeTime(Integer actualChargeTime) {
        this.actualChargeTime = actualChargeTime;
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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}