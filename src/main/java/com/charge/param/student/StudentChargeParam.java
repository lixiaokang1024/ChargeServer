package com.charge.param.student;


import java.io.Serializable;
import java.util.List;

/**
 * 学生缴费参数
 */
public class StudentChargeParam implements Serializable {

    private Integer studentId;

    private Double chargeAmount;//缴费金额

    private Integer isUseDeposit;//是否使用预缴费金额

    private List<ProjectChargeParam> projectChargeParamList;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Integer getIsUseDeposit() {
        return isUseDeposit;
    }

    public void setIsUseDeposit(Integer isUseDeposit) {
        this.isUseDeposit = isUseDeposit;
    }

    public List<ProjectChargeParam> getProjectChargeParamList() {
        return projectChargeParamList;
    }

    public void setProjectChargeParamList(List<ProjectChargeParam> projectChargeParamList) {
        this.projectChargeParamList = projectChargeParamList;
    }
}