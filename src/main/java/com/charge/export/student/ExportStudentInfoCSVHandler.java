package com.charge.export.student;

import com.charge.export.common.AbstractExportCSVRandomHandler;
import com.charge.param.student.StudentSearchParam;
import com.charge.pojo.common.PageResultDTO;
import com.charge.proxy.student.StudentProxy;
import com.charge.vo.student.StudentInfoVo;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class ExportStudentInfoCSVHandler extends AbstractExportCSVRandomHandler<StudentInfoVo, StudentSearchParam> {

    private StudentProxy studentProxy;

    public ExportStudentInfoCSVHandler(StudentProxy studentProxy, StudentSearchParam searchParam) {
        super.searchParam = searchParam == null ? new StudentSearchParam() : searchParam;
        this.studentProxy = studentProxy;
        setPageSize(5000);
    }

    protected PageResultDTO<List<StudentInfoVo>> queryPageData(StudentSearchParam parameter, int currentPage, int pageSize) {
        parameter.setCurrentPage(currentPage);
        parameter.setPageSize(pageSize);
        return this.studentProxy.queryStudentInfo(searchParam);
    }

    protected List<String> initData(List<StudentInfoVo> data) {
        List<String> rowData = new ArrayList<String>();
        for(StudentInfoVo studentInfoVo:data){
            StringBuilder sb = new StringBuilder();
            // 姓名
            String defaultStr = StringUtils.defaultIfEmpty(studentInfoVo.getName(), "/");
            sb.append("\"").append(defaultStr).append("\t\",");
            // 性别
            defaultStr = StringUtils.defaultIfEmpty(studentInfoVo.getSexStr(), "/");
            sb.append("\"").append(defaultStr).append("\t\"\r\n");
            rowData.add(sb.toString());
        }
        return rowData;
    }

    @Override
    protected String initColumnHeader() {
        StringBuilder columnHeader = new StringBuilder();
        columnHeader.append("\"").append("自动入库单号").append("\",");
        columnHeader.append("\"").append("运单号").append("\"\r\n");
        return columnHeader.toString();
    }
}
