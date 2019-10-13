package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   OrderListActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO
 */

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.fragments.webs.OrderListFragment;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class OrderListActivity
        extends BaseActionBarActivity {

    private OrderListFragment mOrderListFragment;

    @Override
    protected IShopBaseFragment getShowFragment() {
        mTvToolbarTitle.setText(UIUtils.getString(R.string.我的订单));
//        return new ActOrderListFragment();
        if (mOrderListFragment==null) {
            mOrderListFragment = new OrderListFragment();
        }
        return mOrderListFragment;
    }

    @Override
    public void onBackPressed() {
        if (mOrderListFragment!=null){
            if (mOrderListFragment.onBackPressed()){
                super.onBackPressed();
            }
        }else {
            super.onBackPressed();
        }

    }
}
