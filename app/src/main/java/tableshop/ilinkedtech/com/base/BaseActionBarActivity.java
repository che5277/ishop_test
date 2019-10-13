package tableshop.ilinkedtech.com.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.ScanActivity;
import tableshop.ilinkedtech.com.utils.SystemApiUtil;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.zxingqrcodes.CodeUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.base
 *  @文件名:   BaseActionBarActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/3/13 12:21
 *  @描述：    TODO 包含标题的视图基类
 */
@RuntimePermissions
public abstract class BaseActionBarActivity
        extends IShopBaseActivity
        implements View.OnClickListener
{
    private static final String  TAG       = "BaseActionBarActivity";
    public static final  String  POSITION  = "mPosition";
    public               int     mIndex    = -1;
    public               boolean isApiCall = false;
    public IShopBaseFragment mBaseFragment;
    @BindView(R.id.main_viewpager)
    FrameLayout          mLlContentLayout;
    @BindView(R.id.tv_toolbar_title)
    public TextView mTvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_add_button)
    public TextView  mTvRight;
    @BindView(R.id.iv_right)
    public ImageView mIvRight;


    @Override
    public int getLayoutViewId() {
        return R.layout.base_actionbar_activity;
    }


    protected abstract IShopBaseFragment getShowFragment();


    @Override
    protected void onResume() {
        isApiCall = false;
        if (mBaseFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_viewpager, mBaseFragment);
            fragmentTransaction.show(mBaseFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        super.onResume();
        initDatas();
    }

    public void initDatas() {

    }

    @Override
    public void initView() {
        mBaseFragment = getShowFragment();
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mTvRight.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doNavigationAction();
            }
        });
    }

    public void doNavigationAction() {
        finish();
        UIUtils.activityBackToMain(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//
//    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_add_button) {
            doRightTextViewAction();
        } else if (view.getId() == R.id.iv_right) {
            doRightImgAction();
        }else {
            clickView(view);
        }
    }

    /**
     * 右侧图片的点击事件
     */
    public void doRightImgAction() {
        BaseActionBarActivityPermissionsDispatcher.onNeedsPermissionWithPermissionCheck(this);
    }

    /**
     * 右侧TextView的点击事件
     */
    public void doRightTextViewAction() {

    }

    public void clickView(View view) {

    }

    public boolean getPermission(String permision,int requesCode){
        String[] PERMISSIONS = {permision};
        if (!SystemApiUtil.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, requesCode);
            return false;
        } else {
            return true;
        }
    }
    public void callCameraTask() {
        if (getPermission(Manifest.permission.CAMERA, KeyConst.RequestCode.REQUEST_CODE_ASK_CAMERA)){
            goToScanQRCode();
        }
    }


    /**
     * 扫描二维码，填充数据
     */
    private void goToScanQRCode() {
        try {
            Intent intent = new Intent(this, ScanActivity.class);
            startActivityForResult(intent, KeyConst.RequestCode.REQUEST_CODE);
        }catch (Exception e){

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActionBarActivityPermissionsDispatcher.onRequestPermissionsResult(this,
                                                                              requestCode,
                                                                              grantResults);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == KeyConst.RequestCode.REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String json = bundle.getString(CodeUtils.RESULT_STRING);
                    onScanResult(json);

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.toast("解析二维码失败");
                }
            }
        }
    }

    /**
     * 处理二维码扫描后的结果
     * @param qrcodeStr
     */
    public void onScanResult(String qrcodeStr) {
        ToastUtil.toast(qrcodeStr);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void onNeedsPermission() {
        goToScanQRCode();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onPermissionDenied() {
        ToastUtil.toast(UIUtils.getString(R.string.申请使用摄像头权限被拒绝));
    }
}
