package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   ShoppingCarItemHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:03
 *  @描述：    TODO
 */

import android.text.Layout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.beans.events.ShoppinCarPriceChange;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.utils.RxTextUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.AmountView;

public class ShoppingCarItemHolder
        extends BaseViewHolder
{


    @BindView(R.id.cb_item)
    CheckBox       mCbItem;
    @BindView(R.id.iv_icon)
    public ImageView      mIvIcon;
    @BindView(R.id.tv_price)
    TextView       mTvPrice;
    @BindView(R.id.tv_product_name)
    TextView       mTvProductName;
    @BindView(R.id.tv_color)
    TextView       mTvColor;
    @BindView(R.id.amount_view)
    AmountView     mAmountView;
    @BindView(R.id.ll_content_layout)
    public RelativeLayout mLlContentLayout;
    TextView       mTvStorege;

    public ShoppingCarItemHolder(View itemView) {
        super(itemView);

        mTvStorege=itemView.findViewById(R.id.tv_storage);
        View view = View.inflate(UIUtils.getContext(), R.layout.item_shopping_car, null);

    }

    /**
     * 商品列表数据刷新
     * @param productItemBean
     */
    public void refreshViewWithDatas(final ProductItemBean productItemBean) {

        String defaultImage=productItemBean.productDefaultImgUrl;
//        if (!StringUtils.isEmpty(productItemBean.productDefaultImgUrl)) {
            Glide.with(mIvIcon.getContext())
                 .load(productItemBean.productDefaultImgUrl)
                 .error(R.drawable.noproduct)
                 .into(mIvIcon);
//        }

        if (StringUtils.isEmpty(defaultImage)){
            defaultImage=productItemBean.img1Url;
        }
        Glide.with(mIvIcon.getContext())
             .load(defaultImage)
             .error(R.drawable.noproduct)
             .into(mIvIcon);
        RxTextUtils.getBuilder("")
                   .setBold()
                   .setAlign(Layout.Alignment.ALIGN_CENTER)
                   .append("¥ ")
                   .append(productItemBean.sellingPrice + "")
                   .setForegroundColor(UIUtils.getColor(R.color.price_color))
                   .setProportion(1.2f)
                   .into(mTvPrice);

        String storegeText=UIUtils.getString(R.string.库存0件);
        mTvStorege.setText(storegeText.replace("%",productItemBean.stock+""));
        mTvProductName.setText(StringUtils.checkString(productItemBean.productName));
        mTvColor.setText(StringUtils.checkString(productItemBean.type));
        mAmountView.setGoods_storage(productItemBean.stock);
        mAmountView.etAmount.setText(productItemBean.num+"");
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                productItemBean.num=amount;
                EventBus.getDefault().post(ShoppinCarPriceChange.newInstance());
            }
        });

        mCbItem.setChecked(productItemBean.isSeleted);
        mCbItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productItemBean.isSeleted=mCbItem.isChecked();
                EventBus.getDefault().post(ShoppinCarPriceChange.newInstance());
            }
        });
    }


    /**
     * 购物车列表数据刷新
     * @param productItemBean
     */
    public void refreshShoppingCarViewWithDatas(ProductItemBean productItemBean) {


//        if (!StringUtils.isEmpty(productItemBean.productDefaultImgUrl)) {
            Glide.with(mIvIcon.getContext())
                 .load(productItemBean.productDefaultImgUrl)
                 .error(R.drawable.noproduct)
                 .into(mIvIcon);
//        }

        RxTextUtils.getBuilder("")
                   .setBold()
                   .setAlign(Layout.Alignment.ALIGN_CENTER)
                   .append("¥ ")
                   .append(productItemBean.sellingPrice + "")
                   .setForegroundColor(UIUtils.getColor(R.color.price_color))
                   .setProportion(1.2f)
                   .into(mTvPrice);

        mTvProductName.setText(StringUtils.checkString(productItemBean.productName));
    }

    @Override
    protected void clear() {
    }
}
