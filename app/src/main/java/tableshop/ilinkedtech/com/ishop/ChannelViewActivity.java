package tableshop.ilinkedtech.com.ishop;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.ishop
 *  @文件名:   ChannelViewActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 10:50
 *  @描述：    TODO
 */

import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.fragments.ChannelFragment;

public class ChannelViewActivity
        extends BaseActionBarActivity {
    @Override
    protected IShopBaseFragment getShowFragment() {
        return ChannelFragment.newInstance();
    }

}
