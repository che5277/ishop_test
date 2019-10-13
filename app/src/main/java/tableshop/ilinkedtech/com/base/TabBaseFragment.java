package tableshop.ilinkedtech.com.base;

import android.support.annotation.ColorRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.TagFragmentPagerAdapter;
import tableshop.ilinkedtech.com.callBacks.ViewPagerPageChangeListener;
import tableshop.ilinkedtech.com.utils.UIUtils;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.base
 *  @文件名:   TabBaseFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/3/15 16:37
 *  @描述：    TODO
 */
public abstract class TabBaseFragment
        extends IShopBaseFragment
{
    private static final String TAG = "TabBaseFragment";
    @BindView(R.id.tl_tabs)
    TabLayout   mTabLayout;
    @BindView(R.id.fl_tab_view_left_contentview)
    FrameLayout mFlTabViewLeftContentview;
    @BindView(R.id.vp_view)
    public ViewPager   mViewPager;
    @BindView(R.id.iv_add)
    public ImageView   mIvAdd;


    public List<String>            mTitleList = new ArrayList<>();//页卡标题集合
    public List<IShopBaseFragment> mViewList  = new ArrayList<>();//页卡视图集合
    public  int                         mPosition;
    public  TagFragmentPagerAdapter     mAdapter;
    private ViewPagerPageChangeListener mListener;

    @Override
    protected int getLayoutId() {
        return R.layout.tab_base_fragment;
    }

    public TabBaseFragment() {
        super();
    }

    @Override
    protected void initView() {

    }


    @Override
    public void initEvent() {
        mListener = new ViewPagerPageChangeListener() {
            @Override
            protected void doOnPageSeleted(int position) {
                doOnViewPageSeleted(position);
            }
        };

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置可滑动
        mIvAdd.setVisibility(View.GONE);
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAddIConAction();
            }
        });
    }

    public void doAddIConAction(){
    }


    public boolean isCalled=false;
    /**
     * viewpager选中不同页面时会调用，包含了滑动选择
     * @param position
     */
    public void doOnViewPageSeleted(int position) {
        if (!isFragmentApiCall&&!isCalled) {
//            isCalled=true;
            isFragmentApiCall=true;
//            mAdapter.getItem(position)
//                    .refreshDatas();//TODO 2 3 4 5
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        isCalled=false;
    }

    @Override
    public void refreshDatas() {
        setTabIndicatorColor(R.color.tab_indicator_color);
        setTabTitleColor(R.color.item_normal, R.color.tab_text_nomal_color);
        if (mViewPager != null) {
            mViewPager.removeAllViews();
        }
        mAdapter = new TagFragmentPagerAdapter(getChildFragmentManager(), mViewList, mTitleList);

        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器

        mViewPager.addOnPageChangeListener(mListener);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        if (mViewList != null && mViewList.size() > 0) {
            mViewList.clear();
            mTitleList.clear();
            mAdapter.notifyDataSetChanged();
        }

        addDatas(mViewList, mTitleList);
        mAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(mPosition<mTitleList.size()?mPosition:0);
    }

    /**
     * 添加viewpager的view 和 标题
     * @param viewList  添加viewpager的view
     * @param titleList 添加viewpager的标题
     */
    public abstract void addDatas(List<IShopBaseFragment> viewList, List<String> titleList);


    @Override
    public void onDestroy() {
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(mListener);
        }
        if (mViewList != null) {
            mViewList.clear();
        }

        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    /**
     * 设置条目的字体颜色
     * @param normalColor
     * @param selectedColor
     */
    public void setTabTitleColor(@ColorRes int normalColor, @ColorRes int selectedColor) {
        mTabLayout.setTabTextColors(UIUtils.getColor(normalColor), UIUtils.getColor(selectedColor));
    }


    /**
     * 设置条目的分割线颜色
     * @param colorId
     */
    public void setTabIndicatorColor(@ColorRes int colorId) {
        mTabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(colorId));
    }


}
