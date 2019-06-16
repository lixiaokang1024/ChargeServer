package com.charge.param.student;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;
import java.util.Date;

public class StudentSearchParam extends BaseDataPaginationParam implements Serializable {
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
    private String mobile;
    private Integer graduate;
    private String creatTimeBegin;
    private String creatTimeEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGraduate() {
        return graduate;
    }

    public void setGraduate(Integer graduate) {
        this.graduate = graduate;
    }

    public String getCreatTimeBegin() {
        return creatTimeBegin;
    }

    public void setCreatTimeBegin(String creatTimeBegin) {
        this.creatTimeBegin = creatTimeBegin;
    }

    public String getCreatTimeEnd() {
        return creatTimeEnd;
    }

    public void setCreatTimeEnd(String creatTimeEnd) {
        this.creatTimeEnd = creatTimeEnd;
    }
}