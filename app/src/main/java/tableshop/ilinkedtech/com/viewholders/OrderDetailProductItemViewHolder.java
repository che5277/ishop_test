package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   OrderDetailProductItemViewHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 12:54
 *  @描述：    TODO
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.utils.StringUtils;

public class OrderDetailProductItemViewHolder
        extends BaseViewHolder
{


    @BindView(R.id.iv_order_icon)
    public ImageView      mIvOrderIcon;
    @BindView(R.id.ll_left_layout)
    RelativeLayout mLlLeftLayout;
    @BindView(R.id.tv_product_name)
    TextView       mTvProductName;
    @BindView(R.id.tv_price)
    TextView       mTvPrice;
    @BindView(R.id.tv_product_type)
    TextView       mTvProductType;
    @BindView(R.id.tv_product_num)
    TextView       mTvProductNum;
    @BindView(R.id.rl_item_view)
    public RelativeLayout mRlItemView;

    public OrderDetailProductItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

//        View view = View.inflate(UIUtils.getContext(), R.layout.item_order_detial_product, null);

    }


    /**
     * 商品订单条目刷新
     * @param productItemBean
     */
    public void refreshViewWithDatas(ProductItemBean productItemBean) {

        mTvProductName.setText(StringUtils.checkString(productItemBean.name));
        mTvProductType.setText(StringUtils.checkString(productItemBean.type));
        mTvProductNum.setText("数量 :"+productItemBean.num);
        mTvPrice.setText("¥"+StringUtils.checkString(productItemBean.price));

        Glide.with(mIvOrderIcon.getContext())
             .load(productItemBean.defaultImgUrl)
             .error(R.drawable.noproduct)
             .into(mIvOrderIcon);

//        ImageLoadUtils.into(mIvOrderIcon,productItemBean.productDefaultImgUrl);

    }

    @Override
    protected void clear() {

    }

}
