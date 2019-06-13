package com.charge.mapper.charge;

import com.charge.pojo.charge.ChargeProject;
import java.util.List;
import java.util.Map;

public interface ChargeProjectMapper {
    int insert(ChargeProject record);

    int insertSelective(ChargeProject record);

    int updateByPrimaryKeySelective(ChargeProject record);

    int updateByPrimaryKey(ChargeProject record);

    ChargeProject getChargeProjectById(Integer id);
    int countChargeProject(Map<String, Object> param);
    List<ChargeProject> queryChargeProjectPageList(Map<String, Object> param);


}