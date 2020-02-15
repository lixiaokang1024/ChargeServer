package com.charge.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
            DecimalFormat df = new DecimalFormat("#");//转换成整型
            return String.valueOf(df.format(xssfRow.getNumericCellValue()).trim());
        } else if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_FORMULA){
            XSSFFormulaEvaluator evaluator = xssfRow.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            evaluator.evaluateFormulaCell(xssfRow);
            return String.valueOf(xssfRow.getNumericCellValue()).trim();
        } else {
            return String.valueOf(xssfRow.getStringCellValue()).trim();
        }
    }


    private static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?\\d*[.]\\d+$");
        return pattern.matcher(str).matches();
    }

    public static File createXLSXExcel(Map<String, List<String>> map, String[] strArray, String filePath, String fileName) {
        File file = new File(filePath + fileName + ".xlsx");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");
            XSSFRow rowHeader = sheet.createRow(0);
            for(int i=0;i<strArray.length;i++){
                rowHeader.createCell(i).setCellValue(strArray[i]);
            }
            int k = 1;
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            for(String str : map.keySet()){
                List<String> rowData = map.get(str);
                XSSFRow sheetRow = sheet.createRow(k);
                for(int i=0;i<rowData.size();i++){
                    XSSFCell cell = sheetRow.createCell(i);
                    if(isDouble(rowData.get(i))){
                        cell.setCellValue(Double.parseDouble(rowData.get(i)));
                        cell.setCellStyle(cellStyle);
                    }else{
                        cell.setCellValue(rowData.get(i));
                    }
                }
                k++;
            }
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}