package tableshop.ilinkedtech.com.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.webkit.WebSettings;

import java.io.File;
import java.util.List;

import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.ishop.PhotoPickActivity;

/*
 *  @项目名：  trunk 
 *  @包名：    com.ilinkedtech.utils
 *  @文件名:   SystemApiUtil
 *  @创建者:   Wilson
 *  @创建时间:  2017/4/18 11:23
 *  @描述：    TODO
 */
public class SystemApiUtil {
    private static final String TAG = "SystemApiUtil";

    /**
     * 拨打电话
     * @param mobile
     */
    public static void callMobile(String mobile) {
        Intent intent = new Intent(Intent.ACTION_DIAL);//ACTION_CALL 直接拨打
        Uri    data   = Uri.parse("tel:" + mobile);
        intent.setData(data);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }

    /**
     * 跳到发送短信页面
     * @param mobile
     */
    public static void sendMsg(String mobile){
        Uri uri2 = Uri.parse("smsto:"+mobile);
//        2、创建意图。
        Intent intentMessage = new Intent(Intent.ACTION_VIEW, uri2);
        intentMessage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        3、打开系统短信界面，号码已经填写，只需填写要发送
        UIUtils.getContext().startActivity(intentMessage);
    }


    /**
     * 进入选择图片界面
     */
    public static void showPhotoRoom(Activity activity,int requestCode) {
        Intent intent = new Intent(activity, PhotoPickActivity.class);
        intent.putExtra(PhotoPickActivity.IS_SHOW_CAMERA, true);
        activity.startActivityForResult(intent, requestCode);
    }
    /**
     * 进入选择图片界面
     */
    public static void showPhotoRoom(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), PhotoPickActivity.class);
        intent.putExtra(PhotoPickActivity.IS_SHOW_CAMERA, true);
        fragment.startActivityForResult(intent, requestCode);
    }




    public static boolean hasPermissions(Activity context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            if (permissions.length==1){
                if (context.checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
                    if (context.shouldShowRequestPermissionRationale(permissions[0])){
                        return true;
                    }else{
                        return false;
                    }

                }else{
                    return true;
                }
            }
            for (String permission : permissions) {
                if (context.checkSelfPermission( permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } else {
            return true;
        }
        return true;

    }


    public static void initWebViewSetting(WebSettings mWebSettings){
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSavePassword(false);//密码明文存储漏洞  关闭密码保存提醒

//        LOAD_CACHE_ONLY:  不使用网络，只读取本地缓存数据
//        LOAD_DEFAULT:  根据cache-control决定是否从网络上取数据。
//        LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
//        LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
//        LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //域控制不严格漏洞 的 修复方式
        mWebSettings.setAllowFileAccess(false);
        mWebSettings.setAllowFileAccessFromFileURLs(false);
        mWebSettings.setAllowUniversalAccessFromFileURLs(false);



    }


    public static void clearWebViewCache(Activity activity){
        //WebView 缓存文件
        File appCacheDir = new File(activity.getFilesDir().getAbsolutePath()+ Const.DEBUG_TAG);
        LogUtils.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(activity.getCacheDir().getAbsolutePath()+"/webviewCache");
        LogUtils.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            FileUtil.deleteDir(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            FileUtil.deleteDir(appCacheDir);
        }
        activity.deleteDatabase("WebView.db");
        activity.deleteDatabase("webview.db");
        activity.deleteDatabase("WebViewCache.db");
        activity.deleteDatabase("webviewCache.db");
    }


    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static int getAppSatus(Context context, String pageName) {

        ActivityManager                       am   = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }


    /**
     * 获取进程名
     * @param cxt
     * @param pid
     * @return
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
