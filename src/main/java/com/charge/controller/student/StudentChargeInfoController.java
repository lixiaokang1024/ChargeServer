package com.charge.controller.student;

import com.charge.export.common.ExportCSVService;
import com.charge.export.student.ExportStudentHistoryChargeInfoCSVHandler;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.param.student.StudentChargeParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.proxy.student.StudentChargeInfoProxy;
import com.charge.util.ExcelUtil;
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

    @RequestMapping("/exportStudentChargeInfo")
    @ResponseBody
    public ModelMap exportStudentChargeInfo(StudentChargeInfoSearchParam searchParam,HttpServletRequest request) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("success", false);
        final String templateFilePath = request.getSession().getServletContext().getRealPath("/");
        try {
            searchParam.setPageSize(Integer.MAX_VALUE);
            PageResultDTO<List<StudentChargeInfoDetailVo>> listPageResultDTO = chargeInfoProxy.queryStudentChargeInfoDetailPageList(searchParam);
            File targetFile = ExcelUtil.createXLSXExcel(initExcelColumn(listPageResultDTO.getData()), initExcelHeader(),templateFilePath, "学生缴费统计");
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

    private Map<String, List<String>> initExcelColumn(List<StudentChargeInfoDetailVo> data) {
        Map<String, List<String>> result = new HashMap<>();
        for(int i=0;i<data.size();i++){
            List<String> column = new ArrayList<>();
            StudentChargeInfoDetailVo dto = data.get(i);
            column.add(dto.getStudentName());
            column.add(dto.getGradeName());
            column.add(dto.getClassName());
            column.add(dto.getChargeProjectName());
            column.add(dto.getChargeAmount() + "");
            column.add(dto.getActualChargeAmount() + "");
            column.add(dto.getUseDepositAmount() + "");
            column.add(dto.getChargeTimeStr());
            column.add(dto.getActualChargeTimeStr());
            column.add(dto.getPayTypeStr());
            column.add(dto.getStatusStr());
            result.put(String.valueOf(i), column);
        }
        return result;
    }

    private String[] initExcelHeader() {
        String[] strArray = { "姓名", "年级", "班级", "缴费项目", "应缴费金额", "实际缴费金额", "使用预缴费金额", "应缴费时间", "实际缴费时间", "缴费方式", "缴费状态"};
        return strArray;
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
            List<Integer> chargeStatus = new ArrayList<>();
            chargeStatus.add(2);
            searchParam.setChargeStatus(chargeStatus);
            searchParam.setPageSize(Integer.MAX_VALUE);
            PageResultDTO<List<StudentChargeInfoVo>> listPageResultDTO = chargeInfoProxy.queryStudentChargeInfo(searchParam);
            File targetFile = ExcelUtil.createXLSXExcel(initExcelColumn1(listPageResultDTO.getData()), initExcelHeader1(),templateFilePath, "学生历史缴费");
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

    private Map<String, List<String>> initExcelColumn1(List<StudentChargeInfoVo> data) {
        Map<String, List<String>> result = new HashMap<>();
        for(int i=0;i<data.size();i++){
            List<String> column = new ArrayList<>();
            StudentChargeInfoVo dto = data.get(i);
            column.add(dto.getStudentName());
            column.add(dto.getChargeAmount() + "");
            column.add(dto.getActualChargeAmount() + "");
            column.add(dto.getDeposit() + "");
            column.add(dto.getPrepaymentAmount() + "");
            result.put(String.valueOf(i), column);
        }
        return result;
    }

    private String[] initExcelHeader1() {
        String[] strArray = { "姓名", "应缴费金额", "实际缴费金额", "预缴费剩余金额", "押金"};
        return strArray;
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
