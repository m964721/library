package com.app.toolboxlibrary;

import android.graphics.drawable.Drawable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 *
 */
public class AboutJavaClass {

    public static void main(String args[]){
        String suijiString = "!@#$%^&*()Q5" ;
        suijiString = suijiString + "getDict119110" ;
        String jiamihou =  BitmapUtils.md5("getDict119110".getBytes());
        jiamihou = "5D:D2:3C:9D:66:00:BD:5F:0B:5E:0F:9C:B7:21:33:05:2A:30:BB:18" ;
//        System.out.print(jiamihou.replace(":",""));
        String mima = "Cpic12345!";
        //Q3BpYzEyMzQ1IQ==
        System.out.print(Base64Utils.encode(mima.getBytes()));

        maYiLend(
                new BigDecimal(Double.toString(20000)),
                new BigDecimal(Double.toString(0.00015)),
                new BigDecimal(Double.toString(12))
        );
    }

    // 反射私有的构造方法
    public static void reflectPrivateConstructor( Class<?> classBook ) {
        try {
            //此处传入的类型需要和newInstance（）传入的数据保持统一
            Constructor<?> declaredConstructorBook = classBook.getDeclaredConstructor(Drawable.class, String.class, String.class, boolean.class, long.class);
            declaredConstructorBook.setAccessible(true);
            //无参数的构造方法
            //Object objectBook = declaredConstructorBook.newInstance();
            Object objectBook = declaredConstructorBook.newInstance( null, "appName", "packageName", false, 0);
            AppInfo book = (AppInfo) objectBook;
            System.out.println("设置输出结果:"+book.toString() );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 反射私有属性
    public static void reflectPrivateField(Class<?> classBook) {
        try {
            Object objectBook = classBook.newInstance();
            //私有属性的 变量名称
            Field fieldTag = classBook.getDeclaredField("serialVersionUID");
            fieldTag.setAccessible(true);
            long tag = (long) fieldTag.get(objectBook);
            System.out.println("siyou shuxing:"+tag );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 反射私有方法
    public static void reflectPrivateMethod(Class<?> classBook) {
        try {
            //formatdatewithMonthandDay 方法名，
            //String.class，多个参数以此传入对象类型
            Method methodBook = classBook.getDeclaredMethod("declaredMethod",int.class);
            methodBook.setAccessible(true);
            Object objectBook = classBook.newInstance();
            //同理调用的时候需要传入对相应的参数
            String string = (String) methodBook.invoke(objectBook,0);
            System.out.println("reflectPrivateMethod 方法:"+string );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void maYiLend(final BigDecimal total, BigDecimal rate, BigDecimal period){//total总额  rate日利率   period期数
        Calendar[] dates=new Calendar[period.intValue()];
        Calendar now=Calendar.getInstance();
        setTimeToMidnight(now);
        int now_year = now.get(Calendar.YEAR);
        int now_month = now.get(Calendar.MONTH);
        int now_day = now.get(Calendar.DAY_OF_MONTH);
        System.out.println("now_day :"+now_day);
        int year=now_year;
        int month = now_day > 15 ? now_month+1:now_month;
        int day=10;//每月10号
        Repayment repays[]=new Repayment[period.intValue()];//保存每期还款信息
        for(int i=0;i<period.intValue();i++){
            repays[i]=new Repayment();
            if(++month>11){
                month=0;
                year++;
            }
            Calendar c=Calendar.getInstance();
            setTimeToMidnight(c);
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            dates[i]=c;
        }
        BigDecimal average=total.divide(period,2, BigDecimal.ROUND_DOWN);//平均每月还款，最开始设为贷款总额/期数
        BigDecimal remain=null;//剩余未还的
        BigDecimal step=new BigDecimal(Double.toString(0.01));
        do {
            if(remain!=null) {
                average = average.add(remain.divide(period, 2, BigDecimal.ROUND_DOWN));//平均还款=平均还款(上次)+(剩余(上次)/期数)  保留两位小数点
            }
            remain = total;
            long lastTime = now.getTimeInMillis();
            for (int i = 0; i < period.intValue(); i++) {
                long rePlayTime = dates[i].getTimeInMillis();//还款时间
                BigDecimal days = new BigDecimal(Double.toString(millisecondsToDays(rePlayTime - lastTime)));//计算与上一次还款日间隔(天数)
                BigDecimal interest =days.multiply(rate).multiply(remain).setScale(2,BigDecimal.ROUND_DOWN);//计算当期利息，天数*日利率*剩余未还金额
                BigDecimal principal = average.subtract(interest);
                repays[i].date=dates[i];
                repays[i].interest =interest;
                repays[i].principal =principal;
                remain=remain.subtract(principal);
                if(i==period.intValue()-1){//最后一次还款加上(或减)余数
                    repays[i].principal =repays[i].principal.add(remain);
                }
                repays[i].payment =interest.add(repays[i].principal);
                lastTime = rePlayTime;
            }
        }while(remain.abs().compareTo(step.multiply(period))>=0);//如果剩余未还的绝对值大于等于(期数*0.01)，则继续分割
        BigDecimal bigDecimal = new BigDecimal("0");
        for(int i=0;i<period.intValue();i++){
            Repayment repay = repays[i];
            bigDecimal = bigDecimal.add(repays[i].interest );
            System.out.println("第"+(i+1)+"期  时间："+repay.date.get(Calendar.YEAR)+"年"+(repay.date.get(Calendar.MONTH)+1)+"月"
                    +repay.date.get(Calendar.DAY_OF_MONTH)+"日 还款:"+repay.payment+" = 本金:"+repays[i].principal +" + 利息:"+repays[i].interest);
        }
        System.out.println("总利息 :"+bigDecimal);
    }

    static class Repayment {
        Calendar date;
        BigDecimal interest;
        BigDecimal principal;
        BigDecimal payment;
    }

    static void setTimeToMidnight(Calendar calendar) {//设置每天12点0分0秒为分割点
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    static int millisecondsToDays(long intervalMs) {//计算天数
        return (int) (intervalMs / (1000 * 86400));
    }
}
