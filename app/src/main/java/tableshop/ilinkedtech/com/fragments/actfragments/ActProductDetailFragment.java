package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActAIListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/8 14:51
 *  @描述：    TODO AI 视图
 */

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.MainActivity;
import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.ShowFilterTagAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.RulesItem;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.beans.reques.AddToShoppingCarRequestBean;
import tableshop.ilinkedtech.com.beans.reques.JsonDataBean;
import tableshop.ilinkedtech.com.beans.reques.ObjRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.ObjResponsBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.DBJsonHelper;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.dialog.QR_CodeDialog;
import tableshop.ilinkedtech.com.fragments.BackFragment;
import tableshop.ilinkedtech.com.ishop.LoginActivity;
import tableshop.ilinkedtech.com.ishop.PictureViewActivity;
import tableshop.ilinkedtech.com.loader.GlideProductImageLoader;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.ShareUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;
import tableshop.ilinkedtech.com.utils.zxingqrcodes.encoding.EncodingHandler;
import tableshop.ilinkedtech.com.viewholders.PaymentMethodHolder;
import tableshop.ilinkedtech.com.views.AmountView;
import tableshop.ilinkedtech.com.views.flowlayout.FlowLayout;
import tableshop.ilinkedtech.com.views.flowlayout.TagFlowLayout;
import tableshop.ilinkedtech.com.views.keyboard.EditView;
import tableshop.ilinkedtech.com.views.keyboard.SKeyboardView;

