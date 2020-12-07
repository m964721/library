package com.app.toolboxlibrary;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {

    public static String cardJiaMi(String cardnum) {
        if (null != cardnum) {
            int length = cardnum.length();
            return cardnum.substring(0, 6) + "*********"
                    + cardnum.substring(length - 4, length);
        }
        return cardnum;
    }

    public static String getCardLastFour(String cardnum) {
        if (null != cardnum && cardnum.length() > 0) {
            int length = cardnum.length();
            if (length > 4) {
                return cardnum.substring(length - 4);
            }
        }
        return cardnum;
    }

    public static String certPidJiaMi(String certPid) {
        if (null != certPid) {
            int length = certPid.length();
            if (length == 18) {
                return certPid.substring(0, 6) + "******"
                        + certPid.substring(length - 4, length);
            }
            if (length == 15) {
                return certPid.substring(0, 6) + "*********"
                        + certPid.substring(length - 4, length);
            }

        }
        return certPid;
    }

    public static String phoneJiaMi(String phonenum) {
        if (phonenum != null) {
            int length = phonenum.length();
            if (length == 11) {
                return phonenum.substring(0, 3) + "****"
                        + phonenum.substring(7, 11);
            }

        }
        return phonenum;
    }

    public static String realNameJiaMi(String realName) {
        String name = "";
        if (null != realName && realName.length() > 0) {
            int length = realName.length();
            if (1 == length) {
                name = realName;
            } else if (2 == length) {
                name = realName.substring(0, 1) + "*";
            } else if (length > 2) {
                name = realName.substring(0, 1) + "*" + realName.substring(length - 1, length);
            }
        }
        return name;
    }

    public static String lengthLimit(int length, String ss) {
        if (null == ss) {
            return "";
        } else {
            if (ss.length() < length) {
                return ss;
            } else {
                return ss.substring(0, length) + "……";
            }
        }
    }

    public static String formatPrice(Double price) {
        final DecimalFormat df = new DecimalFormat("0.00");
        if (null == price || price == 0) {
            return "￥0.00";
        } else {
            return "￥" + df.format(price);
        }
    }

    public static String formatPriceToString(Double price) {
        final DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP); // 真正意义上的四舍五入，学校里教的
        if (null == price || price == 0) {
            return "0.00";
        } else {
            return "" + df.format(price);
        }
    }

    /**
     * 目前只处理保留小数点后1位和后9位,超出则不处理,直接返回空字串
     *
     * @param price  ：输入的金额
     * @param length ：输入的保留小数点长度
     * @return： 不做四舍五入，小数点后超出要求长度的部分直接舍弃
     */
    public static String formatPriceToString(Double price, int length) {

        if (length > 9 || length < 1)
            return "";

        Double divider = 1 / Math.pow(10, length + 1);
        final DecimalFormat df = new DecimalFormat(
                Double.toString(divider));

        df.setRoundingMode(RoundingMode.FLOOR);
        if (null == price || price == 0) {
            return "0.00";
        } else {
            String result = "" + df.format(price);
            return result.substring(0, result.length() - 1);
        }
    }

    /**
     * 将姓名的名字部分用*代替
     *
     * @param name
     * @return：如果名字为空，返回空字串
     */
    public static String formatNameWithStar(String name) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (null != name && name.length() > 1) {
            sb.append(name, 0, 1);
            for (int i = 1; i < name.length(); i++) {
                sb.append("*");
            }
            str = sb.toString();
        } else if (null != name && name.length() == 1) {
            str = name + "**";
        }
        return str;
    }

    /****
     * 功能：判断字符串是否为数字**
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr :身份证号
     * @return 有效：返回false 无效：返回false
     * @throws ParseException
     */
    @SuppressLint("DefaultLocale")
    public static boolean IDCardValidate(String IDStr) throws ParseException {

        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            return false;
        }

        // ================ 数字 除最后一位都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            return false;
        }
        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals((IDStr.toLowerCase())) == false) {
                return false;
            }
        } else {
            return true;
        }
        return true;
    }

    /**
     * @说明：校验手机号 return: boolean
     */
    public static boolean isCheckcertPhoNum(String cardPhoNum_str) {
        // ()表示手机号前三位:13，14,15，17,18手机号开头前两位;
        // []第三位数字，{8}后八位可以为[0-9]其中的任意数字
        String telRegex = "(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}";
        return cardPhoNum_str.matches(telRegex);
    }

    /**
     * 判断一个String字段是不是空值；
     */
    public static String checknull(String check_str) {
        if (null != check_str) {
            return check_str;
        }
        return "";
    }

    /**
     * 分段显示，string需要分段显示的，mark 是否需要前段显示的特殊符号
     */
    public static String subStringToShow(String string, String mark) {
        String show_str = "";
        if (null != string && string.length() > 0) {
            if ("-".equals(string.substring(0, 1))) {
                if (string.length() > 3) {
                    show_str = string.substring(0, string.length() - 2) + "." + string.substring(string.length() - 2);
                } else if (string.length() == 3) {
                    show_str = "-0." + string.substring(1);
                } else if (string.length() == 2) {
                    show_str = "-0.0" + string.substring(1);
                } else {
                    show_str = "0.00";
                }
            } else {
                if (string.length() > 2) {
                    show_str = mark + string.substring(0, string.length() - 2) + "." + string.substring(string.length() - 2);
                } else if (string.length() == 2) {
                    show_str = mark + "0." + string;
                } else {
                    show_str = mark + "0.0" + string;
                }
            }
        } else {
            show_str = "0.00";
        }
        return show_str;
    }

    //返回的时间，处理后显示
    public static String timeToShow(String dataTime) {
        String showtime = null;
        if (null != dataTime) {
            int size = dataTime.length();
            switch (size) {
                case 8:
                    showtime = dataTime.substring(0, 4) + "-" + dataTime.substring(4, 6) + "-" + dataTime.substring(6);
                    break;
                case 14:
                    showtime = dataTime.substring(0, 4) + "-" + dataTime.substring(4, 6) + "-" + dataTime.substring(6, 8)
                            + " " + dataTime.substring(8, 10) + ":" + dataTime.substring(10, 12)
                            + ":" + dataTime.substring(12);
                    break;
                default:
                    break;
            }
        } else {
            showtime = "";
        }
        return showtime;
    }

    //返回时间以xx年xx月显示
    public static String yearAndMonth(String dataTime) {
        String show_str = null;
        if (null != dataTime && dataTime.length() > 5) {
            show_str = dataTime.substring(0, 4) + "年" + Integer.parseInt(dataTime.substring(4, 6)) + "月";
        } else {
            show_str = "";
        }
        return show_str;
    }

    //终端号加密显示
    public static String encryptTermId(String TermId, String show_str) {
        String str_TermId = null;
        if (null != TermId) {
            if (TermId.length() > 9) {
                str_TermId = TermId.substring(0, 6) + "******"
                        + TermId.substring(TermId.length() - 5, TermId.length() - 1);
            } else {
                str_TermId = show_str;
            }
        } else {
            str_TermId = show_str;
        }
        return str_TermId;
    }

    /**
     * @return 无
     * @说明：判断是否为负数
     * @Parameters 无
     */
    public static String checkMoney(String money) {
        String data = "";
        if (null != money && money.length() > 0) {
            if ("-".equals(money.substring(0, 1)) || "0.00".equals(money)) {
                data = "0.00";
            } else {
                data = money;
            }
        }
        return data;
    }

    /**
     * 分段显示
     *
     * @param input
     * @return
     */
    public static String numSubToShow(String input, int subSize, String subData) {
        String num = "";
        if (null != input && input.length() > 0 && subSize > 0) {
            int flag = input.length() / subSize;
            if (flag > 0) {
                for (int i = 0; i <= flag; i++) {
                    if (input.length() > subSize * (1 + i)) {
                        num = num + input.substring(subSize * i, subSize * (1 + i)) + subData;
                    } else {
                        num = num + input.substring(subSize * i, input.length());
                    }
                }
            } else {
                num = input;
            }
        }
        return num;
    }

    /**
     * 数字末尾去零
     *
     * @param number
     * @return
     */
    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }

    /**
     * 过滤特殊字符，只能输入字母数字
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        // 只允许字母、数字,特殊符号
        String PASSWORD_REGEX = "[A-Z0-9a-z!@#$%^&*.~/\\{\\}|()'\"?><,.`\\+-=_\\[\\]:;]+";
        Pattern p = Pattern.compile(PASSWORD_REGEX);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    /**
     * 拼装url
     *
     * @return
     */
    public static String backUrl(String saveUrl , String netUrl) {
        String allUrl = "";
        if (!StringUtils.isStringToNUll(netUrl)) {
            if (netUrl.startsWith("http") || netUrl.startsWith("https")) {
                allUrl = netUrl;
            } else if (!StringUtils.isStringToNUll(netUrl)) {
                if (saveUrl.endsWith("/")) {
                    saveUrl = saveUrl.substring(0, saveUrl.length() - 1);
                }
                if (netUrl.startsWith("/")) {
                    netUrl = netUrl.substring(1);
                }
                allUrl = saveUrl + "/" + netUrl;
            }
        }
        return allUrl;
    }

    /**
     * 返回0.00格式的数据
     *
     * @return
     */
    public static String back00(String numStr) {
        String showData = numStr;
        try {
            Double d = Double.parseDouble(showData);
            DecimalFormat df = new DecimalFormat("#0.00");
            showData = df.format(d);
        } catch (Exception e) {

        }
        return showData;
    }
}
