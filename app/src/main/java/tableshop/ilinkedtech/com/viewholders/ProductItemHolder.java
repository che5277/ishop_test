package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   ProductItemHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:03
 *  @描述：    TODO
 */

import android.text.Layout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.utils.RxTextUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class ProductItemHolder
        extends BaseViewHolder
{

    @BindView(R.id.cb_choice)
   public CheckBox choice;
    @BindView(R.id.iv_icon)
    ImageView    mIvIcon;
    @BindView(R.id.iv_product_state)
    ImageView    mIvProductState;
    @BindView(R.id.tv_product_name)
    TextView     mTvProductName;
    @BindView(R.id.tv_price)
    TextView     mTvPrice;
    @BindView(R.id.ll_content_layout)
    public LinearLayout mLlContentLayout;

    public ProductItemHolder(View itemView) {
        super(itemView);
//        View view = View.inflate(UIUtils.getContext(), R.layout.item_product_view, null);

    }

    /**
     * 商品列表数据刷新
     * @param productItemBean
     */
    public void refreshProductViewWithDatas(ProductItemBean productItemBean) {

//        ImageLoadUtils.into(mIvIcon,productItemBean.img1Url);
//        if (productItemBean.productImgUrl!=null&&productItemBean.productImgUrl.size()>0) {
//            Glide.with(UIUtils.getContext())
//                 .load(productItemBean.productDefaultImgUrl)
//                 .error(R.drawable.noproduct)
//                 .into(mIvIcon);
//        }

        if (!StringUtils.isEmpty(productItemBean.productDefaultImgUrl)) {
            Glide.with(mIvIcon.getContext())
                 .load(productItemBean.productDefaultImgUrl)
                 .error(R.drawable.noproduct)
                 .into(mIvIcon);
        }
        RxTextUtils.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                   .append("¥ ")
                   .append(productItemBean.sellingPrice+"").setForegroundColor(UIUtils.getColor(R.color.price_color)).setProportion(1.2f)
                   .into(mTvPrice);

        mTvProductName.setText(StringUtils.checkString(productItemBean.productName));

    }


    /**
     * 购物车列表数据刷新
     * @param productItemBean
     */
    public void refreshShoppingCarViewWithDatas(ProductItemBean productItemBean) {

//        ImageLoadUtils.into(mIvIcon,productItemBean.img1Url);
//        if (productItemBean.productImgUrl!=null&&productItemBean.productImgUrl.size()>0) {
//            Glide.with(mIvIcon.getContext())
//                 .load(productItemBean.productImgUrl.get(0))
//                 .error(R.drawable.noproduct)
//                 .into(mIvIcon);
//        }

        Glide.with(mIvIcon.getContext())
             .load(productItemBean.productDefaultImgUrl)
             .error(R.drawable.noproduct)
             .into(mIvIcon);
        RxTextUtils.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                   .append("¥ ")
                   .append(productItemBean.sellingPrice+"").setForegroundColor(UIUtils.getColor(R.color.price_color)).setProportion(1.2f)
                   .into(mTvPrice);

        mTvProductName.setText(StringUtils.checkString(productItemBean.productName));
    }

    @Override
    protected void clear() {
    }
}
