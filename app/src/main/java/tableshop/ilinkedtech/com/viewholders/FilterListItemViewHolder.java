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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;

/**
 * 我的频道
 */
public class FilterListItemViewHolder
        extends BaseViewHolder
{

    @BindView(R.id.tv_content)
    public TextView mTvContent;

    public FilterListItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
//        View inflate = View.inflate(UIUtils.getContext(), R.layout.tv_show, null);

    }

    @Override
    protected void clear() {

    }

}
