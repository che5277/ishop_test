package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @文件名:   ActLoginFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:15
 *  @描述：    TODO
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.reques.LoginForMenberFindById;
import tableshop.ilinkedtech.com.beans.reques.MemberDetailsBean;
import tableshop.ilinkedtech.com.beans.responbeans.LoginMenberResponBean;
import tableshop.ilinkedtech.com.beans.responbeans.LoginUserResponBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.ishop.AgreementDetailAct;
import tableshop.ilinkedtech.com.ishop.RegisterActivity;
import tableshop.ilinkedtech.com.others.VersionCheckHelper;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.fingerprint.FingerprintCore;
import tableshop.ilinkedtech.com.utils.fingerprint.FingerprintUtil;
import tableshop.ilinkedtech.com.utils.fingerprint.KeyguardLockScreenManager;
import tableshop.ilinkedtech.com.views.DownLoadProgressBar;
import tableshop.ilinkedtech.com.views.SwitchButton;

import static android.app.Activity.RESULT_OK;
import static tableshop.ilinkedtech.com.utils.MyHttpUtils.INVALID_TOKEN;

public class ActLoginFragment
        extends IShopBaseFragment
        implements View.OnClickListener
{
    @BindView(R.id.et_name)
    EditText     mEtName;
    @BindView(R.id.et_password)
    EditText     mEtPassword;
    @BindView(R.id.tv_get_mobile_msg)
    TextView     mTvGetMobileMsg;
    @BindView(R.id.ll_input_layout)
    LinearLayout mLlInputLayout;
    @BindView(R.id.btn_login)
    Button       mBtnLogin;
    @BindView(R.id.progress_bar)
    ProgressBar  mProgressBar;
    @BindView(R.id.sw_agree)
    SwitchButton mSwAgreen;
    @BindView(R.id.tv_agree_detail)
    TextView     mAgreeDetail;
    private Timer                     timer;
    private int                       time;
    private SharedStorage             mSharedStorage;
    private FingerprintCore           mFingerprintCore;
    private KeyguardLockScreenManager mKeyguardLockScreenManager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        int virtualBarHeigh = UIUtils.getVirtualBarHeigh(getContext());
        if (virtualBarHeigh > 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBtnLogin.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, virtualBarHeigh);
            mBtnLogin.setLayoutParams(layoutParams);
        }

        SharedStorage instance = SharedStorage.getInstance(getContext());
        String        mobile   = instance.getMobile();
        mEtName.setText(StringUtils.checkString(mobile));
        mEtName.setSelection(mEtName.length());
    }

    @Override
    protected void initEvent() {
        mBtnLogin.setOnClickListener(this);
        mTvGetMobileMsg.setOnClickListener(this);
        mSharedStorage = SharedStorage.getInstance(getContext());
        mSwAgreen.setThumbColorRes(R.color.selector_switch_thumb);
        mSwAgreen.setBackColorRes(R.color.selector_login_switch_bg);
        mAgreeDetail.setTextColor(UIUtils.getColor(R.color.backgroud_nomal));
        mSwAgreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBtnLogin.setEnabled(isChecked);
            }
        });
        mSwAgreen.setChecked(true);
    }

    @OnClick(R.id.tv_agree_detail)
    public void onShowAgreeDetail() {
        Intent intent = new Intent(getActivity(), AgreementDetailAct.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());
    }

    @Override
    public void refreshDatas() {

    }

    public void showProgressBar() {
        if (mProgressBar != null) { mProgressBar.setVisibility(View.VISIBLE); }
    }

    public void dismissProgressBar() {
        if (mProgressBar != null) { mProgressBar.setVisibility(View.GONE); }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        //        initFingerprintCore();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        final String mobile = mEtName.getText()
                                     .toString()
                                     .trim();
        switch (v.getId()) {
            case R.id.btn_login:
                if (!mProgressBar.isShown()) {
                    final String msmCode = mEtPassword.getText()
                                                      .toString()
                                                      .trim();
                    if (!Const.isMobile(mEtName)) {

                    } else if (StringUtils.isEmpty(msmCode)) {
                        ToastUtil.toast(UIUtils.getString(R.string.请输入验证码));
                        mEtPassword.setSelection(mEtPassword.length());
                        mEtPassword.requestFocus();
                    } else {
                        boolean hasNewVersion = VersionCheckHelper.checkNewVersionOnLogin(getActivity(),
                                                                              new DialogInterface.OnClickListener() {
                                                                                  @Override
                                                                                  public void onClick(
                                                                                          DialogInterface dialog,
                                                                                          int which)
                                                                                  {
                                                                                      VersionCheckHelper.getNewVersionAndInstall(
                                                                                              getActivity(),
                                                                                              new DownLoadProgressBar(
                                                                                                      getContext()));
                                                                                  }
                                                                              },
                                                                              new DialogInterface.OnClickListener() {
                                                                                  @Override
                                                                                  public void onClick(
                                                                                          DialogInterface dialog,
                                                                                          int which)
                                                                                  {
                                                                                      callToLogin(
                                                                                              mobile,
                                                                                              msmCode);

                                                                                  }
                                                                              });
                        if (!hasNewVersion){
                            callToLogin(
                                    mobile,
                                    msmCode);
                        }

                        //                        startFingerprintRecognition();
                    }
                }
                break;
            case R.id.tv_get_mobile_msg:
                if (!mTvGetMobileMsg.isActivated()) {
                    if (!Const.isMobile(mEtName)) {
                    } else {
                        doGetMobleMsm(mobile);
                    }
                }
                break;
        }
    }


    private void doGetMobleMsm(String mobile) {
        gotoGetMobleMsm(mobile);
        RunTimer();
    }

    private void gotoGetMobleMsm(String mobile) {
        showProgressBar();
        Map<String, String> params = new HashMap<>();
        params.put("j_mobile", mobile);
        MyHttpUtils.post(getActivity(), Const.LOGIN, null, params, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }

            @Override
            public void doAffterRequestCall() {
                dismissProgressBar();
            }
        });

    }

    private void callToLogin(final String mobile, final String msm) {
        showProgressBar();
        Map<String, String> head = new HashMap<>();
        head.put(KeyConst.ContentType.ContentType, KeyConst.ContentType.URLENCODED);
        Map<String, String> params = new HashMap<>();
        params.put(KeyConst.RequestParamsKey.LOGIN_MOBILE, mobile);
        params.put(KeyConst.RequestParamsKey.LOGIN_SMS_CODE, msm);
        MyHttpUtils.post(getActivity(), Const.LOGIN, head, params, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
                if (id == INVALID_TOKEN) {
                    AlertUtils.showErrorDialog(getContext(), UIUtils.getString(R.string.验证码错误));
                } else {
                    AlertUtils.showErrorDialog(getActivity(), UIUtils.getString(R.string.系统出错));
                }
            }

            @Override
            public void onResponse(String response, int id) {
                if (!StringUtils.isEmpty(response)) {
                    LogUtils.d(response);
                    try {
                        LoginUserResponBean loginResponBean = new Gson().fromJson(response,
                                                                                  LoginUserResponBean.class);
                        if (loginResponBean != null) {
                            if (loginResponBean.status.equals(KeyConst.ResponseStatues.SUCCESS)) {
                                //TODO 验证成功
                                doAffterCheckMsmSuccess(mobile, msm);
                            } else {
                                AlertUtils.showErrorDialog(getActivity(),
                                                           UIUtils.getString(R.string.验证码错误));

                            }
                        } else {
                            AlertUtils.showErrorDialog(getActivity(),
                                                       UIUtils.getString(R.string.系统出错));
                        }
                    } catch (Exception e) {
                        AlertUtils.showErrorDialog(getActivity(), e.getMessage());
                    }

                }

            }

            @Override
            public void doAffterRequestCall() {
                dismissProgressBar();
                cancelMsmTask();

            }
        });
    }

    private void doAffterCheckMsmSuccess(String mobile, String msm) {
        //        SharedStorage.mIsLogin=true;
        mSharedStorage.setUsername(mobile);
        mSharedStorage.setMobileMsm(msm);
        mSharedStorage.setMobile(mobile);
        gotoFindMenberId(mobile);
    }

    private void gotoFindMenberId(final String mobile) {
        showProgressBar();
        //        final LoginForMenberFindById          loginForMenberFindById =new LoginForMenberFindById();
        final LoginForMenberFindById.DataBean dataBean = new LoginForMenberFindById.DataBean();
        dataBean.username = mobile;
        //        loginForMenberFindById.data=dataBean;
        String json = new Gson().toJson(dataBean);

        Map<String, String> head = new HashMap<>();
        MyHttpUtils.addFullHead(head);
        MyHttpUtils.postJson(Const.MEMBER_FINDBYID, head, json, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
                if (id == INVALID_TOKEN) {
                    AlertUtils.showErrorDialog(getContext(), UIUtils.getString(R.string.登录超时请重新登录));
                } else {
                    AlertUtils.showErrorDialog(getActivity(), UIUtils.getString(R.string.系统出错));
                }
            }

            @Override
            public void onResponse(String response, int id) {

                try {
                    LoginMenberResponBean loginMenberResponBean = new Gson().fromJson(response,
                                                                                      LoginMenberResponBean.class);
                    if (loginMenberResponBean.status.equals("1")) {

                        if (loginMenberResponBean != null && loginMenberResponBean.datas != null && loginMenberResponBean.datas.size() > 0) {
                            MemberDetailsBean datasBean = loginMenberResponBean.datas.get(0);
                            if (datasBean != null) {
                                if (!StringUtils.isEmpty(datasBean.lastName) || !StringUtils.isEmpty(
                                        datasBean.firstName))
                                {
                                    //已经注册过，进入主页面
                                    mSharedStorage.setIsLogin(true);
                                    mSharedStorage.setMobile(mobile);
                                    mSharedStorage.setUsername(mobile);
                                    mSharedStorage.setCustomerInfo(response);
                                    EventBus.getDefault()
                                            .post(loginMenberResponBean);
                                    //                                    MainActivity.hasChangeInfo =true;
                                    gotoMainAct();
                                } else {
                                    //完善注册信息 进入填写注册信息页面
                                    gotoRegisterAct();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    AlertUtils.showErrorDialog(getContext(), e.getMessage());
                }

            }

            @Override
            public void doAffterRequestCall() {
                dismissProgressBar();
            }
        });
    }

    private void gotoRegisterAct() {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());

    }

    /**
     * 获取验证码 倒计时
     */
    public void RunTimer() {
        mTvGetMobileMsg.setClickable(false);
        mTvGetMobileMsg.setActivated(true);
        time = 30;
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                time--;
                MainApp.getHandler()
                       .post(new Runnable() {
                           @Override
                           public void run() {
                               if (mTvGetMobileMsg != null) {
                                   mTvGetMobileMsg.setText(time + UIUtils.getString(R.string.秒));
                               }
                           }
                       });
                if (time == 0 || time < 0) {
                    MainApp.getHandler()
                           .post(new Runnable() {
                               @Override
                               public void run() {
                                   cancelMsmTask();
                               }
                           });

                }
            }
        };
        timer.schedule(task, 100, 1000);
    }

    @Override
    public void onDestroy() {
        cancelMsmTask();
        if (mFingerprintCore != null) {
            mFingerprintCore.onDestroy();
            mFingerprintCore = null;
        }
        if (mKeyguardLockScreenManager != null) {
            mKeyguardLockScreenManager.onDestroy();
            mKeyguardLockScreenManager = null;
        }
        mResultListener = null;
        super.onDestroy();
    }

    private void cancelMsmTask() {
        if (timer != null && mTvGetMobileMsg != null) {
            timer.cancel();
            timer = null;
            mTvGetMobileMsg.setClickable(true);//恢复可获取验证码
            mTvGetMobileMsg.setActivated(false);
            mTvGetMobileMsg.setText(UIUtils.getString(R.string.获取动态密码));
        }

    }

    private void gotoMainAct() {
        //        Intent intent = new Intent(getContext(), MainActivity.class);
        //        startActivity(intent);
        getActivity().finish();
        UIUtils.activityAnimInt(getActivity());
    }

    private void initFingerprintCore() {
        mFingerprintCore = new FingerprintCore(getContext());
        mFingerprintCore.setFingerprintManager(mResultListener);
        mKeyguardLockScreenManager = new KeyguardLockScreenManager(getContext());
    }

    /**
     * 开始指纹识别
     */
    private void startFingerprintRecognition() {
        if (mFingerprintCore.isSupport()) {
            if (!mFingerprintCore.isHasEnrolledFingerprints()) {
                ToastUtil.toast("您还没有录入指纹，请录入");
                FingerprintUtil.openFingerPrintSettingPage(getContext());
                return;
            }
            ToastUtil.toast("请进行指纹识别，长按指纹解锁键");
            if (mFingerprintCore.isAuthenticating()) {
                ToastUtil.toast("指纹识别已经开启，长按指纹解锁键");
            } else {
                mFingerprintCore.startAuthenticate();
            }
        } else {
            ToastUtil.toast("此设备不支持指纹解锁");
        }
    }

    /**
     * 取消指纹识别
     */
    private void cancelFingerprintRecognition() {
        if (mFingerprintCore.isAuthenticating()) {
            mFingerprintCore.cancelAuthenticate();
        }
    }

    private FingerprintCore.IFingerprintResultListener mResultListener = new FingerprintCore.IFingerprintResultListener() {
        @Override
        public void onAuthenticateSuccess() {
            ToastUtil.toast("指纹识别成功");
        }

        @Override
        public void onAuthenticateFailed(int helpId) {
            ToastUtil.toast("指纹识别失败，请重试");
        }

        @Override
        public void onAuthenticateError(int errMsgId) {
            ToastUtil.toast("指纹识别错误，请稍后再试");
        }

        @Override
        public void onStartAuthenticateResult(boolean isSuccess) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KeyguardLockScreenManager.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            // Challenge completed, proceed with using cipher
            if (resultCode == RESULT_OK) {
                ToastUtil.toast("系统手势密码识别成功");
            } else {
                ToastUtil.toast("系统手势密码识别失败");
            }
        }
    }
}
