package com.charge.pojo.charge;

import java.io.Serializable;
import java.util.Date;

public class PayProject implements Serializable {
    /**
     * pay_project.id 
     */
    private Integer id;

    /**
     * pay_project.project_name 
     */
    private String projectName;

    /**
     * pay_project.create_time 
     */
    private Integer createTime;

    /**
     * pay_project.modify_time 
     */
    private Date modifyTime;

    /**
     * pay_project.is_deleted 
     */
    private Integer deleted;

    /**
     * @return pay_project.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for pay_project.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return pay_project.project_name 
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the value for pay_project.project_name 
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * @return pay_project.create_time 
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the value for pay_project.create_time 
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * @return pay_project.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for pay_project.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return pay_project.is_deleted 
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for pay_project.is_deleted 
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}