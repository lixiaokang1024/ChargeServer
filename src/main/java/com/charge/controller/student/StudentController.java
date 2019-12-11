package com.charge.controller.student;

import com.charge.export.common.ExportCSVService;
import com.charge.export.student.ExportStudentInfoCSVHandler;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.student.StudentInfo;
import com.charge.proxy.student.StudentProxy;
import com.charge.vo.student.StudentInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentProxy studentProxy;

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
        PageResultDTO<List<StudentInfoVo>> pageResultDTO = studentProxy.queryStudentInfo(studentSearchParam);
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/export")
    @ResponseBody
    public ModelMap exportStudentInfo(StudentSearchParam searchParam,HttpServletRequest request) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("success", false);
        final String templateFilePath = request.getSession().getServletContext().getRealPath("/");
        try {
            ExportCSVService exportCSVService = new ExportStudentInfoCSVHandler(studentProxy, searchParam);
            File targetFile = exportCSVService.exportCSVFile(templateFilePath);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("filePath", targetFile.getName());
            data.put("fileName", "student_info_" + targetFile.getName());
            modelMap.put("success", true);
            modelMap.put("data", data);
        } catch (Exception e) {
            modelMap.put("msg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping("/importStudentInfo")
    @ResponseBody
    public Map<String, Object> importStudentInfo(@RequestParam(value = "studentFileBuildInfo", required = false) MultipartFile buildInfo,
                                                HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            InputStream io = buildInfo.getInputStream();
            studentProxy.importStudentInfo(io);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/saveStudentInfo")
    @ResponseBody
    public Map<String, Object> saveStudentInfo(StudentInfo studentInfo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            studentProxy.saveOrUpdateStudentInfo(studentInfo);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

}
