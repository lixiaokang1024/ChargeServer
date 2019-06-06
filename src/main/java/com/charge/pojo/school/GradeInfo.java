package com.charge.pojo.school;

import java.io.Serializable;
import java.util.Date;

public class GradeInfo implements Serializable {
    /**
     * grade_info.id 
     */
    private Integer id;

    /**
     * grade_info.name 
     */
    private String name;

    /**
     * grade_info.level 
     */
    private Integer level;

    /**
     * grade_info.create_time 
     */
    private Integer createTime;

    /**
     * grade_info.modify_time 
     */
    private Date modifyTime;

    /**
     * grade_info.is_deleted 
     */
    private int deleted = 0;

    /**
     * @return grade_info.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for grade_info.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return grade_info.name 
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the value for grade_info.name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return grade_info.level 
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level the value for grade_info.level 
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return grade_info.create_time 
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the value for grade_info.create_time 
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * @return grade_info.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for grade_info.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return grade_info.is_deleted 
     */
    public int getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for grade_info.is_deleted 
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}