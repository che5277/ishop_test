package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   ShopListAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.ShopListBean;
import tableshop.ilinkedtech.com.beans.events.SeleteShopMsgBean;
import tableshop.ilinkedtech.com.fragments.actfragments.ActOrderSeleteShopFragment;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.ShopListItemViewHolder;

public class ShopListAdapter
        extends BaseRecyclerAdapter<ShopListBean> {

    public ShopListAdapter(ArrayList<ShopListBean> mArrayList) {
        super(mArrayList);
    }

    public ShopListAdapter(ArrayList<ShopListBean> arrayList, ActOrderSeleteShopFragment fragment) {
        super(arrayList);
        mArrayList=arrayList;

    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                   inflate                 = View.inflate(UIUtils.getContext(), R.layout.item_shop_list, null);
        ShopListItemViewHolder orderListItemViewHolder =new ShopListItemViewHolder(inflate);
        return orderListItemViewHolder;
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ShopListItemViewHolder orderListItemViewHolder =(ShopListItemViewHolder)holder;
        final ShopListBean     shopListBean            = mArrayList.get(position);
        if (shopListBean!=null){
            orderListItemViewHolder.mTvShopName.setText(StringUtils.checkString(shopListBean.shopName));
            orderListItemViewHolder.mTvShopAddress.setText(StringUtils.checkString(shopListBean.shopAddress));
            if (!StringUtils.isEmpty(shopListBean.distance)) {
                double parseDouble = Double.parseDouble(shopListBean.distance)/1000d;
                orderListItemViewHolder.mIvDistanse.setText(parseDouble + "km");
            }else {
                orderListItemViewHolder.mIvDistanse.setText(0.0 + "km");
            }
            orderListItemViewHolder.mIvShopNumberIcon.setText((position+1)+"");
            orderListItemViewHolder.mRlItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new SeleteShopMsgBean(position));
                }
            });
            orderListItemViewHolder.mTvSeleteShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new SeleteShopMsgBean(shopListBean.tabSysShopId));
                }
            });
        }

    }


}


