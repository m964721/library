package com.app.toolboxlibrary;

/**
 * @ClassName: PaymentUtil
 * @Description: 日期工具类
 * @author: PPX
 */
public class PaymentUtil {

    /**
     * 计算手续费
     *
     * @param amt  输入金额
     * @param rate 费率，string类型的
     * @return
     */
    public static String getFee(String amt, String rate) {

        if (StringUtils.isBlank(amt)) {
            return "0.00";
        }
        if (StringUtils.isBlank(rate)) {
            return "0.00";
        }
        double dbamt = Double.parseDouble(amt);
        double dbrate = Double.parseDouble(rate);
        double fee = dbamt * dbrate + 0.005;
        return DataUtils.formatPriceToString(fee, 2);
    }

    /**
     * 计算手续费
     *
     * @param amt  输入金额
     * @param rate 费率，double类型
     * @return
     */
    public static String getFee(String amt, double rate) {

        if (StringUtils.isBlank(amt)) {
            return "0.00";
        }

        double dbamt = Double.parseDouble(amt);
        double dbrate = rate;
        double fee = dbamt * dbrate + 0.005;
        return DataUtils.formatPriceToString(fee, 2);
    }

    /**
     * 实际到账
     * amt 输入金额
     * fee 手续费
     *
     * @return str
     */
    public static String getRealAmt(String amt, String fee) {

        if (StringUtils.isBlank(amt)) {
            amt = "0.00";
        }
        if (StringUtils.isBlank(fee)) {
            fee = "0.00";
        }

        double dbamt = Double.parseDouble(amt);
        double dbfee = Double.parseDouble(fee);
        double dbrealamt = dbamt - dbfee + 0.001;

        return DataUtils.formatPriceToString(dbrealamt, 2);
    }

    /**
     * 实际到账
     * amt 输入金额
     * fee 手续费
     *
     * @return str
     */
    public static String getRealAmt(String amt, double fee) {

        if (StringUtils.isBlank(amt)) {
            amt = "0.00";
        }

        double dbamt = Double.parseDouble(amt);
        double dbfee = fee;
        double dbrealamt = dbamt - dbfee + 0.001;

        return DataUtils.formatPriceToString(dbrealamt, 2);

    }


}