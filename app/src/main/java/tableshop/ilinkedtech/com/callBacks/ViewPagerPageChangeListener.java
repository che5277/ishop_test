package tableshop.ilinkedtech.com.callBacks;

import android.support.v4.view.ViewPager;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.callBacks
 *  @文件名:   ViewPagerPageChangeListener
 *  @创建者:   Wilson
 *  @创建时间:  2017/4/5 12:09
 *  @描述：    TODO
 */
public abstract class ViewPagerPageChangeListener
        implements ViewPager.OnPageChangeListener
{
    int stat=0;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        if (stat==0) {
//            doOnPageSeleted(position);
//        }
    }

    @Override
    public void onPageSelected(int position) {
        doOnPageSeleted(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        stat=state;
    }


    protected abstract void doOnPageSeleted(int position);

}
