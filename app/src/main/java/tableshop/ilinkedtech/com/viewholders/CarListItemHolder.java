package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   CarListItemHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:03
 *  @描述：    TODO
 */

import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.utils.RxTextUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class CarListItemHolder
        extends BaseViewHolder
{


//    @BindView(R.id.iv_bg)
    public ImageView      mIvBg;
    @BindView(R.id.iv_icon)
    public ImageView      mIvIcon;
//    @BindView(R.id.iv_vehicle_state)
    public ImageView      mIvVehicleState;
//    @BindView(R.id.rl_showCarDetail)
//    public View mRlShowCarDetail;//---
    @BindView(R.id.tv_car_name)
    public TextView       mTvCarName;
    @BindView(R.id.tv_car_model_name)
    public TextView       mTvCarModelName;

    @BindView(R.id.tv_car_body_color)
    public TextView     mTvCarBodyColor;
    @BindView(R.id.tv_car_interior_color)
    public TextView     mTvCarInteriorColor;
    @BindView(R.id.ll_color_layout)
    public LinearLayout mLlColorLayout;

    @BindView(R.id.ll_content_layout)
    public View   mContentLayout;
    @BindView(R.id.tv_price)
    public TextView       mTvPrice;
    public TextView mTvPosition;
    public ImageView mIvBodyColor;
    public ImageView mIvInteriorColor;

    public CarListItemHolder(View itemView) {
        super(itemView);
        mTvPosition = itemView.findViewById(R.id.tv_position);
        mIvBg = itemView.findViewById(R.id.iv_bg);
        mIvBodyColor = itemView.findViewById(R.id.iv_bodyColor);
        mIvInteriorColor = itemView.findViewById(R.id.iv_interiorColor);
        mIvVehicleState = itemView.findViewById(R.id.iv_vehicle_state);
        //        View view = View.inflate(UIUtils.getContext(), R.layout.item_model_view, null);
    }

    @Override
    protected void clear() {
    }


    public void refreshView(CarDetailBean carModelListItemBean){
        if (carModelListItemBean!=null) {
            if (!StringUtils.isEmpty(carModelListItemBean.seriesDefaultImageURL)&&mIvIcon!=null) {
                Glide.with(UIUtils.getContext())
                     .load(carModelListItemBean.seriesDefaultImageURL)
                     .into(mIvIcon);
            }
            String carName=StringUtils.checkString(carModelListItemBean.brandName)+" "
                    +StringUtils.checkString(carModelListItemBean.seriesName)+" "
                    +StringUtils.checkString(carModelListItemBean.modelName);
            mTvCarName.setText(carName);

            RxTextUtils.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                       .append("¥ ")
                       .append(StringUtils.getTenThousandStringFromDouble(carModelListItemBean.price)+UIUtils.getString(R.string.万))
//                       .append(carModelListItemBean.price+UIUtils.getString(R.string.万))
                       .setForegroundColor(UIUtils.getColor(R.color.price_color)).setProportion(1.3f)
                       .into(mTvPrice);
        }
    }
}
