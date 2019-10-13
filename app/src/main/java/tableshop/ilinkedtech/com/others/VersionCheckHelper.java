package tableshop.ilinkedtech.com.others;

/*
 *  @文件名:   VersionCheckHelper
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/29 10:10
 *  @描述：    TODO
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.beans.VersionCheckRequestBean;
import tableshop.ilinkedtech.com.beans.VersionCheckResponBean;
import tableshop.ilinkedtech.com.callBacks.MyICanFileCallBack;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.CommonUtils;
import tableshop.ilinkedtech.com.utils.FileUtil;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.DownLoadProgressBar;

public class VersionCheckHelper {
    private static final String TAG = "VersionCheckHelper";
    private static String mNewVersionDownLoadUrl;
    private static String mContent;
    public static final int ERROR_TYPE        = -1;//版本检测失败
    public static final int NO_NEW_VERSION    = 0;//没有新版本
    public static final int UN_FORCED_VERSION = 3;//登录可选更新
    public static final int FORCED_VERSION    = 2;//登录强制更新
    public static final int FORCED_UPDATE     = 1;//启动强制更新(登录前强制更新)
    public static final String CHECK_NEWVERSION     = "/ext/ver/request/getVersion";
    public static int mVersionType=NO_NEW_VERSION;
    private static boolean isLoadingNewVersion;


    //每次进入页面都要检测
    public static void checkNewVersionOnResum(final Activity activity, final DialogInterface.OnClickListener neutralListener,
                                              final DialogInterface.OnClickListener negativeListener){
//        mVersionType = ERROR_TYPE;

        VersionCheckRequestBean bean = new VersionCheckRequestBean();
        try {
            bean.version_number = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            bean.version_number = "1.0";
        }
        bean.os_version = Build.VERSION.RELEASE + "";
        bean.operation_mode = 1 + "";
        bean.time_information = System.currentTimeMillis() + "";
        bean.visitor_system = 2 + "";
        bean.phone_model = Build.MODEL;

        final Gson gson   = new Gson();
        String     toJson = gson.toJson(bean);

        String url = KeyConst.HttpsContentUrlHead.ICAN_ADMIN+CHECK_NEWVERSION;

        Map<String, String> head = new HashMap<>();
        head.put("Content-Type", "application/json");

        MyHttpUtils.postJson(0, null, false, url, head, toJson, new MysalesCallBack() {

            @Override
            public void onError(Exception e, int id) {
                negativeListener.onClick(null,-1);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (response != null) {
                        VersionCheckResponBean responBean = gson.fromJson(response,
                                                                          VersionCheckResponBean.class);
                        if (responBean.status == 1 && responBean.version != null && !StringUtils.isEmpty(
                                responBean.version.update_type))
                        {
                            mNewVersionDownLoadUrl = responBean.version.url;
                            mVersionType = Integer.parseInt(responBean.version.update_type.trim());
                            if (StringUtils.isEmpty(mNewVersionDownLoadUrl)){
                                mVersionType=NO_NEW_VERSION;
                                negativeListener.onClick(null,mVersionType);
                                return;
                            }
                            mContent = responBean.version.content;
                            isShowForcedUpdateDialog(activity, neutralListener,negativeListener);
                        }
                    }
                    //无论怎样也不强制更新
                    //                    mVersionType = NO_NEW_VERSION;
                } catch (Exception e) {
                    mVersionType = NO_NEW_VERSION;
                }
            }

            @Override
            public void doAffterRequestCall() {
                mVersionType = NO_NEW_VERSION;
            }
        });

    }


    public static void isShowForcedUpdateDialog(final Activity activity,
                                                DialogInterface.OnClickListener neutralListener,
                                                DialogInterface.OnClickListener negativeListener) {
        if (mContent == null) {
            mContent = "发现新版本！\n修复大量的bug！";
        }
        if (mVersionType == FORCED_UPDATE) {
            AlertUtils.showVersionUpdateDialog(activity, mContent,UIUtils.getString(R.string.立即更新),null, neutralListener,negativeListener);
        }else if (mVersionType==FORCED_VERSION||mVersionType==UN_FORCED_VERSION){
            AlertUtils.showVersionUpdateDialog(activity, mContent,UIUtils.getString(R.string.立即更新),UIUtils.getString(R.string.稍后更新), neutralListener,negativeListener);
        }
    }



    //登录后执行，执行该方法前需确认调用了checkNewVersionOnResum();
    public static boolean checkNewVersionOnLogin(Activity activity,final DialogInterface.OnClickListener neutralListener,
                                                 final DialogInterface.OnClickListener negativeListener) {
        boolean hasNewVersion=false;
        //TODO 判断是否需要版本更新
        if (mVersionType == ERROR_TYPE) {
            checkNewVersionOnResum(activity,neutralListener,negativeListener);//重新发起版本更新检测
        } else {
            if (mContent == null) {
                mContent = "发现新版本！\n修复大量的bug！";
            }
            switch (mVersionType) {

                case NO_NEW_VERSION://没有新版本需要更新
                    break;

                case UN_FORCED_VERSION://有新版本，但可以继续使用旧版本
                    hasNewVersion=true;
                    AlertUtils.showVersionUpdateDialog(activity, mContent,UIUtils.getString(R.string.立即更新),UIUtils.getString(R.string.稍后更新), neutralListener,negativeListener);
                    break;

                case FORCED_VERSION://有新版本，必须更新才能使用
                    hasNewVersion=true;
                    AlertUtils.showVersionUpdateDialog(activity, mContent,UIUtils.getString(R.string.立即更新),null, neutralListener,null);
                    break;
                case FORCED_UPDATE:
                    hasNewVersion=true;
                    isShowForcedUpdateDialog(activity,neutralListener,negativeListener);
                    break;
                default:
                    negativeListener.onClick(null,0);
                    break;
            }
        }
        return hasNewVersion;
    }


    private static void showProgressDialog(Activity activity, final DownLoadProgressBar downLoadProgressBar){
        AlertUtils.showViewDialog(activity, downLoadProgressBar);
    }

    public static synchronized void getNewVersionAndInstall(final Activity activity, final DownLoadProgressBar downLoadProgressBar) {
        //TODO 版本更新操作
        //1.下载新版本
        String downloadurl = mNewVersionDownLoadUrl;//正式版的url
//        String downloadurl = "https://www.pgyer.com/app/install/bc23994205410ba8805dc901eb1baabd";//测试版的url
        if (TextUtils.isEmpty(downloadurl) || StringUtils.isEmpty(downloadurl)) {
//            checkNewVersionOnResum();
//            showNewVersionErrorDialog(newVersionDialog);
            return;
        }
        isLoadingNewVersion = false;

        Map<String, String> headParams = new HashMap<>();
        MyHttpUtils.getFile(activity,
                            downloadurl,
                            headParams,
                            null,
                            new MyICanFileCallBack(FileUtil.getExterStorageDirectory()+"/",
                                                   CommonUtils.newVersionName)
                            {

                                @Override
                                public boolean doBeforDownLoad(long downloadFileLength) {
                                    File file = new File(filePath);
                                    if (file != null && file.length() > 0) {
                                        if (file.length() == downloadFileLength) {
                                            CommonUtils.installApp(MainApp.getContext(),
                                                                   new File(filePath));
                                            return true;
                                        }
                                    }
                                    MainApp.getHandler()
                                           .post(new Runnable() {
                                               @Override
                                               public void run() {

                                                   ToastUtil.toast(UIUtils.getString(R.string.开始下载新版本));
                                                   showProgressDialog(activity,downLoadProgressBar);
                                                   isLoadingNewVersion = true;
                                                   downLoadProgressBar.setProgress(0);
                                               }
                                           });
                                    return false;

                                }

                                @Override
                                public void inProgress(float progress, long total, int id) {
                                    downLoadProgressBar.setProgress((int) (progress * 100));
                                    if (progress >= 1.0) {
//                                        mBar.setVisibility(View.GONE);
                                        downLoadProgressBar.finishLoad();
                                        //下载完成安装下载好的apk
                                        ToastUtil.toast(UIUtils.getString(R.string.下载完成安装新版本));
//                                        newVersionDialog.setActionLayoutEnable(true);
                                        isLoadingNewVersion = false;
                                    }
                                }

                                @Override
                                public void onError(Exception e, int id) {
                                    LogUtils.e(TAG, "=====download error:" + e);
                                    isLoadingNewVersion = false;
//                                    showNewVersionErrorDialog(newVersionDialog);
                                }

                                @Override
                                public void onResponse(File response, int id) {
                                    if (response != null) {
                                        LogUtils.e(TAG,
                                                   ":code=========file size:" + response.length());
                                        AlertUtils.dismissDialog();
                                        CommonUtils.installApp(activity, response);
//
                                    }
                                }
                            });


    }
}
