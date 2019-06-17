package com.charge.pojo.charge;

import java.io.Serializable;
import java.util.Date;

public class PayProjectIo implements Serializable {
    /**
     * pay_project_io.id 
     */
    private Integer id;

    /**
     * pay_project_io.pay_project_id
     */
    private Integer payProjectId;
    private String payProjectName;

    /**
     * pay_project_io.pay_amount 
     */
    private Double payAmount;

    /**
     * pay_project_io.pay_time 
     */
    private Integer payTime;

    /**
     * pay_project_io.remark 
     */
    private String remark;

    /**
     * pay_project_io.create_time 
     */
    private Integer createTime;

    /**
     * pay_project_io.modify_time 
     */
    private Date modifyTime;

    /**
     * pay_project_io.is_deleted 
     */
    private Integer deleted;

    /**
     * @return pay_project_io.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for pay_project_io.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayProjectName() {
        return payProjectName;
    }

    public void setPayProjectName(String payProjectName) {
        this.payProjectName = payProjectName;
    }

    /**
     * @return pay_project_io.pay_project_id 
     */
    public Integer getPayProjectId() {
        return payProjectId;
    }

    /**
     * @param payProjectId the value for pay_project_io.pay_project_id 
     */
    public void setPayProjectId(Integer payProjectId) {
        this.payProjectId = payProjectId;
    }

    /**
     * @return pay_project_io.pay_amount 
     */
    public Double getPayAmount() {
        return payAmount;
    }

    /**
     * @param payAmount the value for pay_project_io.pay_amount 
     */
    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * @return pay_project_io.pay_time 
     */
    public Integer getPayTime() {
        return payTime;
    }

    /**
     * @param payTime the value for pay_project_io.pay_time 
     */
    public void setPayTime(Integer payTime) {
        this.payTime = payTime;
    }

    /**
     * @return pay_project_io.remark 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the value for pay_project_io.remark 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return pay_project_io.create_time 
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the value for pay_project_io.create_time 
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * @return pay_project_io.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for pay_project_io.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return pay_project_io.is_deleted 
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for pay_project_io.is_deleted 
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}