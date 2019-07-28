package com.charge.param.charge;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;

public class PayProjectIoSearchParam extends BaseDataPaginationParam implements Serializable {

    private Integer projectType;
    private Integer payProjectId;
    private String projectName;
    private String payTimeBegin;
    private String payTimeEnd;

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getPayProjectId() {
        return payProjectId;
    }

    public void setPayProjectId(Integer payProjectId) {
        this.payProjectId = payProjectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPayTimeBegin() {
        return payTimeBegin;
    }

    public void setPayTimeBegin(String payTimeBegin) {
        this.payTimeBegin = payTimeBegin;
    }

    public String getPayTimeEnd() {
        return payTimeEnd;
    }

    public void setPayTimeEnd(String payTimeEnd) {
        this.payTimeEnd = payTimeEnd;
    }
}