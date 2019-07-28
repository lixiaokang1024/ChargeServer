package com.charge.mapper.school;

import com.charge.pojo.school.ClassInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClassInfoMapper {
    int insert(ClassInfo record);

    int insertSelective(ClassInfo record);

    int updateByPrimaryKeySelective(ClassInfo record);

    int updateByPrimaryKey(ClassInfo record);

    void deleteById(Integer id);

    ClassInfo getClassInfoById(Integer id);
    List<ClassInfo> getClassInfoByName(String className);
    ClassInfo getByClassNameGradeId(@Param("className") String className, @Param("gradeId") Integer gradeId);

    int countClassInfo(Map<String, Object> param);
    List<ClassInfo> queryClassInfoPageList(Map<String, Object> param);
}