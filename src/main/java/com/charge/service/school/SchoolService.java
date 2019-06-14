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
    void updateClassInfo(ClassInfo classInfo);
    void updateGradeInfo(GradeInfo gradeInfo);

    ClassInfo getClassInfoById(Integer classId);
    List<ClassInfo> getClassInfoByName(String className);
    ClassInfo getByClassNameGradeId(String className, Integer gradeId);
    GradeInfo getGradeInfoById(Integer gradeId);
    GradeInfo getGradeInfoByName(String gradeName);

    int countGrade(GradeSearchParam gradeSearchParam);
    List<GradeInfo> queryGradeList(GradeSearchParam gradeSearchParam);

    int countClass(ClassSearchParam classSearchParam);
    List<ClassInfo> queryClassList(ClassSearchParam classSearchParam);

}