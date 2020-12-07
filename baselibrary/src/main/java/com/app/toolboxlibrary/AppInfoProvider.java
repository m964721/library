package com.app.toolboxlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;


/**
 * 类名称：AppInfoProvider 类描述：获取应用程序的相关信息
 */
public class AppInfoProvider {
	/**
	 * 获取系统中所有应用信息， 并将应用软件信息保存到list列表中。
	 **/
//	public static List<AppInfo> getAllApps(Context context) {
//		String all_app = "";
//		PackageManager packageManager = context.getPackageManager();
//		String packageCodePath = context.getPackageCodePath();
//		PackageInfo infos = packageManager.getPackageArchiveInfo(packageCodePath, PackageManager.GET_ACTIVITIES);
//
//		List<AppInfo> list = new ArrayList<AppInfo>();
//		AppInfo myAppInfo;
//		// 获取到所有安装了的应用程序的信息，包括那些卸载了的，但没有清除数据的应用程序
//		List<PackageInfo> packageInfos = packageManager
//				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
//		for (PackageInfo info : packageInfos) {
//			myAppInfo = new AppInfo();
//			// 拿到包名
//			String packageName = info.packageName;
//			// 拿到应用程序的信息
//			ApplicationInfo appInfo = info.applicationInfo;
//			// 拿到应用程序的图标
//			Drawable icon = appInfo.loadIcon(packageManager);
//			// 拿到应用程序的大小
//			// 拿到应用程序的程序名
//			String appName = appInfo.loadLabel(packageManager).toString();
//			all_app = appName + " " + all_app;
//			myAppInfo.setPackageName(packageName);
//			myAppInfo.setAppName(appName);
//			myAppInfo.setIcon(icon);
//
//			if (filterApp(appInfo)) {
//				list.add(myAppInfo);
//			}
//
//		}
//		return list;
//
//	}

	/**
	 * 应用程序过滤
	 * 
	 * @param info
	 * @return true 第三方应用 false 系统应用
	 */
	public static boolean filterApp(ApplicationInfo info) {

		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			// 代表的是系统的应用,但是被用户升级了. 用户应用
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			// 代表的用户的应用
			return true;
		}
		return false;
	}
	
	/**
	 * 输出所有安装的包名与app名字
	 * */
	public static String getAllAppName(Context context){
		String all_app= "";
		PackageManager packageManager = context.getPackageManager();
		List<AppInfo> list = new ArrayList<AppInfo>();
		AppInfo myAppInfo;
		// 获取到所有安装了的应用程序的信息，包括那些卸载了的，但没有清除数据的应用程序
		List<PackageInfo> packageInfos = packageManager
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo info : packageInfos) {
			myAppInfo = new AppInfo();
			// 拿到包名
			String packageName = info.packageName;
			// 拿到应用程序的信息
			ApplicationInfo appInfo = info.applicationInfo;
			// 拿到应用程序的图标
			Drawable icon = appInfo.loadIcon(packageManager);
			// 拿到应用程序的大小
			// long codesize = packageStats.codeSize;
			// Log.i("info", "-->"+codesize);
			// 拿到应用程序的程序名
			String appName = appInfo.loadLabel(packageManager).toString();

			myAppInfo.setPackageName(packageName);
			myAppInfo.setAppName(appName);
			myAppInfo.setIcon(icon);

			if (filterApp(appInfo)) {
				list.add(myAppInfo);
				all_app = appName + " " + all_app;
			}

		}
		return all_app;
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Activity activity) {
		try {
			PackageManager manager = activity.getPackageManager();
			PackageInfo info = manager.getPackageInfo(activity
					.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	//根据Activity文件名获取实例
	public  static Class getClasses(Context context,String specClassName) {
		//截取specClassName里面的点，
		if(StringUtils.dataHasChar(specClassName,".")){
			int indexPoint= specClassName.lastIndexOf(".") ;
			if(indexPoint+1<specClassName.length()){
				specClassName = specClassName.substring(indexPoint+1);
			}
		}
		Class specClass = null;
		String classname = "";
		PackageManager packageManager = context.getPackageManager();
		String packageCodePath = context.getPackageCodePath();
		PackageInfo infos = packageManager.getPackageArchiveInfo(packageCodePath, PackageManager.GET_ACTIVITIES);
		if (null != infos && null != infos.activities && infos.activities.length > 0) {
			for (ActivityInfo activityInfo : infos.activities){
				if (null!= activityInfo.name && activityInfo.name.contains(specClassName)) {
					int index = activityInfo.name.lastIndexOf(".");
					if (index != -1) {
						String activity = activityInfo.name.substring(index+1,activityInfo.name.length());
						if (specClassName.equals(activity)) {
							classname = activityInfo.name;
							break;
						}
					}
				}
			}
		}
		LogUtil.showLog("AppInfoProvider","classname:"+classname);
		if (null != classname && !classname.equals("")) {
			specClass = getClassToJump(classname);
		}
		return specClass;
	}

	/**
	 * 根据包名获取Class实例
	 *
	 * @param packageName
	 * @param name
	 * @return
	 */
	public static Class getClassInstanceByName(String packageName, String name) {
		Class instance = null ;
		try {
			instance = Class.forName(packageName + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		LogUtil.showLog("AppInfoProvider","classname:"+instance);
		return instance;
	}

	/**
	 * 根据文件名获取Class实例
	 * @return
	 */
	public static Class getClassToJump(String className) {
		Class instance = null;
		try {
			instance = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return instance;
	}
}
