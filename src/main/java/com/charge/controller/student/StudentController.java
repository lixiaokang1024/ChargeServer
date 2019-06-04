package com.charge.controller.student;

import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.student.StudentInfo;
import com.charge.service.student.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @RequestMapping("/index")
    public String toIndex(){
        return "student/studentList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ModelMap getStudentList(StudentSearchParam studentSearchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        studentSearchParam.setCurrentPage(page);
        studentSearchParam.setPageSize(rows);
        int count = studentService.countStudent(studentSearchParam);
        List<StudentInfo> studentInfoList =  studentService.queryStudentList(studentSearchParam);
        model.put("rows", studentInfoList);
        model.put("total", count);
        model.put("page", page);

        return model;
    }

}
