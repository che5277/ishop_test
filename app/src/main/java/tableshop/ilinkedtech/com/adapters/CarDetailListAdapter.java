package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   CarDetailListAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.CarDetailActivity;
import tableshop.ilinkedtech.com.utils.RxTextUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;
import tableshop.ilinkedtech.com.viewholders.CarListItemHolder;

public class CarDetailListAdapter
        extends BaseRecyclerAdapter<CarDetailBean> {
    private Activity                        mActivity;
//    private ArrayList<CarDetailBean> mArrayList;
    public ArrayList<CarDetailBean>        favoriteList;
    boolean isSmallView;

    public int viewFromType =0;

    public static final int CAR_DETAIL_NORMAL_VIEW=0;
    public static final int CAR_DETAIL_SEARCH_VIEW=1;//水平滑动的recycleview 需要指定宽度
    public static final int CAR_DETAIL_POPULAR_VIEW=2;//编辑精选的view

    public CarDetailListAdapter(ArrayList<CarDetailBean> mArrayList) {
        super(mArrayList);
    }

    public CarDetailListAdapter(ArrayList<CarDetailBean> arrayList, Activity activity,boolean isSmallView) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=activity;
        this.isSmallView=isSmallView;
    }
    public CarDetailListAdapter(ArrayList<CarDetailBean> arrayList, Activity activity,int viewFromType) {
        super(arrayList);
        this.viewFromType = viewFromType;
        mArrayList=arrayList;
        mActivity=activity;
        this.isSmallView=(viewFromType ==0);
    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                    view                    =null;
        switch (viewFromType){
            case CAR_DETAIL_NORMAL_VIEW:
                view                    =View.inflate(UIUtils.getContext(), R.layout.item_model_big_view, null);
                break;
            case CAR_DETAIL_SEARCH_VIEW:
                view                    =View.inflate(UIUtils.getContext(), R.layout.item_model_view, null);
                break;
            case CAR_DETAIL_POPULAR_VIEW:
                view                    =View.inflate(UIUtils.getContext(), R.layout.item_model_popular_view, null);
                break;
            default:
                view                    =View.inflate(UIUtils.getContext(), R.layout.item_model_big_view, null);
                break;
        }
            //TODO 替换
        CarListItemHolder carDetailListItemHolder =new CarListItemHolder(view);

        return carDetailListItemHolder;
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CarListItemHolder   carDetailListItemHolder =(CarListItemHolder)holder;
        final CarDetailBean carDetailBean           = mArrayList.get(position);
        if (carDetailBean ==null) {
            return;
        }
        final String            modelName               = carDetailBean.modelName;
        final String            seriesShareURL          = carDetailBean.seriesDefaultImageURL;
        final String carName =StringUtils.checkString(carDetailBean.brandName)+" | "+StringUtils.checkString(carDetailBean.seriesName);
        carDetailListItemHolder.mTvCarName.setText(carName);
        carDetailListItemHolder.mTvCarModelName.setText(StringUtils.checkString(modelName));

        RxTextUtils.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                   .append("¥ ")
                   .append(StringUtils.getTenThousandStringFromDouble(carDetailBean.price)+mActivity.getString(R.string.万))
//                   .append(carDetailBean.price+mActivity.getString(R.string.万))
                   .setForegroundColor(UIUtils.getColor(R.color.price_color)).setProportion(1.3f)
                   .into(carDetailListItemHolder.mTvPrice);
        if (!StringUtils.isEmpty(seriesShareURL)) {
            ImageLoadUtils.into(carDetailListItemHolder.mIvIcon,seriesShareURL);
        }
        carDetailListItemHolder.mTvCarBodyColor.setText("外 ："+StringUtils.checkString(carDetailBean.bodyColorName));
        carDetailListItemHolder.mTvCarInteriorColor.setText("内 ："+StringUtils.checkString(carDetailBean.interiorColorName));
        if (carDetailListItemHolder.mIvBodyColor!=null&&carDetailListItemHolder.mIvInteriorColor!=null) {
            ImageLoadUtils.into(carDetailListItemHolder.mIvBodyColor, carDetailBean.bodyColorUrl);
            ImageLoadUtils.into(carDetailListItemHolder.mIvInteriorColor, carDetailBean.interiorColorUrl);
        }

        switch (viewFromType){
            case CAR_DETAIL_NORMAL_VIEW:
                //TODO 标注车辆销售状态
                if (!StringUtils.isEmpty(carDetailBean.flag)) {
                    refreshSalesState(carDetailListItemHolder, carDetailBean);
                }
                break;
            case CAR_DETAIL_SEARCH_VIEW:
                break;
            case CAR_DETAIL_POPULAR_VIEW:
                if (carDetailListItemHolder.mTvPosition!=null){
                    carDetailListItemHolder.mTvPosition.setText((position+1)+"");
                    if (position<3){
                        carDetailListItemHolder.mTvPosition.setTextColor(UIUtils.getColor(R.color.price_color));
                    }else {
                        carDetailListItemHolder.mTvPosition.setTextColor(UIUtils.getColor(R.color.tip));
                    }
                }

                break;
            default:
                break;
        }



        carDetailListItemHolder.mContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIUtils.getContext(), CarDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON,new Gson().toJson(
                        carDetailBean));
                intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_ID,carDetailBean.uid);
                intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_FLAG,carDetailBean.flag);
                mActivity.startActivity(intent);
                UIUtils.activityAnimInt(mActivity);
            }
        });


    }

    public int recyleWidth=0;


    private void refreshSalesState(CarListItemHolder carDetailListItemHolder,
                                   CarDetailBean carDetailBean)
    {
        switch (Integer.parseInt(carDetailBean.flag)){
            case KeyConst.VehicleSellableState.ON_SALES_VEHICLE:
                carDetailListItemHolder.mIvVehicleState.setVisibility(View.GONE);
                break;
            case KeyConst.VehicleSellableState.DELIVERY_VEHICLE:
                carDetailListItemHolder.mIvVehicleState.setVisibility(View.VISIBLE);
                carDetailListItemHolder.mIvVehicleState.setImageResource(R.drawable.sold_out_simp);
                break;
            case KeyConst.VehicleSellableState.PURCHASE_VEHICLE:
                carDetailListItemHolder.mIvVehicleState.setVisibility(View.VISIBLE);
                carDetailListItemHolder.mIvVehicleState.setImageResource(R.drawable.reserved_simp);
                break;
                default:
                    carDetailListItemHolder.mIvVehicleState.setVisibility(View.VISIBLE);
                    carDetailListItemHolder.mIvVehicleState.setImageResource(R.drawable.reserved_simp);
                    break;
        }
    }


}


