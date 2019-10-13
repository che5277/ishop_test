package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   CarListActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO 编辑之选的车辆列表
 */

import android.view.View;

import com.google.gson.Gson;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActPopularCarListFragment;
import tableshop.ilinkedtech.com.utils.StringUtils;

public class CarPopularListActivity
        extends BaseActionBarActivity {
    @Override
    protected IShopBaseFragment getShowFragment() {
        mTvToolbarTitle.setText(getString(R.string.编辑之选));
        mIvRight.setVisibility(View.GONE);

        String                    json               = getIntent().getStringExtra(KeyConst.BundleIntentKey.DATA_JSON);
        ActPopularCarListFragment actCarListFragment = new ActPopularCarListFragment();
        if (!StringUtils.isEmpty(json)){
            CarModelListItemBean carModelListItemBean = new Gson().fromJson(json,
                                                                  CarModelListItemBean.class);
            if (carModelListItemBean!=null){
                mTvToolbarTitle.setText(StringUtils.checkString(carModelListItemBean.title));
            }
            if (carModelListItemBean!=null){
                actCarListFragment.setCarModelListItemBean(carModelListItemBean);
            }
        }

        return actCarListFragment;
    }

}
