package tableshop.ilinkedtech.com.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by TO on 2018/4/28.
 */

public class ProductDetailPagerAdpter extends FragmentPagerAdapter {

    List<Fragment> mList;
    FragmentManager manager;
    public ProductDetailPagerAdpter(FragmentManager fm) {
        super(fm);
    }

    public ProductDetailPagerAdpter(FragmentManager fm,List<Fragment> list){
        super(fm);
        this.manager=fm;
        this.mList=list;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }
}
