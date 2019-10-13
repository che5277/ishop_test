package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   MyViewHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 12:54
 *  @描述：    TODO
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.base.IShopBaseActivity;
import tableshop.ilinkedtech.com.beans.WeChatPayResponBean;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.main.WeChatRequestPayBean;
import tableshop.ilinkedtech.com.beans.reques.ObjRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.ObjResponsBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.dialog.CommonUtils;
import tableshop.ilinkedtech.com.ishop.LoginActivity;
import tableshop.ilinkedtech.com.others.alipay.PayResult;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ThreadPoolUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.dialogs.RxDialogShapeLoading;
import tableshop.ilinkedtech.com.wxapi.WXPayEntryActivity;

import static tableshop.ilinkedtech.com.consts.KeyConst.AliPayKey.SDK_PAY_FLAG;

/**
 * 选择支付方式对话框
 */
public class PaymentMethodHolder
        extends BaseViewHolder
        implements View.OnClickListener
{
    public static final int WX_PAY                    = 1;
    public static final int ALI_PAY                   = 2;
    public static final int PRODUCT_TYPE_CAR          = 3;
    public static final int PRODUCT_TYPE_SHOPPING_CAR = 4;
    public static final int PRODUCT_TYPE_PRODUCT_DETAIL = 5;
    Activity mActivity;
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
    Button         mBtnPay;
    private int                  payment_type;
    private int                  product_type;
    private String               mOutTradeNumber;
    private CarDetailBean        carDetailBean;
    public  ObjRequestBean       mObjRequestBean;
    private ObjResponsBean       mObjResponsBean;
    private SharedStorage        mInstance;
    private RxDialogShapeLoading mRxDialogShapeLoading;

    public PaymentMethodHolder(View itemView) {
        super(itemView);
        View dialogView = View.inflate(UIUtils.getContext(),
                                       R.layout.view_seleted_payment_method,
                                       null);


    }


    public PaymentMethodHolder(View itemView, Activity activity, int product_type) {
        super(itemView);
        mActivity = activity;
        this.product_type = product_type;
        api = WXAPIFactory.createWXAPI(mActivity, Const.WX_APP_ID, false);
        iniEvent();
    }

    private void iniEvent() {
        mInstance = SharedStorage.getInstance(mActivity);
        payment_type = WX_PAY;
        refreshCheckBox();
        mBtnPay.setOnClickListener(this);
        mCbAli.setOnClickListener(this);
        mCbWx.setOnClickListener(this);
    }

    private void refreshCheckBox() {
        mCbWx.setChecked(false);
        mCbAli.setChecked(false);
        switch (payment_type) {
            case WX_PAY:
                mCbWx.setChecked(true);
                break;
            case ALI_PAY:
                mCbAli.setChecked(true);
                break;
        }
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_ali:
                //ali pay
                payment_type = ALI_PAY;
                refreshCheckBox();
                break;
            case R.id.cb_wx:
                //wx pay
                payment_type = WX_PAY;
                refreshCheckBox();
                break;
            case R.id.btn_pay:
                // pay
                if (!WXPayEntryActivity.isPayCalling) {
                    String        customToken = mInstance.getCustomToken();
                    String        mobile      = mInstance.getMobile();
                    if (SharedStorage.mIsLogin && !StringUtils.isEmpty(customToken) && !StringUtils.isEmpty(
                            mobile))
                    {
                        if (!isApiCall) {
                            createOrderNumber();
                        }
                    } else {
                        //TODO 未登录请先登录
                        AlertUtils.showCancleableErrorDialog(mActivity,
                                                             UIUtils.getString(R.string.您好请先登录),
                                                             UIUtils.getString(R.string.确定),
                                                             UIUtils.getString(R.string.取消),
                                                             new DialogInterface.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(DialogInterface dialog,
                                                                                     int which)
                                                                 {
                                                                     Intent intent = new Intent(
                                                                             mActivity,
                                                                             LoginActivity.class);
                                                                     mActivity.startActivity(intent);
                                                                     UIUtils.activityAnimInt(
                                                                             mActivity);
                                                                 }
                                                             });

                    }
                }
                break;
        }

    }

    public boolean isApiCall=false;
    public void showLoading() {
        hideLoading();
        mRxDialogShapeLoading = CommonUtils.showShapLoadingDialog(mActivity, false);
    }

    public void hideLoading() {
        if (mRxDialogShapeLoading!=null){
            mRxDialogShapeLoading.dismiss();
        }
    }


    private void createOrderNumber() {
        if (mObjRequestBean==null) {
            mObjRequestBean = new ObjRequestBean();
        }
        String json=new Gson().toJson(mObjRequestBean);
        showLoading();
        isApiCall=true;
        MyHttpUtils.postJson(0, mActivity, true, Const.SHOPPING_CREATEORDER, null, json, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
                AlertUtils.dismissDialog();
                ToastUtil.toast("订单创建失败！");


            }

            @Override
            public void onResponse(String response, int id) {
                if (!StringUtils.isEmpty(response)){
                    mObjResponsBean = new Gson().fromJson(response,
                                                          ObjResponsBean.class);
                    if (mObjResponsBean!=null) {
                        if (mObjResponsBean.status.equals("1")&&!StringUtils.isEmpty(mObjResponsBean.transactionId)) {
                            goToPay();
                        }else if (!mObjResponsBean.status.equals("1")){
                            AlertUtils.showErrorDialog(mActivity,StringUtils.checkString(mObjResponsBean.message));
                        }
                    }

                }

            }

            @Override
            public void doAffterRequestCall() {
                isApiCall=false;
                hideLoading();
            }
        });
    }


    private void goToPay() {
        mOutTradeNumber = null;
        switch (payment_type) {
            case WX_PAY:
                goToWXPay();
                break;
            case ALI_PAY:
                goToAliPay();
                break;
            default:
                ((IShopBaseActivity) mActivity).showSnackbar(UIUtils.getString(R.string.请至少选择一种支付方式));
                break;
        }
    }


    private void goToWXPay() {
        WeChatRequestPayBean requestPayBean = new WeChatRequestPayBean();
        if (product_type == PRODUCT_TYPE_CAR) {
            mOutTradeNumber = "vehicle_";
            requestPayBean.attach = "vehicle," + SharedStorage.getInstance(mActivity)
                                                              .getMobile();
            requestPayBean.batchNumber = carDetailBean.batchNumber;
            requestPayBean.vehicleUid = carDetailBean.uid;
            requestPayBean.vehicleSalesPrice = carDetailBean.stringTypePrice;
        }else {
            requestPayBean.outTradeNumber=mObjResponsBean.transactionId;
            requestPayBean.attach=mObjResponsBean.attach;
        }
        Map<String, String> head = new HashMap<>();
        MyHttpUtils.addFullHead(head);
        String json = new Gson().toJson(requestPayBean);
        String url  = Const.getBaseHttpsUrl() + Const.TEST_WXPAY;
        MyHttpUtils.postJson(KeyConst.CacheType.NO_CACHE,
                             mActivity,
                             false,
                             url,
                             head,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
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
                                     }

                                 }

                                 @Override
                                 public void doAffterRequestCall() {

                                 }
                             });
    }

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    private void openWxPay(WeChatPayResponBean responBean) {
        PayReq req = new PayReq();
        req.appId = Const.WX_APP_ID;
        req.packageValue = Const.WX_PACKEGE_VALUE;/** 商家根据财付通文档填写的数据和签名 */
        req.partnerId = responBean.partnerid;/** "partnerid"商家向财付通申请的商家id TODO 可以写死*/
        req.prepayId = responBean.prepayid;/** 预支付订单 */
        req.nonceStr = responBean.noncestr;/** 随机串，防重发 */
        req.timeStamp = responBean.timestamp;/** 时间戳，防重发 */
        req.sign = responBean.sign;/** 商家根据微信开放平台文档对数据做的签名 */
        //        req.extData = carDetailBean.vinNo; //
        // 在支付之前，如果应用没有注册到微信，应该先调IWXMsg.registerApp将应用注册到微信
        //支付结果在 WXPayEntryActivity 处理
        try {
            api.sendReq(req);
        } catch (Exception e) {
            e.printStackTrace();
            getBaseActivity().showSnackbar("打开微信失败");
        }
    }

    private IShopBaseActivity getBaseActivity() {
        return (IShopBaseActivity) mActivity;
    }


    /**
     * TODO 支付宝支付 向服务器获取支付串码
     */
    private void goToAliPay() {
        WeChatRequestPayBean requestPayBean = new WeChatRequestPayBean();
        if (product_type == PRODUCT_TYPE_CAR) {
            mOutTradeNumber = "vehicle_" + System.currentTimeMillis();
            requestPayBean.body = "vehicle," + SharedStorage.getInstance(mActivity)
                                                            .getMobile();
            requestPayBean.batchNumber = carDetailBean.batchNumber;
            requestPayBean.vehicleUid = carDetailBean.uid;
            requestPayBean.vehicleSalesPrice = carDetailBean.stringTypePrice;
        }else {
            requestPayBean.outTradeNumber=mObjResponsBean.transactionId;
            requestPayBean.body=mObjResponsBean.attach;
        }
        Map<String, String> head = new HashMap<>();
        MyHttpUtils.addFullHead(head);
        String json = new Gson().toJson(requestPayBean);
        String url  = Const.getBaseHttpsUrl() + Const.TEST_ALIPAY;
        MyHttpUtils.postJson(KeyConst.CacheType.NO_CACHE,
                             mActivity,
                             false,
                             url,
                             head,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                 }

                                 @Override
                                 public void onResponse(String response, int id) {

                                     try {
                                         if (!StringUtils.isEmpty(response)) {
                                             openAliPay(response);
                                         } else {
                                         }
                                     } catch (Exception e) {
                                     }

                                 }

                                 @Override
                                 public void doAffterRequestCall() {

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

                               PayTask             alipay = new PayTask(mActivity);
                               Map<String, String> result = alipay.payV2(orderInfo, true);
                               LogUtils.i("msp", result.toString());

                               Message msg = new Message();
                               msg.what = SDK_PAY_FLAG;
                               msg.obj = result;
                               mHandler.sendMessage(msg);

                           }
                       });

    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
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
                        showPayResultSuccesDialog();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(PayResultBean.newInstance(PayResultBean.TYPE_CANCLE));
//                        ToastUtil.toast(UIUtils.getString(R.string.取消支付) + resultInfo);
                    }
                    break;

                default:
                    break;
            }

        }

    };


    private void showPayResultSuccesDialog() {
        AlertUtils.dismissDialog();
        PayResultBean payResultBean =PayResultBean.newInstance(PayResultBean.TYPE_SUSSECE);
        EventBus.getDefault().post(payResultBean);
    }


}
