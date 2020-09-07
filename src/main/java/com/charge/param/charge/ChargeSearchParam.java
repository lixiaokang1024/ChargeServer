package com.charge.param.charge;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;

public class ChargeSearchParam extends BaseDataPaginationParam implements Serializable {

    private String projectName;
    private Integer studentId;
    private Integer gradeId;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
}