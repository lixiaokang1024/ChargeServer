package com.charge.export.student;

import com.charge.export.common.AbstractExportCSVRandomHandler;
import com.charge.param.student.StudentChargeInfoSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.proxy.student.StudentChargeInfoProxy;
import com.charge.vo.student.StudentChargeInfoVo;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ExportStudentHistoryChargeInfoCSVHandler extends AbstractExportCSVRandomHandler<StudentChargeInfoVo, StudentChargeInfoSearchParam> {

    private StudentChargeInfoProxy studentChargeInfoProxy;

    public ExportStudentHistoryChargeInfoCSVHandler(StudentChargeInfoProxy studentChargeInfoProxy, StudentChargeInfoSearchParam searchParam) {
        super.searchParam = searchParam == null ? new StudentChargeInfoSearchParam() : searchParam;
        this.studentChargeInfoProxy = studentChargeInfoProxy;
        setPageSize(5000);
    }

    protected PageResultDTO<List<StudentChargeInfoVo>> queryPageData(StudentChargeInfoSearchParam parameter, int currentPage, int pageSize) {
        parameter.setCurrentPage(currentPage);
        parameter.setPageSize(pageSize);
        return this.studentChargeInfoProxy.queryStudentChargeInfo(parameter);
    }

    protected List<String> initData(List<StudentChargeInfoVo> data) {
        List<String> rowData = new ArrayList<String>();
        for(StudentChargeInfoVo studentChargeInfoVo:data){
            StringBuilder sb = new StringBuilder();

            String defaultStr = StringUtils.defaultIfEmpty(studentChargeInfoVo.getStudentName(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");

            defaultStr = StringUtils.defaultIfEmpty(String.valueOf(studentChargeInfoVo.getChargeAmount()), "/");
            sb.append("\"").append(defaultStr).append("\t\"\r\n");
            rowData.add(sb.toString());
        }
        return rowData;
    }

    @Override
    protected String initColumnHeader() {
        StringBuilder columnHeader = new StringBuilder();
        columnHeader.append("\"").append("学生姓名").append("\",");
        columnHeader.append("\"").append("应缴费金额").append("\"\r\n");
        return columnHeader.toString();
    }
}
