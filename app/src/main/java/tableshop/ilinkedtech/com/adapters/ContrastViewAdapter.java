package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   ContrastViewAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/14 15:59
 *  @描述：    TODO 具体的车辆对比面
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.viewholders.ContrastViewItemHolder;

public class ContrastViewAdapter
        extends BaseRecyclerAdapter<CarDetailBean> {
    private Activity                        mActivity;
//    private ArrayList<CarDetailBean> mArrayList;

    public ContrastViewAdapter(ArrayList<CarDetailBean> mArrayList) {
        super(mArrayList);
    }

    public ContrastViewAdapter(ArrayList<CarDetailBean> arrayList, Activity activity) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=activity;

    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contrast_detail_view, parent, false);
        view.getLayoutParams().width=parent.getWidth()/2;
        ContrastViewItemHolder carDetailListItemHolder =new ContrastViewItemHolder(view);

        return carDetailListItemHolder;
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ContrastViewItemHolder carDetailListItemHolder =(ContrastViewItemHolder)holder;
        CarDetailBean          detailBean              = mArrayList.get(position);
        if (detailBean!=null) {
            carDetailListItemHolder.refreshView(detailBean);
//            ImageLoadUtils.setImageSource(mActivity, detailBean.seriesDefaultImageURL, carDetailListItemHolder.mIvCar1Icon, null);
//            carDetailListItemHolder.mTvSeriesName.setText(StringUtils.checkString(detailBean.seriesName));
//            carDetailListItemHolder.mTvModelName.setText(StringUtils.checkString(detailBean.modelName));
////            String carSize=detailBean.bodyLength+"mm*"+detailBean.bodyWidth+"mm*"+detailBean.bodyHeight+"mm";
//            String carSize=StringUtils.checkString(detailBean.lengthWidthHeight);
//            carDetailListItemHolder.mTvCarSize.setText(carSize);
//            carDetailListItemHolder.mTvCarNoOfSeat.setText(detailBean.noOfSeat+"");
//            carDetailListItemHolder.mTvCarNoOfDoor.setText(detailBean.noOfDoors+"");
//
//            carDetailListItemHolder.mTvFuseSource.setText(StringUtils.checkString(detailBean.fuseSource + ""));//燃油
//            carDetailListItemHolder.mGearBox.setText(StringUtils.checkString(detailBean.gearBox + ""));//变速
//            carDetailListItemHolder.mTvHorsePower.setText(StringUtils.checkString(detailBean.horsePower + ""));//马力
//            carDetailListItemHolder.mTvEngineType.setText(StringUtils.checkString(detailBean.engineType + ""));//发动机
//            carDetailListItemHolder.mEngineLayout.setText(StringUtils.checkString(detailBean.engineLayout + ""));//驱动
//
////            carDetailListItemHolder.mTagItems.addAll(StringUtils.getArrObjKeyName(detailBean.feature));
//            carDetailListItemHolder.mTagItems.add(StringUtils.checkString(detailBean.vehicleFeatures));
//            carDetailListItemHolder.mTagAdapter.notifyDataChanged();



        }

    }



}


