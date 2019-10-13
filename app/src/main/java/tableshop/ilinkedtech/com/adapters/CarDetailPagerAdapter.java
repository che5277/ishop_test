package tableshop.ilinkedtech.com.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import tableshop.ilinkedtech.com.base.IShopBaseFragment;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.adapters
 *  @文件名:   TagFragmentPagerAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/3/10 12:11
 *  @描述：    TODO    FragmentStatePagerAdapter数量少时使用
 */
public class CarDetailPagerAdapter
        extends FragmentStatePagerAdapter
{
    private static final String TAG = "TagFragmentPagerAdapter";
    private List<IShopBaseFragment> mViewList;
    private List<String>            mTitleList;


    public CarDetailPagerAdapter(FragmentManager fragmentManager, List<IShopBaseFragment> viewList, List<String> titleList) {
        super(fragmentManager);
        this.mViewList = viewList;
        this.mTitleList = titleList;
    }


        @Override
    public int getCount() {
        return mViewList!=null?mViewList.size():0;//页卡数
    }


    @Override
    public IShopBaseFragment getItem(int position) {
        IShopBaseFragment fragment = mViewList.get(position);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList!=null&&mTitleList.size()>0&&position<mTitleList.size()) {
            return mTitleList.get(position);//页卡标题
        }else {
            return null;
        }
    }


}
