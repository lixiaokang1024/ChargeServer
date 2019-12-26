package com.charge.service.charge.impl;

import com.charge.mapper.charge.ChargeProjectMapper;
import com.charge.mapper.charge.PayProjectIoMapper;
import com.charge.mapper.charge.PayProjectMapper;
import com.charge.param.charge.ChargeSearchParam;
import com.charge.param.charge.PayProjectIoSearchParam;
import com.charge.param.charge.PayProjectSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.charge.PayProject;
import com.charge.pojo.charge.PayProjectIo;
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

    @Autowired
    private PayProjectMapper payProjectMapper;

    @Autowired
    private PayProjectIoMapper payProjectIoMapper;

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

    public void deleteChargeProjectById(Integer id) {
        chargeProjectMapper.deleteChargeProjectById(id);
    }

    public int countChargeProject(ChargeSearchParam chargeSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(chargeSearchParam.getCurrentPage(), chargeSearchParam.getPageSize(), chargeSearchParam);
        return chargeProjectMapper.countChargeProject(paramMap);
    }

    public List<ChargeProject> queryChargeProjectList(ChargeSearchParam chargeSearchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(chargeSearchParam.getCurrentPage(), chargeSearchParam.getPageSize(), chargeSearchParam);
        return chargeProjectMapper.queryChargeProjectPageList(paramMap);
    }

    public void insertPayProjectSelective(PayProject payProject) {
        payProject.setCreateTime(DateUtil.getCurrentTimespan());
        payProjectMapper.insertSelective(payProject);
    }

    public void updatePayProject(PayProject payProject) {
        payProjectMapper.updateByPrimaryKeySelective(payProject);
    }

    public PayProject getPayProjectById(Integer id) {
        return payProjectMapper.getPayProjectById(id);
    }

    public void deletePayProjectById(Integer id) {
        payProjectMapper.deleteById(id);
    }

    public int countPayProject(PayProjectSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return payProjectMapper.countPayProject(paramMap);
    }

    public List<PayProject> queryPayProjectList(PayProjectSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return payProjectMapper.queryPayProjectPageList(paramMap);
    }

    public void insertPayProjectIoSelective(PayProjectIo payProjectIo) {
        payProjectIo.setCreateTime(DateUtil.getCurrentTimespan());
        payProjectIo.setPayTime(DateUtil.getCurrentTimespan());
        payProjectIoMapper.insertSelective(payProjectIo);
    }

    public void updatePayProjectIo(PayProjectIo payProjectIo) {
        payProjectIoMapper.updateByPrimaryKeySelective(payProjectIo);
    }

    public PayProjectIo getPayProjectIoById(Integer id) {
        return payProjectIoMapper.getPayProjectIoById(id);
    }

    public int countPayProjectIo(PayProjectIoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return payProjectIoMapper.countPayProjectIo(paramMap);
    }

    public List<PayProjectIo> queryPayProjectIoList(PayProjectIoSearchParam searchParam) {
        Map<String, Object> paramMap = RequestParamUtil.getRequestParamMap(searchParam.getCurrentPage(), searchParam.getPageSize(), searchParam);
        return payProjectIoMapper.queryPayProjectIoPageList(paramMap);
    }

}