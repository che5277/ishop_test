package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   OrderDetailProductActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO 商品订单详情 查看二维码
 */

import android.content.DialogInterface;
import android.view.View;

import com.google.gson.Gson;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.QRcodeRquestBean;
import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActProductOrderDetailFragment;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class OrderDetailProductActivity
        extends BaseActionBarActivity {

    boolean isApiCall=false;
    private OrderListItemBean mOrderListItemBean;

    @Override
    protected IShopBaseFragment getShowFragment() {
        mTvToolbarTitle.setText(UIUtils.getString(R.string.订单二维码));
        mIvRight.setVisibility(View.GONE);
        mOrderListItemBean = getIntent().getParcelableExtra(KeyConst.BundleIntentKey.DATA_JSON);
        return new ActProductOrderDetailFragment(mOrderListItemBean);
    }

    @Override
    public void doNavigationAction() {
        if (!isApiCall)
        super.doNavigationAction();
    }


    @Override
    public void onScanResult(final String qrcodeStr) {
        if (!StringUtils.isEmpty(qrcodeStr)){
            try {
                final QRcodeRquestBean qRcodeRquestBean = new Gson().fromJson(qrcodeStr,
                                                                              QRcodeRquestBean.class);
                if (qRcodeRquestBean!=null){


                    AlertUtils.showErrorDialog(this, getString(R.string.是否在table上展示该车辆信息), getString(R.string.确定),getString(R.string.取消),new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callToStoreInfoToBackend(qrcodeStr,qRcodeRquestBean);
                        }
                    });
                }
            }catch (Exception e){
                AlertUtils.showErrorDialog(this, getString(R.string.扫描结果),qrcodeStr);
            }

        }
    }

    private void callToStoreInfoToBackend(final String qrcodeStr, final QRcodeRquestBean qRcodeRquestBean) {
        QRcodeRquestBean bean=new QRcodeRquestBean();
        bean.vehicleSalesOnholdUid= mOrderListItemBean.vehicleSalesUid;
        bean.machineId=qRcodeRquestBean.machineId;
        final String json =new Gson().toJson(bean);
        MyHttpUtils.postJson(Const.STORE_MACHINE_REQUEST, true,null, json, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
                AlertUtils.showErrorDialog(OrderDetailProductActivity.this, UIUtils.getString(R.string.系统出错));
            }

            @Override
            public void onResponse(String response, int id) {
                AlertUtils.showErrorDialog(OrderDetailProductActivity.this, UIUtils.getString(R.string.保存成功));
            }

            @Override
            public void doAffterRequestCall() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
