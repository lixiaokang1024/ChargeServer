package com.charge.controller;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestMain1 {
    private static String companyName = "FLASH EXPRESS CO LIMITED";
    private static String space = " ";
    private static String zero = "0";
    private static Map<Integer, String> columIndexMap;
    private static int[] index = {1,4,124,254,294,334,374,414,454,464,480,488,496,499,514,529,549,569,584,600,620,622,624,626,646,651,701,751,801,814,817,822,826,856,961,1031,1071,1111,1151,1191,1207,1214,1216,1231,1247,1257,1357,1373,1390,1400,1500,1510,1610,1630,1730,1860,1880,1980,2110,2130,2230,2360,2375,2383,2391,2399,2498,2501};
    private static int[] index2 = {1,4,124,254,294,464,480,488,496,499,549,550,569,584,587,};
    private static String asd = "TXNFLASH EXPRESS CO LIMITED                                                                                                ไตรสิทธิ์ กล้าหาญ                                                                                                                                                                                                                                                                                                                                   TH01203K8N01B,TH1811201918112019THB                                                  00541065538         000000004340.000110015         00152521431         0400                      EMAIL                                                  pairote10@hotmail.com                             0840813490                                        OUR          DCB                                                                                                                                                                                                                                                                                                                                                                                                                                                       END\n"
        + "WHT             0105560159254  000000000000.00                                          000000000000.00000000000000.00                                          000000000000.00                                                                                                                                                FLASH EXPRESS CO LIMITED                                                                                                184/233 Radchadapisak                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ";
    static {
        columIndexMap = new HashMap<>();
        // columIndexMap.put()
    }
    public static void main(String[] args) {
        // List<String> stringList = new ArrayList<>();
        // stringList.add("1哈哈 黑");
        // stringList.add("2嘿嘿 黑");
        // writeDataHubData(stringList, "111");
        System.out.println("ไตรสิทธิ์ กล้าหาญ".length());
        // for(int i = 0;i<index.length-1;i++){
        //     int begin = index[i] - 1;
        //     int end = index[i+1] - 1;
        //     System.out.println("beginIndex="+begin+",endIndex="+end+",content="+asd.substring(begin,end));
        // }
        // System.out.println("+++++++++++++++++++++++++++++++++++");
        // getApend()
        Double a = 0d;
        System.out.println(getAmountApend(a, 15));
        String s = "011:TMB Bank";
        System.out.println(getSpaceApend(s, 3));
        System.out.println(getReceiveBRCode("011:TMB Bank", "015251431"));
        StringBuilder sb = new StringBuilder();
        sb.append(getTXN()).append(getWHT());
        System.out.println(sb.toString());
    }

    /**
     * 写入text文件
     *
     * @param result
     * @param fileName
     * @return
     */
    public static boolean writeDataHubData(List<String> result, String fileName) {
        long start = System.currentTimeMillis();
        String filePath = "D:\\temp\\txt";
        StringBuilder content = new StringBuilder();
        boolean flag = false;
        BufferedWriter out = null;
        try {
            if (result != null && !result.isEmpty() && StringUtils.isNotEmpty(fileName)) {
                fileName += "_" + System.currentTimeMillis();
                File pathFile = new File(filePath);
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                }
                String relFilePath = filePath + File.separator + fileName;
                File file = new File(relFilePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
//                //标题头
//                out.write("curr_time,link_id,travel_time,speed,reliabilitycode,link_len,adcode,time_stamp,state,public_rec_time,ds");
//                out.newLine();
                for (String info : result) {

                    out.write(info);
                    out.newLine();
                }
                flag = true;
//                logger.info("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
                System.out.println("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        }
    }

    public static String getStrApend(int begin, int end, String str){
        int length = end - begin + 1;
        int spaceLength = length - str.length();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for(int i=0;i<spaceLength;i++){
            sb.append(space);
        }
        return sb.toString();
    }

    public static String getAmountApend(Double amount, int length){
        String amountStr = String.format("%.2f", amount);
        int zeroLength = length - amountStr.length();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<zeroLength;i++){
            sb.append(zero);
        }
        sb.append(amountStr);
        return sb.toString();
    }

    public static String getSpaceApend(String str, int length){
        if(str.length()>length){
            return str.substring(0, length);
        }
        int spaceLength = length - str.length();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for(int i=0;i<spaceLength;i++){
            sb.append(space);
        }
        return sb.toString();
    }

    public static String getTXN(){
        StringBuilder sb = new StringBuilder();
        sb.append("TXN");
        sb.append(getSpaceApend(companyName, 120));
        sb.append(getSpaceApend("Payee Name", 130));
        sb.append(getSpaceApend("Mail to Name", 40));
        sb.append(getSpaceApend("Payee Address", 170));
        sb.append(getSpaceApend("Payment Ref", 16));
        sb.append(getSpaceApend("Effective Date", 8));
        sb.append(getSpaceApend("Effective Date", 8));
        sb.append("THB");
        sb.append(getSpaceApend("", 50));
        sb.append("0");
        sb.append(getSpaceApend("Company A/C", 19));
        sb.append(getAmountApend(2200.00, 15));
        sb.append(getSpaceApend("Payee Bank", 3));
        sb.append(getReceiveBRCode("Payee Bank", "Payee AC"));
        sb.append(getSpaceApend("", 9));
        sb.append(getSpaceApend(getReceiveBRCode("Payee Bank", "Payee AC"), 20));
        sb.append("0400");
        sb.append(getSpaceApend("Delivery Method", 2));
        sb.append(getSpaceApend("Pick Up", 2));
        sb.append(getSpaceApend("Advise Model", 5));
        sb.append(getSpaceApend("Fax Number", 50));
        sb.append(getSpaceApend("Email", 50));
        sb.append(getSpaceApend("Mobile NO.", 50));
        sb.append(getSpaceApend("Charge on", 13));
        sb.append(getSpaceApend("Product Type", 3));
        sb.append(getSpaceApend("Schedule Time", 5));
        sb.append(getSpaceApend("", 34));
        sb.append(getSpaceApend("DocReq", 105));
        sb.append(getSpaceApend("", 295));
        sb.append("END").append("\r\n");
        return sb.toString();
    }

    public static String getWHT() {
        StringBuilder sb = new StringBuilder();
        sb.append("WHT");
        sb.append(getSpaceApend("", 13));
        sb.append(getSpaceApend("TaxId", 13));
        sb.append(getSpaceApend("", 2));
        sb.append(getAmountApend(0d, 15));
        sb.append(getSpaceApend("", 2));
        sb.append(getSpaceApend("Description", 35));
        sb.append(getSpaceApend("Tax Rate", 5));
        sb.append(getAmountApend(0d, 15));//tax amount
        sb.append(getAmountApend(0d, 15));//tax able amt
        sb.append(getSpaceApend("", 2));
        sb.append(getSpaceApend("Description", 35));
        sb.append(getSpaceApend("Tax Rate", 5));
        sb.append(getAmountApend(0d, 15));//tax amount
        sb.append(getSpaceApend("", 144));
        sb.append(getSpaceApend(companyName, 120));
        sb.append(getSpaceApend("company address", 160));
        sb.append(getSpaceApend("", 120));
        sb.append(getSpaceApend("", 160));
        sb.append(getSpaceApend("", 20));//第六列 没header
        sb.append(getSpaceApend("", 938));
        return sb.toString();
    }

    private static String getReceiveBRCode(String bankCode, String payeeAC){
        StringBuilder tmp = new StringBuilder();
        if(bankCode.startsWith("002") || bankCode.startsWith("004") ||
            bankCode.startsWith("006") || bankCode.startsWith("011") ||
            bankCode.startsWith("014") || bankCode.startsWith("015") ||
            bankCode.startsWith("020") || bankCode.startsWith("022") ||
            bankCode.startsWith("024") || bankCode.startsWith("025") ||
            bankCode.startsWith("033") || bankCode.startsWith("065") ||
            bankCode.startsWith("069") || bankCode.startsWith("073") || bankCode.startsWith("066")){
            tmp.append("0").append(payeeAC, 0, 3);
        }else if(bankCode.startsWith("030") || bankCode.startsWith("067") || bankCode.startsWith("072")){
            tmp.append(payeeAC, 0, 4);
        }else if(bankCode.startsWith("034")){
            tmp.append("0000");
        }else{
            tmp.append("0001");
        }
        return tmp.toString();
    }
}