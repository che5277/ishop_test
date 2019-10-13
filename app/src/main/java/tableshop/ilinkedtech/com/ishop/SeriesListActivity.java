package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   SeriesListActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO
 */

import android.content.Intent;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.events.FilterItemTypeBean;
import tableshop.ilinkedtech.com.beans.events.SeriesListFilterBean;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActSerieListFragment;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class SeriesListActivity
        extends BaseActionBarActivity {

    private ActSerieListFragment mActSerieListFragment;


    @Override
    protected IShopBaseFragment getShowFragment() {
        mTvToolbarTitle.setText(getString(R.string.车系列表));
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(UIUtils.getString(R.string.筛选));
        mActSerieListFragment = new ActSerieListFragment();
        mActSerieListFragment.mFilterView =mTvRight;
        ListRequestBean listRequestBean = getIntent().getParcelableExtra(KeyConst.BundleIntentKey.DATA_JSON);
        boolean         showFilterView    = getIntent().getBooleanExtra(KeyConst.BundleIntentKey.SHOW_FILTER_VIEW,
                                                                      false);
        FilterItemTypeBean filterItemTypeBean= getIntent().getParcelableExtra(KeyConst.BundleIntentKey.POSITION);
        if (listRequestBean!=null){
            mActSerieListFragment.fromHomeSearchPositionBean =filterItemTypeBean;
            mActSerieListFragment.setListRequestBean(listRequestBean, showFilterView);
        }

        return mActSerieListFragment;
    }


    @Override
    public void doNavigationAction() {
        finish();
        UIUtils.activityBackToMain(this);
    }

    @Override
    public void doRightTextViewAction() {
        mTvRight.setSelected(!mTvRight.isSelected());
        SeriesListFilterBean filterMessegeBean = SeriesListFilterBean.newInstance();
        filterMessegeBean.isShowFilterLayout=mTvRight.isSelected()?"1":"0";
        EventBus.getDefault().post(filterMessegeBean);//-->ActSerieListFragment
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null&&data.getExtras()!=null&&requestCode==KeyConst.RequestCode.RESULT_CODE_SELETE_BRAND){
            CarDetailBean carDetailBean = data.getExtras()
                                              .getParcelable(KeyConst.BundleIntentKey.DATA_JSON);
            if (mActSerieListFragment!=null&&carDetailBean!=null) {
                mActSerieListFragment.mListRequestBean.brandId = carDetailBean.brandId;
                mActSerieListFragment.mBtnSeleteBrand.setText(StringUtils.checkString(carDetailBean.brandName));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
