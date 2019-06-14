package com.charge.param.student;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;

public class StudentChargeInfoSearchParam extends BaseDataPaginationParam implements Serializable {

    private String name;

    private String mobile;

    private Boolean graduate;

    private Integer chargeStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getGraduate() {
        return graduate;
    }

    public void setGraduate(Boolean graduate) {
        this.graduate = graduate;
    }

    public Integer getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(Integer chargeStatus) {
        this.chargeStatus = chargeStatus;
    }
}