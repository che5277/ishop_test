package tableshop.ilinkedtech.com.factorys;

import java.util.HashMap;
import java.util.Map;

import tableshop.ilinkedtech.com.base.IShopBaseFragment;

/**
 * 类    名:  FragmentFactory
 * 创 建 者:  Wilson
 * 创建时间:  2017/3/17 14:03
 * 描    述： 创建Fragment的工厂
 */
public class FragmentFactory {
    public static final int                             FRAGMENT_HOME           = 0;//工作台
    public static final int                             FRAGMENT_CHOOSE_CAR     = 1;//任务
    public static final int                             FRAGMENT_ACCESSORY      = 2;//客户
    public static final int                             FRAGMENT_MY             = 3;//报表
    public static final int                             FRAGMENT_MORE           = 4;//个设
    public static final int                             FRAGMENT_SM_TASK_REPORT = 5;//SM 任务报表
    public static final int                             FRAGMENT_SM_LEAD_REPORT = 6;//SM 客户报表
    public static final int                             FRAGMENT_SBU_DASHBOARD  = 7;//SBU 工作台
    //定义集合保存Fragment
    public static       Map<Integer, IShopBaseFragment> mCacheFragments         = new HashMap<>();

    public static boolean isAdd(int position){
        return mCacheFragments.containsKey(position);
    }

    public static void clearFragments(){
        for (int i = 0; i < mCacheFragments.size(); i++) {
            IShopBaseFragment mysalesBaseFragment = mCacheFragments.get(i);
            if (mysalesBaseFragment!=null) {
                mCacheFragments.remove(mysalesBaseFragment);
            }
        }
        mCacheFragments.clear();
    }
    public static IShopBaseFragment createFragment(int position) {
        IShopBaseFragment fragment = null;
//        优先从集合中取出来
//        if (mCacheFragments.containsKey(position)) {
//            fragment = mCacheFragments.get(position);
//            return fragment;
//        }

        switch (position) {
            default:
                break;
        }
//        存储对应的Fragment到集合中
//        mCacheFragments.put(position, fragment);

        return fragment;
    }

}
