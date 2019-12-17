package com.charge.pojo.student;

import java.io.Serializable;
import java.util.Date;

public class StudentChargeInfo implements Serializable {
    /**
     * student_charge_info.id 
     */
    private Integer id;

    /**
     * student_charge_info.student_id 
     */
    private Integer studentId;

    /**
     * student_charge_info.charge_project_id 
     */
    private Integer chargeProjectId;

    /**
     * student_charge_info.charge_amount 
     */
    private Double chargeAmount;

    private Double actualChargeAmount;//实际缴费金额

    private Double discount;//折扣

    private Double useDepositAmount;//使用预缴费金额

    /**
     * student_charge_info.charge_time 应缴费日期
     */
    private Integer chargeTime;

    private Integer actualChargeTime;

    /**
     * student_charge_info.pay_type 缴费方式(0:现金 1:预缴费扣除 2:其他)
     */
    private Integer payType;

    /**
     * student_charge_info.status 缴费状态(0:未缴费 1:已缴费)
     */
    private Integer status;

    /**
     * student_charge_info.create_time 
     */
    private Integer createTime;

    /**
     * student_charge_info.modify_time 
     */
    private Date modifyTime;

    /**
     * student_charge_info.is_deleted 
     */
    private Integer deleted;

    /**
     * @return student_charge_info.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for student_charge_info.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return student_charge_info.student_id 
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the value for student_charge_info.student_id 
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * @return student_charge_info.charge_project_id 
     */
    public Integer getChargeProjectId() {
        return chargeProjectId;
    }

    /**
     * @param chargeProjectId the value for student_charge_info.charge_project_id 
     */
    public void setChargeProjectId(Integer chargeProjectId) {
        this.chargeProjectId = chargeProjectId;
    }

    /**
     * @return student_charge_info.charge_amount 
     */
    public Double getChargeAmount() {
        return chargeAmount;
    }

    /**
     * @param chargeAmount the value for student_charge_info.charge_amount 
     */
    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
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

    public Integer getActualChargeTime() {
        return actualChargeTime;
    }

    public void setActualChargeTime(Integer actualChargeTime) {
        this.actualChargeTime = actualChargeTime;
    }

    /**
     * @return student_charge_info.charge_time 缴费日期
     */
    public Integer getChargeTime() {
        return chargeTime;
    }

    /**
     * @param chargeTime the value for student_charge_info.charge_time 缴费日期
     */
    public void setChargeTime(Integer chargeTime) {
        this.chargeTime = chargeTime;
    }

    /**
     * @return student_charge_info.pay_type 缴费方式(0:现金 1:预缴费扣除 2:其他)
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * @param payType the value for student_charge_info.pay_type 缴费方式(0:现金 1:预缴费扣除 2:其他)
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * @return student_charge_info.status 缴费状态(0:未缴费 1:已缴费)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the value for student_charge_info.status 缴费状态(0:未缴费 1:已缴费)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return student_charge_info.create_time 
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the value for student_charge_info.create_time 
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * @return student_charge_info.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for student_charge_info.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return student_charge_info.is_deleted 
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for student_charge_info.is_deleted 
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}