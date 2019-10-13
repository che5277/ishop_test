package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   ContrastListItemHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:03
 *  @描述：    TODO
 */

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;

public class ContrastListItemHolder
        extends BaseViewHolder
{


    @BindView(R.id.view_seleted)
    public CheckBox       mViewSeleted;
    @BindView(R.id.iv_bg)
    public ImageView      mIvBg;
    @BindView(R.id.iv_icon)
    public ImageView      mIvIcon;
    @BindView(R.id.tv_car_name)
    public TextView       mTvCarName;
    @BindView(R.id.rl_showCarDetail)
    public RelativeLayout mRlShowCarDetail;

    public ContrastListItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    @Override
    protected void clear() {
    }
}