@SuppressLint("ValidFragment")
public class ActProductDetailFragment
        extends IShopBaseFragment
{

    Unbinder unbinder;

    @BindView(R.id.detail_finish)
    TextView tv_finish;

    @BindView(R.id.btn_buy)
    Button             mBtnBuy;
    @BindView(R.id.ll_bottom_layout)
    LinearLayout       mLlBottomLayout;
    @BindView(R.id.iv_icon)
    ImageView          mIvIcon;
    @BindView(R.id.iv_favorite)
    ImageView          mIvFavorite;
    @BindView(R.id.tv_product_name)
    TextView           mTvProductName;
    @BindView(R.id.flowlayout_type)
    TagFlowLayout      mFlowlayoutType;
    @BindView(R.id.amount_view)
    AmountView         mAmountView;
    @BindView(R.id.tv_product_price)
    TextView           mTvProductPrice;
    @BindView(R.id.tv_storage)
    TextView           mTvStorage;
    @BindView(R.id.tv_product_description)
    TextView           mTvProductDescription;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.banner_product)
    Banner             mBannerProduct;
    Unbinder unbinder1;
    private ArrayList<RulesItem> productTypeDatas;
    private ShowFilterTagAdapter mProductTypeAdapter;
    QR_CodeDialog qr_codeDialog;
    BackFragment backFragment;
    String QRCODE_JSON;
    private EditView mEditView;
    private Dialog mViewDialog;
    public static final String password = "1508";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_detail;
    }

    public ActProductDetailFragment() {
        super();
    }

    ProductItemBean mProductItemBean;

    public ActProductDetailFragment(ProductItemBean productItemBean,String product_uid) {
        super();
        this.mProductItemBean = productItemBean;
        this.mProduct_uid = product_uid;
    }

    @Override
    protected void initView() {

    }

    public void shareProduct(){
        if (mProductItemBean!=null) {
            String carName = StringUtils.checkString(mProductItemBean.productName);
            String url     = mProductItemBean.productDefaultImgUrl;//productDefaultImgUrl
            ShareUtils.showShareListViewNew(getActivity(),
                                            carName,
                                            mProductItemBean.productDescription,
                                            url,
                                            Const.WEB_PRODUCT_DETAIL_URL,
                                            mProductItemBean.uid);
        }
    }

    /**
     * 刷新库存
     * @param stock
     */
    private void refreshStock(int stock) {
        String text = UIUtils.getString(R.string.库存件);
        mTvStorage.setText(text.replace("%", stock + ""));
        mAmountView.setGoods_storage(stock);
        mAmountView.etAmount.setText(stock > 0 ? "1" : "0");
    }

    @Override
    protected void initEvent() {
        mRefreshLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EventBus.getDefault().post(true);
                return false;
            }
        });

        mRefreshLayout.setEnableOverScrollDrag(true);
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getProductDetail();
            }
        });

        mAmountView.setGoods_storage(0);
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
            }
        });

        productTypeDatas = new ArrayList<>();
        mProductTypeAdapter = new ShowFilterTagAdapter(productTypeDatas, mFlowlayoutType);
        mProductTypeAdapter.setType(ShowFilterTagAdapter.PRODUCT_TYPE);
        mFlowlayoutType.setAdapter(mProductTypeAdapter);

        mFlowlayoutType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                RulesItem rulesItem = productTypeDatas.get(position % productTypeDatas.size());
                onTagChange(rulesItem);
                return false;
            }
        });
        defaultEventBus.register(this);
        mRefreshLayout.autoRefresh();

    }

    /**
     * 选中不同的款式
     * @param rulesItem
     */
    private void onTagChange(RulesItem rulesItem) {
        if (rulesItem != null && rulesItem.colorBeanListBean != null) {
            refreshStock(rulesItem.colorBeanListBean.stock);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean) {
        if (payResultBean.resultState == PayResultBean.TYPE_SUSSECE) {
            AlertUtils.dismissDialog();
            AlertUtils.showErrorDialog(getContext(), UIUtils.getString(R.string.商品支付成功提示));
            if (mRefreshLayout != null) { mRefreshLayout.autoRefresh(); }
        }else if (payResultBean.resultState == PayResultBean.TYPE_CANCLE) {
            ToastUtil.toast(UIUtils.getString(R.string.取消支付));
        }else {
            ToastUtil.toast(getString(R.string.支付错误));
        }
    }


    private void refreshViewWithDatas() {
        if (mProductItemBean != null) {
            ArrayList<ProductItemBean> favoriteList = DBJsonHelper.getProductFavoriteList();
            if (favoriteList != null) {
                mProductItemBean.isFavorite = favoriteList.contains(mProductItemBean);
                mIvFavorite.setActivated(mProductItemBean.isFavorite);
            }
            if (mProductItemBean.productDefaultImgUrl!=null)
            ImageLoadUtils.getImageFile(mProductItemBean.productDefaultImgUrl, null);
            else
                Toast.makeText(getContext(),"没有图片！",Toast.LENGTH_SHORT).show();
            mTvProductName.setText(StringUtils.checkString(mProductItemBean.productName));
            if (mProductItemBean.productImgUrl != null && mProductItemBean.productImgUrl.size() > 0) {
                mBannerProduct.setImageLoader(new GlideProductImageLoader());
                mBannerProduct.setImages(mProductItemBean.productImgUrl);
                mBannerProduct.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        goToPictureView(mProductItemBean.productImgUrl,position);
                    }
                });
                mBannerProduct.start();
            }
            mTvProductDescription.setText(StringUtils.checkString(mProductItemBean.productDescription));

            refreshStock(mProductItemBean.stock);

            if (!StringUtils.isEmpty(mProductItemBean.sellingPrice)) {
                mTvProductPrice.setText("¥" + mProductItemBean.sellingPrice + UIUtils.getString(R.string.元));
            }
            RulesItem                         rulesItem         = null;
            ProductItemBean.ColorBeanListBean colorBeanListBean = null;
            productTypeDatas.clear();
            for (int i = 0; i < mProductItemBean.colorBeanList.size(); i++) {
                colorBeanListBean = mProductItemBean.colorBeanList.get(i);
                rulesItem = new RulesItem();
                rulesItem.displayText = colorBeanListBean.colorName;
                rulesItem.colorBeanListBean = colorBeanListBean;
                productTypeDatas.add(rulesItem);
            }
            mProductTypeAdapter.notifyDataChanged();

        }
    }

    @Override
    public void refreshDatas() {

    }


    @OnClick(R.id.iv_favorite)
    public void onFavoriteBtnClick() {
        if (mProductItemBean != null) {
            mProductItemBean.isFavorite = !mProductItemBean.isFavorite;
            mIvFavorite.setActivated(mProductItemBean.isFavorite);
            DBJsonHelper.upProductFavoriteList(mProductItemBean,
                                               mProductItemBean.isFavorite
                                               ? KeyConst.LocalData.ORIENTATION_ADD
                                               : KeyConst.LocalData.ORIENTATION_DEL);
        }
    }


    public RulesItem generaInfo() {
        RulesItem seletedTagItem = null;
        if (!SharedStorage.mIsLogin) {
            //TODO 未登录请先登录
            AlertUtils.showCancleableErrorDialog(getContext(),
                                                 UIUtils.getString(R.string.您好请先登录),
                                                 UIUtils.getString(R.string.确定),
                                                 UIUtils.getString(R.string.取消),
                                                 new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog,
                                                                         int which)
                                                     {
                                                         Intent intent = new Intent(getActivity(),
                                                                                    LoginActivity.class);
                                                         startActivity(intent);
                                                         UIUtils.activityAnimInt(getActivity());
                                                     }
                                                 });
        } else {
            if (mAmountView.amount < 1) {
                ToastUtil.toast("请选择数量");
                return seletedTagItem;
            } else {
                seletedTagItem = StringUtils.getSeletedTagItem(productTypeDatas, mFlowlayoutType);
                if (seletedTagItem == null || seletedTagItem.colorBeanListBean == null) {
                    ToastUtil.toast("请选择商品款式");
                    return null;
                }
            }
        }
        return seletedTagItem;
    }


    @OnClick(R.id.btn_add_to_shopping_car)
    public void onAddToShoppingCar() {
        RulesItem seletedTagItem = generaInfo();
        if (seletedTagItem != null) {
            ObjRequestBean              objRequestBean              = new ObjRequestBean();
            AddToShoppingCarRequestBean addToShoppingCarRequestBean = new AddToShoppingCarRequestBean();
            addToShoppingCarRequestBean.productUid = mProductItemBean.uid;
            addToShoppingCarRequestBean.type = seletedTagItem.colorBeanListBean.colorName;
            addToShoppingCarRequestBean.num = mAmountView.amount + "";
            objRequestBean.data = addToShoppingCarRequestBean;
            objRequestBean.userName = SharedStorage.userName;
            callToAddToShoppingCar(objRequestBean);
        }

    }

    @OnClick(R.id.btn_buy)
    public void onGoToPay() {
     //   RulesItem seletedTagItem = generaInfo();
     //   if (seletedTagItem != null) {
            ObjRequestBean                    objRequestBean    = new ObjRequestBean();
         //   ProductItemBean.ColorBeanListBean colorBeanListBean = seletedTagItem.colorBeanListBean;
            AddToShoppingCarRequestBean       carRequestBean    = new AddToShoppingCarRequestBean();
            carRequestBean.productUid = mProductItemBean.uid;
          //  carRequestBean.type = colorBeanListBean.colorName;
            carRequestBean.num = mAmountView.amount + "";

            List<Object> datas = new ArrayList<>();
            datas.add(carRequestBean);
            objRequestBean.datas = datas;
        Log.e("fromid","testid---"+mProductItemBean.uid);
         QRCODE_JSON="{"+"\"id\""+":"+"\""+mProductItemBean.uid+"\""+","+"\"type\""+":"+"\"commodity\""+"}";
            if (qr_codeDialog==null)
                qr_codeDialog=new QR_CodeDialog();
        try {
            qr_codeDialog.setBitmap(EncodingHandler.createQRCode(QRCODE_JSON,280));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        qr_codeDialog.setVisible(true);
      //  qr_codeDialog.setQRCodeToshopCallBack(qrCodeToshopCallBack);
            qr_codeDialog.show(getActivity().getFragmentManager(),"qrcode");


        //      callToCreateOrder(objRequestBean);
     //   }
    }

    @OnClick(R.id.detail_finish)
    public void getBackShop(){
//        if (backFragment==null){
//            backFragment=new BackFragment();
//        }
//        backFragment.CallBackSuccess(new BackFragment.IsSuccessCallBack() {
//            @Override
//            public void OnIsSuccessCallBack() {
//                getActivity().finish();
//            }
//        });
//
//        backFragment.show(getActivity().getFragmentManager(),"getback");
//

        View          view          = View.inflate(getContext(), R.layout.dialog_passwored, null);
        SKeyboardView sKeyboardView = view.findViewById(R.id.s_keyboard_view);
        mEditView = view.findViewById(R.id.edit_view);

        LinearLayout llKeyboard = view.findViewById(R.id.ll_keyboard);
        mEditView.setEditView(llKeyboard, sKeyboardView, true);

        iniEditView();

        mViewDialog = AlertUtils.showViewDialog(getContext(), true, view);
        mEditView.requestFocus();



    }

    private void callToAddToShoppingCar(ObjRequestBean objRequestBean) {
        String json = new Gson().toJson(objRequestBean);
        MyHttpUtils.postJson(0,
                             getActivity(),
                             true,
                             Const.SHOPPING_SAVECART,
                             null,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {

                                 }

                                 @Override
                                 public void onResponse(String response, int id) {

                                     if (!StringUtils.isEmpty(response)) {
                                         try {
                                             doAffterAddToShoppingCar(response);
                                         } catch (Exception e) {

                                         }
                                     }

                                 }

                                 @Override
                                 public void doAffterRequestCall() {

                                 }
                             });

    }

    private void doAffterAddToShoppingCar(String response) {
        ObjResponsBean objResponsBean = new Gson().fromJson(response, ObjResponsBean.class);
        if (objResponsBean != null && objResponsBean.status.equals("1")) {
            AlertUtils.showErrorDialog(getContext(), "添加购物车成功");
        }
    }

    public String mProduct_uid;

    public void getProductDetail() {
        ObjRequestBean objRequestBean = new ObjRequestBean();
        JsonDataBean   dataBean       = new JsonDataBean();
        dataBean.uid = mProduct_uid;
        objRequestBean.data = dataBean;
        String json = new Gson().toJson(objRequestBean);
        refreshViewWithDatas();
        MyHttpUtils.postJson(0,
                             getActivity(),
                             true,
                             Const.FIND_PRODUCT_DETAILS_BY_UID,
                             null,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                     if (mRefreshLayout != null) {
                                         mRefreshLayout.finishRefresh(false);
                                     }
                                 }

                                 @Override
                                 public void onResponse(String response, int id) {
                                     if (mRefreshLayout != null) {
                                         mRefreshLayout.finishRefresh(true);
                                     }
                                     if (!StringUtils.isEmpty(response)) {
                                         try {
                                             ObjResponsBean objResponsBean = new Gson().fromJson(
                                                     response,
                                                     ObjResponsBean.class);
                                             if (objResponsBean != null &&
                                                     (StringUtils.isEmpty(objResponsBean.status)||objResponsBean.status.equals("1")))
                                             {
                                                 if (objResponsBean.datas != null && objResponsBean.datas.size() > 0) {
                                                     mProductItemBean = objResponsBean.datas.get(0);
//                                                     mProductItemBean = (ProductItemBean) obj;
                                                     refreshViewWithDatas();
                                                 }
                                             }


                                         } catch (Exception e) {
                                             LogUtils.e(TAG,e.toString());
                                         }
                                     }


                                 }

                                 @Override
                                 public void doAffterRequestCall() {
                                     if (mRefreshLayout != null) {
                                         mRefreshLayout.finishRefresh(1000);
                                     }
                                 }
                             });
    }

    public void goToPictureView(List<String> datas, int position) {
        PictureViewActivity.startPictureViewAct(getActivity(), (ArrayList<String>) datas, position);
    }

    private void callToCreateOrder(ObjRequestBean objRequestBean) {
        View                dialogView          = View.inflate(getContext(),
                                                               R.layout.view_seleted_payment_method,
                                                               null);
        PaymentMethodHolder paymentMethodHolder = new PaymentMethodHolder(dialogView,
                                                                          getActivity(),
                                                                          PaymentMethodHolder.PRODUCT_TYPE_SHOPPING_CAR);
        paymentMethodHolder.mObjRequestBean = objRequestBean;
        AlertUtils.showViewDialog(getContext(), true, paymentMethodHolder.itemView, null);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

//ishop二维码消失回调--备用方法
    QR_CodeDialog.QR_CodeToShopCallBack qrCodeToshopCallBack =new QR_CodeDialog.QR_CodeToShopCallBack() {
    @Override
    public void OnQR_CodeToShopCallBack() {
        QR_CodeDialog qr_codeDialog =new QR_CodeDialog();
        try {
            qr_codeDialog.setBitmap(EncodingHandler.createQRCode(QRCODE_JSON,280));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        qr_codeDialog.setVisible(true);

        qr_codeDialog.show(getActivity().getFragmentManager(),"shopCode");
    }
};


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
                    if (MainApp.isRemberData) {
                        startActivity(new Intent(getActivity(),MainActivity.class));
                    }
                    getActivity().finish();

                    //    UIUtils.activityBackToMain(MainActivity.this);
                }
            }
        });
    }


}
