package com.charge.mapper.school;

import com.charge.pojo.school.GradeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GradeInfoMapper {
    int insert(GradeInfo record);

    int insertSelective(GradeInfo record);

    int updateByPrimaryKeySelective(GradeInfo record);

    int updateByPrimaryKey(GradeInfo record);

    void deleteById(Integer id);

    int countGradeInfo(Map<String, Object> param);
    List<GradeInfo> queryGradeInfoPageList(Map<String, Object> param);

    GradeInfo getGradeInfoById(Integer id);
    GradeInfo getGradeInfoByLevel(Integer level);
    GradeInfo getGradeInfoByName(@Param("gradeName") String gradeName);

    int getMaxGradeLevel();
}