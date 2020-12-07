package com.app.toolboxlibrary;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * com.imobpay.tools.EdittextUtils Create at 2016-9-18
 * 上午11:53:26
 *
 * @author Hanpengfei
 * @说明：edittext 输入框，显示字符分段显示， 分为：1：手机号 2：身份证号 3：银行卡号
 */
public class EdittextUtils {

    // 输入的内容
    private static String oldStr = "";

    /**
     * @说明：身份证，分段显示6-4-4-4 replaceFlag为1表示replace掉特殊符号
     * 2:表示分段显示
     */
    public static String iDNumShow(String result, String replaceFlag) {
        if (null != result && result.length() > 0) {
            if (replaceFlag.equals("1")) {
                result = result.replaceAll("\\d{4}(?!$)", "$0 ");
            } else if (replaceFlag.equals("2")) {
                StringBuilder sb = new StringBuilder(result);

                if (sb.length() >= 7 && sb.length() < 11) {
                    sb.insert(6, " ");
                    return sb.toString();
                }
                if (sb.length() >= 11 && sb.length() < 15) {
                    sb.insert(6, " ");
                    sb.insert(11, " ");
                    return sb.toString();
                }
                if (sb.length() >= 15 && sb.length() <= 18) {
                    sb.insert(6, " ");
                    sb.insert(11, " ");
                    sb.insert(16, " ");
                    return sb.toString();
                }
                return sb.toString();
            }
        }
        return result;
    }

    /**
     * 银行卡卡号输入，4-4-4-4-4-4-4
     */
    public static String cardNumShow(String result, String separateTyp) {
        if (separateTyp.equals("1")) {
            result = result.replaceAll("\\d{4}(?!$)", "$0 ");
        } else if (separateTyp.equals("2")) {
            StringBuilder sb = new StringBuilder(result);

            if (sb.length() > 4 && sb.length() < 9) {
                sb.insert(4, " ");
                return sb.toString();
            }
            if (sb.length() > 8 && sb.length() < 13) {
                sb.insert(4, " ");
                sb.insert(9, " ");
                return sb.toString();
            }
            if (sb.length() > 12 && sb.length() < 17) {
                sb.insert(4, " ");
                sb.insert(9, " ");
                sb.insert(14, " ");
                return sb.toString();
            }
            if (sb.length() > 16 && sb.length() < 21) {
                sb.insert(4, " ");
                sb.insert(9, " ");
                sb.insert(14, " ");
                sb.insert(19, " ");
                return sb.toString();
            }
            if (sb.length() > 20 && sb.length() < 25) {
                sb.insert(4, " ");
                sb.insert(9, " ");
                sb.insert(14, " ");
                sb.insert(19, " ");
                sb.insert(24, " ");
                return sb.toString();
            }
        }
        return result;
    }

    /**
     * 手机号输入显示3-4-4
     */
    public static String phoneNumShow(String result, String separateTyp) {
        if (separateTyp.equals("1")) {
            result = result.replaceAll("\\d{4}(?!$)", "$0 ");
        } else if (separateTyp.equals("2")) {
            StringBuilder sb = new StringBuilder(result);

            if (sb.length() > 3 && sb.length() < 8) {
                sb.insert(3, " ");
                return sb.toString();
            }
            if (sb.length() > 7 && sb.length() < 12) {
                sb.insert(3, " ");
                sb.insert(8, " ");
                return sb.toString();
            }
            if (sb.length() > 11 && sb.length() < 16) {
                sb.insert(3, " ");
                sb.insert(8, " ");
                sb.insert(13, " ");
                return sb.toString();
            }
        }
        return result;
    }

    /***
     * 根据editor内容调整光标位置 插入时，检查后一位是否为空格，
     * 如果是光标往前进一位 删除时，如果光标位置在空格前，则维持不变；否则调整
     *
     * @param pos
     */
    public static void adjustEditorCursor(int pos, EditText inputEditText,
                                          String inputStr) {
        if (oldStr.length() < inputStr.length()) // insert
        {
            if (pos == inputStr.length()) {
                inputEditText.setSelection(pos);
            } else {
                if (pos > 1) {
                    if (inputStr.charAt(pos - 1) == ' ') {
                        inputEditText.setSelection(pos + 1); // 多了一个空格
                    } else {
                        inputEditText.setSelection(pos);
                    }
                } else if (pos == 1) {
                    inputEditText.setSelection(1);
                }
            }
        } else if (oldStr.length() > inputStr.length()) // delete
        {
            if (pos == 0) {
                inputEditText.setSelection(pos);
            } else {
                if (oldStr.charAt(pos - 1) == ' ') {
                    inputEditText.setSelection(pos - 1); // 多了一个空格
                } else {
                    inputEditText.setSelection(pos);
                }
            }
        } else // 如果相等，说明当前光标在空格前，那么位置无无需调整，回到空格后
        {
            inputEditText.setSelection(pos);
        }
        oldStr = inputStr;
    }

