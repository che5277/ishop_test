package tableshop.ilinkedtech.com.fragments.main;

/*
 *  @文件名:   AccessoryFragment
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/22 10:55
 *  @描述：    TODO 商品
 */

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.base.TabBaseFragment;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.fragments.accessory.AccessoryAllListFragment;
import tableshop.ilinkedtech.com.fragments.accessory.AccessoryListFragment;
import tableshop.ilinkedtech.com.fragments.accessory.AccessoryOtherListFragment;
import tableshop.ilinkedtech.com.fragments.accessory.ModelListFragment;

public class AccessoryFragment
        extends TabBaseFragment{


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void addDatas(List<IShopBaseFragment> viewList, List<String> titleList) {

        viewList.add(new AccessoryAllListFragment());
        viewList.add(new AccessoryListFragment());
        viewList.add(new ModelListFragment());
        viewList.add(new AccessoryOtherListFragment());

        titleList.add("全部");
        titleList.add("配件");
        titleList.add("模型");
        titleList.add("其他");

        mAdapter.notifyDataSetChanged();
        mViewPager.setOffscreenPageLimit(3);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
