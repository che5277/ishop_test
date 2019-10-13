package tableshop.ilinkedtech.com.fragments.main;

/*
 *  @文件名:   AccessoryFragment
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/22 10:55
 *  @描述：    TODO 我的
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.responbeans.LoginMenberResponBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.ishop.AIViewActivity;
import tableshop.ilinkedtech.com.ishop.ContrastListActivity;
import tableshop.ilinkedtech.com.ishop.FavoriteViewActivity;
import tableshop.ilinkedtech.com.ishop.LoginActivity;
import tableshop.ilinkedtech.com.ishop.OrderListActivity;
import tableshop.ilinkedtech.com.ishop.RegisterActivity;
import tableshop.ilinkedtech.com.ishop.ShoppingCarActivity;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.CommonUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.GlideCircleTransform;

public class MyFragment
        extends IShopBaseFragment
{
    @BindView(R.id.iv_icon)
    ImageView    mIvIcon;
    @BindView(R.id.tv_user_name)
    TextView     mTvUserName;
    @BindView(R.id.tv_user_detail)
    TextView     mTvUserDetail;
    @BindView(R.id.ll_customer_info_layout)
    LinearLayout mLlCustomerInfoLayout;
    @BindView(R.id.tv_my_favorite)
    TextView     mTvMyFavorite;
    @BindView(R.id.tv_shopping_car)
    TextView     mTvShoppingCart;
    @BindView(R.id.tv_contrast)
    TextView     mTvContrast;
    @BindView(R.id.tv_setting)
    TextView     mTvSetting;
    @BindView(R.id.tv_order_list)
    TextView     mTvOrderList;
    @BindView(R.id.tv_about)
    TextView     mTvAbout;
    @BindView(R.id.tv_user_logout)
    TextView     mTvUserLogout;
    Unbinder unbinder;
    private String mUserName;
    private String userIconUrl;
    private SharedStorage mSharedStorage;
    private Timer mTimer;
    private int mTimes;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultEventBus.register(this);
        mSharedStorage = SharedStorage.getInstance(getActivity());
        boolean login = mSharedStorage.isLogin();
        if (login) {
            String customerInfo = mSharedStorage.getCustomerInfo();
            if (!StringUtils.isEmpty(customerInfo)) {
                LoginMenberResponBean loginMenberResponBean = new Gson().fromJson(customerInfo,
                                                                                  LoginMenberResponBean.class);
                mUserName=loginMenberResponBean.getUserName();
                userIconUrl=loginMenberResponBean.getUserIconUrl();
                refreshLoginStatues();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_layout;
    }

    @Override
    public void initView() {
    }

    @Override
    protected void initEvent() {


    }

    @Override
    public void refreshDatas() {

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLoginStatues();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginMenberResponBean(LoginMenberResponBean loginMenberResponBean) {
        LogUtils.e("event:",(loginMenberResponBean==null)+":=loginMenberResponBean:"+loginMenberResponBean.toString());
        if (loginMenberResponBean != null && SharedStorage.mIsLogin) {
            mUserName = loginMenberResponBean.getUserName();
            userIconUrl = loginMenberResponBean.getUserIconUrl();
            if (mIvIcon!=null) {
                refreshLoginStatues();
            }
        }else if (!SharedStorage.mIsLogin) {
            if (mIvIcon != null) {
                refreshLoginStatues();
            }
        }
    }

    //我的收藏
    @OnClick(R.id.tv_my_favorite)
    public void gotoFavoriteAct() {
        Intent intent = new Intent(getContext(), FavoriteViewActivity.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());
    }

    //我的对比
    @OnClick(R.id.tv_contrast)
    public void gotoContrastAct() {
        Intent intent = new Intent(getContext(), ContrastListActivity.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());
    }


    //关于
    @OnClick(R.id.tv_about)
    public void goToBackendSetting() {
        try {
            if (CommonUtils.isInstalled(getContext(), KeyConst.HAPPY_PLAY_PAKEGE)){
                CommonUtils.openApp(getContext(),KeyConst.HAPPY_PLAY_PAKEGE);
            }else {
                ToastUtil.toast("关于");
            }
        }catch (Exception e){
            ToastUtil.toast(e.getMessage());
        }

    }

    //我的订单
    @OnClick(R.id.tv_order_list)
    public void goToMyOrderListAct() {
        if (SharedStorage.mIsLogin) {
            Intent intent = new Intent(getActivity(), OrderListActivity.class);
            startActivity(intent);
            UIUtils.activityAnimInt(getActivity());
        } else {
            //TODO 未登录请先登录
            AlertUtils.showCancleableErrorDialog(getActivity(),
                                       UIUtils.getString(R.string.您好请先登录),UIUtils.getString(R.string.确定),UIUtils.getString(R.string.取消),
                                       new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which)
                                           {
                                               Intent intent = new Intent(getActivity(),
                                                                          LoginActivity.class);
                                               startActivity(intent);
                                               UIUtils.activityAnimInt(getActivity());
                                           }
                                       });
        }
    }


    //购物车
    @OnClick(R.id.tv_shopping_car)
    public void goToShoppingCarAct(){
        if (SharedStorage.mIsLogin) {
            Intent intent = new Intent(getActivity(), ShoppingCarActivity.class);
            startActivity(intent);
            UIUtils.activityAnimInt(getActivity());
        } else {
            //TODO 未登录请先登录
            AlertUtils.showCancleableErrorDialog(getActivity(),
                                                 UIUtils.getString(R.string.您好请先登录),UIUtils.getString(R.string.确定),UIUtils.getString(R.string.取消),
                                                 new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which)
                                                     {
                                                         Intent intent = new Intent(getActivity(),
                                                                                    LoginActivity.class);
                                                         startActivity(intent);
                                                         UIUtils.activityAnimInt(getActivity());
                                                     }
                                                 });
        }
    }


    @OnClick(R.id.tv_setting)
    public void goToSettingAct(){
        goToRegisterAct();
    }

    @OnClick(R.id.tv_user_detail)
    public void onClickCustomerInfo() {
        if (SharedStorage.mIsLogin) {
            goToRegisterAct();
        }
    }

    @OnClick(R.id.tv_user_name)
    public void goToLoginAct() {
        if (!SharedStorage.mIsLogin) {
            //进入登录注册页面
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            UIUtils.activityAnimInt(getActivity());
        }
    }

    @OnClick(R.id.tv_user_logout)
    public void doLogout() {
        if (!SharedStorage.mIsLogin) {
            //进入登录注册页面
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            UIUtils.activityAnimInt(getActivity());
        } else {
            AlertUtils.showErrorDialog(getActivity(),
                                       UIUtils.getString(R.string.退出登录提示),
                                       UIUtils.getString(R.string.退出登录),
                                       UIUtils.getString(R.string.取消),
                                       new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               SharedStorage.mIsLogin = false;
                                               SharedStorage.getInstance(getActivity())
                                                            .reSetUserData();
                                               //清除登录信息
                                               refreshLoginStatues();
                                           }
                                       });

        }
    }

    private void goToRegisterAct() {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());
    }

    public void gotoAIAct() {
        Intent intent = new Intent(getContext(), AIViewActivity.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());
    }

    public void refreshLoginStatues() {
        if (!SharedStorage.mIsLogin) {
            mTvUserName.setText(UIUtils.getString(R.string.您好请先登录));
            mIvIcon.setImageResource(R.drawable.user);
            mTvUserDetail.setVisibility(View.GONE);
            mTvUserLogout.setVisibility(View.GONE);
        } else {
            mTvUserLogout.setVisibility(View.VISIBLE);
            mTvUserName.setText(StringUtils.checkString(mUserName));
            mTvUserDetail.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(userIconUrl)) {
                Glide.with(UIUtils.getContext())
                     .load(userIconUrl)
                     .error(R.drawable.user)
                     .bitmapTransform(new GlideCircleTransform(getActivity(), null))
//                     .fitCenter()
                     .into(mIvIcon);
            }
        }
    }



}
