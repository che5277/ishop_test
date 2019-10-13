package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   CarModelPopularListAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.CarPopularListActivity;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.CarSeriesItemHolder;

public class CarModelPopularListAdapter
        extends BaseRecyclerAdapter<CarModelListItemBean> {
    private Activity                        mActivity;
    private ArrayList<CarModelListItemBean> mArrayList;
    private ArrayList<CarModelListItemBean>        favoriteList;

    public CarModelPopularListAdapter(ArrayList<CarModelListItemBean> mArrayList) {
        super(mArrayList);
    }

    public CarModelPopularListAdapter(ArrayList<CarModelListItemBean> arrayList, Activity activity) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=activity;

    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                    view                    =View.inflate(UIUtils.getContext(), R.layout.item_model_big_view, null);
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
        carDetailListItemHolder.refreshViewWithDatas(carModelListItemBean);

        carDetailListItemHolder.mLlContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPopularListAct(carModelListItemBean);
            }
        });

    }

    private void goToPopularListAct(CarModelListItemBean carModelListItemBean) {
        if (carModelListItemBean!=null&&mActivity!=null) {
            String dataJson = new Gson().toJson(carModelListItemBean);

            Intent intent = new Intent(mActivity, CarPopularListActivity.class);
            intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, dataJson);
            mActivity.startActivity(intent);
            UIUtils.activityAnimInt(mActivity);
        }
    }


}


