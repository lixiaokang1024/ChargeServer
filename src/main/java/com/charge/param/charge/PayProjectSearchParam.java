package com.charge.param.charge;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;

public class PayProjectSearchParam extends BaseDataPaginationParam implements Serializable {

    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}