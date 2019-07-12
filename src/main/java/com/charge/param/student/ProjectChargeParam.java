package com.charge.param.student;

import java.io.Serializable;

public class ProjectChargeParam implements Serializable {

    private Integer projectId;
    private Double projectAmount;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Double getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(Double projectAmount) {
        this.projectAmount = projectAmount;
    }
}
