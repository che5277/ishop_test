package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   CarDetailListAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.CarDetailActivity;
import tableshop.ilinkedtech.com.ishop.OrderDetailActivity;
import tableshop.ilinkedtech.com.ishop.OrderSeleteShopAmapAct;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.OrderListItemViewHolder;

public class OrderListAdapter
        extends BaseRecyclerAdapter<OrderListItemBean> {
    private Activity                     mActivity;

    public int viewType=0;
    public static final int VIEW_TYPE_PRODUCT_ITEM=1;

    public OrderListAdapter(ArrayList<OrderListItemBean> mArrayList) {
        super(mArrayList);
    }

    public OrderListAdapter(ArrayList<OrderListItemBean> arrayList, Activity activity) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=activity;

    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                    inflate                 = View.inflate(UIUtils.getContext(), R.layout.item_order_list, null);
        OrderListItemViewHolder orderListItemViewHolder =new OrderListItemViewHolder(inflate);
        return orderListItemViewHolder;
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OrderListItemViewHolder orderListItemViewHolder =(OrderListItemViewHolder)holder;
        final OrderListItemBean orderListDataBean       = mArrayList.get(position);
        if (orderListDataBean!=null){
            orderListItemViewHolder.refreshViewWithDatas(orderListDataBean);
            switch (orderListDataBean.status){
                //TODO 根据订单状态进行不同的操作
                case OrderListItemViewHolder.SUPPLER_NOT_CONFIRM:
                    orderListItemViewHolder.mRlItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertUtils.showErrorDialog(mActivity,UIUtils.getString(R.string.请耐心等待供应商确认));
                        }
                    });
                    break;
                case OrderListItemViewHolder.SUPPLIER_CONFIRM:
                    orderListItemViewHolder.mRlItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToOrderSeleteShopAct(orderListDataBean);
                        }
                    });
                    break;
                case OrderListItemViewHolder.MEMBER_SELECT_SHOP:
                    orderListItemViewHolder.mRlItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToOrderDetailAct(orderListDataBean);
                        }
                    });
                    break;
            }
            orderListItemViewHolder.mIvOrderIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToCarDetailAct(orderListDataBean);
                }
            });
        }

    }

    private void goToOrderSeleteShopAct(OrderListItemBean orderListDataBean) {
        Intent intent =new Intent(mActivity, OrderSeleteShopAmapAct.class);
        intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, orderListDataBean);
        mActivity.startActivity(intent);
        UIUtils.activityAnimInt(mActivity);
    }

    private void goToOrderDetailAct(OrderListItemBean orderListDataBean) {
        Intent intent =new Intent(mActivity, OrderDetailActivity.class);
        intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, orderListDataBean);
        mActivity.startActivity(intent);
        UIUtils.activityAnimInt(mActivity);
    }

    public void goToCarDetailAct(OrderListItemBean carDetailBean){
        Intent intent = new Intent(UIUtils.getContext(), CarDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_ID,carDetailBean.vehicleSalesUid);
        intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_FLAG,carDetailBean.flag);
        mActivity.startActivity(intent);
        UIUtils.activityAnimInt(mActivity);
    }


}


