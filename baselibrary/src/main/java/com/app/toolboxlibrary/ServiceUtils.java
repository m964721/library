package com.app.toolboxlibrary;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * com.imobpay.tools.ServiceUtils Create at 2016-9-12 上午10:13:12
 * 
 * @author Hanpengfei
 * @说明：处理有关service的方法
 */
public class ServiceUtils {


	/**
	 * 判断Service是否启动
	 * @param context 调用方法的Activity
	 * @param serviceAction 需要关闭服务的服务名
     * @return
     */
	public static boolean isWorked(Context context, String serviceAction) {
		ActivityManager myManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(30);//30设置已启动服务的最大数，可以更改
		if (null != runningService && runningService.size()>0) {
			for (int i = 0; i < runningService.size(); i++) {
				if (runningService.get(i).service.getClassName().toString()
						.equals(serviceAction)) {
					return true;
				}
			}
		}
		return false;
	}
}
