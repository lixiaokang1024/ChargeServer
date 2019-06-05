package com.charge.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtil {

    public static String getValue(XSSFCell xssfRow) {
        if (null == xssfRow) {
            return null;
        } else if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue()).trim();
        } else if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(xssfRow)) {// 处理日期格式、时间格式
                SimpleDateFormat sdf;
                if (xssfRow.getCellStyle().getDataFormat() == HSSFDataFormat
                        .getBuiltinFormat("h:mm")) {
                    sdf = new SimpleDateFormat("HH:mm");
                } else {// 日期
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                }
                Date date = xssfRow.getDateCellValue();
                return sdf.format(date);
            }
            return String.valueOf(xssfRow.getNumericCellValue()).trim();
        } else if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_FORMULA){
            XSSFFormulaEvaluator evaluator = xssfRow.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            evaluator.evaluateFormulaCell(xssfRow);
            return String.valueOf(xssfRow.getNumericCellValue()).trim();
        } else {
            return String.valueOf(xssfRow.getStringCellValue()).trim();
        }
    }
}