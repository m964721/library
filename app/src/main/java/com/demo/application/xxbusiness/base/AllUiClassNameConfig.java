package com.demo.application.xxbusiness.base;

/**
 * 存放跳转界面的文件名和报名
 */
public class AllUiClassNameConfig {
    private static volatile AllUiClassNameConfig allUiClassNameConfig = null ;
    //单例模式
    public static AllUiClassNameConfig getInstance(){
        if(null == allUiClassNameConfig){
            synchronized (AllUiClassNameConfig.class){
                if(null == allUiClassNameConfig){
                    allUiClassNameConfig = new AllUiClassNameConfig() ;
                }
            }
        }
        return allUiClassNameConfig ;
    }

    public String MainActivity = "AppMainActivity" ;//主界面文件名
    public String WelcomeActivity = "WelcomeActivity" ;//欢迎界面
    public String LoginActivity = "UserLoginActivity" ;//登录基面

}
