package tableshop.ilinkedtech.com.ishop;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import tableshop.ilinkedtech.com.MainActivity;
import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.db.SharedStorage;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/*
 *  SplashActivity
 *  iShop
 *  Wilson
 */
@RuntimePermissions
public class SplashActivity
        extends AppCompatActivity
{

    private static final int SDK_PERMISSION_REQUEST = 1123;
    Context mContext;
    Activity      mActivity;
    SharedStorage sharedStorage;

    boolean isApiCall = false, isPressed = false;
    private boolean mIsGotoMain;
    private String permissionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        sharedStorage = SharedStorage.getInstance(mContext);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash);

        if (Const.isOpened){
            finish();
        }
        mIsGotoMain = false;
        findViewById(R.id.rl_splash_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                checkPermission();
                return true;
            }
        });
        MainApp.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermission();
            }
        }, 1000*2);
        //====================TODO wilson begin===========================
        String url =sharedStorage.getStringSharedData(BackendSettingsActivity.PREFERENCE_BASE_URL_KEY,Const.BASE_HOST);
        Const.BASE_HOST=Const.getBaseUrl(url)+Const.PROJECK_NAME;
        Const.BACKEDTYPE =sharedStorage.getStringSharedData(BackendSettingsActivity.PREFERENCE_BACKENDTYPE_KEY,Const.BACKEDTYPE);
        Const.setBaseUrl();
//        if (StringUtils.isEmpty(backendType)) {
//            sharedStorage.putStringSharedData("backendType", Const.BACKEDTYPE);
//        }else {
////            Const.BACKEDTYPE=backendType;//TODO 保存后台指向到本地
//            if (backendType.equals(KeyConst.BackendType.CUSTOM)){
//                String custom_base_url = sharedStorage.getStringSharedData("custom_backend_url");
//                if (!StringUtils.isEmpty(custom_base_url)) {
//                    Const.CUSTOM_BASE_URL = Const.getBaseUrl(custom_base_url);
//                }
//            }
//        }
//        Const.setBaseUrl();
//        sharedStorage.setBaseUrl(Const.getBaseUrl());

        //====================TODO wilson end===========================
    }

    private void checkPermission() {
        if (!mIsGotoMain) {
            mIsGotoMain=true;
            SplashActivityPermissionsDispatcher.onThirdGetNeedPermissionWithPermissionCheck(this);
        }
    }



    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this,
                                                                       requestCode,
                                                                       grantResults);
    }

    private void gotoMainActivity() {
      //  startActivity(new Intent(mContext, MainActivity.class));
        startActivity(new Intent(mContext, ToProductDetailAct.class));
        finish();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @NeedsPermission({Manifest.permission.CAMERA,
                      Manifest.permission.ACCESS_FINE_LOCATION,
                      Manifest.permission.ACCESS_COARSE_LOCATION,
                      Manifest.permission.READ_PHONE_STATE,
                      Manifest.permission.READ_EXTERNAL_STORAGE,
                      Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onThirdGetNeedPermission() {
        gotoMainActivity();
    }


    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE,
                         Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionDenied() {
        finish();
    }
}
