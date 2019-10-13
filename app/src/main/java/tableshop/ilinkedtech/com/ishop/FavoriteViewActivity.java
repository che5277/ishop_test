package tableshop.ilinkedtech.com.ishop;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.ishop
 *  @文件名:   FavoriteViewActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 10:50
 *  @描述：    TODO 收藏车辆列表
 */

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.fragments.actfragments.ActFavoriteFragment;

public class FavoriteViewActivity
        extends BaseActionBarActivity {
    public static ArrayList<CarModelListItemBean> favoriteList =new ArrayList<>();
    @Override
    protected void onResume() {
        super.onResume();
        mTvToolbarTitle.setText(getString(R.string.我的收藏));
    }

    @Override
    protected IShopBaseFragment getShowFragment() {
        return ActFavoriteFragment.newInstance();
    }

}
