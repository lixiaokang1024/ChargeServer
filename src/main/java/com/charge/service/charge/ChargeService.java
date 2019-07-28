package com.charge.service.charge;

import com.charge.param.charge.ChargeSearchParam;
import com.charge.param.charge.PayProjectIoSearchParam;
import com.charge.param.charge.PayProjectSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.charge.PayProject;
import com.charge.pojo.charge.PayProjectIo;

import java.util.List;

public interface ChargeService {

    void insertSelective(ChargeProject chargeProject);
    void updateChargeProject(ChargeProject chargeProject);

    ChargeProject getChargeProjectByName(String chargeName, Integer gradeId);
    ChargeProject getChargeProjectById(Integer id);
    void deleteChargeProjectById(Integer id);
    int countChargeProject(ChargeSearchParam chargeSearchParam);
    List<ChargeProject> queryChargeProjectList(ChargeSearchParam chargeSearchParam);


    void insertPayProjectSelective(PayProject payProject);
    void updatePayProject(PayProject payProject);

    PayProject getPayProjectById(Integer id);
    void deletePayProjectById(Integer id);
    int countPayProject(PayProjectSearchParam searchParam);
    List<PayProject> queryPayProjectList(PayProjectSearchParam searchParam);


    void insertPayProjectIoSelective(PayProjectIo payProjectIo);
    void updatePayProjectIo(PayProjectIo payProjectIo);

    PayProjectIo getPayProjectIoById(Integer id);
    int countPayProjectIo(PayProjectIoSearchParam searchParam);
    List<PayProjectIo> queryPayProjectIoList(PayProjectIoSearchParam searchParam);

}