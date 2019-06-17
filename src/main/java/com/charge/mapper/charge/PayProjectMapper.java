package com.charge.mapper.charge;

import com.charge.pojo.charge.PayProject;

import java.util.List;
import java.util.Map;

public interface PayProjectMapper {
    int insert(PayProject record);

    int insertSelective(PayProject record);

    int updateByPrimaryKeySelective(PayProject record);

    int updateByPrimaryKey(PayProject record);

    PayProject getPayProjectById(Integer id);

    int countPayProject(Map<String, Object> param);
    List<PayProject> queryPayProjectPageList(Map<String, Object> param);
}