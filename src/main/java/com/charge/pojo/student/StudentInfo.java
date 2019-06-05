package com.charge.pojo.student;

import java.io.Serializable;
import java.util.Date;

public class StudentInfo implements Serializable {
    /**
     * student_info.id 
     */
    private Integer id;

    /**
     * student_info.name 
     */
    private String name;

    /**
     * student_info.sex 
     */
    private Integer sex;

    /**
     * student_info.year 
     */
    private Integer year;

    /**
     * student_info.month 
     */
    private Integer month;

    /**
     * student_info.day 
     */
    private Integer day;

    /**
     * student_info.age 
     */
    private Integer age;

    /**
     * student_info.parent_name 
     */
    private String parentName;

    /**
     * student_info.relation 
     */
    private String relation;

    /**
     * student_info.mobile 
     */
    private String mobile;

    /**
     * student_info.address 
     */
    private String address;

    /**
     * student_info.is_graduate 
     */
    private Boolean graduate = false;

    /**
     * student_info.creat_time 
     */
    private Integer creatTime;

    /**
     * student_info.modify_time 
     */
    private Date modifyTime;

    /**
     * student_info.is_deleted 
     */
    private Boolean deleted;

    /**
     * @return student_info.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for student_info.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return student_info.name 
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the value for student_info.name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return student_info.sex 
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex the value for student_info.sex 
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return student_info.year 
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year the value for student_info.year 
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return student_info.month 
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month the value for student_info.month 
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return student_info.day 
     */
    public Integer getDay() {
        return day;
    }

    /**
     * @param day the value for student_info.day 
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * @return student_info.age 
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the value for student_info.age 
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return student_info.parent_name 
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName the value for student_info.parent_name 
     */
    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }

    /**
     * @return student_info.relation 
     */
    public String getRelation() {
        return relation;
    }

    /**
     * @param relation the value for student_info.relation 
     */
    public void setRelation(String relation) {
        this.relation = relation == null ? null : relation.trim();
    }

    /**
     * @return student_info.mobile 
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the value for student_info.mobile 
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return student_info.address 
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the value for student_info.address 
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return student_info.is_graduate 
     */
    public Boolean getGraduate() {
        return graduate;
    }

    /**
     * @param graduate the value for student_info.is_graduate 
     */
    public void setGraduate(Boolean graduate) {
        this.graduate = graduate;
    }

    /**
     * @return student_info.creat_time 
     */
    public Integer getCreatTime() {
        return creatTime;
    }

    /**
     * @param creatTime the value for student_info.creat_time 
     */
    public void setCreatTime(Integer creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * @return student_info.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for student_info.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return student_info.is_deleted 
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for student_info.is_deleted 
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}