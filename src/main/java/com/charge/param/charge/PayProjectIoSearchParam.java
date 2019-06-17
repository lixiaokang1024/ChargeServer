package com.charge.param.charge;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;

public class PayProjectIoSearchParam extends BaseDataPaginationParam implements Serializable {

    private String projectName;
    private String payTimeBegin;
    private String payTimeEnd;

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