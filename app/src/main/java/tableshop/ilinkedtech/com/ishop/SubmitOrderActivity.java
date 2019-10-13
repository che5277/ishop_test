package tableshop.ilinkedtech.com.ishop;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.ishop
 *  @文件名:   SubmitOrderActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/28 11:32
 *  @描述：    提交订单页
 */

import com.google.gson.Gson;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActSubmitOrderFragment;
import tableshop.ilinkedtech.com.utils.StringUtils;


public class SubmitOrderActivity
        extends BaseActionBarActivity
{
    private ActSubmitOrderFragment mActPayDetailFragment;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initView() {
        super.initView();
        String stringExtra = getIntent().getStringExtra(KeyConst.BundleIntentKey.DATA_JSON);
        if (!StringUtils.isEmpty(stringExtra)) {
            try {
                CarDetailBean carDetailBeen = new Gson().fromJson(stringExtra,CarDetailBean.class);
                if (carDetailBeen != null ) {
                    mActPayDetailFragment.setData(carDetailBeen);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mTvToolbarTitle.setText(getString(R.string.提交订单));
    }

    @Override
    protected IShopBaseFragment getShowFragment() {
        mActPayDetailFragment = ActSubmitOrderFragment.newInstance();
        return mActPayDetailFragment;
    }

}
