package tableshop.ilinkedtech.com.fragments.webs;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import tableshop.ilinkedtech.com.base.BaseWebViewFragment;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.utils.AlertUtils;


/*
 *  @文件名:   AgreementLoginFragment
 *  @创建者:   Wilson
 *  @创建时间:  2018/3/30 12:29
 *  @描述：    TODO
 */

public class AgreementLoginFragment
        extends BaseWebViewFragment
{
    private boolean isPageFinish;

    @Override
    protected String getContentUrl() {
        return Const.getBaseUrl(Const.BASE_HOST) + Const.WebUrl.LOGIN_AGREEMENT;
    }

    @Override
    protected void initEvent() {

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

        mWebView.loadUrl(mContentUrl);

    }

    @Override
    public void refreshDatas() {

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


}
