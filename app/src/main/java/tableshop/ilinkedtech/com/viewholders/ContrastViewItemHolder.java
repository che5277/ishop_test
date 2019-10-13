package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   ContrastViewItemHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:03
 *  @描述：    TODO
 */

import android.app.Activity;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.ShowStringTagAdapter;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;
import tableshop.ilinkedtech.com.views.flowlayout.TagFlowLayout;

public class ContrastViewItemHolder
        extends BaseViewHolder
{


    @BindView(R.id.iv_car1_icon)
    public ImageView     mIvCar1Icon;
    @BindView(R.id.id_flow_layout)
    public TagFlowLayout mIdFlowLayout;

    public ShowStringTagAdapter mTagAdapter;
    public ArrayList<String>    mTagItems;
    @BindView(R.id.tv_category)
    TextView         mTvCategory;
    @BindView(R.id.tv_oil_supply)
    TextView         mTvOilSupply;
    @BindView(R.id.tv_hub_size)
    TextView         mTvHubSize;
    @BindView(R.id.tv_tank_capacity)
    TextView         mTvTankCapacity;
    @BindView(R.id.tv_series_name)
    TextView         mTvSeriesName;
    @BindView(R.id.tv_model_name)
    TextView         mTvModelName;
    @BindView(R.id.tv_motor)
    TextView         mTvMotor;
    @BindView(R.id.tv_fuel)
    TextView         mTvFuel;
    @BindView(R.id.tv_size)
    TextView         mTvSize;
    @BindView(R.id.tv_engine)
    TextView         mTvEngine;
    @BindView(R.id.tv_tanmittion)
    TextView         mTvTanmittion;
    @BindView(R.id.tv_door)
    TextView         mTvDoor;
    @BindView(R.id.tv_seat)
    TextView         mTvSeat;
    @BindView(R.id.tv_drive)
    TextView         mTvDrive;
    @BindView(R.id.rl_my_item_layout)
    NestedScrollView mRlMyItemLayout;


    public ContrastViewItemHolder(View itemView) {
        super(itemView);
        View.inflate(UIUtils.getContext(), R.layout.item_contrast_detail_view, null);
        mTagItems = new ArrayList<>();
        mTagAdapter = new ShowStringTagAdapter(mTagItems, mIdFlowLayout);
        mIdFlowLayout.setAdapter(mTagAdapter);
        mIdFlowLayout.setClickable(false);
    }

    public ContrastViewItemHolder(View itemView, CarDetailBean detailBean, Activity activity) {
        this(itemView);
        refreshView(detailBean);
    }

    public void refreshView(CarDetailBean detailBean) {

        ImageLoadUtils.into(mIvCar1Icon, detailBean.seriesDefaultImageURL);
        mTvSeriesName.setText(StringUtils.checkString(detailBean.seriesName));
        mTvModelName.setText(StringUtils.checkString(detailBean.modelName));
        //        String carSize=detailBean.bodyLength+"mm*"+detailBean.bodyWidth+"mm*"+detailBean.bodyHeight+"mm";

        String carSize = StringUtils.checkString(detailBean.lengthWidthHeight);
        mTvSize.setText(carSize);//长宽高
        mTvSeat.setText(StringUtils.checkString(detailBean.noOfSeat + ""));//座位
        mTvDoor.setText(StringUtils.checkString(detailBean.noOfDoors + ""));//车门
        mTvFuel.setText(StringUtils.checkString(detailBean.fuseSource));//燃油
        mTvTanmittion.setText(StringUtils.checkString(detailBean.gearBox));//变速
        mTvEngine.setText(StringUtils.checkString(detailBean.horsePower));//马力
        mTvDrive.setText(StringUtils.checkString(detailBean.engineType));//驱动
        mTvMotor.setText(StringUtils.checkString(detailBean.engineLayout));//发动机
        mTvCategory.setText(StringUtils.checkString(detailBean.category));//车型
        mTvOilSupply.setText(StringUtils.checkString(detailBean.oilSupply));//供油方式
        mTvHubSize.setText(StringUtils.checkString(detailBean.hubSize));//轮毂尺寸
        mTvTankCapacity.setText(StringUtils.checkString(detailBean.tankCapacity));//油箱容量


        //        mTagItems.addAll(StringUtils.getArrObjKeyName(detailBean.feature));
        mTagItems.clear();
        mTagItems.add(StringUtils.checkString(detailBean.vehicleFeatures));
        mTagAdapter.notifyDataChanged();
    }

    @Override
    protected void clear() {
    }
}
