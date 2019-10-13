
package tableshop.ilinkedtech.com;
/*
 *  @项目名：  iShop
 *  @文件名:   MainApp
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import cn.jpush.android.api.JPushInterface;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.others.HttpsTrustManager;
import tableshop.ilinkedtech.com.others.NullHostNameVerifier;
import tableshop.ilinkedtech.com.servies.LocationService;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import okhttp3.OkHttpClient;


public class MainApp
        extends Application
{

    static Context mContext;
    private final String LINE_SEPARATOR = "\n";
    String[] monthValue = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

   //====================wilson begin===========================
   private static Handler            mHandler;
   public static  Map<String,Object> mCacheJsonMap;
     //====================wilson end===========================

    //记录是否有保存勾选商品的状态
    public static boolean isRemberData=false;

    public LocationService locationService;
    public Vibrator        mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        mContext = getBaseContext();
        mHandler = new Handler();
        mCacheJsonMap = new HashMap<>();
        //====================TODO bugtags begin===========================

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
        //====================TODO bugtags end===========================

        initOkhttp();
        Config.DEBUG = true;
        try{
            PlatformConfig.setWeixin(Const.WX_APP_ID, Const.WX_APP_ID);
            JPushInterface.init(this);
            /***
             * 初始化定位sdk，建议在Application中创建
             */
            locationService = new LocationService(getApplicationContext());
            mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//            SDKInitializer.initialize(getApplicationContext());  BTGInvocationEventNone
//           Bugtags.start("16343e4b16d02269dfa0d5a9e5b7563e", this, Bugtags.BTGInvocationEventNone);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void initOkhttp() {
        OkHttpClient.Builder builder      = new OkHttpClient.Builder();
        try {
            X509TrustManager trustManager = new HttpsTrustManager();
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,new X509TrustManager[]{trustManager},null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory).hostnameVerifier(new NullHostNameVerifier());
        } catch (GeneralSecurityException e) {
            LogUtils.e("NullHostNameVerifier SSL Socket Error:"+e.toString());
        }
        builder.connectTimeout(MyHttpUtils.TIME_OUT_LENGH, TimeUnit.SECONDS)
               .readTimeout(MyHttpUtils.TIME_OUT_LENGH*2, TimeUnit.SECONDS)
               .writeTimeout(MyHttpUtils.TIME_OUT_LENGH*2, TimeUnit.SECONDS);
        OkHttpUtils.initClient(builder.build());
    }
    /*
    * Handle exceptions
    * */

    public void handleUncaughtException(Thread thread, Throwable e) {
        SimpleDateFormat currentDateTime_1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ssa");
        StringWriter     stackTrace        = new StringWriter(); // not all Android versions will print the stack trace automatically
        e.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("********************** Start Error Log (" + currentDateTime_1.format(new Date()) + ") **********************\n\n");
        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());
        LogUtils.e(stackTrace.toString());
        errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("********************** End Error Log (" + currentDateTime_1.format(new Date()) + ") **********************\n\n\n");
        extractLogToFile(errorReport.toString());
        android.os.Process.killProcess(android.os.Process.myPid());
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*
    * Error Exception log to file
    * */

    private void extractLogToFile(String error) {
        SimpleDateFormat currentDateTime = new SimpleDateFormat("ddMMyyyy");

        try {
            File   f;
            String dirName  = "_iShop";
            String fileName = "iShop_Exception_" + currentDateTime.format(new Date()) + ".txt";
            File   newFile  = new File(Environment.getExternalStorageDirectory(), dirName);
            if (!newFile.exists()) {
                newFile.mkdirs();
            } else {
            }
            f = new File(newFile, fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter writer = new FileWriter(f, true);
            writer.append(error);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //====================wilson begin===========================
    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    /**
     * 得到主线程handler对象
     *
     * @return
     */

    public static void putCache(String key, String value){
        mCacheJsonMap.put(key,value);
    }

//    public static void setToken(String value){
//        putCache("token",value);
//    }
    //====================wilson end===========================

}
