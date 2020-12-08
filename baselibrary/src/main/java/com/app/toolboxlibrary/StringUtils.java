package com.app.toolboxlibrary;

import java.util.List;

/**
 * 类说明： 字符串操作类
 */
public class StringUtils {


    /**
     * 判断给定字符串是否空白串。<br>
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串<br>
     * 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isBlank(String input) {
        if (null == input || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查中文名输 入是否正确
     *
     * @param value
     * @return
     */
    public boolean checkChineseName(String value, int length) {
        return value.matches("^[\u4e00-\u9fa5]+$") && value.length() <= length;
    }


    /**
     * 检查中文名输 入是否正确
     *
     * @param value
     * @return
     */
    public static boolean checkChineseName(String value) {
        return value.matches("^[\u4e00-\u9fa5]+$");
    }

    /**
     * 检查输入数据是否为空，或者空指针
     *
     * @param input
     * @return
     */
    public static boolean isByteArrayBlank(byte[] input) {
        boolean ret = false;
        if (null == input || "".equals(input)) {
            ret = true;
        }
        return ret;
    }


    /**
     * 检查输入数据是否为空，或者空指针
     *
     * @param input
     * @return
     */
    public static String backInput(String input) {
        String backInput = "" ;
        if(isStringToNUll(input)){
            backInput = "" ;
        }else{
            backInput = input ;
        }
        return backInput;
    }

    /**
     * 检查输入数据是否为空，或者空指针
     *
     * @param input
     * @return
     */
    public static boolean isStringToNUll(String input) {
        boolean ret = false;
        if (null == input || "".equals(input) || "null".equals(input)) {
            ret = true;
        }
        return ret;
    }

    /**
     * 按照固定格式获取左边数据
     *
     * @param value
     * @return
     */
    public static String getLeftValue(String value) {
        String[] values;
        if (value != null) {
            values = value.split("\\|");
            return values[0];
        }
        return "";
    }

    /**
     * 按照固定格式获取右边数据
     *
     * @param value
     * @return
     */
    public static String getRightValue(String value) {
        String[] values;
        if (value != null) {
            values = value.split("\\|");
            if (values.length == 2) {
                return values[1];
            }
            return "";
        }
        return "";
    }

    /**
     * 判断字符创是否包含某个字符
     *
     * @param input
     * @param char_str
     * @return
     */
    public static boolean dataHasChar(String input, String char_str) {
        boolean isHas = false;
        if (null != input && !"".equals(input)) {
            if (input.indexOf(char_str) != -1) {
                isHas = true;
            }
        }
        return isHas;
    }

    /**
     * 返回默认0值
     *
     * @param check_str
     * @return
     */
    public static String checkZero(String check_str) {
        if (null != check_str && isNumberStr(check_str)) {
            return check_str;
        }
        return "0";
    }

    /**
     * 返回默认0值
     *
     * @param check_str
     * @return
     */
    public static boolean checkDayuZero(String check_str) {
        boolean isDayu0 = false;
        if (isNumberStr(check_str)) {
            if (Double.valueOf(check_str) > 0) {
                isDayu0 = true;
            }
        }
        return isDayu0;
    }

    /**
     * 判断一个字符串是否全为数字
     *
     * @param numStr
     * @return
     */
    public static boolean isNumberStr(String numStr) {
        boolean ret = false;
        if (null != numStr && !"".equals(numStr.trim())) {
            try {
                Double.valueOf(numStr);//把字符串强制转换为数字
                ret = true;//如果是数字，返回True
            } catch (Exception e) {
                ret = false;//如果抛出异常，返回False
            }
        }
        return ret;
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @param str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

    /**
     * 验证是否含有某个字段
     *
     * @param data    验证的字段
     * @param hasData 包含某个字段
     * @return
     */
    public boolean contains(String data, String hasData) {
        boolean isHas = false;
        if (data.toLowerCase().contains(hasData.toLowerCase())) {
            isHas = true;
        }
        return isHas;
    }


    /**
     * 将日期格式化   20171112-》 2017/11/12
     *
     * @param data   输入字符
     * @param
     * @return
     */
    public static String formatDateAndSpLite(String data, String spLite) {
        if (null == data) {
            return "";
        }
        if (data.length() == 8) {
            return data.substring(0, 4) + spLite + data.substring(4, 6) + spLite + data.substring(6, 8);
        }
        return data;
    }


    /**
     * 将日期格式化   20171112-》 11月12日
     *
     * @param data 输入字符
     * @return
     */
    public static String formatDateWithMonthAndDay(String data) {
        if (null == data) {
            return "";
        }
        if (data.length() == 8) {
            return data.substring(4, 6) + "月" + data.substring(6, 8) + "日";
        }
        return data;
    }

    /**
     * 去掉url中的路径,获取文件名
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    public static String getNameFromUrl(String strURL) {
        String strAllParam = "";
        String[] arrSplit = null;
        strURL = strURL.trim().toLowerCase();
        arrSplit = strURL.split("[?]");

        if (strURL.length() > 1) {
            if (arrSplit.length > 0) {
                if (arrSplit[0].contains(".apk")) {
                    strAllParam = arrSplit[0].substring(arrSplit[0].lastIndexOf("/") + 1);
                }
            }
        }
        return strAllParam;
    }


    /**
     * 返回长度最大的数据
     *
     * @param array
     * @return
     */
    public static String backArrayStringMax(List<String> array) {
        String str = "";
        if (null != array && array.size() > 0) {
            int max = array.get(0).length();
            boolean isHasPoint = false;
            for (int i = 0; i < array.size(); i++) {
                if (!isHasPoint) {
                    isHasPoint = dataHasChar(array.get(i), ".");
                }
                if (max < array.get(i).length()) {
                    max = array.get(i).length();
                    str = array.get(i);
                }
            }
        }
        str = str.replace(".", "") + ".";
        return str;
    }

    //返回需要补充的数据
    public static String backFillStr(String maxData, String oldData, String fillData) {
        String str = "";
        if (dataHasChar(oldData, ".")) {
            maxData = maxData.replace(".", "");
            oldData = oldData.replace(".", "");
            if (oldData.length() < maxData.length()) {
                str = maxData.substring(oldData.length()) + fillData;
            }
        } else {
            if (oldData.length() < maxData.length()) {
                str = maxData.substring(oldData.length()) + fillData;
            }
        }
        return str;
    }

    public static int backMinSize(List<Integer> array) {
        if (null != array && array.size() > 0) {
            int max = array.get(0);
            for (int i = 0; i < array.size(); i++) {
                if (max > array.get(i)) {
                    max = array.get(i);
                }
            }
            return max;
        }
        return 0;
    }

    //时间格式 0000-00-00
    public static String backMonthAndDay(String date){
        String backDate = "" ;
        if(!StringUtils.isStringToNUll(date)){
            backDate = date.length()>10?date.substring(0,10):backDate;
        }
        return backDate ;
    }

    //检查内容是否为空并且返回默认值
    public static String backDefaultStr(String needCheckStr ,String defaulrStr){
        if(isStringToNUll(needCheckStr)){
         return defaulrStr;
        }
        return needCheckStr;
    }

    public static String fill10(int t) {
        return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
    }
}