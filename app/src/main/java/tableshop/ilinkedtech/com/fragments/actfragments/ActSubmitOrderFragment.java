package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActSubmitOrderFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/28 14:51
 *  @描述：    TODO 提交订单 页面
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.AgreementDetailAct;
import tableshop.ilinkedtech.com.ishop.PayDetailActivity;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.SwitchButton;

public class ActSubmitOrderFragment
        extends IShopBaseFragment
{
    private static final String TAG = "ActSubmitOrderFragment";
    CarDetailBean mCarDetailBean;
    @BindView(R.id.tv_car_name)
    TextView     mTvCarName;
    @BindView(R.id.tv_price)
    TextView     mTvPrice;
    @BindView(R.id.tv_brand_name)
    TextView     mTvBrandName;
    @BindView(R.id.tv_series_name)
    TextView     mTvSeriesName;
    @BindView(R.id.tv_model_name)
    TextView     mTvModelName;
    @BindView(R.id.tv_earnest_money)
    TextView     mTvEarnestMoney;
    @BindView(R.id.sw_agree)
    SwitchButton mSwAgreen;
    @BindView(R.id.btn_submit_order)
    Button       mBtnSubmitOrder;
    Unbinder unbinder;
    @BindView(R.id.tv_agree_detail)
    TextView mTvAgreeDetail;
    Unbinder unbinder1;

    public static ActSubmitOrderFragment newInstance() {
        ActSubmitOrderFragment fragment = new ActSubmitOrderFragment();
        Bundle                 args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_submit_order;
    }

    @Override
    protected void initView() {
        defaultEventBus.register(this);
    }

    @Override
    protected void initEvent() {
        mSwAgreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBtnSubmitOrder.setEnabled(isChecked);
            }
        });
        mSwAgreen.setChecked(true);
    }

    @OnClick(R.id.tv_agree_detail)
    public void onShowAgreeDetail(){
        Intent intent =new Intent(getActivity(), AgreementDetailAct.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());
    }


    @OnClick(R.id.btn_submit_order)
    public void goToOderDetailAct(){
        if (mCarDetailBean!=null) {
            Intent intent = new Intent(getActivity(), PayDetailActivity.class);
            intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, new Gson().toJson(mCarDetailBean));
            startActivity(intent);
            UIUtils.activityAnimInt(getActivity());
        }

    }

    public void setData(CarDetailBean carDetailBeen) {
        this.mCarDetailBean = carDetailBeen;
    }

    @Override
    public void refreshDatas() {
        if (mCarDetailBean != null) {
            String carName = StringUtils.checkString(mCarDetailBean.brandName) +" | "+ StringUtils.checkString(
                    mCarDetailBean.seriesName)+" | " + mCarDetailBean.modelName;
            mTvCarName.setText(StringUtils.checkString(carName));
            mTvBrandName.setText(StringUtils.checkString(mCarDetailBean.brandName));
            mTvSeriesName.setText(StringUtils.checkString(mCarDetailBean.seriesName));
            mTvModelName.setText(StringUtils.checkString(mCarDetailBean.modelName));
            mTvPrice.setText(StringUtils.checkString(mCarDetailBean.price) + getString(R.string.万));
            mTvEarnestMoney.setText("¥99.00" + getString(R.string.元));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean) {
        if (payResultBean != null) {
            if (payResultBean.resultState == PayResultBean.TYPE_SUSSECE) {
                //TODO 进入支付成功页面
                //                showPayResultSuccesDialog();
                getActivity().finish();
            } else if (payResultBean.resultState == PayResultBean.TYPE_CANCLE) {
                //TODO 取消支付对应的操作
//                ToastUtil.toast(getString(R.string.取消支付));
            } else {
//                ToastUtil.toast(getString(R.string.支付错误));
            }
        }
    }


    private void showPayResultSuccesDialog() {
        AlertUtils.showErrorDialog(getContext(),
                                   UIUtils.getString(R.string.支付成功) + "" + UIUtils.getString(R.string.请耐心等待供应商确认),
                                   new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           getActivity().finish();
                                           UIUtils.activityBackToMain(getActivity());
                                       }
                                   });
    }


}
