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
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.ProductDetailActivity;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.OrderDetailProductItemViewHolder;

public class OrderDetailProductAdapter
        extends BaseRecyclerAdapter<ProductItemBean> {
    private Activity                     mActivity;

    public int viewType=0;

    public OrderDetailProductAdapter(ArrayList<ProductItemBean> mArrayList) {
        super(mArrayList);
    }

    public OrderDetailProductAdapter(ArrayList<ProductItemBean> arrayList, Activity activity) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=activity;

    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                             inflate                 = View.inflate(UIUtils.getContext(), R.layout.item_order_detial_product, null);
        OrderDetailProductItemViewHolder orderListItemViewHolder =new OrderDetailProductItemViewHolder(inflate);
        return orderListItemViewHolder;
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OrderDetailProductItemViewHolder orderListItemViewHolder =(OrderDetailProductItemViewHolder)holder;
        final ProductItemBean            orderListDataBean       = mArrayList.get(position);
        if (orderListDataBean!=null){
            orderListItemViewHolder.refreshViewWithDatas(orderListDataBean);
            orderListItemViewHolder.mIvOrderIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToProductDetailAct(orderListDataBean);
                }
            });

        }
    }

    private void goToProductDetailAct(ProductItemBean orderListDataBean) {
        if (orderListDataBean!=null&&!StringUtils.isEmpty(orderListDataBean.productUid)) {
            Intent intent = new Intent(mActivity, ProductDetailActivity.class);
            intent.putExtra(KeyConst.BundleIntentKey.PRODUCT_ID, orderListDataBean.productUid);
            mActivity.startActivity(intent);
            UIUtils.activityAnimInt(mActivity);
        }
    }


}


