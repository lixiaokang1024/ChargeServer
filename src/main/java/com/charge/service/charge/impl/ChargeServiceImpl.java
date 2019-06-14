package com.charge.service.charge.impl;

import com.charge.mapper.charge.ChargeProjectMapper;
import com.charge.param.charge.ChargeSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.service.charge.ChargeService;
import com.charge.util.DateUtil;
import com.charge.util.RequestParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChargeServiceImpl implements ChargeService {

    @Autowired
    private ChargeProjectMapper chargeProjectMapper;

    public void insertSelective(ChargeProject chargeProject) {
        chargeProject.setCreateTime(DateUtil.getCurrentTimespan());
        chargeProjectMapper.insertSelective(chargeProject);
    }

    public void updateChargeProject(ChargeProject chargeProject) {
        chargeProjectMapper.updateByPrimaryKeySelective(chargeProject);
    }

    public ChargeProject getChargeProjectByName(String chargeName, Integer gradeId) {
        return chargeProjectMapper.getChargeProjectByName(chargeName, gradeId);
    }

    public ChargeProject getChargeProjectById(Integer id) {
        return chargeProjectMapper.getChargeProjectById(id);
    }

    public int countChargeProject(ChargeSearchParam chargeSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(chargeSearchParam.getCurrentPage(), chargeSearchParam.getPageSize(), chargeSearchParam);
        return chargeProjectMapper.countChargeProject(paramMap);
    }

    public List<ChargeProject> queryChargeProjectList(ChargeSearchParam chargeSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(chargeSearchParam.getCurrentPage(), chargeSearchParam.getPageSize(), chargeSearchParam);
        return chargeProjectMapper.queryChargeProjectPageList(paramMap);
    }

}