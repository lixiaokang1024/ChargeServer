package com.charge.vo.charge;

import java.io.Serializable;
import java.util.Date;

public class ChargeProjectVo implements Serializable {
    /**
     * charge_project.id 
     */
    private Integer id;

    /**
     * charge_project.project_name 收费项目
     */
    private String projectName;

    /**
     * charge_project.amount 
     */
    private Double amount;

    /**
     * charge_project.grade_id 所属年级
     */
    private Integer gradeId;
    private String gradeName;

    /**
     * charge_project.create_time 
     */
    private Integer createTime;

    /**
     * charge_project.modify_time 
     */
    private Date modifyTime;

    /**
     * charge_project.is_deleted 
     */
    private Integer deleted;

    /**
     * @return charge_project.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for charge_project.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return charge_project.project_name 收费项目
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the value for charge_project.project_name 收费项目
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * @return charge_project.amount 
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the value for charge_project.amount 
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return charge_project.grade_id 所属年级
     */
    public Integer getGradeId() {
        return gradeId;
    }

    /**
     * @param gradeId the value for charge_project.grade_id 所属年级
     */
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    /**
     * @return charge_project.create_time 
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the value for charge_project.create_time 
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * @return charge_project.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for charge_project.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return charge_project.is_deleted 
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for charge_project.is_deleted 
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}