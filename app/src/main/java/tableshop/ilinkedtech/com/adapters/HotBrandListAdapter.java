package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   HotBrandListAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.events.SeletedBrand;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.main.ChooseCarFragment;
import tableshop.ilinkedtech.com.ishop.SeriesListActivity;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.HotBrandListItemHolder;

public class HotBrandListAdapter
        extends BaseRecyclerAdapter<CarDetailBean> {
    private Activity                 mActivity;
    public  ArrayList<CarDetailBean> favoriteList;

    public int fromType;

    public HotBrandListAdapter(ArrayList<CarDetailBean> mArrayList) {
        super(mArrayList);
    }

    public HotBrandListAdapter(Activity activity,ArrayList<CarDetailBean> arrayList) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=activity;
    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view                    =View.inflate(UIUtils.getContext(), R.layout.item_hot_brand, null);
        HotBrandListItemHolder hotBrandListItemHolder =new HotBrandListItemHolder(view);

        return hotBrandListItemHolder;
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HotBrandListItemHolder hotBrandListItemHolder =(HotBrandListItemHolder)holder;
        final CarDetailBean     carDetailBean    = mArrayList.get(position);
        if (carDetailBean ==null||hotBrandListItemHolder==null) {
            return;
        }
        hotBrandListItemHolder.mTvBrandName.setText(StringUtils.checkString(carDetailBean.brandName));
        hotBrandListItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromType== ChooseCarFragment.FROM_ACT){
                    EventBus.getDefault().post(SeletedBrand.newInstance(carDetailBean));
                }else {
                    goToSerieseListAct(carDetailBean);
                }
            }
        });
        Glide.with(UIUtils.getContext()).load(carDetailBean.logoImageURL).error(R.drawable.default_logo).fitCenter().into(hotBrandListItemHolder.mIvIcon);


    }

    private void goToSerieseListAct(CarDetailBean carDetailBean) {
        if (carDetailBean!=null) {
            ListRequestBean listRequestBean = new ListRequestBean();
            listRequestBean.brandId = carDetailBean.brandId;
            listRequestBean.brandName=carDetailBean.brandName;
//            String dataJson = new Gson().toJson(listRequestBean);

            Intent intent = new Intent(mActivity, SeriesListActivity.class);
            intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, listRequestBean);
            mActivity.startActivity(intent);
            UIUtils.activityAnimInt(mActivity);
        }
    }


}


