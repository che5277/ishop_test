package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   OrderListActivity
 *  @创建者:   ShoppingCarActivity
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO
 */

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.fragments.actfragments.ActShoppingCarFragment;

public class ShoppingCarActivity
        extends BaseActionBarActivity {
    @Override
    protected IShopBaseFragment getShowFragment() {
        mTvToolbarTitle.setText(getString(R.string.购物车));
        return new ActShoppingCarFragment();
    }

}
