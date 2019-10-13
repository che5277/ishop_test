package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   ShopListItemViewHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 12:54
 *  @描述：    TODO
 */

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.utils.UIUtils;

/**
 * 我的频道
 */
public class ShopListItemViewHolder
        extends BaseViewHolder
{


    @BindView(R.id.tv_shop_number_icon)
    public TextView       mIvShopNumberIcon;
    @BindView(R.id.ll_left_layout)
    public RelativeLayout mLlLeftLayout;
    @BindView(R.id.tv_distanse)
    public TextView       mIvDistanse;
    @BindView(R.id.tv_selete_shop)
    public TextView       mTvSeleteShop;
    @BindView(R.id.ll_right_layout)
    public LinearLayout   mLlRightLayout;
    @BindView(R.id.tv_shop_name)
    public TextView       mTvShopName;
    @BindView(R.id.tv_shop_address)
    public TextView       mTvShopAddress;
    @BindView(R.id.rl_item_view)
    public RelativeLayout mRlItemView;

    public ShopListItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        View inflate = View.inflate(UIUtils.getContext(), R.layout.item_shop_list, null);

    }

    @Override
    protected void clear() {

    }

}
