package com.charge.proxy.charge;

import com.charge.Exception.BusinessException;
import com.charge.param.charge.ChargeSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.common.PageResultDTO;
import com.charge.service.charge.ChargeService;
import com.charge.util.JsonUtil;
import com.charge.vo.charge.ChargeProjectVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChargeProxy {

    private static final Logger logger = LoggerFactory.getLogger(ChargeProxy.class);

    @Autowired
    private ChargeService chargeService;

    public PageResultDTO<List<ChargeProjectVo>> queryChargeProjectList(ChargeSearchParam chargeSearchParam){
        logger.info("查询收费项目信息搜索参数：{}", JsonUtil.toJson(chargeSearchParam));
        PageResultDTO<List<ChargeProjectVo>> pageResultDTO = new PageResultDTO<List<ChargeProjectVo>>();
        try {

            int count = chargeService.countChargeProject(chargeSearchParam);
            pageResultDTO.setTotalRecord(count);
            if(count == 0){
                return pageResultDTO;
            }
            pageResultDTO.setCurrentPage(chargeSearchParam.getCurrentPage());
            pageResultDTO.setPageSize(chargeSearchParam.getPageSize());
            List<ChargeProject> chargeProjectList = chargeService.queryChargeProjectList(chargeSearchParam);
            if(!CollectionUtils.isEmpty(chargeProjectList)){
                List<ChargeProjectVo> chargeProjectVoList = transferToChargeProjectVo(chargeProjectList);
                pageResultDTO.setData(chargeProjectVoList);
            }
        }catch (Exception e){
            logger.error("查询收费项目信息出错,msg={}",e.getMessage(),e);
        }
        return pageResultDTO;
    }

    public void saveOrUpdateChargeProject(ChargeProject chargeProject){
        logger.info("收费项目信息保存参数：{}", JsonUtil.toJson(chargeProject));
        if(chargeProject.getId() != null){
            ChargeProject chargeProjectIndb = chargeService.getChargeProjectById(chargeProject.getId());
            if(chargeProjectIndb == null){
                throw new BusinessException("记录不存在！");
            }
            chargeService.updateChargeProject(chargeProject);
        }else{
            chargeService.insertSelective(chargeProject);
        }
    }

    private List<ChargeProjectVo> transferToChargeProjectVo(List<ChargeProject> chargeProjectList) {
        List<ChargeProjectVo> chargeProjectVoList = new ArrayList<ChargeProjectVo>();
        if(CollectionUtils.isEmpty(chargeProjectList)){
            return chargeProjectVoList;
        }
        for(ChargeProject chargeProject:chargeProjectList){
            ChargeProjectVo chargeProjectVo = new ChargeProjectVo();
            BeanUtils.copyProperties(chargeProject, chargeProjectVo);
            chargeProjectVoList.add(chargeProjectVo);
        }
        return chargeProjectVoList;
    }

}
