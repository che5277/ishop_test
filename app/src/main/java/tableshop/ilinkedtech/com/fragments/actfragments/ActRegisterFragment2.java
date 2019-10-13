package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @文件名:   ActRegisterFragment2
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:15
 *  @描述：    TODO
 */

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.MainActivity;
import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.events.MenberSateBean;
import tableshop.ilinkedtech.com.beans.reques.MemberDetailsBean;
import tableshop.ilinkedtech.com.beans.responbeans.LoginMenberResponBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.ishop.AgreementDetailAct;
import tableshop.ilinkedtech.com.ishop.LoginActivity;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.SystemApiUtil;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.GlideCircleTransform;
import tableshop.ilinkedtech.com.views.SwitchButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static tableshop.ilinkedtech.com.utils.MyHttpUtils.INVALID_TOKEN;

public class ActRegisterFragment2
        extends IShopBaseFragment
        implements View.OnClickListener
{


    private static final String TAG = "ActRegisterFragment2";
    @BindView(R.id.iv_logo)
    ImageView            mIvLogo;
    @BindView(R.id.auto_et_last_name)
    AutoCompleteTextView mEtLastName;
    @BindView(R.id.last_name_layout)
    TextInputLayout      mLastNameLayout;
    @BindView(R.id.auto_et_first_name)
    AutoCompleteTextView mEtFirstName;
    @BindView(R.id.first_name_layout)
    TextInputLayout      mFirstNameLayout;
    @BindView(R.id.auto_et_sex)
    AutoCompleteTextView mAutoEtSex;
    @BindView(R.id.sex_layout)
    TextInputLayout      mSexLayout;
    @BindView(R.id.auto_et_email)
    AutoCompleteTextView mAutoEtEmail;
    @BindView(R.id.email_layout)
    TextInputLayout      mEmailLayout;
    @BindView(R.id.ll_input_layout)
    LinearLayout         mLlInputLayout;
    @BindView(R.id.btn_login)
    Button               mBtnLogin;
    @BindView(R.id.progress_bar)
    ProgressBar          mProgressBar;

    public String mAvatarFilePath;//要上传的用户头像
    @BindView(R.id.sw_agree)
    SwitchButton mSwAgree;
    @BindView(R.id.tv_agree_detail)
    TextView     mTvAgreeDetail;
    Unbinder unbinder;
    @BindView(R.id.rl_agreen_layout)
    RelativeLayout       mRlAgreenLayout;
    @BindView(R.id.auto_et_phone)
    AutoCompleteTextView mAutoEtPhone;
    @BindView(R.id.phone_layout)
    TextInputLayout      mPhoneLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_2;
    }

    @Override
    protected void initView() {
        int virtualBarHeigh = UIUtils.getVirtualBarHeigh(getContext());
        if (virtualBarHeigh > 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBtnLogin.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, virtualBarHeigh);
            mBtnLogin.setLayoutParams(layoutParams);
        }

        String customerInfo = SharedStorage.getInstance(getContext())
                                           .getCustomerInfo();
        if (!StringUtils.isEmpty(customerInfo)) {
            LogUtils.e(TAG, "customerInfo:" + customerInfo);
            LoginMenberResponBean loginMenberResponBean = new Gson().fromJson(customerInfo,
                                                                              LoginMenberResponBean.class);
            if (loginMenberResponBean != null && loginMenberResponBean.datas != null && loginMenberResponBean.datas.size() > 0) {

                MemberDetailsBean memberDetailsBean = loginMenberResponBean.datas.get(0);

                if (memberDetailsBean != null) {
                    mEtFirstName.setText(StringUtils.checkString(memberDetailsBean.firstName));
                    mEtLastName.setText(StringUtils.checkString(memberDetailsBean.lastName));
                    mAutoEtSex.setText(StringUtils.checkString(memberDetailsBean.gender));
                    mAutoEtEmail.setText(StringUtils.checkString(memberDetailsBean.email));
                    mAutoEtPhone.setText(StringUtils.checkString(memberDetailsBean.mobile));
                    mBtnLogin.setText(UIUtils.getString(R.string.确认修改));
                    mIvLogo.setImageResource(R.drawable.user);
                }
                if (loginMenberResponBean.ossResponseList != null && loginMenberResponBean.ossResponseList.size() > 0) {
                    String userIconUrl = loginMenberResponBean.ossResponseList.get(0).url;
                    //                    ImageLoadUtils.into(mIvLogo, userIconUrl);
                    Glide.with(UIUtils.getContext())
                         .load(userIconUrl)
                         .error(R.drawable.user)
                         .bitmapTransform(new GlideCircleTransform(getContext(), null))
                         .into(mIvLogo);
                }
            }
        }
    }

    @Override
    protected void initEvent() {
        mBtnLogin.setOnClickListener(this);
        mAutoEtSex.setOnClickListener(this);
        mIvLogo.setOnClickListener(this);
        ArrayAdapter<String> mModelListAdapter = new ArrayAdapter<String>(getContext(),
                                                                          android.R.layout.simple_list_item_1,
                                                                          getResources().getStringArray(
                                                                                  R.array.sex_array));
        mAutoEtSex.setAdapter(mModelListAdapter);
        mAutoEtSex.setThreshold(1);

        mAutoEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//        mSwAgree.setThumbColorRes(R.color.selector_switch_thumb);
//        mSwAgree.setBackColorRes(R.color.selector_login_switch_bg);
//        mTvAgreeDetail.setTextColor(UIUtils.getColor(R.color.backgroud_nomal));
        mSwAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBtnLogin.setEnabled(isChecked);
            }
        });
        mSwAgree.setChecked(true);
        mRlAgreenLayout.setVisibility(SharedStorage.mIsLogin
                                      ? View.GONE
                                      : View.VISIBLE);
    }

    @Override
    public void refreshDatas() {

    }

    public void setAvatarFilePath(Bitmap bitmap, String filePath) {
        this.mAvatarFilePath = filePath;
        if (mIvLogo != null) {
            mIvLogo.setImageBitmap(bitmap);
        }
    }

    public void showProgressBar() {
        isFragmentApiCall = true;
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void dismissProgressBar() {
        isFragmentApiCall = false;
        if (mProgressBar != null) { mProgressBar.setVisibility(View.GONE); }
    }


    private void gotoMainAct() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());
    }

    @OnClick(R.id.tv_agree_detail)
    public void onShowAgreeDetail() {
        Intent intent = new Intent(getActivity(), AgreementDetailAct.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                doRegister();
                break;
            case R.id.auto_et_sex:
                mAutoEtSex.showDropDown();
                break;
            case R.id.iv_logo:
                //TODO 上传头像
                doUpLoadAvatar();
                break;
        }
    }

    public void doUpLoadAvatar() {
        if (SystemApiUtil.hasPermissions(getActivity(),
                                         new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}))
        {
            SystemApiUtil.showPhotoRoom(getActivity(), KeyConst.RequestCode.SELETED_AVATAR);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                                              new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                              KeyConst.RequestCode.REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    private void doRegister() {
        String firstName = mEtFirstName.getText()
                                       .toString()
                                       .trim();
        String lastName = mEtLastName.getText()
                                     .toString()
                                     .trim();
        String email = mAutoEtEmail.getText()
                                   .toString()
                                   .trim();
        String femail = mAutoEtSex.getText()
                                  .toString()
                                  .trim();
        String phoneNum = mAutoEtPhone.getText()
                                  .toString()
                                  .trim();
        if (checkInputInfo(firstName, lastName) && !isFragmentApiCall) {
            if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
                ToastUtil.showSnackbar(mView, "请输入必填项");
                return;
            }
            MemberDetailsBean dataBean = new MemberDetailsBean();
            dataBean.firstName = firstName;
            dataBean.lastName = lastName;
            dataBean.mobile = phoneNum;
            dataBean.username = SharedStorage.userName;
            if (!StringUtils.isEmpty(email)) {
                if (!Const.isEmail(email)) {
                    ToastUtil.toast(UIUtils.getString(R.string.电子邮箱有误));
                    mAutoEtEmail.requestFocus();
                    return;
                } else {
                    dataBean.email = email;
                }
            }
            if (!StringUtils.isEmpty(femail)) {
                dataBean.gender = femail;
            }
            goToDoRegisterNew(dataBean);
        }
    }


    private void goToDoRegisterNew(final MemberDetailsBean bean) {
        final Map<String, String> params = new HashMap<>();
        params.put("firstName", bean.firstName);
        params.put("username", bean.username);
        if (!StringUtils.isEmpty(bean.lastName)) {
            params.put("lastName", bean.lastName);
        }
        if (!StringUtils.isEmpty(bean.gender)) {
            params.put("gender", bean.gender);
        }
        if (!StringUtils.isEmpty(bean.email)) {
            params.put("email", bean.email);
        }
        if (!StringUtils.isEmpty(bean.mobile)) {
            params.put("mobile", bean.mobile);
        }
        File   file    = null;
        String fileKey = null;
        if (!StringUtils.isEmpty(mAvatarFilePath)) {
            params.put(KeyConst.OSS_FILE.FILE_TYPE, KeyConst.OSS_FILE.FileType.MEMBER_AVATAR);
            file = new File(mAvatarFilePath);
            fileKey = "file";
        }
        final Map<String, String> head = new HashMap<>();
        MyHttpUtils.addThreeHead(head);
        final String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
        head.put("Content-Type", contentType);
        if (!StringUtils.isEmpty(mAvatarFilePath)) {
            showProgressBar();
            MyHttpUtils.postFile(getActivity(),
                                 Const.MEMBER_REGISTER,
                                 file,
                                 fileKey,
                                 head,
                                 params,
                                 new MysalesCallBack() {
                                     @Override
                                     public void onError(Exception e, int id) {
                                         if (id == INVALID_TOKEN) {
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
                                         } else {
                                             AlertUtils.showErrorDialog(getContext(),
                                                                        e.getMessage());
                                         }
                                     }

                                     @Override
                                     public void onResponse(String response, int id) {
                                         try {
                                             LoginMenberResponBean loginMenberResponBean = new Gson().fromJson(
                                                     response,
                                                     LoginMenberResponBean.class);
                                             if (loginMenberResponBean != null && !StringUtils.isEmpty(
                                                     loginMenberResponBean.status))
                                             {
                                                 if (loginMenberResponBean.status.equals("1")) {
                                                     //TODO 成功
                                                     saveCustomerInfo(params, head, contentType);
                                                 }
                                             }
                                         } catch (Exception e) {
                                             SharedStorage.mIsLogin=false;
                                             defaultEventBus.post(new LoginMenberResponBean());
                                         }


                                     }

                                     @Override
                                     public void doAffterRequestCall() {
                                         dismissProgressBar();
                                     }
                                 });
        } else {
            saveCustomerInfo(params, head, contentType);
        }

    }

    /**
     * 保存客户基本信息
     * @param params
     * @param head
     * @param contentType
     */
    private void saveCustomerInfo(Map<String, String> params,
                                  Map<String, String> head,
                                  String contentType)
    {
        showProgressBar();
        StringBuffer sb = new StringBuffer();
        //设置表单参数
        for (String key : params.keySet()) {
            sb.append(key + "=" + params.get(key) + "&");
        }

        MediaType   FORM_CONTENT_TYPE = MediaType.parse(contentType);
        RequestBody body              = RequestBody.create(FORM_CONTENT_TYPE, sb.toString());

        //创建请求
        String url = Const.getUrlForRequest(Const.MEMBER_REGISTER);
        head.remove(KeyConst.OSS_FILE.FILE_TYPE);
        Headers heads = Headers.of(head);
        Request request = new Request.Builder().url(url)
                                               .headers(heads)
                                               .post(body)
                                               .build();
        OkHttpClient okHttpClient = OkHttpUtils.getInstance()
                                               .getOkHttpClient();
        okHttpClient.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogUtils.e(TAG, e.getMessage());
                            MainApp.getHandler()
                                   .post(new Runnable() {
                                       @Override
                                       public void run() {
                                           dismissProgressBar();
                                       }
                                   });

                        }

                        @Override
                        public void onResponse(Call call, Response response)
                                throws IOException
                        {
                            MainApp.getHandler()
                                   .post(new Runnable() {
                                       @Override
                                       public void run() {
                                           dismissProgressBar();
                                       }
                                   });
                            try {
                                String string = response.body()
                                                        .string();
                                LogUtils.e(TAG + "--okhttp", string);
                                LoginMenberResponBean loginMenberResponBean = new Gson().fromJson(
                                        string,
                                        LoginMenberResponBean.class);
                                if (loginMenberResponBean != null && !StringUtils.isEmpty(
                                        loginMenberResponBean.status))
                                {
                                    if (loginMenberResponBean.status.equals("1")) {
                                        //TODO 成功
                                        SharedStorage.getInstance(getContext())
                                                     .setIsLogin(true);

                                        MainApp.getHandler()
                                               .post(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       MenberSateBean menberSateBean = new MenberSateBean();
                                                       menberSateBean.isLogin = true;
                                                       defaultEventBus.post(menberSateBean);
                                                       doAffterRegisterSuccess();
                                                   }
                                               });


                                    }
                                }
                            } catch (Exception e) {

                            }
                        }
                    });
    }


    private void goToLoginAct() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        UIUtils.activityBackToMain(getActivity());
    }

    private void doAffterRegisterSuccess() {
        gotoMainAct();
    }

//    private boolean checkInputInfo(String firstName, String lastName) {
//
//        if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {
//            return true;
//        }
//        AlertUtils.showErrorDialog(getActivity(), UIUtils.getString(R.string.请输入所有必填项));
//        return false;
//    }
    private boolean checkInputInfo(String... fiels) {

        boolean isCheckResult=true;
        for (String msg:fiels){
            if (StringUtils.isEmpty(msg)){
                isCheckResult=false;
                break;
            }
        }
        if (!isCheckResult) {
            AlertUtils.showErrorDialog(getActivity(), UIUtils.getString(R.string.请输入所有必填项));
        }
        return isCheckResult;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
