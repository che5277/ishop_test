package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActPayDetailFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/28 14:51
 *  @描述：    TODO 支付订单具体页面
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.WeChatPayResponBean;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.main.WeChatRequestPayBean;
import tableshop.ilinkedtech.com.beans.responbeans.CheckCustomerOrder;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.ishop.LoginActivity;
import tableshop.ilinkedtech.com.others.alipay.PayResult;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ThreadPoolUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;
import tableshop.ilinkedtech.com.wxapi.WXPayEntryActivity;

import static tableshop.ilinkedtech.com.consts.KeyConst.AliPayKey.SDK_PAY_FLAG;

public class ActPayDetailFragment
        extends IShopBaseFragment
        implements View.OnClickListener
{
    private static final String TAG = "ActPayDetailFragment";
    @BindView(R.id.iv_order_icon)
    ImageView      mIvOrderIcon;
    @BindView(R.id.ll_iv_layout)
    RelativeLayout mLlIvLayout;
    @BindView(R.id.tv_order_price)
    TextView       mTvOrderPrice;
    @BindView(R.id.tv_order_name)
    TextView       mTvOrderName;
    @BindView(R.id.iv_wx_icon)
    ImageView      mIvWxIcon;
    @BindView(R.id.rl_wx_iv_layout)
    RelativeLayout mRlWxIvLayout;
    @BindView(R.id.tv_wx_title)
    TextView       mTvWxTitle;
    @BindView(R.id.cb_wx)
    CheckBox       mCbWx;
    @BindView(R.id.iv_ali_icon)
    ImageView      mIvAliIcon;
    @BindView(R.id.rl_ali_iv_layout)
    RelativeLayout mRlAliIvLayout;
    @BindView(R.id.tv_ali_title)
    TextView       mTvAliTitle;
    @BindView(R.id.cb_ali)
    CheckBox       mCbAli;
    @BindView(R.id.btn_pay)
    View           mBtnPay;
    @BindView(R.id.tv_result)
    TextView       mTvResult;
    @BindView(R.id.iv_code_img)
    ImageView      mIvCodeImg;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    static final int ALI_PAY = 1;
    static final int WX_PAY  = 2;
    int pay_type = 0;
    CarDetailBean carDetailBean;
    public static String mOutTradeNumber;

    public int orderState=-1;

    private static final int STATE_FULL=1;
    private static final int STATE_NOFULL=2;

    public static ActPayDetailFragment newInstance() {
        ActPayDetailFragment fragment = new ActPayDetailFragment();
        Bundle               args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pay_detail_view;
    }

    @Override
    protected void initView() {
        defaultEventBus.register(this);
        //====================TODO 微信支付 begin===========================
        api = WXAPIFactory.createWXAPI(getActivity(), Const.WX_APP_ID, false);
        //====================TODO 微信支付 end===========================
        mOutTradeNumber=null;
    }

    @Override
    protected void initEvent() {
        pay_type = WX_PAY;
        refreshCheckBox();
        mBtnPay.setOnClickListener(this);
        mCbAli.setOnClickListener(this);
        mCbWx.setOnClickListener(this);
        goToCheckCustomerOrder();

    }

    private void goToCheckCustomerOrder() {
        orderState=-1;
        MyHttpUtils.postJson(Const.IS_ORDER_FULL, null, null, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                try {
                    CheckCustomerOrder checkCustomerOrder = new Gson().fromJson(response,
                                                                                CheckCustomerOrder.class);
                    if (checkCustomerOrder!=null){
                        orderState=checkCustomerOrder.check?STATE_NOFULL:STATE_FULL;
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void doAffterRequestCall() {

            }
        });
    }

    @Override
    public void refreshDatas() {
        if (carDetailBean != null) {
            ImageLoadUtils.setImageSource(getActivity(),
                                          carDetailBean.seriesDefaultImageURL,
                                          mIvOrderIcon,
                                          null);
            String carName = StringUtils.checkString(carDetailBean.brandName)+StringUtils.checkString(carDetailBean.seriesName) + carDetailBean.modelName;
            mTvOrderName.setText(StringUtils.checkString(carName));
            mTvOrderPrice.setText("¥" + StringUtils.getTenThousandStringFromDouble(carDetailBean.price) + getString(
                    R.string.万));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean){
        if (payResultBean!=null){
            if (payResultBean.resultState == PayResultBean.TYPE_SUSSECE) {
                //TODO 进入支付成功页面
                showPayResultSuccesDialog();

            } else if (payResultBean.resultState == PayResultBean.TYPE_CANCLE) {
                //TODO 取消支付对应的操作
                ToastUtil.toast(getString(R.string.取消支付));
            } else {
                ToastUtil.toast(getString(R.string.支付错误));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_ali:
                //ali pay
                pay_type = ALI_PAY;
                refreshCheckBox();
                break;
            case R.id.cb_wx:
                //wx pay
                pay_type = WX_PAY;
                refreshCheckBox();
                break;
            case R.id.btn_pay:
                // pay
                if (!WXPayEntryActivity.isPayCalling) {
                    SharedStorage instance = SharedStorage.getInstance(getContext());
                    String customToken = instance.getCustomToken();
                    String mobile = instance.getMobile();
                    if (SharedStorage.mIsLogin&&!StringUtils.isEmpty(customToken)&&!StringUtils.isEmpty(mobile)) {
                        if (orderState==STATE_NOFULL){
                            goToPay();
                        }else {
                            ToastUtil.toast("您未完成的订单数量已超过最大下单数。");
                        }
                    }else {
                        //TODO 未登录请先登录
                        AlertUtils.showCancleableErrorDialog(getContext(),
                                                   UIUtils.getString(R.string.您好请先登录),UIUtils.getString(R.string.确定),UIUtils.getString(R.string.取消),
                                                   new DialogInterface.OnClickListener() {
                                                       @Override
                                                       public void onClick(DialogInterface dialog,
                                                                           int which)
                                                       {
                                                           Intent intent =new Intent(getActivity(), LoginActivity.class);
                                                           startActivity(intent);
                                                           UIUtils.activityAnimInt(getActivity());
                                                       }
                                                   });

                    }
                }

                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        WXPayEntryActivity.isPayCalling = false;
    }

    private void goToPay() {
        WXPayEntryActivity.isPayCalling = true;
//        goToGetQRCodeFile();
        mOutTradeNumber=null;
        switch (pay_type) {
            case WX_PAY:
                goToWXPay();
                break;
            case ALI_PAY:
//                goToGetQRCodeFile();//test
                goToAliPay();
                break;
            default:
                WXPayEntryActivity.isPayCalling = false;
                getBaseActivity().showSnackbar(getString(R.string.请至少选择一种支付方式));
                break;
        }
    }

    private void refreshCheckBox() {
        mCbWx.setChecked(false);
        mCbAli.setChecked(false);
        switch (pay_type) {
            case WX_PAY:
                mCbWx.setChecked(true);
                break;
            case ALI_PAY:
                mCbAli.setChecked(true);
                break;
        }
    }

    private void goToWXPay() {
//        mOutTradeNumber = mCarDetailBean.vinNo+"_";
        mOutTradeNumber = "vehicle_";
        WeChatRequestPayBean requestPayBean = new WeChatRequestPayBean();
//        requestPayBean.attach=mCarDetailBean.vehicleSalesUid+","+SharedStorage.getInstance(getContext()).getMobile();
        requestPayBean.attach="vehicle,"+SharedStorage.getInstance(getContext()).getMobile();
        requestPayBean.batchNumber= carDetailBean.batchNumber;
//        requestPayBean.supplierId= mCarDetailBean.supplierCompanyUid;
        requestPayBean.vehicleUid= carDetailBean.uid;
        requestPayBean.vehicleSalesPrice=carDetailBean.stringTypePrice;
        Map<String, String> head=new HashMap<>();
        MyHttpUtils.addFullHead(head);
        String json = new Gson().toJson(requestPayBean);
        String url=Const.getBaseHttpsUrl()+Const.TEST_WXPAY;
        showLoading();
        MyHttpUtils.postJson(KeyConst.CacheType.NO_CACHE,
                             getActivity(),
                             false,
                             url,
                             head,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                     WXPayEntryActivity.isPayCalling = false;
                                 }

                                 @Override
                                 public void onResponse(String response, int id) {

                                     try {
                                         WeChatPayResponBean responBean = new Gson().fromJson(
                                                 response,
                                                 WeChatPayResponBean.class);
                                         if (responBean != null) {
                                             openWxPay(responBean);
                                         }
                                     } catch (Exception e) {
                                         WXPayEntryActivity.isPayCalling = false;
                                     }

                                 }

                                 @Override
                                 public void doAffterRequestCall() {
                                     hideLoading();
                                 }
                             });
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mBtnPay.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mBtnPay!=null) {
            mBtnPay.setEnabled(true);
        }
    }

    private void openWxPay(WeChatPayResponBean responBean) {
        PayReq req = new PayReq();
        req.appId = Const.WX_APP_ID;
        req.packageValue = Const.WX_PACKEGE_VALUE;/** 商家根据财付通文档填写的数据和签名 */
        req.partnerId = responBean.partnerid;/** "partnerid"商家向财付通申请的商家id TODO 可以写死*/
        req.prepayId = responBean.prepayid;/** 预支付订单 */
        req.nonceStr = responBean.noncestr;/** 随机串，防重发 */
        req.timeStamp = responBean.timestamp;/** 时间戳，防重发 */
        req.sign = responBean.sign;/** 商家根据微信开放平台文档对数据做的签名 */
        req.extData = carDetailBean.vinNo; //
        // 在支付之前，如果应用没有注册到微信，应该先调IWXMsg.registerApp将应用注册到微信
        //支付结果在 WXPayEntryActivity 处理
        try {
            api.sendReq(req);
        } catch (Exception e) {
            e.printStackTrace();
            WXPayEntryActivity.isPayCalling = false;
            getBaseActivity().showSnackbar("打开微信失败");
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    WXPayEntryActivity.isPayCalling = false;
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult(); //TODO  同步返回需要验证的信息,有数据返回
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //TODO 生成二维码图片
                        defaultEventBus.post(PayResultBean.newInstance(PayResultBean.TYPE_SUSSECE));
//                        onPayResultBean(PayResultBean.newInstance(PayResultBean.TYPE_SUSSECE));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        onPayResultBean(PayResultBean.newInstance(PayResultBean.TYPE_CANCLE));
//                        ToastUtil.toast(getString(R.string.取消支付) + resultInfo);
                    }
                    break;

                default:
                    break;
            }

        }

        ;
    };

    private void showPayResultSuccesDialog() {
        AlertUtils.showErrorDialog(getContext(),
                                   UIUtils.getString(R.string.支付成功)+""+UIUtils.getString(R.string.请耐心等待供应商确认),
                                   new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           getActivity().finish();
                                           UIUtils.activityBackToMain(getActivity());
                                       }
                                   });
    }

    /**
     * TODO 支付宝支付 向服务器获取支付串码
     */
    private void goToAliPay() {
//        mOutTradeNumber = mCarDetailBean.vinNo+"_"+ System.currentTimeMillis();
        mOutTradeNumber = "vehicle_"+ System.currentTimeMillis();
        WeChatRequestPayBean requestPayBean = new WeChatRequestPayBean();
//        requestPayBean.body=mCarDetailBean.vehicleSalesUid+","+SharedStorage.getInstance(getContext()).getMobile();
        requestPayBean.body="vehicle,"+SharedStorage.getInstance(getContext()).getMobile();
        requestPayBean.batchNumber= carDetailBean.batchNumber;
//        requestPayBean.supplierId= mCarDetailBean.supplierCompanyUid;
        requestPayBean.vehicleUid= carDetailBean.uid;
        requestPayBean.vehicleSalesPrice=carDetailBean.stringTypePrice;
        Map<String, String> head=new HashMap<>();
        MyHttpUtils.addFullHead(head);
        String json = new Gson().toJson(requestPayBean);
        String url=Const.getBaseHttpsUrl()+Const.TEST_ALIPAY;
        showLoading();
        MyHttpUtils.postJson(KeyConst.CacheType.NO_CACHE,
                             getActivity(),
                             false,
                             url,
                             head,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                     WXPayEntryActivity.isPayCalling = false;
                                 }

                                 @Override
                                 public void onResponse(String response, int id) {

                                     try {
                                         if (!StringUtils.isEmpty(response)) {
                                             openAliPay(response);
                                         } else {
                                             WXPayEntryActivity.isPayCalling = false;
                                         }
                                     } catch (Exception e) {
                                         WXPayEntryActivity.isPayCalling = false;
                                     }

                                 }

                                 @Override
                                 public void doAffterRequestCall() {
                                     hideLoading();
                                 }
                             });
    }

    /**
     * TODO 拿到阿里支付的支付串码后调起阿里支付接口
     * @param orderInfo
     */
    private void openAliPay(final String orderInfo) {

        ThreadPoolUtils.getInstance()
                       .addTask(new Runnable() {
                           @Override
                           public void run() {

                               PayTask             alipay = new PayTask(getActivity());
                               Map<String, String> result = alipay.payV2(orderInfo, true);
                               LogUtils.i("msp", result.toString());

                               Message msg = new Message();
                               msg.what = SDK_PAY_FLAG;
                               msg.obj = result;
                               mHandler.sendMessage(msg);

                           }
                       });

    }

    public void setData(CarDetailBean carDetailBeen) {
        this.carDetailBean = carDetailBeen;
    }
}
