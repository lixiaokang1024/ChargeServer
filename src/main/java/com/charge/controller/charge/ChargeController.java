package com.charge.controller.charge;

import com.charge.param.charge.ChargeSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.common.PageResultDTO;
import com.charge.proxy.charge.ChargeProxy;
import com.charge.vo.charge.ChargeProjectVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("charge")
public class ChargeController {

    private static final Logger logger = LoggerFactory.getLogger(ChargeController.class);

    @Autowired
    private ChargeProxy chargeProxy;

    @RequestMapping("/index")
    public String toIndex(){
        return "charge/chargeList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ModelMap getChargeProjectList(ChargeSearchParam chargeSearchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        chargeSearchParam.setCurrentPage(page);
        chargeSearchParam.setPageSize(rows);
        PageResultDTO<List<ChargeProjectVo>> pageResultDTO = chargeProxy.queryChargeProjectList(chargeSearchParam);
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/saveProject")
    @ResponseBody
    public Map<String, Object> saveChargeProject(ChargeProject chargeProject) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            chargeProxy.saveOrUpdateChargeProject(chargeProject);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }
}
