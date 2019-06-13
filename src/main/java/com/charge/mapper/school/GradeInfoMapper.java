package com.charge.mapper.school;

import com.charge.pojo.school.GradeInfo;

import java.util.List;
import java.util.Map;

public interface GradeInfoMapper {
    int insert(GradeInfo record);

    int insertSelective(GradeInfo record);

    int updateByPrimaryKeySelective(GradeInfo record);

    int updateByPrimaryKey(GradeInfo record);

    int countGradeInfo(Map<String, Object> param);
    List<GradeInfo> queryGradeInfoPageList(Map<String, Object> param);

    GradeInfo getGradeInfoById(Integer id);
}