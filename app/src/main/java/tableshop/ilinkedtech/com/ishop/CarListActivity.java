package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   CarListActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO 车辆列表
 */

import android.view.View;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.events.SeriesListFilterBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActCarListFragment;
import tableshop.ilinkedtech.com.utils.StringUtils;

public class CarListActivity
        extends BaseActionBarActivity {
    @Override
    protected IShopBaseFragment getShowFragment() {
        mTvToolbarTitle.setText(getString(R.string.车辆列表));
        mIvRight.setVisibility(View.GONE);

        String             json                 = getIntent().getStringExtra(KeyConst.BundleIntentKey.DATA_JSON);
        ActCarListFragment actCarListFragment = new ActCarListFragment();
        actCarListFragment.mIvRight=mIvRight;
        if (!StringUtils.isEmpty(json)){
            ListRequestBean listRequestBean = new Gson().fromJson(json,
                                                                    ListRequestBean.class);
            if (listRequestBean!=null){
                actCarListFragment.setListRequestBean(listRequestBean);
            }
        }

        return actCarListFragment;
    }

    @Override
    public void doRightImgAction() {
        mIvRight.setSelected(!mIvRight.isSelected());
        SeriesListFilterBean filterMessegeBean = SeriesListFilterBean.newInstance();
        filterMessegeBean.isShowFilterLayout=mIvRight.isSelected()?"1":"0";
        EventBus.getDefault().post(filterMessegeBean);
    }
}
