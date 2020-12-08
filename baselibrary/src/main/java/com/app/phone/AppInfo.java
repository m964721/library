package com.app.phone;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class AppInfo implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Drawable icon;
	private String appName;
	private String packageName;
	private boolean isSystemApp;
	private long codesize;

	public AppInfo() {
	}

	public AppInfo(Drawable icon, String appName, String packageName, boolean isSystemApp, long codesize) {
		this.icon = icon;
		this.appName = appName;
		this.packageName = packageName;
		this.isSystemApp = isSystemApp;
		this.codesize = codesize;
	}

	public long getCodesize() {
		return codesize;
	}
	public void setCodesize(long codesize) {
		this.codesize = codesize;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public boolean isSystemApp() {
		return isSystemApp;
	}
	public void setSystemApp(boolean isSystemApp) {
		this.isSystemApp = isSystemApp;
	}

	@Override
	public String toString() {
		return "AppInfo{" +
				"icon=" + icon +
				", appName='" + appName + '\'' +
				", packageName='" + packageName + '\'' +
				", isSystemApp=" + isSystemApp +
				", codesize=" + codesize +
				'}';
	}


    private String declaredMethod(int index) {
        String string = null;
        switch (index) {
            case 0:
                string = "I am declaredMethod 1 !";
                break;
            case 1:
                string = "I am declaredMethod 2 !";
                break;
            default:
                string = "I am declaredMethod 1 !";
        }

        return string;
    }

}
