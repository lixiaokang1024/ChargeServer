package com.charge.export.student;

import com.charge.export.common.AbstractExportCSVRandomHandler;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.proxy.student.StudentChargeInfoProxy;
import com.charge.vo.student.StudentChargeInfoDetailVo;
import com.charge.vo.student.StudentChargeInfoVo;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ExportStudentHistoryChargeInfoCSVHandler extends AbstractExportCSVRandomHandler<StudentChargeInfoDetailVo, StudentChargeInfoSearchParam> {

    private StudentChargeInfoProxy studentChargeInfoProxy;

    public ExportStudentHistoryChargeInfoCSVHandler(StudentChargeInfoProxy studentChargeInfoProxy, StudentChargeInfoSearchParam searchParam) {
        super.searchParam = searchParam == null ? new StudentChargeInfoSearchParam() : searchParam;
        this.studentChargeInfoProxy = studentChargeInfoProxy;
        setPageSize(5000);
    }

    protected PageResultDTO<List<StudentChargeInfoDetailVo>> queryPageData(StudentChargeInfoSearchParam parameter, int currentPage, int pageSize) {
        parameter.setCurrentPage(currentPage);
        parameter.setPageSize(pageSize);
        return this.studentChargeInfoProxy.queryStudentChargeInfoDetailPageList(parameter);
    }

    protected List<String> initData(List<StudentChargeInfoDetailVo> data) {
        List<String> rowData = new ArrayList<String>();
        for(StudentChargeInfoDetailVo studentChargeInfoVo:data){
            StringBuilder sb = new StringBuilder();

            String defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getStudentName(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getGradeName(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getClassName(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getChargeProjectName(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getChargeAmount().toString(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getActualChargeAmount().toString(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getUseDepositAmount().toString(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getChargeTimeStr(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getActualChargeTimeStr(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getPayTypeStr(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getStatusStr(), "/");
            sb.append("\"").append(defaultStr).append("\t\"\r\n");
            rowData.add(sb.toString());
        }
        return rowData;
    }

    @Override
    protected String initColumnHeader() {
        StringBuilder columnHeader = new StringBuilder();
        columnHeader.append("\"").append("学生姓名").append("\",");
        columnHeader.append("\"").append("年级").append("\",");
        columnHeader.append("\"").append("班级").append("\",");
        columnHeader.append("\"").append("缴费项目").append("\",");
        columnHeader.append("\"").append("应缴费金额").append("\",");
        columnHeader.append("\"").append("实际缴费金额").append("\",");
        columnHeader.append("\"").append("使用预缴费金额").append("\",");
        columnHeader.append("\"").append("应缴费时间").append("\",");
        columnHeader.append("\"").append("时间缴费时间").append("\",");
        columnHeader.append("\"").append("缴费方式").append("\",");
        columnHeader.append("\"").append("缴费状态").append("\"\r\n");
        return columnHeader.toString();
    }
}
