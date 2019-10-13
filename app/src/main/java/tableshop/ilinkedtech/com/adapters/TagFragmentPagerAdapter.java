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
public class TagFragmentPagerAdapter
        extends FragmentStatePagerAdapter
{
    private static final String TAG = "TagFragmentPagerAdapter";
    private List<IShopBaseFragment> mViewList;
    private List<String>            mTitleList;
    FragmentManager fm;

    public TagFragmentPagerAdapter(FragmentManager fragmentManager, List<IShopBaseFragment> viewList, List<String> titleList) {
        super(fragmentManager);
        this.fm=fragmentManager;
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
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        //得到缓存的fragment
//        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//        //得到tag，这点很重要
//        String fragmentTag = fragment.getTag();
//
//
////        if (fragmentsUpdateFlag.get(position % fragmentsUpdateFlag.size())) {
//            //如果这个fragment需要更新
//
//            FragmentTransaction ft = fm.beginTransaction();
//            //移除旧的fragment
//            ft.remove(fragment);
//            //换成新的fragment
////            fragment = fragments.get(position % fragments.size());
//            //添加新fragment时必须用前面获得的tag，这点很重要
//            ft.add(container.getId(), fragment, fragmentTag);
//            ft.attach(fragment);
//            ft.commit();
//
////        }
//        return fragment;
//    }


}
