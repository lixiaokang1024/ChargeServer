package com.charge.controller.student;

import com.charge.param.student.StudentClassInfoSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.proxy.student.StudentClassInfoProxy;
import com.charge.vo.student.StudentClassInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("studentClassInfo")
public class StudentClassInfoController {

    private static final Logger logger = LoggerFactory.getLogger(StudentClassInfoController.class);

    @Autowired
    private StudentClassInfoProxy studentClassInfoProxy;

    @RequestMapping("/index")
    public String toIndex(){
        return "student/studentClassInfoList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ModelMap getStudentClassInfoList(StudentClassInfoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        PageResultDTO<List<StudentClassInfoVo>> pageResultDTO = studentClassInfoProxy.queryStudentClassInfo(searchParam);
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

}
