package tableshop.ilinkedtech.com.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.ArrayMap;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 创建者     wilson
 * 描述	      ${TODO}
 */
public class CommonUtils {

	public static final String newVersionName = "mysale.apk";

	/**
	 * 判断包是否安装
	 *
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isInstalled(Context context, String packageName)
	{
		PackageManager manager = context.getPackageManager();
		try {
			manager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);

			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 安装应用程序
	 *
	 * @param context
	 * @param apkFile
	 */
	public static void installApp(Context context, File apkFile)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri data;
		// 判断版本大于等于7.0
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			// "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
			data = FileProvider.getUriForFile(context,
											  context.getPackageName() + ".fileprovider",
											  apkFile);
			// 给目标应用一个临时授权
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		} else {
			data = Uri.fromFile(apkFile);
		}
		intent.setDataAndType(data, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 打开应用程序
	 *
	 * @param context
	 * @param packageName
	 */
	public static void openApp(Context context, String packageName)
	{
		Intent intent = context.getPackageManager()
							   .getLaunchIntentForPackage(packageName);
		context.startActivity(intent);
	}


	/**
	 * 关闭栈顶activity
	 * @param application
	 */
	public static void clossTopAct(Activity application) {
		String actName = topAct(application);
		Class  clz           = null;
		try {
			clz = application.getClass()
							 .forName("android.app.ActivityThread");

			Method meth                  = clz.getMethod("currentActivityThread");
			Object currentActivityThread = meth.invoke(null);
			Field  f                     = clz.getDeclaredField("mActivities");
			f.setAccessible(true);
			ArrayMap obj = (ArrayMap) f.get(currentActivityThread);
			for (Object key : obj.keySet()) {
				Object activityRecord = obj.get(key);
				Field actField = activityRecord.getClass()
											   .getDeclaredField("activity");
				actField.setAccessible(true);
				Object activity = actField.get(activityRecord);
				System.out.println(activity);
				Activity act1 = (Activity) activity;
				String act1N = act1.getClass().toString();// class
				// com.example.calledjar.MainActivity
				String act1Name = act1N.substring(6);// class
				// com.example.calledjar.MainActivity

				if (actName.equals(act1Name)) {
					act1.finish();
					return;
				}
			}


		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private static String topAct(Activity activity) {
		ActivityManager mAm           = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName   cn            = mAm.getRunningTasks(30)
										   .get(0).topActivity;
		return cn.getClassName();
	}



	public static void killProcess(Context context, final String packagename){
		ActivityManager                             am              = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> mRunningProcess = am.getRunningAppProcesses();
		int                                         pid             =-1;
		for (ActivityManager.RunningAppProcessInfo amProcess : mRunningProcess) {
			if (amProcess.processName.equals(packagename)) {
				pid = amProcess.pid;
				break;
			}
		}
		if (pid!=-1){
			android.os.Process.killProcess(pid);
		}
	}

}
