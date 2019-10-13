package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   MyViewHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 12:54
 *  @描述：    TODO
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;

/**
 * 我的频道
 */
public class OrderListItemViewHolder
        extends BaseViewHolder
{
    @BindView(R.id.iv_order_icon)
    public ImageView      mIvOrderIcon;
    @BindView(R.id.ll_left_layout)
    public RelativeLayout mLlLeftLayout;
    @BindView(R.id.iv_order_state)
    public ImageView      mIvOrderState;
    @BindView(R.id.tv_order_state)
    public TextView       mTvOrderState;
    @BindView(R.id.ll_right_layout)
    public LinearLayout   mLlRightLayout;
    @BindView(R.id.tv_order_name)
    public TextView       mTvOrderName;
    @BindView(R.id.tv_order_id)
    public TextView       mTvOrderId;
    @BindView(R.id.tv_order_color)
    public TextView       mTvOrderColor;
    @BindView(R.id.iv_order_icon_bg)
    public ImageView      mIvOrderIconBg;
    @BindView(R.id.rl_item_view)
    public RelativeLayout mRlItemView;

    public TextView       mTvPrice;

    public static final String SUPPLER_NOT_CONFIRM = "SUPPLER_NOT_CONFIRM";//待确认
    public static final String SUPPLIER_CONFIRM    = "SUPPLIER_CONFIRM"; //待选店
    public static final String MEMBER_SELECT_SHOP  = "MEMBER_SELECT_SHOP"; //已选店
    public static final String PAID  = "PAID"; //商品  已付款
    public static final String NOTPAID  = "NOTPAID"; //商品 未付款


    public OrderListItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mTvPrice=itemView.findViewById(R.id.tv_price);
//        View inflate = View.inflate(UIUtils.getContext(), R.layout.item_order_list, null);

    }


    /**
     * 汽车订单条目刷新
     * @param orderListDataBean
     */
    public void refreshViewWithDatas(OrderListItemBean orderListDataBean){
        if (!StringUtils.isEmpty(orderListDataBean.category)) {
            if (orderListDataBean.category.equals(KeyConst.CategoryType.VEHICLE))
            {
                String name = StringUtils.checkString(orderListDataBean.vehicleBrandName) + StringUtils.checkString(
                        orderListDataBean.vehicleSeriesName) + StringUtils.checkString(
                        orderListDataBean.vehicleModelName);
                mTvOrderName.setText(name);
                ImageLoadUtils.into(mIvOrderIcon, orderListDataBean.defaultImgUrl);
                ImageLoadUtils.into(mIvOrderIconBg, orderListDataBean.backgroundImgUrl);
                mTvOrderColor.setText(orderListDataBean.colorName);

                switch (orderListDataBean.status) {
                    //TODO 根据订单状态进行不同的操作
                    case OrderListItemViewHolder.SUPPLER_NOT_CONFIRM:
                        mTvOrderState.setTextColor(UIUtils.getColor(R.color.text_color));
                        mIvOrderState.setImageResource(R.drawable.confirm_no);
                        mTvOrderState.setText(UIUtils.getString(R.string.待确认));
                        break;
                    case OrderListItemViewHolder.SUPPLIER_CONFIRM:
                        mTvOrderState.setTextColor(UIUtils.getColor(R.color.text_color));
                        mIvOrderState.setImageResource(R.drawable.confirm_store);
                        mTvOrderState.setText(UIUtils.getString(R.string.待选店));
                        break;
                    case OrderListItemViewHolder.MEMBER_SELECT_SHOP:
                        mIvOrderState.setImageResource(R.drawable.confirm);
                        mTvOrderState.setText(UIUtils.getString(R.string.已选店));
                        mTvOrderState.setTextColor(UIUtils.getColor(R.color.order_confirm_statue_color));
                        break;
                }

            } else {
                refreshProductView(orderListDataBean);
            }
        }


    }



    public static final int MAX_SHOW_ITEMS=3;//商品最高显示条数
    /**
     * 商品订单条目刷新
     * @param orderListDataBean
     */
    public void refreshProductView(OrderListItemBean orderListDataBean) {
        mLlRightLayout.setVisibility(View.GONE);

        String          productDetails  ="";
        if (orderListDataBean.productDetails!=null&&orderListDataBean.productDetails.size()>0) {
            ProductItemBean productItemBean =null;

            int sum=orderListDataBean.productDetails.size()>MAX_SHOW_ITEMS?MAX_SHOW_ITEMS:orderListDataBean.productDetails.size();

            for (int i = 0; i < sum; i++) {
                productItemBean = orderListDataBean.productDetails.get(i);
                if (i==0){
                    ImageLoadUtils.into(mIvOrderIcon, productItemBean.defaultImgUrl);
                }
                productDetails+=(productItemBean.name+"("+productItemBean.type+")   x"+productItemBean.num);
                if (i<orderListDataBean.productDetails.size()-1){
                    productDetails+="\n";
                }
            }
            if (orderListDataBean.productDetails.size()>MAX_SHOW_ITEMS){
                productDetails+="。。。";
            }
        }
        mTvPrice.setText("¥"+StringUtils.checkString(orderListDataBean.totalAmount));
        mTvOrderName.setText(productDetails);
    }

    @Override
    protected void clear() {

    }

}
