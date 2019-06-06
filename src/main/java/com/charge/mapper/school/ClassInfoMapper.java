package com.charge.mapper.school;

import com.charge.pojo.school.ClassInfo;
import java.util.List;
import java.util.Map;

public interface ClassInfoMapper {
    int insert(ClassInfo record);

    int insertSelective(ClassInfo record);

    int updateByPrimaryKeySelective(ClassInfo record);

    int updateByPrimaryKey(ClassInfo record);

    int countClassInfo(Map<String, Object> param);
    List<ClassInfo> queryClassInfoPageList(Map<String, Object> param);
}