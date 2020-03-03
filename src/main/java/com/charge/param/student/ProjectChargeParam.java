package com.charge.param.student;

import java.io.Serializable;

public class ProjectChargeParam implements Serializable {

    private Integer studentProjectId;
    private Double projectAmount;
    private Double discount;


    public Integer getStudentProjectId() {
        return studentProjectId;
    }

    public void setStudentProjectId(Integer studentProjectId) {
        this.studentProjectId = studentProjectId;
    }

    public Double getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(Double projectAmount) {
        this.projectAmount = projectAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
