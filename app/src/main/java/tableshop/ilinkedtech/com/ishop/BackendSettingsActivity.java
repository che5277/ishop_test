package tableshop.ilinkedtech.com.ishop;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.ishop
 *  @文件名:   BackendSettingsActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/14 15:12
 *  @描述：    TODO
 */

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseActivity;
import tableshop.ilinkedtech.com.beans.events.BaseUrlChangeBean;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.DBJsonHelper;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class BackendSettingsActivity
        extends IShopBaseActivity
        implements View.OnClickListener
{

    @BindView(R.id.rb_dev)
    RadioButton mRbDev;
    @BindView(R.id.rb_custom)
    RadioButton mRbCustom;
    @BindView(R.id.et_dev)
    EditText    mEtDev;
    @BindView(R.id.et_custom)
    EditText    mEtCustom;
    @BindView(R.id.tv_toolbar_title)
    TextView    mTvToolbarTitle;
    @BindView(R.id.tv_add_button)
    TextView    mTvAddButton;
    @BindView(R.id.toolbar)
    Toolbar     mToolbar;
    @BindView(R.id.rb_pd)
    RadioButton mRbPd;
    @BindView(R.id.et_pd)
    EditText    mEtPd;
    @BindView(R.id.rb_te)
    RadioButton mRbTe;
    @BindView(R.id.et_te)
    EditText    mEtTe;
    private String        backendType;
    private SharedStorage sharedStorage;


    @Override
    public int getLayoutViewId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        return R.layout.activity_backend_settings;
    }

    @Override
    public void initView() {
        sharedStorage = SharedStorage.getInstance(this);
        mTvToolbarTitle.setText(getString(R.string.选择后台服务器));
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                UIUtils.activityBackToMain(BackendSettingsActivity.this);
            }
        });

    }

    public static final String PREFERENCE_BACKENDTYPE_KEY = "backendType";
    public static final String PREFERENCE_CUSTOMERURL_KEY = "custom_backend_url";
    public static final String PREFERENCE_BASE_URL_KEY    = "base_url";

    @Override
    public void initEvent() {
        mRbDev.setOnClickListener(this);
        mRbPd.setOnClickListener(this);
        mRbTe.setOnClickListener(this);
        mRbCustom.setOnClickListener(this);
        mTvAddButton.setOnClickListener(this);
        mEtDev.setText(Const.getBaseUrl(Const.DEV_HOST));
        mEtPd.setText(Const.getBaseUrl(Const.PD_HOST));
        mEtTe.setText(Const.getBaseUrl(Const.TE_HOST));

        String custom_base_url = sharedStorage.getStringSharedData(PREFERENCE_CUSTOMERURL_KEY,
                                                                   Const.CUSTOM_BASE_URL);
        mEtCustom.setText(StringUtils.isEmpty(custom_base_url)
                          ? Const.CUSTOM_BASE_URL
                          : custom_base_url);

        backendType = sharedStorage.getStringSharedData(PREFERENCE_BACKENDTYPE_KEY,
                                                        Const.BACKEDTYPE);
        setupBackendSetting(backendType);
    }

    /*
* 刷新选择状态
* */
    private void setupBackendSetting(String backend_type) {
        backendType = backend_type;
        EditText editText = null;
        String   url      = null;
        switch (backend_type) {
            case KeyConst.BackendType.DEV:
                mRbDev.setChecked(true);
                editText = mEtDev;
                break;
            case KeyConst.BackendType.PD:
                mRbPd.setChecked(true);
                editText = mEtPd;
                break;
            case KeyConst.BackendType.TE:
                mRbTe.setChecked(true);
                editText = mEtTe;
                break;
            case KeyConst.BackendType.CUSTOM:
                mRbCustom.setChecked(true);
                editText = mEtCustom;
                break;
            default:
                break;
        }
        if (editText != null) {
            url = editText.getText()
                          .toString()
                          .trim();
            if (!StringUtils.isEmpty(url) && url.contains("/")) {
                int index = url.indexOf("/");
                editText.setSelection((index + 2) % editText.length());
                editText.requestFocus();
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_dev:
                setupBackendSetting(KeyConst.BackendType.DEV);
                break;
            case R.id.rb_pd:
                setupBackendSetting(KeyConst.BackendType.PD);
                break;
            case R.id.rb_te:
                setupBackendSetting(KeyConst.BackendType.TE);
                break;
            case R.id.rb_custom:
                setupBackendSetting(KeyConst.BackendType.CUSTOM);
                break;
            case R.id.tv_add_button:
                doSaveBackendUrl();
                break;
        }
    }


    private void doSaveBackendUrl() {
        if (!StringUtils.isEmpty(backendType)) {
            Const.BACKEDTYPE = backendType;
            DBJsonHelper.deleteDBBeanDatas();
            switch (backendType) {
                case KeyConst.BackendType.DEV:
                    Const.DEV_HOST = Const.getBaseUrl(mEtDev.getText()
                                                            .toString()
                                                            .trim());
                    break;
                case KeyConst.BackendType.PD:
                    Const.PD_HOST = Const.getBaseUrl(mEtPd.getText()
                                                          .toString()
                                                          .trim());
                    break;
                case KeyConst.BackendType.TE:
                    Const.TE_HOST = Const.getBaseUrl(mEtTe.getText()
                                                          .toString()
                                                          .trim());
                    break;
                case KeyConst.BackendType.CUSTOM:
                    String customerUrl = mEtCustom.getText()
                                                  .toString()
                                                  .trim();
                    if (!StringUtils.isEmpty(customerUrl)) {
                        customerUrl = Const.getBaseUrl(customerUrl);
                        Const.CUSTOM_BASE_URL = customerUrl;
                        sharedStorage.putStringSharedData(PREFERENCE_CUSTOMERURL_KEY, customerUrl);
                        sharedStorage.setBaseUrl(customerUrl);
                    } else {
                        showSnackbar(getString(R.string.请输入正确的后台地址));
                        return;
                    }
                    break;
                default:
                    backendType = KeyConst.BackendType.DEV;
                    mEtDev.setEnabled(true);
                    break;
            }
            Const.setBaseUrl();
            defaultEventBus.post(BaseUrlChangeBean.newInstance());
            sharedStorage.putStringSharedData(PREFERENCE_BASE_URL_KEY, Const.BASE_HOST);
            sharedStorage.putStringSharedData(PREFERENCE_BACKENDTYPE_KEY, backendType);
            finish();
            overridePendingTransition(R.anim.reverse_enter, R.anim.reverse);
        } else {
            showSnackbar(getString(R.string.请选择至少一个后端地址));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
