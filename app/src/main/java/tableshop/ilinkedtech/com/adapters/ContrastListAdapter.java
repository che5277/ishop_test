package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   ContrastListAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/14 15:59
 *  @描述：    TODO 可对比车辆列表页面
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.gson.Gson;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseActivity;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.DBJsonHelper;
import tableshop.ilinkedtech.com.ishop.CarDetailActivity;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;
import tableshop.ilinkedtech.com.viewholders.ContrastListItemHolder;

public class ContrastListAdapter
        extends BaseRecyclerAdapter<CarDetailBean> {
    private IShopBaseActivity        mActivity;
//    private ArrayList<CarDetailBean> mArrayList;

    public ContrastListAdapter(ArrayList<CarDetailBean> mArrayList) {
        super(mArrayList);
    }

    public ContrastListAdapter(ArrayList<CarDetailBean> arrayList, Activity activity) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=(IShopBaseActivity)activity;

    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                    view                    =View.inflate(UIUtils.getContext(), R.layout.item_contrast_list, null);
        ContrastListItemHolder carDetailListItemHolder =new ContrastListItemHolder(view);

        return carDetailListItemHolder;
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ContrastListItemHolder carDetailListItemHolder =(ContrastListItemHolder)holder;
        final CarDetailBean     carModelListItemBean    = mArrayList.get(position);
        final String            seriesShareURL          = carModelListItemBean.seriesDefaultImageURL;

        if (carModelListItemBean !=null) {
            String name=StringUtils.checkString(carModelListItemBean.brandName)+StringUtils.checkString(carModelListItemBean.seriesName)+carModelListItemBean.modelName;
            carDetailListItemHolder.mTvCarName.setText(name);

            if (!StringUtils.isEmpty(seriesShareURL)) {
                ImageLoadUtils.into(carDetailListItemHolder.mIvIcon,seriesShareURL);
            }
            if (!StringUtils.isEmpty(carModelListItemBean.seriesBackgroundURL)) {
                ImageLoadUtils.into(carDetailListItemHolder.mIvBg,carModelListItemBean.seriesBackgroundURL);
            }

        }
        carModelListItemBean.isContrast=false;
        carDetailListItemHolder.mViewSeleted.setChecked(carModelListItemBean.isContrast);
        carDetailListItemHolder.mViewSeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carModelListItemBean.isContrast=!carModelListItemBean.isContrast;
                ((CheckBox)view).setChecked(carModelListItemBean.isContrast);
                carModelListItemBean.isSeleted=carModelListItemBean.isContrast;

            }
        });
        carDetailListItemHolder.mRlShowCarDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 点击了跳入车辆详情
                Intent intent = new Intent(UIUtils.getContext(), CarDetailActivity.class);
                intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON,new Gson().toJson(
                        carModelListItemBean));
                intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_ID,carModelListItemBean.uid);
                intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_FLAG,carModelListItemBean.flag);
                mActivity.startActivity(intent);
            }
        });
        carDetailListItemHolder.mRlShowCarDetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mActivity.showSnackbar(mActivity.mView,"是否从对比列表中移除该车辆？","确定",new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        DBJsonHelper.upDateContrastList(carModelListItemBean, KeyConst.LocalData.ORIENTATION_DEL);
                        mArrayList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                return true;
            }
        });

    }


}


