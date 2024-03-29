package tableshop.ilinkedtech.com.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import tableshop.ilinkedtech.com.consts.Const;

/*
 *  @项目名：  ICan2--kuangjia
 *  @包名：    com.ilinkedtech.utils.chens
 *  @文件名:   NetUtils
 *  @创建者:   Wilson
 *  @创建时间:  2017/6/29 16:03
 *  @描述：    TODO 网络连接帮助类
 */
public class NetUtils {

	/**
	 * 获得网络连接是否可用
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con      = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo         workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			Const.offNet =true;
			return false;
		}
		Const.offNet =false;
		return true;
	}

	/**
	 * 判断是否是wifi连接
	 */
	public static boolean checkWifiState(Context context) {
		boolean             isWifiConnect = true;
		ConnectivityManager cm            = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[]       networkInfos  = cm.getAllNetworkInfo();
		for (int i = 0; i < networkInfos.length; i++) {
			if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
				if (networkInfos[i].getType() == cm.TYPE_MOBILE) {
					isWifiConnect = false;
				}
				if (networkInfos[i].getType() == cm.TYPE_WIFI) {
					isWifiConnect = true;
				}
			}
		}
		return isWifiConnect;
	}

	/**
	 * 打开网络设置界面
	 */
	public static void openNet(Activity activity) {
		Intent        intent = new Intent("/");
		ComponentName cm     = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}
}
