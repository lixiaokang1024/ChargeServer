package com.charge.controller.school;

import com.charge.param.school.ClassSearchParam;
import com.charge.param.school.GradeSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.school.ClassInfo;
import com.charge.pojo.school.GradeInfo;
import com.charge.proxy.school.SchoolProxy;
import com.charge.vo.school.ClassInfoVo;
import com.charge.vo.school.GradeInfoVo;
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
@RequestMapping("school")
public class SchoolController {

    private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    @Autowired
    private SchoolProxy schoolProxy;

    @RequestMapping("/gradeIndex")
    public String toGradeIndex(){
        return "school/gradeList";
    }

    @RequestMapping("/classIndex")
    public String toClassIndex(){
        return "school/classList";
    }

    @RequestMapping("/gradeList")
    @ResponseBody
    public ModelMap getGradeList(GradeSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        PageResultDTO<List<GradeInfoVo>> pageResultDTO = schoolProxy.queryGradeInfo(searchParam);
        if(pageResultDTO.getData() == null){
           pageResultDTO.setData(new ArrayList<GradeInfoVo>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/calssList")
    @ResponseBody
    public ModelMap getCalssList(ClassSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        PageResultDTO<List<ClassInfoVo>> pageResultDTO = schoolProxy.queryClassInfo(searchParam);
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<ClassInfoVo>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/saveClassInfo")
    @ResponseBody
    public Map<String, Object> saveClassInfo(ClassInfo classInfo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            schoolProxy.saveOrModifyClassInfo(classInfo);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/saveGradeInfo")
    @ResponseBody
    public Map<String, Object> saveGradeInfo(GradeInfo gradeInfo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            schoolProxy.saveGradeInfo(gradeInfo);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/deleteGradeInfo")
    @ResponseBody
    public Map<String, Object> deleteGradeInfo(Integer gradeId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("msg", "删除成功");
        try {
            schoolProxy.deleteGradeInfo(gradeId);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/deleteClassInfo")
    @ResponseBody
    public Map<String, Object> deleteClassInfo(Integer classId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("msg", "删除成功");
        try {
            schoolProxy.deleteClassInfo(classId);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

}
