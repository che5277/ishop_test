package tableshop.ilinkedtech.com.utils;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Build;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.request.RequestCall;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseActivity;
import tableshop.ilinkedtech.com.beans.responbeans.LoginMenberResponBean;
import tableshop.ilinkedtech.com.callBacks.MyICanFileCallBack;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.DBJsonHelper;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.others.NullHostNameVerifier;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.utils
 *  @文件名:   MyHttpUtils
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 18:22
 *  @描述：    TODO
 */
public class MyHttpUtils {
    private static final String TAG   = "MyHttpUtils";
    public static        String token = "";

    public static  long          TIME_OUT_LENGH =30L;//设置连接时间(秒)
    private static SharedStorage sharedStorage  = SharedStorage.getInstance(MainApp.getContext());

    private static DialogFragment mNetworkProblemDailog;
    private static DialogFragment mSomethingWentWrongDialog;


    public static void intDialogs(DialogFragment networkProblemDailog, DialogFragment somethingWentWrongDialog) {
        mNetworkProblemDailog = networkProblemDailog;
        mSomethingWentWrongDialog = somethingWentWrongDialog;
    }

    //nomarl call
    private static void execute(final Activity activity, RequestCall request, final MysalesCallBack callback,
                                final String apiKey) {
        request.execute(new Callback<String>() {
            @Override
            public String parseNetworkResponse(Response response, int id)
                    throws Exception
            {
                int    code = response.code();
                try {
                    String body =response.body().string();
                    LogUtils.e("Response--"+body.length(), code+":code  ::  "+body);
                    if (code!= HttpURLConnection.HTTP_OK){
                        showNetworkProblemDialog(activity);
                        return Const.CONNECTION_TIMEOUT;
                    }

                    String token = response.header("x-customtoken");

                    if (!StringUtils.isEmpty(token)){
                        SharedStorage.getInstance(UIUtils.getContext()).setCustomToken(token);
//                        MyHttpUtils.token =token;
                    }
                    return body;
                } catch (IOException e) {
                    e.printStackTrace();
                    showSomethingWentWrongDialog(activity);
                    return null;
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                //io异常或者response code不在200-300之间  详情 Response 的 isSuccessful() 方法
                callback.doAffterRequestCall();
                LogUtils.e(id+"：id--Response--error", e.toString());
                showSomethingWentWrongDialog(activity);
                try {
                    callback.onError(e,id);
                }catch (Exception e2){
                    AlertUtils.showErrorDialog(UIUtils.getContext(),
                                               UIUtils.getString(R.string.系统出错));
                }


            }

            @Override
            public void onResponse(String response, int id) {//TODO code{200-300}
                callback.doAffterRequestCall();
                if (response!=null&&response!=Const.CONNECTION_TIMEOUT){
                    if (response.toLowerCase().contains(Const.TOKEN_OVEDUE)||response.toLowerCase().contains(Const.LOGIN_FAULT)){
                        //TODO token 失效
//                        if (activity!=null&&!activity.getLocalClassName().contains("MainActivity")) {
//                            SharedStorage.getInstance(activity).reSetUserData();
//                            Intent intent = new Intent(activity, MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.putExtra(KeyConst.BundleIntentKey.TOKEN_OVERDUE, true);
//                            activity.startActivity(intent);
//                            activity.finish();
//                            UIUtils.activityBackToMain(activity);
//                        }else {
//                        }
                        SharedStorage.mIsLogin=false;
                        EventBus.getDefault().post(new LoginMenberResponBean());
                        callback.onError(new Exception(response), INVALID_TOKEN);
                    }else {
                        saveResponJson(apiKey,response);
                        try {
                            callback.onResponse(response, id);//TODO english
                        }catch (Exception e){
                            showSomethingWentWrongDialog(activity);
                        }

                    }
                }
            }
        });
    }
    public static final int INVALID_TOKEN=-101;

    private static void saveResponJson(final String apiKey, final String response) {
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                DBJsonHelper.mdKeyAndUpdateData(apiKey, response);
            }
        });
    }


    //fileCallBack
    private static void execute(final Activity activity, RequestCall request, final MyICanFileCallBack callback) {
        request.execute(new FileCallBack(callback.destFileDir, callback.destFileName) {
            public File parseNetworkResponse(Response response, int id) throws Exception {
                return callback.doBeforDownLoad(response.body().contentLength())?null:this.saveFile(response, id);
            }

            public void onError(Call call, Exception e, int id) {
//                showNetworkProblemDialog(activity);
                callback.onError(e, id);
            }

            public void onResponse(File response, int id) {
                if(response == null) {
                } else {
                    LogUtils.e(TAG,"====getFile:"+response.length());
                    callback.onResponse(response, id);
                }

            }

            public void inProgress(float progress, long total, int id) {
                callback.inProgress(progress, total, id);
            }
        });
    }


    private static void showSomethingWentWrongDialog(Activity activity) {
        if (activity!=null) {
            IShopBaseActivity shopBaseActivity = (IShopBaseActivity) activity;
            shopBaseActivity.showSnackbar(shopBaseActivity.getString(R.string.系统出错));
        }
    }

    private static void showNetworkProblemDialog(Activity activity) {
        if (activity!=null) {
            IShopBaseActivity shopBaseActivity = (IShopBaseActivity) activity;
            shopBaseActivity.showSnackbar(shopBaseActivity.getString(R.string.网络出错));
        }
    }

    //====================get begin===========================

    /**
     *
     * @param activity
     * @param isTakeRole    是否区分角色 一般传false
     * @param url
     * @param addheadParams 如果是标准请求头，传null
     * @param params
     * @param callback
     */
    public static void get(Activity activity, boolean isTakeRole, String url,
                           Map<String, String> addheadParams,
                           Map<String, String> params,
                           MysalesCallBack callback)
    {
        if (StringUtils.isEmpty(url)){
            return ;
        }
        url=Const.getUrlForRequest(url);
        LogUtils.e("GET:URL--API:"+url);
        Map<String, String> param = new HashMap<>();
        Map<String, String> head  = new HashMap<>();
        if (addheadParams==null){
            addThreeHead(head);
        }
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            head.put("Connection", "close");
        }
        else if (addheadParams!=null&&addheadParams.size()>0){
            head.putAll(addheadParams);
        }
        if (params!=null&&params.size()>0){
            param.putAll(params);
            LogUtils.e("Params--API:" + params.toString());
        }

        GetBuilder getBuilder = OkHttpUtils.get()
                                           .url(url)
                                           .headers(head)
                                           .params(param);

        RequestCall build = getBuilder.build();
        execute(activity,build, callback,null);
    }

    //====================get end===========================


    //====================TODO post begin===========================

    /**
     * 除json和文件之外的其他post请求，主要用于登录
     * @param activity
     * @param url
     * @param addheadParams 如果是标准请求头，传null
     * @param params
     * @param callback
     */
    public static void post(Activity activity,  String url,
                            Map<String, String> addheadParams,
                            Map<String, String> params,
                            MysalesCallBack callback)
    {
        if (StringUtils.isEmpty(url)){
            return ;
        }
        url=Const.getUrlForRequest(url);

        LogUtils.e("POST:URL--API:"+url);
        Map<String, String> head  = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        if (addheadParams!=null&&addheadParams.size()>0){
            head.putAll(addheadParams);
        }else if (addheadParams==null){
            addFullHead(head);
        }
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            head.put("Connection", "close");
        }
        if (params!=null&&params.size()>0){
            param.putAll(params);
            LogUtils.e("Params--API:"+params.toString());
        }
        RequestCall build = OkHttpUtils.post()
                                       .url(url)
                                       .headers(head)
                                       .params(param).tag(activity)
                                       .build();
        execute(activity,build,callback,null);
    }


    /**
     *
     * @param cacheType  0：不做离线保存  1：离线查看，保存请求结果
     * @param activity
     * @param isTakeToken 是否需要带会员的token
     * @param url
     * @param addheadParams
     * @param json
     * @param callback
     */
    public static void postJson(int cacheType, Activity activity, boolean isTakeToken, String url, Map<String, String> addheadParams, String json, final MysalesCallBack callback){
        if (StringUtils.isEmpty(url)){
            return ;
        }
        if (StringUtils.isEmpty(json)){
            json="{}";
        }
        //TODO 识别离线数据的apikey
        String apiKey =url + json;

        url=Const.getUrlForRequest(url);

        if (Const.offNet) {
            //TODO 网络断开了
            if (cacheType == KeyConst.CacheType.OFFLINE_WORK) {
                //====================TODO db test begin===========================
                String jsonData = DBJsonHelper.mdKeyAndGetJsonData(apiKey);
                callback.doAffterRequestCall();
                if (!StringUtils.isEmpty(jsonData)) {
                    LogUtils.e("--offlinedata:",jsonData);
                    callback.onResponse(jsonData, cacheType);
                }
                //====================TODO db test end===========================
            }
            callback.doAffterRequestCall();
            return;
        }
        LogUtils.e("URL--API:"+url);
        LogUtils.e("Params--API:"+json);
        Map<String, String> heads = new HashMap();
        if (addheadParams==null){
            if (isTakeToken) {
                MyHttpUtils.addFullHead(heads);
            }
        }else if (addheadParams.size()>0){
            heads.putAll(addheadParams);
        }
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            heads.put("Connection", "close");
        }
        RequestCall build = OkHttpUtils.postString()
                                           .url(url)
                                           .content(json)
                                           .headers(heads)
                                           .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                           .build();

        execute(activity,build, callback,apiKey);
    }

    public static void postJson(String url, Map<String, String> addheadParams, String json, final MysalesCallBack callback){
        postJson(0,null,false,url,addheadParams,json,callback);
    }
    public static void postJson(String url, boolean isTakeToken,Map<String, String> addheadParams, String json, final MysalesCallBack callback){
        postJson(0,null,isTakeToken,url,addheadParams,json,callback);
    }

    //====================TODO post end===========================

    //====================TODO file call begin===========================

    public static void getFile(Activity activity, String url, Map<String, String> addheadParams, Map<String, String> params, MyICanFileCallBack callback) {
        if (StringUtils.isEmpty(url)){
            return ;
        }
        url=Const.getUrlForRequest(url);
        LogUtils.e("URL--API:"+url);
        LogUtils.e(TAG,"===filePath:"+callback.destFileDir+callback.destFileName);
        HashMap headParams = new HashMap();
        HashMap bodyParams = new HashMap();
        if(addheadParams != null && addheadParams.size() > 0) {
            headParams.putAll(addheadParams);
        }
        else if (addheadParams==null){
            addThreeHead(headParams);
        }

        if(params != null && params.size() > 0) {
            bodyParams.putAll(params);
        }

        RequestCall build = ((GetBuilder)((GetBuilder)OkHttpUtils.get().params(bodyParams).url(url)).headers(headParams)).build();
        execute(activity, build, callback);
    }

    public static void getFile(String url, MyICanFileCallBack callback) {
        getFile(null,url,null,null,callback);
    }

    /**
     *  上传文件
     * @param activity
     * @param isTakeRole    是否区分角色
     * @param url
     * @param file
     * @param fileKey
     * @param heads 如果是标准请求头，传null
     * @param params
     * @param callback
     */
    public static void postFile(Activity activity, boolean isTakeRole, String url,
                                File file,
                                String fileKey, Map<String, String> heads,
                                Map<String, String> params,
                                MysalesCallBack callback){
        if (StringUtils.isEmpty(url)){
            return ;
        }
        url=Const.getUrlForRequest(url);
        LogUtils.e("URL--API:"+url);
        Map<String, String> head = new HashMap<>();
        if (heads != null && heads.size() > 0) {
            head.putAll(heads);
        }else if (heads==null){
            addThreeHead(head);
        }
        Map<String, String> bodyParams = new HashMap<>();
        if (params != null && params.size() > 0) {
            bodyParams.putAll(params);
        }
        LogUtils.e("Params--API:"+bodyParams);
        PostFormBuilder postFormBuilder = OkHttpUtils.post()
                                             .url(url)
                                             .headers(head)
                                             .params(bodyParams);
        if (file!=null){
            if (StringUtils.isEmpty(fileKey)){
                fileKey="file";
            }
            LogUtils.e("file--API:",file.length()+":file lenth====path:"+file.getAbsolutePath());
            postFormBuilder.addFile(fileKey, file.getName(), file);
        }
        RequestCall requestCall = postFormBuilder.build();
        execute(activity,requestCall,callback,null);
    }

    public static void postFile(Activity activity, String url,
                                File file,
                                String fileKey, Map<String, String> heads,
                                Map<String, String> params,
                                MysalesCallBack callback){
        postFile(activity,false,url,file,fileKey,heads,params,callback);
    }

    //====================TODO file call end===========================



    public static OkHttpUtils initOkhttp(String url) {
        X509TrustManager trustManager=new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException
            {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException
            {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[]{trustManager};
        HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
        //        SSLContext sc = null;

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(TIME_OUT_LENGH,
                                                                                 TimeUnit.SECONDS)
                                                                 .writeTimeout(TIME_OUT_LENGH, TimeUnit.SECONDS)
                                                                 .readTimeout(TIME_OUT_LENGH, TimeUnit.SECONDS);
        try {
            if (StringUtils.isEmpty(url)){
                return null;
            }
            if (url.startsWith("https")) {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new SecureRandom());
                builder.sslSocketFactory(sc.getSocketFactory(), sslParams.trustManager);
            }else {
                builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            }
        } catch (NoSuchAlgorithmException e) {
            //            e.printStackTrace();
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        } catch (KeyManagementException e) {
            //            e.printStackTrace();
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        }

        OkHttpClient okHttpClient =builder.build();
        //其他配置

        OkHttpUtils okHttpUtils = OkHttpUtils.initClient(okHttpClient);
        return okHttpUtils;
    }


    public static Map addFullHead(Map<String, String> headParams){
        headParams.put(KeyConst.ContentType.ContentType, KeyConst.ContentType.APPLICATION_JSON);
        addNameAndToken(headParams);
        return headParams;
    }

    public static Map addNameAndToken(Map<String, String> headParams){
        if (!StringUtils.isEmpty(sharedStorage.getMobile()))
            headParams.put(KeyConst.HeadKey.USERNAME, sharedStorage.getMobile());
        if (!StringUtils.isEmpty(sharedStorage.getCustomToken()))
            headParams.put(KeyConst.HeadKey.CUSTOME_TOKEN, sharedStorage.getCustomToken());
        LogUtils.e(TAG,sharedStorage.getCustomToken());
        return headParams;
    }
    public static Map addThreeHead(Map<String, String> headParams){
        headParams.put(KeyConst.HeadKey.X_DEBUG, "DEBUG");
        addNameAndToken(headParams);
        return headParams;
    }



    //==================== other===========================
    /**
     * Beta
     * 未来有需要可以考虑加入泛型类直接返回泛型
     */
    private <T extends Object> T tobecotinue(Class<T> cls) {
        T t = null;
        return t;
    }


}
