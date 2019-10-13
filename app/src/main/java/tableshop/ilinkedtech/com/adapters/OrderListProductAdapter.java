package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   OrderListProductAdapter
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
import tableshop.ilinkedtech.com.ishop.OrderDetailProductActivity;
import tableshop.ilinkedtech.com.ishop.ProductDetailActivity;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.OrderListItemViewHolder;

public class OrderListProductAdapter
        extends BaseRecyclerAdapter<OrderListItemBean> {
    private Activity                     mActivity;

    public int viewType=0;

    public OrderListProductAdapter(ArrayList<OrderListItemBean> mArrayList) {
        super(mArrayList);
    }

    public OrderListProductAdapter(ArrayList<OrderListItemBean> arrayList, Activity activity) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=activity;

    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                    inflate = View.inflate(UIUtils.getContext(), R.layout.item_order_list_product, null);
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
            if (!StringUtils.isEmpty(orderListDataBean.category)&&orderListDataBean.category.equals(KeyConst.CategoryType.PRODUCT)){
                orderListItemViewHolder.mRlItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToOrderDetailAct(orderListDataBean);
                    }
                });
            }
            orderListItemViewHolder.mIvOrderIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToProductDetailAct(orderListDataBean);
                }
            });
        }
    }

    private void goToOrderDetailAct(OrderListItemBean orderListDataBean) {
        Intent intent =new Intent(mActivity, OrderDetailProductActivity.class);
        intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, orderListDataBean);
        mActivity.startActivity(intent);
        UIUtils.activityAnimInt(mActivity);
    }

    private void goToProductDetailAct(OrderListItemBean orderListDataBean) {
        if (orderListDataBean!=null&&!StringUtils.isEmpty(orderListDataBean.productUid)) {
            Intent intent = new Intent(mActivity, ProductDetailActivity.class);
            intent.putExtra(KeyConst.BundleIntentKey.PRODUCT_ID, orderListDataBean.productUid);
            mActivity.startActivity(intent);
            UIUtils.activityAnimInt(mActivity);
        }
    }


}


