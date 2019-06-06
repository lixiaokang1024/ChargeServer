package com.charge.param.school;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;

public class GradeSearchParam extends BaseDataPaginationParam implements Serializable {

    private String gradeName;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}