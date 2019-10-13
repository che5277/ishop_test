package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   ChooseCarActivity
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/30 16:44
 *  @描述：    TODO
 */

import android.content.Intent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.events.SeletedBrand;
import tableshop.ilinkedtech.com.beans.events.SeriesListFilterBean;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.main.ChooseCarFragment;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class ChooseCarActivity extends BaseActionBarActivity{
    @Override
    protected IShopBaseFragment getShowFragment() {
        ChooseCarFragment chooseCarFragment = new ChooseCarFragment();
        chooseCarFragment.fromType=ChooseCarFragment.FROM_ACT;
        defaultEventBus.register(this);
        return chooseCarFragment;
    }

    @Override
    public void onBackPressed() {
        CarDetailBean carDetailBean =new CarDetailBean();
        carDetailBean.brandName= UIUtils.getString(R.string.选择品牌);
        setActForResultData(carDetailBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSeletedBrand(SeletedBrand seletedBrand){
        if (seletedBrand!=null&&seletedBrand.carDetailBean!=null){
            setActForResultData(seletedBrand.carDetailBean);
        }
    }

    @Override
    public void doNavigationAction() {
        CarDetailBean carDetailBean =new CarDetailBean();
        carDetailBean.brandName= UIUtils.getString(R.string.选择品牌);
        setActForResultData(carDetailBean);
    }

    private void setActForResultData(CarDetailBean carDetailBean) {
        SeriesListFilterBean filterMessegeBean = SeriesListFilterBean.newInstance();
        filterMessegeBean.isShowFilterLayout="1";
        EventBus.getDefault().post(filterMessegeBean);

        Intent intent =new Intent();
        intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, carDetailBean);
        setResult(KeyConst.RequestCode.RESULT_CODE_SELETE_BRAND,intent);
        finish();
    }
}
