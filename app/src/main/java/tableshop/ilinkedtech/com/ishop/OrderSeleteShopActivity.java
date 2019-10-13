package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   OrderSeleteShopActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO 选店
 */

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActOrderSeleteShopFragment;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class OrderSeleteShopActivity
        extends BaseActionBarActivity {

    boolean isApiCall=false;
    @Override
    protected IShopBaseFragment getShowFragment() {
        mTvToolbarTitle.setText(UIUtils.getString(R.string.选择店面));
        OrderListItemBean serializableExtra = getIntent().getParcelableExtra(KeyConst.BundleIntentKey.DATA_JSON);
        return new ActOrderSeleteShopFragment(serializableExtra);
    }

    @Override
    public void doNavigationAction() {
        if (!isApiCall)
        super.doNavigationAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
