package com.charge.controller.student;

import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.proxy.student.StudentChargeInfoProxy;
import com.charge.vo.student.StudentChargeInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("studentChargeInfo")
public class StudentChargeInfoController {

    private static final Logger logger = LoggerFactory.getLogger(StudentChargeInfoController.class);

    @Autowired
    private StudentChargeInfoProxy chargeInfoProxy;

    @RequestMapping("/index")
    public String toIndex(){
        return "student/studentChargeInfoList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ModelMap getStudentChargeInfoList(StudentChargeInfoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        PageResultDTO<List<StudentChargeInfoVo>> pageResultDTO = chargeInfoProxy.queryStudentChargeInfo(searchParam);
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

}
