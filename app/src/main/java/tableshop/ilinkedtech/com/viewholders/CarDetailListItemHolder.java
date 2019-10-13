package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   CarDetailListItemHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:03
 *  @描述：    TODO
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;

public class CarDetailListItemHolder
        extends BaseViewHolder {

    public TextView mTvPrice;
    public TextView mTvCarName;
    public ImageView mIvIcon;
    public ImageView mIvBg;
    public ImageView mIvFavorite;
    public ImageView mIvVehicleState;
    public ImageView mIvShare;
    public RelativeLayout mRlShowCarDetail;

    public CarDetailListItemHolder(View itemView) {
        super(itemView);
        mTvPrice = itemView.findViewById(R.id.tv_price);
        mTvCarName = itemView.findViewById(R.id.tv_car_name);

        mIvIcon = itemView.findViewById(R.id.iv_icon);
        mIvBg = itemView.findViewById(R.id.iv_bg);
        mIvFavorite = itemView.findViewById(R.id.iv_favorite);
        mIvVehicleState= itemView.findViewById(R.id.iv_vehicle_state);
        mIvShare = itemView.findViewById(R.id.iv_share);
        mRlShowCarDetail = itemView.findViewById(R.id.rl_showCarDetail);
    }

    @Override
    protected void clear() {
    }
}
