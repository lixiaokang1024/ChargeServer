package com.charge.pojo.school;

import java.io.Serializable;
import java.util.Date;

public class ClassInfo implements Serializable {
    /**
     * class_info.id 
     */
    private Integer id;

    /**
     * class_info.name 
     */
    private String name;

    private Integer gradeId;
    private String gradeName;

    /**
     * class_info.creat_time 
     */
    private Integer creatTime;

    /**
     * class_info.modify_time 
     */
    private Date modifyTime;

    /**
     * class_info.is_deleted 
     */
    private int deleted = 0;

    /**
     * @return class_info.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for class_info.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return class_info.name 
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the value for class_info.name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getGradeId() {
        return gradeId;
    }

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
     * @return class_info.creat_time 
     */
    public Integer getCreatTime() {
        return creatTime;
    }

    /**
     * @param creatTime the value for class_info.creat_time 
     */
    public void setCreatTime(Integer creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * @return class_info.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for class_info.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return class_info.is_deleted 
     */
    public int getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for class_info.is_deleted 
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}