package com.charge.controller.student;

import com.charge.param.student.StudentClassInfoSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.pojo.student.StudentClassInfo;
import com.charge.proxy.student.StudentClassInfoProxy;
import com.charge.util.ExcelUtil;
import com.charge.vo.student.StudentClassInfoVo;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<StudentClassInfoVo>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/importStudentClassInfo")
    @ResponseBody
    public Map<String, Object> importStudentClassInfo(@RequestParam(value = "studentClassFileBuildInfo", required = false) MultipartFile buildInfo,
                                                       HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            InputStream io = buildInfo.getInputStream();
            studentClassInfoProxy.importStudentClassInfo(io);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/upStudentClass")
    @ResponseBody
    public ModelMap upStudentClass() {
        ModelMap model = new ModelMap();
        model.put("success", true);
        try {
            studentClassInfoProxy.upStudentClass();
        }catch(Exception e){
            model.put("success", false);
            model.put("msg", e.getMessage());
        }
        return model;
    }

    @RequestMapping("/updateStudentClassInfo")
    @ResponseBody
    public Map<String, Object> updateStudentClassInfo(@RequestBody StudentClassInfo studentClassInfo) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            studentClassInfoProxy.updateStudentClassInfo(studentClassInfo);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/leaveSchool")
    @ResponseBody
    public Map<String, Object> leaveSchool(@RequestBody StudentClassInfo studentClassInfo) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            studentClassInfoProxy.leaveSchool(studentClassInfo);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/export")
    @ResponseBody
    public ModelMap exportStudentInfo(StudentClassInfoSearchParam searchParam,HttpServletRequest request) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("success", false);
        final String templateFilePath = request.getSession().getServletContext().getRealPath("/");
        try {
            searchParam.setPageSize(Integer.MAX_VALUE);
            PageResultDTO<List<StudentClassInfoVo>> pageResultDTO = studentClassInfoProxy.queryStudentClassInfo(searchParam);
            File targetFile = ExcelUtil.createXLSXExcel(initExcelColumn(pageResultDTO.getData()), initExcelHeader(),templateFilePath, "学生班级信息");
            Map<String, Object> data = new HashMap<>();
            data.put("filePath", targetFile.getName());
            data.put("fileName", targetFile.getName());
            modelMap.put("success", true);
            modelMap.put("data", data);
        } catch (Exception e) {
            modelMap.put("msg", e.getMessage());
        }
        return modelMap;
    }

    private String[] initExcelHeader() {
        String[] strArray = { "姓名", "学号", "班级", "年级", "毕业状态", "毕业时间", "入学时间", "备注"};
        return strArray;
    }
    private Map<String, List<String>> initExcelColumn(List<StudentClassInfoVo> data) {
        Map<String, List<String>> result = new HashMap<>();
        for(int i=0;i<data.size();i++){
            List<String> column = new ArrayList<>();
            StudentClassInfoVo dto = data.get(i);
            column.add(dto.getStudentName());
            column.add(String.valueOf(dto.getStudentId()));
            column.add(dto.getClassName());
            column.add(dto.getGradeName());
            column.add(dto.getIsGraduateStr());
            column.add(dto.getGraduateTime());
            column.add(dto.getAdmissionTime());
            column.add(dto.getRemark());
            result.put(String.valueOf(i), column);
        }
        return result;
    }
}
