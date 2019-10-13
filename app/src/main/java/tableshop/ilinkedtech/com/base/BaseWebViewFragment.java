package tableshop.ilinkedtech.com.base;

/*
 *  @文件名:   BaseWebViewFragment
 *  @创建者:   Wilson
 *  @创建时间:  2018/3/30 12:22
 *  @描述：    TODO
 */

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.factorys.WebViewFactory;
import tableshop.ilinkedtech.com.utils.SystemApiUtil;

public abstract class BaseWebViewFragment
        extends IShopBaseFragment
{
    @BindView(R.id.progress_bar)
    public ProgressBar    mProgressBar;
    @BindView(R.id.head_layout)
    LinearLayout   mHeadLayout;
    @BindView(R.id.web_parent)
    RelativeLayout mWebParent;

    public String  mContentUrl;
    public WebView mWebView;


    @Override
    protected int getLayoutId() {
        return R.layout.base_web_view_layout;
    }

    @Override
    public void initView() {
        mContentUrl =getContentUrl();
        if (mWebView ==null) {
            mWebView = WebViewFactory.getInstance(getContext())
                                     .getWebView();
        }
        mWebParent.addView(mWebView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
    }

    boolean isPageFinished=false;
    @Override
    protected void initEvent() {

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                isPageFinished=false;
            }


            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                mWebView.clearHistory();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                isPageFinished=true;
            }
        });
    }

    protected abstract String getContentUrl();

    public void setProcess(int process) {
        if (mProgressBar!=null) {
            if (process == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(process);
            }
        }
    }

    @Override
    public void onDestroyView() {
        WebViewFactory.getInstance(getContext())
                      .detachWebView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        SystemApiUtil.clearWebViewCache(getActivity());
        super.onDestroy();
    }

}
