package com.charge.param.student;

import com.charge.param.common.BaseDataPaginationParam;

import java.io.Serializable;

public class StudentClassInfoSearchParam extends BaseDataPaginationParam implements Serializable {

    private String studentName;
    private String className;
    private Integer classId;
    private String gradeName;
    private Integer isGraduate = 0;
    private String admissionTimeBegin;//入学时间
    private String admissionTimeEnd;

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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getIsGraduate() {
        return isGraduate;
    }

    public void setIsGraduate(Integer isGraduate) {
        this.isGraduate = isGraduate;
    }

    public String getAdmissionTimeBegin() {
        return admissionTimeBegin;
    }

    public void setAdmissionTimeBegin(String admissionTimeBegin) {
        this.admissionTimeBegin = admissionTimeBegin;
    }

    public String getAdmissionTimeEnd() {
        return admissionTimeEnd;
    }

    public void setAdmissionTimeEnd(String admissionTimeEnd) {
        this.admissionTimeEnd = admissionTimeEnd;
    }
}