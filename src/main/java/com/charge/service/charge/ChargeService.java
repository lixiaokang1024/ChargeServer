package com.charge.service.charge;

import com.charge.param.charge.ChargeSearchParam;
import com.charge.param.school.ClassSearchParam;
import com.charge.param.school.GradeSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.school.GradeInfo;

import java.util.List;

public interface ChargeService {

    void insertSelective(ChargeProject chargeProject);

    int countChargeProject(ChargeSearchParam chargeSearchParam);
    List<ChargeProject> queryChargeProjectList(ChargeSearchParam chargeSearchParam);

}