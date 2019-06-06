package com.charge.service.school;

import com.charge.param.school.ClassSearchParam;
import com.charge.param.school.GradeSearchParam;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.school.GradeInfo;
import com.charge.pojo.student.StudentInfo;

import java.util.List;

public interface SchoolService {

    void insertSelectiveGradeInfo(GradeInfo gradeInfo);
    void insertSelectiveClassInfo(ClassInfo classInfo);

    int countGrade(GradeSearchParam gradeSearchParam);
    List<GradeInfo> queryGradeList(GradeSearchParam gradeSearchParam);

    int countClass(ClassSearchParam classSearchParam);
    List<ClassInfo> queryClassList(ClassSearchParam classSearchParam);

}