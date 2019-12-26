package com.charge.controller.student;

import com.charge.export.common.ExportCSVService;
import com.charge.export.student.ExportStudentHistoryChargeInfoCSVHandler;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.param.student.StudentChargeParam;
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
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping("/expireIndex")
    public String toexpireIndex(){
        return "student/studentExpireChargeInfoList";
    }

    @RequestMapping("/historyIndex")
    public String toHistoryIndex(){
        return "student/studentHistoryChargeInfoList";
    }

    @RequestMapping("/countIndex")
    public String toCountIndex(){
        return "student/studentChargeDetailList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ModelMap getStudentChargeInfoList(StudentChargeInfoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        List<Integer> chargeStatus = new ArrayList<Integer>();
        chargeStatus.add(0);
        chargeStatus.add(1);
        searchParam.setChargeStatus(chargeStatus);
        PageResultDTO<List<StudentChargeInfoVo>> pageResultDTO = chargeInfoProxy.queryStudentChargeInfo(searchParam);
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<StudentChargeInfoVo>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/expireList")
    @ResponseBody
    public ModelMap getStudentExpireChargeInfoList(StudentChargeInfoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        List<Integer> chargeStatus = new ArrayList<>();
        chargeStatus.add(3);
        searchParam.setChargeStatus(chargeStatus);
        PageResultDTO<List<StudentChargeInfoVo>> pageResultDTO = chargeInfoProxy.queryStudentChargeInfo(searchParam);
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/count")
    @ResponseBody
    public ModelMap countStudentChargeInfo(StudentChargeInfoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        PageResultDTO<List<StudentChargeInfoDetailVo>> pageResultDTO = chargeInfoProxy.queryStudentChargeInfoDetailPageList(searchParam);
        if(pageResultDTO.getData() == null){
            pageResultDTO.setData(new ArrayList<StudentChargeInfoDetailVo>());
        }
        model.put("rows", pageResultDTO.getData());
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/detail/{studentId}")
    public String getStudentChargeInfoDetail(Model model, @PathVariable Integer studentId) {
        List<Integer> chargeStatus = new ArrayList<Integer>();
        chargeStatus.add(0);
        chargeStatus.add(1);
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = chargeInfoProxy.queryStudentChargeInfoDetail(studentId, chargeStatus, null, null);
        model.addAttribute("data", studentChargeInfoDetailVoList);
        return "student/studentChargeInfoDetail";
    }

    @RequestMapping("/expire/detail/{studentId}")
    public String getOverDueStudentChargeInfoDetail(Model model, @PathVariable Integer studentId) {
        List<Integer> chargeStatus = new ArrayList<>();
        chargeStatus.add(3);
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = chargeInfoProxy.queryStudentChargeInfoDetail(studentId, chargeStatus, null, null);
        model.addAttribute("data", studentChargeInfoDetailVoList);
        return "student/studentChargeInfoDetail";
    }

    @RequestMapping("/getByStudentId")
    @ResponseBody
    public ModelMap getByStudentId(@RequestParam("studentId") Integer studentId, @RequestParam("chargeStatus[]") List<Integer> chargeStatus) {
        ModelMap model = new ModelMap();
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = chargeInfoProxy.queryStudentChargeInfoDetail(studentId, chargeStatus, null, null);
        model.put("rows", studentChargeInfoDetailVoList);
        return model;
    }

    @RequestMapping("/historyList")
    @ResponseBody
    public ModelMap getStudentHistoryChargeInfoList(StudentChargeInfoSearchParam searchParam, Integer page, Integer rows) {
        ModelMap model = new ModelMap();
        searchParam.setCurrentPage(page);
        searchParam.setPageSize(rows);
        List<Integer> chargeStatus = new ArrayList<Integer>();
        chargeStatus.add(2);
        searchParam.setChargeStatus(chargeStatus);
        PageResultDTO<List<StudentChargeInfoVo>> pageResultDTO = chargeInfoProxy.queryStudentChargeInfo(searchParam);
        List<StudentChargeInfoVo> data = pageResultDTO.getData();
        model.put("rows", data == null ? new ArrayList<>():data);
        model.put("total", pageResultDTO.getTotalRecord());
        model.put("page", page);
        return model;
    }

    @RequestMapping("/historyDetail/{studentId}")
    public String getStudentHistoryChargeInfoDetail(Model model, @PathVariable Integer studentId, String payTimeBegin, String payTimeEnd) {
        List<Integer> chargeStatus = new ArrayList<>();
        chargeStatus.add(2);
        List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = chargeInfoProxy.queryStudentChargeInfoDetail(studentId, chargeStatus, payTimeBegin, payTimeEnd);
        model.addAttribute("data", studentChargeInfoDetailVoList == null ? new ArrayList<>():studentChargeInfoDetailVoList);
        return "student/studentHistoryChargeInfoDetail";
    }

    @RequestMapping("/export")
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

    @RequestMapping("/doCharge")
    @ResponseBody
    public Map<String, Object> doCharge(@RequestBody StudentChargeParam chargeParam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            chargeInfoProxy.doCharge(chargeParam);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/doProjectCharge")
    @ResponseBody
    public Map<String, Object> doProjectCharge(@RequestBody StudentChargeParam chargeParam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            List<StudentChargeInfoDetailVo> studentChargeInfoDetailVoList = chargeInfoProxy.doProjectCharge(chargeParam);
            resultMap.put("data",studentChargeInfoDetailVoList);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    //预缴费
    @RequestMapping("/doDepositCharge")
    @ResponseBody
    public Map<String, Object> doDepositCharge(StudentChargeParam chargeParam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        try {
            chargeInfoProxy.doDepositCharge(chargeParam);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

}
