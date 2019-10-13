package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActOrderListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/8 14:51
 *  @描述：    TODO 我的订单 视图
 */

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.reques.JsonDataBean;
import tableshop.ilinkedtech.com.beans.responbeans.LoginMenberResponBean;
import tableshop.ilinkedtech.com.beans.responbeans.OrderListResponBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.ishop.CarDetailActivity;
import tableshop.ilinkedtech.com.ishop.LoginActivity;
import tableshop.ilinkedtech.com.ishop.ProductDetailActivity;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.SystemApiUtil;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;

@SuppressLint("ValidFragment")
@Deprecated
public class ActNewOrderListFragment
        extends IShopBaseFragment
{

    private static final String APP_CACAHE_DIRNAME = Const.DEBUG_TAG;
    //mWebView.goBack();//后退
    //mWebView.goForward();//前进
    @BindView(R.id.web_view)
    WebView webView;
    Unbinder unbinder;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    Unbinder unbinder1;
    private SharedStorage sharedStorage;
    private String mUrl = Const.getBaseUrl(Const.BASE_HOST) + "#/order";
//        private String mUrl ="http://192.168.31.201:81/#/order";
    //    private String mUrl ="http://192.168.31.148/#/order";
    private String      mUsername;
    private String      mToken;
    private WebSettings mWebSettings;
    private String mBaseHost;

    public void goToGetOrderListData() {
        if (!SharedStorage.mIsLogin) {
            return;
        }
        JsonDataBean dataBean = new JsonDataBean();
        dataBean.userName = SharedStorage.getInstance(getContext())
                                         .getMobile();
        String json = new Gson().toJson(dataBean);
        showLoading();
        MyHttpUtils.postJson(0,
                             getActivity(),
                             true,
                             Const.GET_PURCHASE_ORDER_LIST,
                             null,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                     if (id == MyHttpUtils.INVALID_TOKEN) {
                                         AlertUtils.showErrorDialog(getContext(),
                                                                    UIUtils.getString(R.string.登录超时请重新登录),
                                                                    new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(
                                                                                DialogInterface dialog,
                                                                                int which)
                                                                        {
                                                                            goToLoginAct();
                                                                        }
                                                                    });
                                     }

                                 }

                                 @Override
                                 public void onResponse(String response, int id) {

                                     if (!StringUtils.isEmpty(response)) {
                                         try {
                                             OrderListResponBean orderListResponBean = new Gson().fromJson(
                                                     response,
                                                     OrderListResponBean.class);
                                             if (orderListResponBean != null && orderListResponBean.vehicleOrderDatas != null && orderListResponBean.vehicleOrderDatas.size() > 0) {
                                             }
                                             if (orderListResponBean != null && orderListResponBean.datas != null && orderListResponBean.datas.size() > 0) {
                                             }
                                         } catch (Exception e) {
                                             SharedStorage.mIsLogin = false;
                                             defaultEventBus.post(new LoginMenberResponBean());
                                         }

                                     }
                                 }

                                 @Override
                                 public void doAffterRequestCall() {

                                     hideLoading();
                                 }
                             });

    }

    private void goToLoginAct() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        UIUtils.activityBackToMain(getActivity());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.web_view_layout;
    }

    @Override
    protected void initView() {
        sharedStorage = SharedStorage.getInstance(getContext());
        mUsername = sharedStorage.getMobile();
        mToken = sharedStorage.getCustomToken();
        mWebSettings = webView.getSettings();
//        mBaseHost = sharedStorage.getStringSharedData("base_web_host", Const.getBaseUrl(Const.BASE_HOST));
//        mUrl=mBaseHost+"#/order";
//        mEtHost.setText(mBaseHost);

    }


    boolean isPageFinish;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initEvent() {
        initWebSetting();
        webView.setVerticalScrollbarOverlay(true);
        webView.addJavascriptInterface(new JSHookUp(),
                                       "app"); //在JSHook类里实现javascript想调用的方法，并将其实例化传入webview, "hello"这个字串告诉javascript调用哪个实例的方法
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view,
                                     String url,
                                     String message,
                                     final JsResult result)
            {
                AlertUtils.showErrorDialog(getContext(), message);
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                setProcess(newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                isPageFinish = false;
            }


            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                isPageFinish = true;
            }
        });
        webView.loadUrl(mUrl);
    }

    public void setProcess(int process) {
        if (process == 100) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(process);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initWebSetting() {
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setSavePassword(false);//密码明文存储漏洞  关闭密码保存提醒
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebSettings.setUserAgentString(KeyConst.HttpsContentUrlHead.USER_AGENT);
        //域控制不严格漏洞 的 修复方式
        mWebSettings.setAllowFileAccess(false);
        mWebSettings.setAllowFileAccessFromFileURLs(false);
        mWebSettings.setAllowUniversalAccessFromFileURLs(false);

        webView.setWebContentsDebuggingEnabled(true);//TODO

        //开启 database storage API 功能
        mWebSettings.setDatabaseEnabled(true);
        String cacheDirPath = getActivity().getFilesDir()
                                           .getAbsolutePath() + APP_CACAHE_DIRNAME;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        LogUtils.i(TAG, "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        mWebSettings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebSettings.setAppCacheEnabled(true);

    }

    @Override
    public void refreshDatas() {

    }


    public class JSHookUp
            extends Object
    {
        public void javaMethod(String p) {
            LogUtils.d(TAG, "JSHookUp.JavaMethod() called! + " + p);
        }

        @JavascriptInterface
        public void onPageLoadFinish() {
            //TODO 页面加载完成后，传参给web
            setHeaderToJS();
        }

        @JavascriptInterface
        public void goToCarDetail(String carUid){
            //TODO 跳转到车辆详情，详情页使用原生页面
            if (!StringUtils.isEmpty(carUid)){
                Intent intent =new Intent(getContext(), CarDetailActivity.class);
                intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_ID,carUid);
                startActivity(intent);
                UIUtils.activityAnimInt(getActivity());
            }else {
                ToastUtil.toast("没有该车辆id");
            }
        }

        @JavascriptInterface
        public void goToProductDetail(String productUid){
            //TODO 跳转到商品详情，详情页使用原生页面
            if (!StringUtils.isEmpty(productUid)) {
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra(KeyConst.BundleIntentKey.PRODUCT_ID, productUid);
                startActivity(intent);
                UIUtils.activityAnimInt(getActivity());
            }else {
                ToastUtil.toast("没有该是商品id");
            }
        }
    }

//    @OnClick(R.id.tv_call_js)
    public void setHeaderToJS() {
        //在java中调用js代码
        webView.post(new Runnable() {
            @Override
            public void run() {
                LogUtils.e(TAG,
                           "thread name:" + Thread.currentThread()
                                                  .getName());
                String webMethodName = "javascript:_updateHeader('" + mToken + "','" + mUsername + "')";
//                String webMethodName = "javascript:_updateHeader(" + mToken + "," + mUsername + ")";
                webView.loadUrl(webMethodName);
            }
        });

    }

//    @OnClick(R.id.tv_call_android)
//    public void testEvaluateJavascript() {
//        final String webMethodName = "javascript:_updateHeader('" + mToken + "','" + mUsername + "')";
//        //        String webMethodName="javascript:_updateHeader('" + mToken + "','"+mUsername+"')";
//        mWebView.post(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void run() {
//                mWebView.evaluateJavascript(webMethodName, new ValueCallback<String>() {
//
//                    @Override
//                    public void onReceiveValue(String value) {
//                        AlertUtils.showErrorDialog(getContext(), "onReceiveValue value=" + value);
//                    }
//                });
//            }
//        });
//
//
//    }

//    @OnClick(R.id.tv_go_back)
//    public void goBack() {
//        mWebView.goBack();
//    }

//    @OnClick(R.id.tv_go_forward)
//    public void goforward() {
//        mWebView.goForward();
//    }


    public boolean onBackPressed() {
        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDetach() {
        if (webView != null) {
            webView.clearFormData();
            webView.clearCache(true);
            webView.clearHistory();
            webView.clearMatches();
        }
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        SystemApiUtil.clearWebViewCache(getActivity());
        super.onDestroy();
    }
}
