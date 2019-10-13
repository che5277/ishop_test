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
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.CarDetailActivity;
import tableshop.ilinkedtech.com.ishop.CarListActivity;
import tableshop.ilinkedtech.com.utils.RxTextUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;
import tableshop.ilinkedtech.com.viewholders.CarSeriesItemHolder;

public class CarModelListAdapter
        extends BaseRecyclerAdapter<CarModelListItemBean> {
    private Activity                        mActivity;
//    private ArrayList<CarModelListItemBean> mArrayList;
    private ArrayList<CarModelListItemBean>        favoriteList;

    public CarModelListAdapter(ArrayList<CarModelListItemBean> mArrayList) {
        super(mArrayList);
    }

    public CarModelListAdapter(ArrayList<CarModelListItemBean> arrayList, Activity activity) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=activity;

    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View                    view                    =View.inflate(UIUtils.getContext(), R.layout.item_model_small, null);
        View                    view                    =View.inflate(UIUtils.getContext(), R.layout.item_series_view, null);
        CarSeriesItemHolder carDetailListItemHolder =new CarSeriesItemHolder(view);
        return carDetailListItemHolder;
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CarSeriesItemHolder    carDetailListItemHolder =(CarSeriesItemHolder)holder;
        final CarModelListItemBean carModelListItemBean    = mArrayList.get(position);
        if (carModelListItemBean ==null) {
            return;
        }
        final String               modelName               = carModelListItemBean.modelName;
        final String               seriesShareURL          = carModelListItemBean.seriesShareURL;
        String               carName          = StringUtils.checkString(carModelListItemBean.brandName)+" | "+StringUtils.checkString(carModelListItemBean.seriesName);

        if (!StringUtils.isEmpty(carModelListItemBean.seriesDefaultImageURL)) {
            ImageLoadUtils.into(carDetailListItemHolder.mIvIcon, carModelListItemBean.seriesDefaultImageURL);
        }

//        carDetailListItemHolder.mTvPrice.setText(StringUtils.getTenThousandStringFromDouble(carModelListItemBean.lowestPrice)+mActivity.getString(R.string.万起));
//        carDetailListItemHolder.mTvCarName.setText(carName);

        RxTextUtils.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                   .append(carName+"    ")
                   .append("("+carModelListItemBean.count+")").setForegroundColor(UIUtils.getColor(R.color.sidebar_text_normal)).setProportion(0.9f)
                   .into(carDetailListItemHolder.mTvCarName);

        RxTextUtils.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                   .append("¥ ")
                   .append(carModelListItemBean.minPrice+mActivity.getString(R.string.万))
                   .setForegroundColor(UIUtils.getColor(R.color.price_color)).setProportion(1.5f)
                   .append(mActivity.getString(R.string.起))
                   .into(carDetailListItemHolder.mTvPrice);

        carDetailListItemHolder.mLlContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCarListAct(carModelListItemBean);
            }
        });

    }

    private void goToCarListAct(CarModelListItemBean carModelListItemBean) {
        if (carModelListItemBean!=null&&mActivity!=null) {
            if (carModelListItemBean.count==1){
                //TODO 当只有一部车辆时直接跳到车辆详情
                Intent intent = new Intent(mActivity, CarDetailActivity.class);
                intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_ID, carModelListItemBean.uid);
                mActivity.startActivity(intent);
                UIUtils.activityAnimInt(mActivity);
            }else {
                ListRequestBean listRequestBean = new ListRequestBean();
                listRequestBean.brandId = carModelListItemBean.brandId;
                listRequestBean.seriesId = carModelListItemBean.seriesId;
                String dataJson = new Gson().toJson(listRequestBean);

                Intent intent = new Intent(mActivity, CarListActivity.class);
                intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, dataJson);
                mActivity.startActivity(intent);
                UIUtils.activityAnimInt(mActivity);
            }
        }
    }


}


