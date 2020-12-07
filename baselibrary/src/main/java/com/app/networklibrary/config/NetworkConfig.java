package com.app.networklibrary.config;

/**
 * 自行配置
 */
public class NetworkConfig {

    static String DEBUG = "test";

    static String LocalUrl = "http://192.168.100.93:8081/";//本地

    static String TestUrl = "http://ghy-admin-test.gobestsoft.cn/gbs/api/";//test环境

    static String DemoUrl = "https://ghy-webapp-demo.gobestsoft.cn/gbs/api/";//demo环境

    static String DevUrl = "http://ghy-gateway-dev.gobestsoft.cn/gbs/api/";//dev环境

    static String ProURL = "https://djcloud.gobestsoft.cn/gbs/api/"; //生产环境

    static String TestDoMain = "ghy-fjveec.gobestsoft.cn";//租户

    static String DemoDoMain = "ghy-uyfygc-demo.gobestsoft.cn";//租户

    static String DevDoMain = "ghy-barcvc-dev.gobestsoft.cn";//租户

    static String ProDoMain = "ghy-barcvc-dev.gobestsoft.cn";//租户

    static String LocalDoMain = "ghy-barcvc-dev.gobestsoft.cn";//租户


    public static String getHost() {
        String url = "";
        switch (DEBUG) {
            case "test":
                url = TestUrl;
                break;
            case "demo":
                url = DemoUrl;
                break;
            case "production":
                url = ProURL;
                break;
            case "local":
                url = LocalUrl;
                break;
            case "dev":
                url = DevUrl;
                break;
            default:
                break;
        }
        return url;
    }

    public static String getDoMain() {
        String url = "";
        switch (DEBUG) {
            case "test":
                url = TestDoMain;
                break;
            case "demo":
                url = DemoDoMain;
                break;
            case "production":
                url = ProDoMain;
                break;
            case "local":
                url = LocalDoMain;
                break;
            case "dev":
                url = DevDoMain;
                break;
            default:
                break;
        }
        return url;
    }

    public static String PDUrl = "https://djcloud.gobestsoft.cn/gbs/api/"; // 生产环境
//    public static String LoginUrl = "http://gateway-ci-demo.gobestsoft.cn/";
//    public static String Cookie =  "SERVICE_ID=3; DEMO_GBS_SEED=44676ea024048fe30a871d2bb343a99efe79a1bbbca099b31809c4298027ad09; GBS_REDIRECT_URL_CLIENT=https://ghy-uyfygc-demo.gobestsoft.cn/home/homepage; REDIRECT_URL=https://ghy-uyfygc-demo.gobestsoft.cn";
//    public static String EnvironmentPrefix = "DEMO_";
    public static String LoginUrl = "https://gateway-ci-test.gobestsoft.cn/";
    public static String Cookie = "SERVICE_ID=3; TEST_GBS_SEED_CLIENT=7b52f2248e3b23082f4609ac07500e45b076e372f17e725bcad49f70acd720d3; GBS_REDIRECT_URL_CLIENT=https://ghy-fjveec.gobestsoft.cn; REDIRECT_URL=https://ghy-fjveec.gobestsoft.cn" ;
    public static String EnvironmentPrefix = "TEST_";
}
