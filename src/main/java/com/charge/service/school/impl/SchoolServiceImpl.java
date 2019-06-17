package com.charge.service.school.impl;

import com.charge.mapper.school.ClassInfoMapper;
import com.charge.mapper.school.GradeInfoMapper;
import com.charge.param.school.ClassSearchParam;
import com.charge.param.school.GradeSearchParam;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.school.GradeInfo;
import com.charge.service.school.SchoolService;
import com.charge.util.DateUtil;
import com.charge.util.RequestParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private GradeInfoMapper gradeInfoMapper;

    @Autowired
    private ClassInfoMapper classInfoMapper;


    public void insertSelectiveGradeInfo(GradeInfo gradeInfo) {
        gradeInfo.setCreateTime(DateUtil.getCurrentTimespan());
        gradeInfoMapper.insertSelective(gradeInfo);
    }

    public void insertSelectiveClassInfo(ClassInfo classInfo) {
        classInfo.setCreatTime(DateUtil.getCurrentTimespan());
        classInfoMapper.insertSelective(classInfo);
    }

    public void updateClassInfo(ClassInfo classInfo) {
        classInfoMapper.updateByPrimaryKeySelective(classInfo);
    }

    public void updateGradeInfo(GradeInfo gradeInfo) {
        gradeInfoMapper.updateByPrimaryKeySelective(gradeInfo);
    }

    public ClassInfo getClassInfoById(Integer classId) {
        return classInfoMapper.getClassInfoById(classId);
    }

    public List<ClassInfo> getClassInfoByName(String className) {
        return classInfoMapper.getClassInfoByName(className);
    }

    public ClassInfo getByClassNameGradeId(String className, Integer gradeId) {
        return classInfoMapper.getByClassNameGradeId(className, gradeId);
    }

    public GradeInfo getGradeInfoById(Integer gradeId) {
        return gradeInfoMapper.getGradeInfoById(gradeId);
    }

    public GradeInfo getGradeInfoByName(String gradeName) {
        return gradeInfoMapper.getGradeInfoByName(gradeName);
    }

    public int countGrade(GradeSearchParam gradeSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(gradeSearchParam.getCurrentPage(), gradeSearchParam.getPageSize(), gradeSearchParam);
        return gradeInfoMapper.countGradeInfo(paramMap);
    }

    public List<GradeInfo> queryGradeList(GradeSearchParam gradeSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(gradeSearchParam.getCurrentPage(), gradeSearchParam.getPageSize(), gradeSearchParam);
        return gradeInfoMapper.queryGradeInfoPageList(paramMap);
    }

    public int countClass(ClassSearchParam classSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(classSearchParam.getCurrentPage(), classSearchParam.getPageSize(), classSearchParam);
        return classInfoMapper.countClassInfo(paramMap);
    }

    public List<ClassInfo> queryClassList(ClassSearchParam classSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(classSearchParam.getCurrentPage(), classSearchParam.getPageSize(), classSearchParam);
        return classInfoMapper.queryClassInfoPageList(paramMap);
    }
}