package com.charge.pojo.student;

import java.io.Serializable;
import java.util.Date;

public class StudentClassInfo implements Serializable {
    /**
     * student_class_info.id 
     */
    private Integer id;

    /**
     * student_class_info.student_id 
     */
    private Integer studentId;

    /**
     * student_class_info.class_id 
     */
    private Integer classId;

    /**
     * student_class_info.grade_id 
     */
    private Integer gradeId;

    /**
     * student_class_info.is_graduate 
     */
    private Integer graduate;

    /**
     * student_class_info.create_time 
     */
    private Integer createTime;

    /**
     * student_class_info.modify_time 
     */
    private Date modifyTime;

    /**
     * student_class_info.is_deleted 
     */
    private int deleted = 0;

    /**
     * @return student_class_info.id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for student_class_info.id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return student_class_info.student_id 
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the value for student_class_info.student_id 
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * @return student_class_info.class_id 
     */
    public Integer getClassId() {
        return classId;
    }

    /**
     * @param classId the value for student_class_info.class_id 
     */
    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    /**
     * @return student_class_info.grade_id 
     */
    public Integer getGradeId() {
        return gradeId;
    }

    /**
     * @param gradeId the value for student_class_info.grade_id 
     */
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * @return student_class_info.is_graduate 
     */
    public Integer getGraduate() {
        return graduate;
    }

    /**
     * @param graduate the value for student_class_info.is_graduate 
     */
    public void setGraduate(Integer graduate) {
        this.graduate = graduate;
    }

    /**
     * @return student_class_info.create_time 
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the value for student_class_info.create_time 
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * @return student_class_info.modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the value for student_class_info.modify_time 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return student_class_info.is_deleted 
     */
    public int getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the value for student_class_info.is_deleted 
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}