package com.charge.param.school;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;

public class ClassSearchParam extends BaseDataPaginationParam implements Serializable {

    private String className;
    private Integer gradeId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
}