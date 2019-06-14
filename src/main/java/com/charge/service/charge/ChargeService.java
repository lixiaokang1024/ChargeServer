package com.charge.service.charge;

import com.charge.param.charge.ChargeSearchParam;
import com.charge.pojo.charge.ChargeProject;

import java.util.List;

public interface ChargeService {

    void insertSelective(ChargeProject chargeProject);
    void updateChargeProject(ChargeProject chargeProject);


    ChargeProject getChargeProjectByName(String chargeName, Integer gradeId);
    ChargeProject getChargeProjectById(Integer id);
    int countChargeProject(ChargeSearchParam chargeSearchParam);
    List<ChargeProject> queryChargeProjectList(ChargeSearchParam chargeSearchParam);

}