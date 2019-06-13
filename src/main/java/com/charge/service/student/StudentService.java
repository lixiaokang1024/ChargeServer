package com.charge.service.student;

import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.student.StudentExtInfo;
import com.charge.pojo.student.StudentInfo;

import java.util.List;

public interface StudentService {

    void insertSelective(StudentInfo studentInfo, StudentExtInfo studentExtInfo);

    int countStudent(StudentSearchParam studentSearchParam);
    List<StudentInfo> queryStudentList(StudentSearchParam studentSearchParam);

}