    /**
     * 计算器输入规则
     */
    public static String CalculatorInput(String input, int subSize, String subData) {
        String calculatorInput = "";
        boolean isInputPoint = StringUtils.dataHasChar(input, ".");//判断是否输入点,
        if (null != input && !"".equals(input)) {
            //输入过小数点
            if (isInputPoint) {
                //小数点不在第一位
                if (input.indexOf(".") > 0) {
                    input = input.replace(",", "");//去除分隔符或者其他符号
                    int point_index = input.indexOf(".");//拿到输入小数点对应的下标
                    int input_leng = input.length();//输入的字符串的长度
                    String front_point = input.substring(0, point_index);//小数点前面的字符
                    String befor_point = "";//小数点后面的字符
                    if (input_leng >= point_index + 3) { //如果小数点下标加3 小于输入字符长度，小数点不在倒数第三位，截取到小数点后两位
                        befor_point = input.substring(point_index, point_index + 3);
                        if (0 == Integer.parseInt(befor_point.replace(".", ""))) {
                            //小数点后只能输入一个0，输入多个零进行截取
                            befor_point = input.substring(point_index, point_index + 2);
                        }
                    } else {
                        //剩余的截取（.X或.XX）
                        befor_point = input.substring(point_index);
                    }
                    input = DataUtils.numSubToShow(backSubData(front_point, isInputPoint), subSize, subData) + befor_point;
                } else {
                    //输入第一位为小数点
                    int intput_leng = input.length();
                    if (intput_leng >= 3) {
                        input = "0" + input.substring(0, 3);
                    } else {
                        input = "0" + input;
                    }
                }
            } else {
                //未输入过小数点
                String front_point = backSubData(input.replace(",", ""), isInputPoint);
                input = DataUtils.numSubToShow(front_point, subSize, subData);
            }
        }
        calculatorInput = input;
        return calculatorInput;
    }

    /**
     * 处理数据，没有输入小数点，
     *
     * @param front_point 截取小数点前面的字段
     * @param isHasPoint  是否输入小数点
     * @return
     */
    private static String backSubData(String front_point, boolean isHasPoint) {
        if (null != front_point && front_point.length() > 0) {
            int front_leng = front_point.length();
            if (front_leng > 9) {//输入长度大于9位进行截取
                front_point = front_point.substring(0, 9);
            } else {
                if (!isHasPoint) {
                    if (front_leng == 2) {
                        //判断是否输入多个零，截取第一位的0
                        if (0 == Integer.parseInt(front_point.substring(0, 1))) {
                            front_point = front_point.substring(1);
                        }
                    }
                }
            }
        }
        return front_point;
    }


    // 限制输入框不能输入汉字
    public static void StringWatcher(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (c >= 0x4e00 && c <= 0X9fff) {
                            s.delete(i, i + 1);
                        }
                    }
                }
            }
        });
    }

    // 输入的内容
    private static String oldNumSubStr = "";

    //删除分隔符
    public static void setEditorCursor(int pos, EditText inputEditText, String inputStr) {
        if (oldNumSubStr.length() < inputStr.length()) // insert
        {
            if (pos == inputStr.length()) {
                inputEditText.setSelection(pos);
            } else {
                if (pos > 1) {
                    if (inputStr.charAt(pos - 1) == ',') {
                        inputEditText.setSelection(pos + 1); // 多了一个空格
                    } else {
                        inputEditText.setSelection(pos);
                    }
                } else if (pos == 1) {
                    inputEditText.setSelection(1);
                }
            }
        } else if (oldNumSubStr.length() > inputStr.length()) // delete
        {
            if (pos == 0) {
                inputEditText.setSelection(pos);
            } else {
                if (oldNumSubStr.charAt(pos - 1) == ',') {
                    inputEditText.setSelection(pos - 1); // 多了一个空格
                } else {
                    inputEditText.setSelection(pos);
                }
            }
        } else // 如果相等，说明当前光标在空格前，那么位置无无需调整，回到空格后
        {
            inputEditText.setSelection(pos);
        }
        oldNumSubStr = inputStr;
    }
}
