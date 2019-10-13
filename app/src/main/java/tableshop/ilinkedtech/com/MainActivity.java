package tableshop.ilinkedtech.com;

/*
 *  @项目名：  iShop
 *  @文件名:   MainActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import tableshop.ilinkedtech.com.base.IShopBaseActivity;
import tableshop.ilinkedtech.com.beans.events.BackToHome;
import tableshop.ilinkedtech.com.beans.events.BaseUrlChangeBean;
import tableshop.ilinkedtech.com.beans.events.MenberSateBean;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.beans.reques.LoginForMenberFindById;
import tableshop.ilinkedtech.com.beans.reques.MemberDetailsBean;
import tableshop.ilinkedtech.com.beans.responbeans.LoginMenberResponBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.factorys.FragmentFactory;
import tableshop.ilinkedtech.com.fragments.main.AccessoryFragment;
import tableshop.ilinkedtech.com.fragments.main.ChooseCarFragment;
import tableshop.ilinkedtech.com.fragments.main.HomeFragment;
import tableshop.ilinkedtech.com.fragments.main.MyFragment;
import tableshop.ilinkedtech.com.ishop.BackendSettingsActivity;
import tableshop.ilinkedtech.com.ishop.SearchActivity;
import tableshop.ilinkedtech.com.ishop.ShowProductActivity;
import tableshop.ilinkedtech.com.others.VersionCheckHelper;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.DownLoadProgressBar;
import tableshop.ilinkedtech.com.views.NoScrollViewPager;
import tableshop.ilinkedtech.com.views.keyboard.EditView;
import tableshop.ilinkedtech.com.views.keyboard.SKeyboardView;

public class MainActivity
        extends IShopBaseActivity
        implements View.OnClickListener
{


    private static final String TAG    = "BottomActivity";
    public               int    mIndex = -1;
    @BindView(R.id.tv_toolbar_title)
    TextView          mTvToolbarTitle;
    @BindView(R.id.iv_logo)
    ImageView         mIvLogo;
    @BindView(R.id.tv_add_button)
    TextView          mTvAddButton;
    @BindView(R.id.iv_right)
    ImageView         mIvRight;
    @BindView(R.id.toolbar)
    Toolbar           mToolbar;
    @BindView(R.id.bottome_divider)
    View              mBottomeDivider;
    @BindView(R.id.home_selected_layout)
    RelativeLayout    mHomeSelectedLayout;
    @BindView(R.id.iv_home_icon)
    ImageView         mIvHomeIcon;
    @BindView(R.id.tv_home_text)
    TextView          mTvHomeText;
    @BindView(R.id.home_layout)
    RelativeLayout    mHomeLayout;
    @BindView(R.id.car_selected_layout)
    RelativeLayout    mCarSelectedLayout;
    @BindView(R.id.iv_car_icon)
    ImageView         mIvCarIcon;
    @BindView(R.id.tv_car_Text)
    TextView          mTvCarText;
    @BindView(R.id.rl_car_normal)
    RelativeLayout    mRlCarNormal;
    @BindView(R.id.carLayout)
    RelativeLayout    mCarLayout;
    @BindView(R.id.accessory_seleted_layout)
    RelativeLayout    mAccessorySeletedLayout;
    @BindView(R.id.iv_accessory_icon)
    ImageView         mIvAccessoryIcon;
    @BindView(R.id.tv_accessory_text)
    TextView          mTvAccessoryText;
    @BindView(R.id.rl_accessory_normal)
    RelativeLayout    mRlAccessoryNormal;
    @BindView(R.id.accessory_layout)
    RelativeLayout    mAccessoryLayout;
    @BindView(R.id.my_selected_layout)
    RelativeLayout    mMySelectedLayout;
    @BindView(R.id.iv_my_icon)
    ImageView         mIvMyIcon;
    @BindView(R.id.tv_my_text)
    TextView          mTvMyText;
    @BindView(R.id.rl_my_normal)
    RelativeLayout    mRlMyNormal;
    @BindView(R.id.my_layout)
    RelativeLayout    mMyLayout;
    @BindView(R.id.main_viewpager)
    NoScrollViewPager mMainViewpager;
    @BindView(R.id.loadingProgressBar)
    ProgressBar       mLoadingProgressBar;
    @BindView(R.id.tv_exit)
    TextView          mTvExit;
    private MainAdapter         mMainAdapter;
    private SharedStorage       mSharedStorage;
    private String              mUserName;
    private long                mExitTime;
    private DownLoadProgressBar mDownLoadProgressBar;
    private EditView            mEditView;
    private Dialog              mViewDialog;

    @Override
    public int getLayoutViewId() {
        return R.layout.base_bottom_activity;
    }

    public void showProgressBar() {
        if (mLoadingProgressBar != null) { mLoadingProgressBar.setVisibility(View.VISIBLE); }
    }

    public void clossProgressBar() {
        if (mLoadingProgressBar != null) { mLoadingProgressBar.setVisibility(View.GONE); }
    }

    @Override
    public void initView() {
        Const.windowWidth = getWindowManager().getDefaultDisplay()
                                              .getWidth();
        Const.windowHeight = getWindowManager().getDefaultDisplay()
                                               .getHeight();
        mIvRight.setImageResource(R.drawable.search);
        mMainAdapter = new MainAdapter(getSupportFragmentManager());
        mMainViewpager.setAdapter(mMainAdapter);
        mMainViewpager.setOffscreenPageLimit(3);
        mMainViewpager.addOnPageChangeListener(mMainAdapter);
        defaultEventBus.register(this);
        initCustomerInfo();
        refreshBottomView(FragmentFactory.FRAGMENT_HOME);
        refreshToolBarTitle(FragmentFactory.FRAGMENT_HOME);

        checkNewVersion();

    }
    //隐藏的应用退出框~~
    @OnLongClick(R.id.tv_exit)
    public boolean quiteApp() {
        View          view          = View.inflate(this, R.layout.dialog_passwored, null);
        SKeyboardView sKeyboardView = view.findViewById(R.id.s_keyboard_view);
        mEditView = view.findViewById(R.id.edit_view);

        LinearLayout llKeyboard = view.findViewById(R.id.ll_keyboard);
        mEditView.setEditView(llKeyboard, sKeyboardView, true);

        iniEditView();

        mViewDialog = AlertUtils.showViewDialog(this, true, view);
        mEditView.requestFocus();
        return false;
    }

    public static final String password = "1508";

    private void iniEditView() {
        mEditView.setText("");
        mEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String msg = mEditView.getText()
                                      .toString()
                                      .trim();
                if (!StringUtils.isEmpty(msg) && msg.equals(password)) {
                    MainActivity.this.finish();
                    UIUtils.activityBackToMain(MainActivity.this);
                }
            }
        });
    }


    public void initCustomerInfo() {
        mSharedStorage = SharedStorage.getInstance(this);
        boolean login = mSharedStorage.isLogin();
        if (login) {
            String customerInfo = mSharedStorage.getCustomerInfo();
            if (!StringUtils.isEmpty(customerInfo)) {
                LoginMenberResponBean loginMenberResponBean = new Gson().fromJson(customerInfo,
                                                                                  LoginMenberResponBean.class);
                if (loginMenberResponBean != null && loginMenberResponBean.datas != null && loginMenberResponBean.datas.size() > 0) {

                    EventBus.getDefault()
                            .post(loginMenberResponBean);
                }
            }

        } else {
            //验证token是否有效
            checkTokenWithFindMenberId();
        }
    }

    private void checkNewVersion() {
        VersionCheckHelper.checkNewVersionOnResum(this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mDownLoadProgressBar == null) {
                    mDownLoadProgressBar = new DownLoadProgressBar(MainActivity.this);
                }
                VersionCheckHelper.getNewVersionAndInstall(MainActivity.this, mDownLoadProgressBar);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void checkTokenWithFindMenberId() {
        String mobile      = mSharedStorage.getMobile();
        String customToken = mSharedStorage.getCustomToken();
        if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(customToken)) {
            gotoFindMenberId(mobile);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMenberSateBean(MenberSateBean menberSateBean) {
        if (menberSateBean != null && menberSateBean.isLogin) {
            checkTokenWithFindMenberId();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackToHome(BackToHome backToHome) {
        if (backToHome != null) {
            refreshBottomView(backToHome.position);
            refreshToolBarTitle(backToHome.position);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseUrlChangeBean(BaseUrlChangeBean changeBean) {
        checkNewVersion();
    }


    private void gotoFindMenberId(final String mobile) {
        LoginForMenberFindById.DataBean dataBean = new LoginForMenberFindById.DataBean();
        dataBean.username = mobile;
        String json = new Gson().toJson(dataBean);

        Map<String, String> head = new HashMap<>();
        MyHttpUtils.addFullHead(head);
        MyHttpUtils.postJson(Const.MEMBER_FINDBYID, head, json, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
                mSharedStorage.setIsLogin(false);
                EventBus.getDefault()
                        .post(new LoginMenberResponBean());
            }

            @Override
            public void onResponse(String response, int id) {
                mSharedStorage.setIsLogin(false);
                LoginMenberResponBean loginMenberResponBean = new Gson().fromJson(response,
                                                                                  LoginMenberResponBean.class);
                if (loginMenberResponBean.status.equals("1") && loginMenberResponBean != null && loginMenberResponBean.datas != null && loginMenberResponBean.datas.size() > 0) {
                    MemberDetailsBean datasBean = loginMenberResponBean.datas.get(0);
                    if (datasBean != null) {
                        if (!StringUtils.isEmpty(datasBean.lastName) || !StringUtils.isEmpty(
                                datasBean.firstName))
                        {
                            //token未过期,更新用户信息
                            mSharedStorage.setIsLogin(true);
                            mSharedStorage.setUsername(mobile);
                            mSharedStorage.setCustomerInfo(response);

                        }
                    }
                }
                EventBus.getDefault()
                        .post(loginMenberResponBean);
            }

            @Override
            public void doAffterRequestCall() {

            }
        });
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mTvAddButton.setOnClickListener(this);
        mHomeLayout.setOnClickListener(this);
        mCarLayout.setOnClickListener(this);
        mAccessoryLayout.setOnClickListener(this);
        mMyLayout.setOnClickListener(this);
        mIvLogo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, BackendSettingsActivity.class);
                startActivity(intent);
                UIUtils.activityAnimInt(MainActivity.this);
                return false;
            }
        });

        refreshBottomView(FragmentFactory.FRAGMENT_HOME);
        refreshToolBarTitle(FragmentFactory.FRAGMENT_HOME);

    }


    public void refreshToolBarTitle(int position) {
        mIvLogo.setVisibility(View.GONE);
        mIvRight.setVisibility(View.GONE);
        mTvAddButton.setVisibility(View.GONE);
        mTvToolbarTitle.setVisibility(View.GONE);
        String title = "";
        switch (position) {
            case FragmentFactory.FRAGMENT_HOME:
                mIvLogo.setVisibility(View.VISIBLE);
                mIvRight.setVisibility(View.VISIBLE);
                title = UIUtils.getString(R.string.app_title);
                break;
            case FragmentFactory.FRAGMENT_CHOOSE_CAR:
                mTvToolbarTitle.setVisibility(View.VISIBLE);
                title = UIUtils.getString(R.string.选择品牌);
                break;
            case FragmentFactory.FRAGMENT_ACCESSORY://2
//
                refreshBottomView(FragmentFactory.FRAGMENT_HOME);
                refreshToolBarTitle(FragmentFactory.FRAGMENT_HOME);

                mTvAddButton.setVisibility(View.VISIBLE);
            mTvAddButton.setText("确定");
                mTvToolbarTitle.setVisibility(View.VISIBLE);
             //   title = UIUtils.getString(R.string.商品列表);
                startActivity(new Intent(this, ShowProductActivity.class));
                break;
            case FragmentFactory.FRAGMENT_MY://3
                mIvLogo.setVisibility(View.VISIBLE);
                title = UIUtils.getString(R.string.我的);
                break;
        }
        mTvToolbarTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_layout:
                refreshBottomView(FragmentFactory.FRAGMENT_HOME);
                refreshToolBarTitle(FragmentFactory.FRAGMENT_HOME);
                break;
            case R.id.carLayout:
                refreshBottomView(FragmentFactory.FRAGMENT_CHOOSE_CAR);
                refreshToolBarTitle(FragmentFactory.FRAGMENT_CHOOSE_CAR);
                break;
            case R.id.accessory_layout:
                refreshBottomView(FragmentFactory.FRAGMENT_ACCESSORY);
                refreshToolBarTitle(FragmentFactory.FRAGMENT_ACCESSORY);
                break;
            case R.id.my_layout:
                refreshBottomView(FragmentFactory.FRAGMENT_MY);
                refreshToolBarTitle(FragmentFactory.FRAGMENT_MY);
                break;
            case R.id.tv_add_button:

                break;
        }
    }


    @OnClick(R.id.iv_right)
    public void goToSearchAct() {
        Intent intent = new Intent(UIUtils.getContext(), SearchActivity.class);
        startActivity(intent);
        UIUtils.activityAnimInt(this);
    }


    private class MainAdapter
            extends FragmentPagerAdapter
            implements ViewPager.OnPageChangeListener
    {

        public List<Fragment> fragments = new ArrayList<>();
        //        private String[]       titles    = {"微信", "通讯录", "发现", "我"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new HomeFragment());
            fragments.add(new ChooseCarFragment());
            fragments.add(new AccessoryFragment());
            fragments.add(new MyFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    public void refreshBottomView(int position) {
        if (mIndex == -1) {
            mHomeSelectedLayout.setVisibility(View.GONE);
            mTvHomeText.setVisibility(View.VISIBLE);
        } else if (position != mIndex) {
            switch (mIndex) {
                case FragmentFactory.FRAGMENT_HOME:
                    mHomeSelectedLayout.setVisibility(View.GONE);
                    mTvHomeText.setVisibility(View.VISIBLE);
                    doNormalAnimate(mIvHomeIcon);
                    break;
                case FragmentFactory.FRAGMENT_CHOOSE_CAR:
                    mCarSelectedLayout.setVisibility(View.GONE);
                    mTvCarText.setVisibility(View.VISIBLE);
                    doNormalAnimate(mIvCarIcon);
                    break;
                case FragmentFactory.FRAGMENT_ACCESSORY://2
                    mAccessorySeletedLayout.setVisibility(View.GONE);
                    mTvAccessoryText.setVisibility(View.VISIBLE);
                    doNormalAnimate(mIvAccessoryIcon);
                    break;
                case FragmentFactory.FRAGMENT_MY://3
                    mMySelectedLayout.setVisibility(View.GONE);
                    mTvMyText.setVisibility(View.VISIBLE);
                    doNormalAnimate(mIvMyIcon);
                    break;
            }
        }
        switch (position) {
            case FragmentFactory.FRAGMENT_HOME:
                mTvHomeText.setVisibility(View.GONE);
                mHomeSelectedLayout.setVisibility(View.VISIBLE);
                doSeletedAnimate(mIvHomeIcon);
                break;
            case FragmentFactory.FRAGMENT_CHOOSE_CAR:
                mTvCarText.setVisibility(View.GONE);
                mCarSelectedLayout.setVisibility(View.VISIBLE);
                doSeletedAnimate(mIvCarIcon);
                break;
            case FragmentFactory.FRAGMENT_ACCESSORY://2
                mTvAccessoryText.setVisibility(View.GONE);
                mAccessorySeletedLayout.setVisibility(View.VISIBLE);
                doSeletedAnimate(mIvAccessoryIcon);
                break;
            case FragmentFactory.FRAGMENT_MY://3
                mTvMyText.setVisibility(View.GONE);
                mMySelectedLayout.setVisibility(View.VISIBLE);
                doSeletedAnimate(mIvMyIcon);
                break;
        }

        mIndex = position;
        try {
            mMainViewpager.setCurrentItem(position);
        } catch (Exception e) {
            LogUtils.e(TAG, "position:" + position + "==count:" + mMainAdapter.getCount());
        }
    }

    private final float scaleSize    = 1.5f;
    private final float translationY = 12f;

    private void doSeletedAnimate(ImageView ivSeleted) {

        ivSeleted.setScaleX(1.0f);
        ivSeleted.setScaleY(1.0f);
        ivSeleted.animate()
                 .scaleX(scaleSize)
                 .scaleY(scaleSize)
                 .y(0)
                 .translationY(translationY)
                 .setInterpolator(new OvershootInterpolator())
                 .setDuration(300)
                 .start();
    }

    private void doNormalAnimate(View ivSeleted) {
        ivSeleted.setScaleX(scaleSize);
        ivSeleted.setScaleY(scaleSize);
        ivSeleted.animate()
                 .scaleX(1.0f)
                 .scaleY(1.0f)
                 .y(translationY)
                 .translationY(0)
                 //                 .setInterpolator(new OvershootInterpolator())
                 .setDuration(300)
                 .start();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.toast(UIUtils.getString(R.string.再按一次退出程序));
            mExitTime = System.currentTimeMillis();
            // 利用handler延迟发送更改状态信息
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedStorage.mIsLogin = false;
        System.exit(0);
    }

    List<String> mProNum;
    List<ProductItemBean> mp;
    ProductItemBean [] arrBean;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getChoiceProduct(List<ProductItemBean> bean){
        Log.e("event-","tryevent_3333__"+bean.get(0).uid);



    }

}
