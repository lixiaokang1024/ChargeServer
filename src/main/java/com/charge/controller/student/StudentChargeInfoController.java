package com.charge.controller.student;

import com.charge.export.common.ExportCSVService;
import com.charge.export.student.ExportStudentHistoryChargeInfoCSVHandler;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.proxy.student.StudentChargeInfoProxy;
import com.charge.vo.student.StudentChargeInfoDetailVo;
import com.charge.vo.student.StudentChargeInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("studentChargeInfo")
public class StudentChargeInfoController {

    private static final Logger logger = LoggerFactory.getLogger(StudentChargeInfoController.class);

    @Autowired
    private StudentChargeInfoProxy chargeInfoProxy;

    @RequestMapping("/index")
    public String toIndex(){
        return "student/studentChargeInfoList";
    }

    @RequestMapping("/historyIndex")
    public String toHistoryIndex(){
        return "student/studentHistoryChargeInfoList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ModelMap getStudentChargeInfoList(StudentChargeInfoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        searchParam.setChargeStatus(0);
        PageResultDTO<List<StudentChargeInfoVo>> pageResultDTO = chargeInfoProxy.queryStudentChargeInfo(searchParam);
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<StudentChargeInfoVo>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/detail/{studentId}")
    public String getStudentChargeInfoDetail(Model model, @PathVariable Integer studentId) {
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = chargeInfoProxy.queryStudentChargeInfoDetail(studentId, 0);
        model.addAttribute("data", studentChargeInfoDetailVoList);
        return "student/studentChargeInfoDetail";
    }

    @RequestMapping("/historyList")
    @ResponseBody
    public ModelMap getStudentHistoryChargeInfoList(StudentChargeInfoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        searchParam.setChargeStatus(1);
        PageResultDTO<List<StudentChargeInfoVo>> pageResultDTO = chargeInfoProxy.queryStudentChargeInfo(searchParam);
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/historyDetail/{studentId}")
    public String getStudentHistoryChargeInfoDetail(Model model, @PathVariable Integer studentId) {
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = chargeInfoProxy.queryStudentChargeInfoDetail(studentId, 1);
        model.addAttribute("data", studentChargeInfoDetailVoList);
        return "student/studentHistoryChargeInfoDetail";
    }

    @RequestMapping("/exportHistory")
    @ResponseBody
    public ModelMap exportStudentHistoryChargeInfo(StudentChargeInfoSearchParam searchParam,HttpServletRequest request) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("success", false);
        final String templateFilePath = request.getSession().getServletContext().getRealPath("/");
        try {
            ExportCSVService exportCSVService = new ExportStudentHistoryChargeInfoCSVHandler(chargeInfoProxy, searchParam);
            File targetFile = exportCSVService.exportCSVFile(templateFilePath);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("filePath", targetFile.getName());
            data.put("fileName", "charge_info_" + targetFile.getName());
            modelMap.put("success", true);
            modelMap.put("data", data);
        } catch (Exception e) {
            modelMap.put("msg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping("/importStudentChargeInfo")
    @ResponseBody
    public Map<String, Object> importStudentChargeInfo(@RequestParam(value = "studentChargeFileBuildInfo", required = false) MultipartFile buildInfo,
                                                 HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            InputStream io = buildInfo.getInputStream();
            chargeInfoProxy.importStudentChargeInfo(io);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

}
