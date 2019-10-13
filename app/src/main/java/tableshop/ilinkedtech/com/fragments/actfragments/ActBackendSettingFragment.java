package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActPayDetailFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/28 14:51
 *  @描述：    TODO 后台管理页面
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.utils.StringUtils;

public class ActBackendSettingFragment
        extends IShopBaseFragment
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
    private SharedStorage sharedStorage;
    private String backendType="";

    public static ActBackendSettingFragment newInstance() {
        ActBackendSettingFragment fragment = new ActBackendSettingFragment();
        Bundle                  args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_backend_settings;
    }

    @Override
    protected void initView() {
        sharedStorage = SharedStorage.getInstance(getContext());
    }

    @Override
    protected void initEvent() {
        mRbDev.setOnClickListener(this);
        mRbCustom.setOnClickListener(this);
    }

    @Override
    public void refreshDatas() {
        String custom_base_url = sharedStorage.getStringSharedData("custom_backend_url");
        mEtCustom.setText(StringUtils.isEmpty(custom_base_url) ? Const.CUSTOM_BASE_URL : custom_base_url);

        backendType = Const.BACKEDTYPE;
        setupBackendSetting(backendType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /*
  * 刷新选择状态
  * */
    private void setupBackendSetting(String backend_type) {
        mEtCustom.setEnabled(false);
        backendType = backend_type;
        switch (backend_type) {
            case KeyConst.BackendType.DEV:
                mRbDev.setChecked(true);
                break;
            case KeyConst.BackendType.CUSTOM:
                mRbCustom.setChecked(true);
                mEtCustom.setEnabled(true);
                break;
            default:
                backendType = "";
                mEtCustom.setEnabled(false);
                mEtDev.setEnabled(false);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_dev:
                setupBackendSetting(KeyConst.BackendType.DEV);
                break;
            case R.id.rb_custom:
                setupBackendSetting(KeyConst.BackendType.CUSTOM);
                break;
        }
    }
}
