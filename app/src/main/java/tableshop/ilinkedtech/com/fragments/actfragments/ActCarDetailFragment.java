package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActCarDetailFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/20 9:47
 *  @描述：    TODO    车辆详情页的具体视图
 */

import android.os.Bundle;
import android.view.View;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;

public class ActCarDetailFragment
        extends IShopBaseFragment
        implements View.OnClickListener
{

    public static ActCarDetailFragment newInstance() {
        ActCarDetailFragment fragment = new ActCarDetailFragment();
        Bundle                  args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.car_detail_activity_new;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void refreshDatas() {

    }
}
