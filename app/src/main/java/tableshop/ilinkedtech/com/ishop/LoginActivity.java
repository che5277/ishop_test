package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   RegisterActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO
 */

import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.fragments.actfragments.ActLoginFragment;

public class LoginActivity
        extends BaseActionBarActivity {

    boolean isApiCall=false;
    @Override
    protected IShopBaseFragment getShowFragment() {
        return new ActLoginFragment();
    }

    @Override
    public void doNavigationAction() {
        if (!isApiCall)
        super.doNavigationAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        isApiCall=true;
//        VersionCheckHelper.checkNewVersionOnLogin(this, comfirmListenr,cancleListenr);
    }

}
