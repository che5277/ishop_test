package tableshop.ilinkedtech.com.ishop;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.fragments.webs.AgreementLoginFragment;
import tableshop.ilinkedtech.com.utils.UIUtils;


/*
 *  @文件名:   AgreementDetailAct
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/23 15:15
 *  @描述：    TODO
 */

public class AboutUsAct
        extends BaseActionBarActivity
{
    @Override
    protected IShopBaseFragment getShowFragment() {
        return new AgreementLoginFragment();
    }

    @Override
    public void initView() {
        super.initView();
        mTvToolbarTitle.setText(UIUtils.getString(R.string.关于领骏达));
    }
}
