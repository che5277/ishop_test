package tableshop.ilinkedtech.com.base;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.beans
 *  @文件名:   IShopBaseActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/6 15:34
 *  @描述：    TODO
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.dialog.CommonUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.dialogs.RxDialogShapeLoading;

public abstract class IShopBaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";
    public  View                 mView;
    private ProgressDialog       mProgressDialog;
    private Snackbar             mSnackbar;
    private Unbinder             mUnbinder;
    private RxDialogShapeLoading mRxDialogShapeLoading;

    public EventBus defaultEventBus;


    public abstract int getLayoutViewId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        try {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }catch (Exception e){

        }

        super.onCreate(savedInstanceState);
        mView=View.inflate(this,getLayoutViewId(),null);
        setContentView(mView);

        defaultEventBus=EventBus.getDefault();
        mUnbinder = ButterKnife.bind(this, mView);
        initView();

        initEvent();
    }


    public abstract void initView() ;

    public void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Bugtags注：回调 1
//        Bugtags.onResume(this);
        LogUtils.e("====onResume:", ""+getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e("====onPause:", ""+getClass().getSimpleName());
        //Bugtags注：回调 2
//        Bugtags.onPause(this);
    }

    @Override
    public void onDestroy() {
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        if (defaultEventBus!=null&&defaultEventBus.isRegistered(this)) {
            defaultEventBus.unregister(this);
        }
        setContentView(R.layout.recycle_view);
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
        mView=null;
        LogUtils.e("====onDestroy:", ""+getClass().getSimpleName());

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //Bugtags注：回调 3
//        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    public void showSnackbar(View view,String msg,String actionTex,View.OnClickListener listener){
        try {
            if (mSnackbar!=null&&mSnackbar.isShown()) {
                View sbView = mSnackbar.getView();
                TextView textView = (TextView) sbView
                        .findViewById(android.support.design.R.id.snackbar_text);
                String trimMsg = textView.getText()
                                         .toString()
                                         .trim();
                if (trimMsg.equals(msg)){
                    return;
                }
                mSnackbar.dismiss();
            }
            mSnackbar = Snackbar.make(view, msg, listener==null?Snackbar.LENGTH_SHORT:Snackbar.LENGTH_INDEFINITE)
                                .setAction(StringUtils.isEmpty(actionTex) ? Const.DEBUG_SNAKBAR_ACTION_TEX : actionTex, listener);
            View sbView = mSnackbar.getView();
            TextView textView = (TextView) sbView
                    .findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(this, R.color.item_seleted));
            sbView.setBackgroundColor(UIUtils.getColor(R.color.snackbar_bg));
            mSnackbar.show();
        }catch (Exception e){
            ToastUtil.toast(msg);
        }

    }

    public void showSnackbar(View view,String msg){
        showSnackbar(view,msg,null,null);
    }

    public void showSnackbar(String msg){
        showSnackbar(mView,msg,null,null);
    }

    @Override
    public void onBackPressed() {
        hideLoading();
        super.onBackPressed();
    }


    public void showLoading() {
        hideLoading();
        mRxDialogShapeLoading = CommonUtils.showShapLoadingDialog(this,false);
    }

    public void hideLoading() {
        if (mRxDialogShapeLoading!=null){
            mRxDialogShapeLoading.dismiss();
        }
    }


}
