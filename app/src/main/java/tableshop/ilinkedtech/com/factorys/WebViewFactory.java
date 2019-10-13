package tableshop.ilinkedtech.com.factorys;

/*
 *  @文件名:   WebViewFactory
 *  @创建者:   Wilson
 *  @创建时间:  2018/3/26 14:59
 *  @描述：    TODO
 */

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.utils.LogUtils;

public class WebViewFactory {

    private static final String TAG = "WebViewFactory";
    private static WebViewFactory instance;
    public         WebView        webView;

    private WebViewFactory(Context context) {
        webView = new WebView(context);
        webView.setVerticalScrollbarOverlay(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSavePassword(false);//密码明文存储漏洞  关闭密码保存提醒
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setUserAgentString("android_ishop");
        //域控制不严格漏洞 的 修复方式
        webSettings.setAllowFileAccess(false);
        webSettings.setAllowFileAccessFromFileURLs(false);
        webSettings.setAllowUniversalAccessFromFileURLs(false);

        webView.setWebContentsDebuggingEnabled(true);//TODO 开启web调试

        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = context.getFilesDir()
                                     .getAbsolutePath() + Const.DEBUG_TAG;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        LogUtils.i(TAG, "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);


    }

    public WebView getWebView() {
        removeWebViewParent();
        return webView;
    }

    public void detachWebView(){
        if (webView != null) {
            webView.clearFormData();//清除自动完成填充的表单数据
            webView.clearCache(true);//
            webView.loadUrl("about:blank");
            webView.clearHistory();
            webView.clearMatches();
            webView.pauseTimers();
            webView.removeAllViews();
            removeWebViewParent();
        }
    }

    private void removeWebViewParent() {
        try {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
        } catch (Exception e) {

        }
    }

    public static WebViewFactory getInstance(Context context) {
        if (instance == null) {
            synchronized (WebViewFactory.class) {
                if (instance == null) {
                    instance = new WebViewFactory(context);
                }
            }
        }
        return instance;
    }
}
