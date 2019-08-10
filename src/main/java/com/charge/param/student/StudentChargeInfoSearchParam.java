package com.charge.param.student;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;
import java.util.List;

public class StudentChargeInfoSearchParam extends BaseDataPaginationParam implements Serializable {

    private String name;

    private Integer gradeId;

    private Integer classId;

    private String mobile;

    private Boolean graduate;

    private Integer chargeProjectId;

    private Integer status;

    private List<Integer> chargeStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Boolean getGraduate() {
        return graduate;
    }

    public void setGraduate(Boolean graduate) {
        this.graduate = graduate;
    }

    public Integer getChargeProjectId() {
        return chargeProjectId;
    }

    public void setChargeProjectId(Integer chargeProjectId) {
        this.chargeProjectId = chargeProjectId;
    }

    public List<Integer> getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(List<Integer> chargeStatus) {
        this.chargeStatus = chargeStatus;
    }
}