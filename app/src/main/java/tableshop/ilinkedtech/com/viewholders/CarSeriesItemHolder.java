package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   CarSeriesItemHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:03
 *  @描述：    TODO
 */

import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.utils.RxTextUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;

public class CarSeriesItemHolder
        extends BaseViewHolder
{


    @BindView(R.id.iv_icon)
    public ImageView mIvIcon;
//    @BindView(R.id.iv_right)
    public ImageView mIvRight;
    @BindView(R.id.tv_car_name)
    public TextView  mTvCarName;
    @BindView(R.id.tv_price)
    public TextView  mTvPrice;
    @BindView(R.id.tv_car_model_name)
    public TextView  mTvCarModelName;
    @BindView(R.id.ll_content_layout)
    public View mLlContentLayout;
    public View priceAndColor;

    public ImageView mIvVehicleState;

    public CarSeriesItemHolder(View itemView) {
        super(itemView);
        try {
            mIvRight=itemView.findViewById(R.id.iv_right);
            mIvVehicleState=itemView.findViewById(R.id.iv_vehicle_state);
            priceAndColor=itemView.findViewById(R.id.rl_price_and_color_view);
        }catch (Exception e){

        }

        View view = View.inflate(UIUtils.getContext(), R.layout.item_series_view, null);

    }

    public void refreshViewWithDatas(CarModelListItemBean carModelListItemBean){
        if (priceAndColor != null) {
            priceAndColor.setVisibility(View.GONE);
        }
        if (carModelListItemBean!=null) {

            String               carName          = StringUtils.checkString(carModelListItemBean.brandName)+" | "+StringUtils.checkString(carModelListItemBean.seriesName);

            if (!StringUtils.isEmpty(carModelListItemBean.seriesDefaultImageURL)) {
                ImageLoadUtils.into(mIvIcon, carModelListItemBean.seriesDefaultImageURL);
            }
            mTvCarName.setText(carName);
            mTvCarName.setVisibility(View.GONE);
            mIvVehicleState.setVisibility(View.VISIBLE);

            RxTextUtils.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                       .append(StringUtils.checkString(carModelListItemBean.title))
                       .setForegroundColor(UIUtils.getColor(R.color.price_color)).setProportion(1.5f)
                       .into(mTvCarModelName);
        }
    }

    @Override
    protected void clear() {
    }
}
