package com.charge.controller.charge;

import com.charge.param.charge.ChargeSearchParam;
import com.charge.param.charge.PayProjectIoSearchParam;
import com.charge.param.charge.PayProjectSearchParam;
import com.charge.pojo.charge.ChargeProject;
import com.charge.pojo.charge.PayProject;
import com.charge.pojo.charge.PayProjectIo;
import com.charge.pojo.common.PageResultDTO;
import com.charge.proxy.charge.ChargeProxy;
import com.charge.vo.charge.ChargeProjectVo;
import com.charge.vo.charge.PayProjectIoVo;
import com.charge.vo.charge.PayProjectVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("charge")
public class ChargeController {

    private static final Logger logger = LoggerFactory.getLogger(ChargeController.class);

    @Autowired
    private ChargeProxy chargeProxy;

    /**
     * 缴费项目
     * @return
     */
    @RequestMapping("/index")
    public String toIndex(){
        return "charge/chargeList";
    }

    /**
     * 日常收入项目
     * @return
     */
    @RequestMapping("/incomeProjectIndex")
    public String toIncomeProjectIndex(){
        return "charge/incomeProjectList";
    }

    /**
     * 日常支出项目
     * @return
     */
    @RequestMapping("/payProjectIndex")
    public String toPayProjectIndex(){
        return "charge/payProjectList";
    }

    /**
     * 日常支出历史
     * @return
     */
    @RequestMapping("/payProjectIoIndex")
    public String toPayProjectIoIndex(){
        return "charge/payProjectIoList";
    }

    /**
     * 日常收入历史
     * @return
     */
    @RequestMapping("/incomeProjectIoIndex")
    public String toIncomeProjectIoIndex(){
        return "charge/incomeProjectIoList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ModelMap getChargeProjectList(ChargeSearchParam chargeSearchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        chargeSearchParam.setCurrentPage(page);
        chargeSearchParam.setPageSize(rows);
        PageResultDTO<List<ChargeProjectVo>> pageResultDTO = chargeProxy.queryChargeProjectList(chargeSearchParam);
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<ChargeProjectVo>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/incomeProjectlist")
    @ResponseBody
    public ModelMap getIncomeProjectList(PayProjectSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        searchParam.setProjectType(0);
        return getModelMap(searchParam, page, model);
    }

    @RequestMapping("/payProjectlist")
    @ResponseBody
    public ModelMap getPayProjectList(PayProjectSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        searchParam.setProjectType(1);
        return getModelMap(searchParam, page, model);
    }

    private ModelMap getModelMap(PayProjectSearchParam searchParam, Integer page, ModelMap model) {
        PageResultDTO<List<PayProjectVo>> pageResultDTO = chargeProxy.queryPayProjectList(searchParam);
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<PayProjectVo>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/incomeProjectIolist")
    @ResponseBody
    public ModelMap getIncomeProjectIolist(PayProjectIoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        searchParam.setProjectType(0);
        return getModelMap(searchParam, page, model);
    }

    private ModelMap getModelMap(PayProjectIoSearchParam searchParam, Integer page, ModelMap model) {
        PageResultDTO<List<PayProjectIoVo>> pageResultDTO = chargeProxy.queryPayProjectIoList(searchParam);
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<PayProjectIoVo>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/payProjectIolist")
    @ResponseBody
    public ModelMap getpayProjectIolist(PayProjectIoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        searchParam.setProjectType(1);
        return getModelMap(searchParam, page, model);
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

    @RequestMapping("/deleteChargeInfo")
    @ResponseBody
    public Map<String, Object> deleteChargeInfo(Integer chargeId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("msg", "删除成功");
        try {
            chargeProxy.deleteChargeInfo(chargeId);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/savePayProject")
    @ResponseBody
    public Map<String, Object> savePayProject(PayProject payProject) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            chargeProxy.saveOrUpdatePayProject(payProject);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/deletePayProjectInfo")
    @ResponseBody
    public Map<String, Object> deletePayProjectInfo(Integer payProjectId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("msg", "删除成功");
        try {
            chargeProxy.deletePayProjectInfo(payProjectId);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/savePayProjectIo")
    @ResponseBody
    public Map<String, Object> savePayProjectIo(PayProjectIo payProjectIo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            chargeProxy.saveOrUpdatePayProjectIo(payProjectIo);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }
}
