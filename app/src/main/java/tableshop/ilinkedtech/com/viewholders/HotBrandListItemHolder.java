package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   HotBrandListItemHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:03
 *  @描述：    TODO
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;

public class HotBrandListItemHolder
        extends BaseViewHolder
{


    @BindView(R.id.iv_icon)
    public ImageView mIvIcon;
    @BindView(R.id.tv_brand_name)
    public TextView  mTvBrandName;

    public HotBrandListItemHolder(View itemView) {
        super(itemView);
//        View view = View.inflate(UIUtils.getContext(), R.layout.item_hot_brand, null);
    }

    @Override
    protected void clear() {
    }
}
