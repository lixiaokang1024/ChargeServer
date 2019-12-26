package com.charge.controller;

import com.charge.service.discount.DiscountService;
import com.charge.vo.setting.DiscountSettingVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("discount")
public class DiscountController {

    private static final Logger logger = LoggerFactory.getLogger(DiscountController.class);

    @Autowired
    private DiscountService discountService;

    @RequestMapping("/discountIndex")
    public String toDiscountIndex(){
        return "discount/discount";
    }

    @RequestMapping("/discountList")
    @ResponseBody
    public ModelMap discountList(String name) {
        ModelMap model = new ModelMap();
        try {
            List<DiscountSettingVo> discountSettingVos = discountService.getAll();
            model.put("rows", discountSettingVos);
            model.put("total", 1);
            model.put("page", 1);
        }catch (Exception e){
        }
        return model;
    }

    @RequestMapping("/saveDiscount")
    @ResponseBody
    public Map<String, Object> saveDiscount(String discount) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            discountService.save(discount);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/deleteDiscount")
    @ResponseBody
    public Map<String, Object> deleteDiscount(Integer discountId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            discountService.delete(discountId);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

}
