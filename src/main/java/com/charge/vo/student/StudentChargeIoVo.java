package com.charge.vo.student;

import java.io.Serializable;

public class StudentChargeIoVo implements Serializable {
    private Integer id;
    private Integer studentChargeInfoId;
    private String receiptId;
    private Integer studentId;
    private String studentName;
    private String className;
    private String gradeName;
    private String chargeTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentChargeInfoId() {
        return studentChargeInfoId;
    }

    public void setStudentChargeInfoId(Integer studentChargeInfoId) {
        this.studentChargeInfoId = studentChargeInfoId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}