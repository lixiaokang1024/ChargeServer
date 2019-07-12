package com.charge.param.charge;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;

public class PayProjectSearchParam extends BaseDataPaginationParam implements Serializable {

    private String projectName;
    private Integer projectType;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }
}