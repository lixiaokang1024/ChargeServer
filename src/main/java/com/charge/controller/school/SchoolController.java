package com.charge.controller.school;

import com.charge.param.school.ClassSearchParam;
import com.charge.param.school.GradeSearchParam;
import com.charge.pojo.common.PageResultDTO;
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
import java.util.List;

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
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

}
