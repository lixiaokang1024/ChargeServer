package com.charge.mapper.charge;

import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.charge.PayProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ChargeProjectMapper {
    int insert(ChargeProject record);

    int insertSelective(ChargeProject record);

    int updateByPrimaryKeySelective(ChargeProject record);

    int updateByPrimaryKey(ChargeProject record);

    void deleteChargeProjectById(Integer id);

    ChargeProject getChargeProjectById(Integer id);
    ChargeProject getChargeProjectByName(@Param("projectName") String projectName, @Param("gradeId") Integer gradeId);
    int countChargeProject(Map<String, Object> param);
    List<ChargeProject> queryChargeProjectPageList(Map<String, Object> param);

}