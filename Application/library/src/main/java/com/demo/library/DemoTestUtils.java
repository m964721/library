package com.demo.library;

public class DemoTestUtils {

    private static volatile DemoTestUtils singleton = null;

    private DemoTestUtils() {}

    public static DemoTestUtils getInstance() {
        if (singleton == null) {
            synchronized (DemoTestUtils.class) {
                if (singleton == null) {
                    singleton = new DemoTestUtils();
                }
            }
        }
        return singleton;
    }

    public String backSting(){
        return "123";
    }
}
