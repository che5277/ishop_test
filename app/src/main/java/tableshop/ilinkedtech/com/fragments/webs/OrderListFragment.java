package tableshop.ilinkedtech.com.fragments.webs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tableshop.ilinkedtech.com.base.BaseWebViewFragment;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.ishop.CarDetailActivity;
import tableshop.ilinkedtech.com.ishop.ProductDetailActivity;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;


/*
 *  @文件名:   OrderListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2018/3/30 12:29
 *  @描述：    TODO
 */

public class OrderListFragment
        extends BaseWebViewFragment
{
    private SharedStorage sharedStorage;
    private String mUsername;
    private String mToken;
    private boolean isPageFinish;

    @Override
    protected String getContentUrl() {
        return Const.getBaseUrl(Const.BASE_HOST) + Const.WebUrl.ORDER_LIST;
    }

    @Override
    protected void initEvent() {
        sharedStorage = SharedStorage.getInstance(getContext());
        mUsername = sharedStorage.getMobile();
        mToken = sharedStorage.getCustomToken();

        mWebView.addJavascriptInterface(new JSHookUp(),
                                        "app"); //在JSHook类里实现javascript想调用的方法，并将其实例化传入webview, "hello"这个字串告诉javascript调用哪个实例的方法
        mWebView.setWebChromeClient(new WebChromeClient() {
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

        mWebView.setWebViewClient(new WebViewClient() {
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
        mWebView.loadUrl(mContentUrl);

    }

    @Override
    public void refreshDatas() {

    }



    public void setHeaderToJS() {
        //在java中调用js代码
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                LogUtils.e(TAG,
                           "thread name:" + Thread.currentThread()
                                                  .getName());
                String webMethodName = "javascript:_updateHeader('" + mToken + "','" + mUsername + "')";
                //                String webMethodName = "javascript:_updateHeader(" + mToken + "," + mUsername + ")";
                mWebView.loadUrl(webMethodName);
            }
        });

    }


    public boolean onBackPressed() {
        if (mWebView != null) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return false;
            }
        }
        return true;
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
                intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_ID, carUid);
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
}
