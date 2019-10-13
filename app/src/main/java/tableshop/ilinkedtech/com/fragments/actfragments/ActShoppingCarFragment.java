package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActShoppingCarFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/8 14:51
 *  @描述：    TODO 我的订单 视图
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.ShoppingCarListAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.events.BackToHome;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.events.ShoppinCarPriceChange;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.beans.reques.AddToShoppingCarRequestBean;
import tableshop.ilinkedtech.com.beans.reques.JsonDataBean;
import tableshop.ilinkedtech.com.beans.reques.ObjRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetProductListBean;
import tableshop.ilinkedtech.com.beans.responbeans.LoginMenberResponBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.factorys.FragmentFactory;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.PaymentMethodHolder;
import tableshop.ilinkedtech.com.views.recyles.RecycleViewDivider;

@SuppressLint("ValidFragment")
public class ActShoppingCarFragment
        extends IShopBaseFragment
{

    @BindView(R.id.cb_all)
    CheckBox           mCbAll;
    @BindView(R.id.tv_totle_price)
    TextView           mTvTotlePrice;
    @BindView(R.id.rl_bottom_layout)
    RelativeLayout     mRlBottomLayout;
    @BindView(R.id.recy)
    RecyclerView       mRecy;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_buy)
    TextView           mTvBuy;
    @BindView(R.id.iv_holder)
    ImageView          mIvHolder;
    @BindView(R.id.tv_holder)
    TextView           mTvHolder;
    @BindView(R.id.ll_back_to_home)
    LinearLayout       mLlBackToHome;
    @BindView(R.id.rl_empty_view)
    RelativeLayout     mRlEmptyView;
    Unbinder unbinder;
    private ShoppingCarListAdapter mAdapter;

    public static ActFavoriteFragment newInstance() {
        ActFavoriteFragment fragment = new ActFavoriteFragment();
        Bundle              args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<ProductItemBean> mArrayList;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopping_car;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        mArrayList = new ArrayList<>();
        RecycleViewDivider recycleViewDivider = new RecycleViewDivider(getContext(),
                                                                       LinearLayoutManager.HORIZONTAL);
        recycleViewDivider.mDividerHeight = 5;
        mRecy.addItemDecoration(recycleViewDivider);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        mRecy.setLayoutManager(manager);
        mAdapter = new ShoppingCarListAdapter(mArrayList, this);
        mAdapter.viewFromType = ShoppingCarListAdapter.VIEW_TYPE_SHOPPING_CAR_LIST;
        mRecy.setAdapter(mAdapter);

        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshTotlePrice(true);
            }
        });

        mRefreshLayout.setEnableOverScrollDrag(true);
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshDatas();
            }
        });

        defaultEventBus.register(this);

        mTvTotlePrice.setText("¥0.0");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShoppinCarPriceChange(ShoppinCarPriceChange priceChange) {
        refreshTotlePrice(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean) {
        if (payResultBean.resultState == PayResultBean.TYPE_SUSSECE) {
            refreshDatas();
            AlertUtils.dismissDialog();
            AlertUtils.showErrorDialog(getContext(), UIUtils.getString(R.string.支付成功));
        } else if (payResultBean.resultState == PayResultBean.TYPE_CANCLE) {
            ToastUtil.toast(UIUtils.getString(R.string.取消支付));
        } else {
            ToastUtil.toast(getString(R.string.支付错误));
        }
    }

    /**
     * 统计总价
     * @param isFromCbAll 是否来自全选的CheckBox
     */
    public ObjRequestBean refreshTotlePrice(boolean isFromCbAll) {
        boolean                     isSeletedAll   = mCbAll.isChecked();
        double                      totlePrice     = 0;
        ObjRequestBean              objRequestBean = new ObjRequestBean();
        AddToShoppingCarRequestBean carRequestBean = null;
        List<Object>                productList    = new ArrayList<>();
        List<String>                productUidList = new ArrayList<>();
        if (mArrayList.size() > 0) {
            ProductItemBean productItemBean = null;
            for (int i = 0; i < mArrayList.size(); i++) {
                productItemBean = mArrayList.get(i);
                if (isFromCbAll) {
                    productItemBean.isSeleted = isSeletedAll;
                }
                if (productItemBean.isSeleted) {
                    if (productItemBean != null && !StringUtils.isEmpty(productItemBean.sellingPrice)) {
                        if (!isFromCbAll) {
                            carRequestBean = new AddToShoppingCarRequestBean();
                            carRequestBean.num = productItemBean.num + "";
                            carRequestBean.type = productItemBean.type;
                            carRequestBean.productUid = productItemBean.uid;
                            productList.add(carRequestBean);
                            productUidList.add(productItemBean.shoppingCartUid);
                        }
                        totlePrice += Double.parseDouble(productItemBean.sellingPrice) * productItemBean.num;
                    }
                }
            }
            if (isFromCbAll) {
                mAdapter.notifyDataSetChanged();
            } else {
                objRequestBean.shoppingCartUidList = productUidList;
                objRequestBean.fromShoppingCart = 1 + "";
                objRequestBean.datas = productList;
            }

        }
        mTvTotlePrice.setText("¥" + totlePrice);

        if (totlePrice > 0) {
            return objRequestBean;
        } else {
            return null;
        }


    }


    @Override
    public void refreshDatas() {
        if (!SharedStorage.mIsLogin) {
            return;
        }
        JsonDataBean dataBean = new JsonDataBean();
        dataBean.userName = SharedStorage.getInstance(getContext())
                                         .getMobile();
        String json = new Gson().toJson(dataBean);
        mArrayList.clear();
        MyHttpUtils.postJson(0,
                             getActivity(),
                             true,
                             Const.SHOPPING_GETSHOPPINGCART,
                             null,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                     if (id==MyHttpUtils.INVALID_TOKEN){
                                         AlertUtils.showErrorDialog(getActivity(),
                                                                    UIUtils.getString(R.string.登录超时请重新登录));
                                     }
                                     if (mRefreshLayout != null) {
                                         mRefreshLayout.finishRefresh(false);
                                         if (mAdapter != null) {
                                             mAdapter.notifyDataSetChanged();
                                         }
                                     }

                                 }

                                 @Override
                                 public void onResponse(String response, int id) {
                                     if (mRefreshLayout != null) {
                                         mRefreshLayout.finishRefresh(true);
                                     }
                                     if (!StringUtils.isEmpty(response)) {
                                         try {
                                             GetProductListBean getProductListBean = new Gson().fromJson(
                                                     response,
                                                     GetProductListBean.class);
                                             if (getProductListBean != null && getProductListBean.status.equals(
                                                     "1"))
                                             {
                                                 if (getProductListBean.datas != null && getProductListBean.datas.size() > 0) {
                                                     if (mAdapter != null && mArrayList != null) {
                                                         mArrayList.clear();
                                                         mArrayList.addAll(getProductListBean.datas);
                                                     }
                                                 }
                                                 showEmptyView(mArrayList.size()==0);
                                             }
                                         } catch (Exception e) {
                                             SharedStorage.mIsLogin=false;
                                             defaultEventBus.post(new LoginMenberResponBean());

                                         }
                                         if (mAdapter != null) {
                                             mAdapter.notifyDataSetChanged();
                                         }

                                     }
                                 }

                                 @Override
                                 public void doAffterRequestCall() {

                                 }
                             });

    }


    @OnClick(R.id.tv_buy)
    public void goToCreateOrderList() {
        ObjRequestBean objRequestBean = refreshTotlePrice(false);
        if (objRequestBean != null) {
            callToCreateOrder(objRequestBean);
        } else {
            ToastUtil.toast("请选择要购买的商品");
        }
    }


    @OnClick(R.id.ll_back_to_home)
    public void goToMainAct() {
        defaultEventBus.post(BackToHome.newInstance(FragmentFactory.FRAGMENT_HOME));
        getActivity().finish();
        UIUtils.activityBackToMain(getActivity());
    }

    public void showEmptyView(boolean isShow) {
        if (mRlEmptyView != null ) {
            mRlEmptyView.setVisibility(isShow
                                       ? View.VISIBLE
                                       : View.GONE);
            mRecy.setVisibility(isShow
                                ? View.GONE
                                : View.VISIBLE);
        }
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
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
