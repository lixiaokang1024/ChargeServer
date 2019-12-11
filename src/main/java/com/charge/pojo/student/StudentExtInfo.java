package com.charge.pojo.student;

import java.io.Serializable;
import java.util.Date;

public class StudentExtInfo implements Serializable {
    /**
     * student_ext_info.id 
     */
    private Integer id;

    /**
     * student_ext_info.student_id 
     */
    private Integer studentId;

    /**
     * student_ext_info.is_graduate 是否毕业(0：否  1：是)
     */
    private Integer graduate;

    /**
     * student_ext_info.admission_time 入学时间
     */
    private Integer admissionTime;

    /**
     * student_ext_info.graduate_time 毕业时间
     */
    private Integer graduateTime;

    /**
     * student_ext_info.prepayment_amount 预缴费剩余金额
     */
    private Double prepaymentAmount;

    /**
     * student_ext_info.deposit 押金
     */
    private Double deposit;

    /**
     * 就读方式
     */
    private String studyType;

    /**
     * 是否独生子女
     */
    private String isOnlyChild;

    /**
     * student_ext_info.create_time 
     */
    private Integer createTime;

    /**
     * student_ext_info.modify_time 
     */
    private Date modifyTime;

    /**
     * student_ext_info.is_deleted 
     */
    private Integer deleted;

    /**
     * @return student_ext_info.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for student_ext_info.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return student_ext_info.student_id 
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the value for student_ext_info.student_id 
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * @return student_ext_info.is_graduate 是否毕业(0：否  1：是)
     */
    public Integer getGraduate() {
        return graduate;
    }

    /**
     * @param graduate the value for student_ext_info.is_graduate 是否毕业(0：否  1：是)
     */
    public void setGraduate(Integer graduate) {
        this.graduate = graduate;
    }

    /**
     * @return student_ext_info.admission_time 入学时间
     */
    public Integer getAdmissionTime() {
        return admissionTime;
    }

    /**
     * @param admissionTime the value for student_ext_info.admission_time 入学时间
     */
    public void setAdmissionTime(Integer admissionTime) {
        this.admissionTime = admissionTime;
    }

    /**
     * @return student_ext_info.graduate_time 毕业时间
     */
    public Integer getGraduateTime() {
        return graduateTime;
    }

    /**
     * @param graduateTime the value for student_ext_info.graduate_time 毕业时间
     */
    public void setGraduateTime(Integer graduateTime) {
        this.graduateTime = graduateTime;
    }

    /**
     * @return student_ext_info.prepayment_amount 预缴费剩余金额
     */
    public Double getPrepaymentAmount() {
        return prepaymentAmount;
    }

    /**
     * @param prepaymentAmount the value for student_ext_info.prepayment_amount 预缴费剩余金额
     */
    public void setPrepaymentAmount(Double prepaymentAmount) {
        this.prepaymentAmount = prepaymentAmount;
    }

    /**
     * @return student_ext_info.deposit 押金
     */
    public Double getDeposit() {
        return deposit;
    }

    /**
     * @param deposit the value for student_ext_info.deposit 押金
     */
    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    /**
     * @return student_ext_info.create_time 
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the value for student_ext_info.create_time 
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * @return student_ext_info.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for student_ext_info.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return student_ext_info.is_deleted 
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for student_ext_info.is_deleted 
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getIsOnlyChild() {
        return isOnlyChild;
    }

    public void setIsOnlyChild(String isOnlyChild) {
        this.isOnlyChild = isOnlyChild;
    }
}