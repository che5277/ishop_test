package tableshop.ilinkedtech.com.ishop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseActivity;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.loader.GlidePictureViewLoader;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.MagicImageView;

/*
 *  @项目名：  trunk 
 *  @包名：    com.ilinkedtech.ican
 *  @文件名:   PictureViewActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/4/27 10:14
 *  @描述：    TODO    照片预览视图
 */
@SuppressLint("ValidFragment")
public class PictureViewActivity
        extends IShopBaseActivity
        implements ViewPager.OnPageChangeListener
{
    private static final String TAG = "PictureViewActivity";
    @BindView(R.id.banner_view)
    Banner mBannerView;
    public List<String> mUrls;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tv_tip)
    TextView  mTvTip;
    private int        mPosition;
    private List<View> imageViews;
    private int        count;
    private int        currentItem;


    @Override
    public int getLayoutViewId() {
        return R.layout.act_picture_view;
    }


    public static void startPictureViewAct(Activity activity, ArrayList<String> url, int position) {
        if (activity != null) {
            Intent intent = new Intent(activity, PictureViewActivity.class);
            intent.putStringArrayListExtra(KeyConst.BundleIntentKey.AVATARLOC, url);
            intent.putExtra(KeyConst.BundleIntentKey.POSITION, position);
            activity.startActivity(intent);
            UIUtils.activityAnimInt(activity);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getWindow().setStatusBarColor(UIUtils.getColor(R.color.car_detail_color));
        } catch (Exception e) {

        }

        mBannerView.setImageLoader(new GlidePictureViewLoader());
        mBannerView.setVisibility(View.GONE);
        //        mBannerView.setImageLoader(new GlideImageLoader());
        mBannerView.setBannerStyle(BannerConfig.NUM_INDICATOR);//显示数字指示器
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.get(KeyConst.BundleIntentKey.AVATARLOC) != null) {
                mUrls = bundle.getStringArrayList(KeyConst.BundleIntentKey.AVATARLOC);
                mBannerView.setImages(mUrls);
                mBannerView.isAutoPlay(false);
                mBannerView.start();
            }

            mPosition =bundle.getInt(KeyConst.BundleIntentKey.POSITION);
        }


        initViewPagerEvent();
    }

    private void initViewPagerEvent() {
        initPagerViews();
        PicturePagerAdapter pagerAdapter =new PicturePagerAdapter();
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(mPosition%count);
    }

    private void initPagerViews() {
        if (mUrls==null){
            mUrls=new ArrayList<>();
        }
        count=mUrls.size();
        mTvTip.setText((mPosition +1) + "/" + count);
        View view =null;
        String imageUrl=null;
        imageViews=new ArrayList<>();
        for (int i = 0; i < mUrls.size(); i++) {
            imageUrl=mUrls.get(i);
            view =View.inflate(UIUtils.getContext(), R.layout.picture_view, null);
            MagicImageView imageView =view.findViewById(R.id.magic_view);
            Glide.with(this).load(imageUrl).into(imageView);
            imageViews.add(view);
        }
    }

    private float downX;
    private float downY;
    public  float moveY;
    public  float moveX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                downX = motionEvent.getX();
                downY = motionEvent.getY();
            case MotionEvent.ACTION_MOVE://移动
                break;
            case MotionEvent.ACTION_UP://抬起
                moveX = motionEvent.getX();
                moveY = motionEvent.getY();
                int moveXDistand = (int) (moveX - downX);
                int moveYDistand = (int) (moveY - downY);
                if ((Math.abs(moveXDistand) + (Const.windowHeight / 20)) < Math.abs(moveYDistand)) {
                    goBack();
                }
                break;
            case MotionEvent.ACTION_CANCEL://取消
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }
    @OnClick(R.id.iv_back)
    public void goBack(){
        PictureViewActivity.this.finish();
        UIUtils.activityBackToMain(PictureViewActivity.this);
    }
    @Override
    public void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTvTip.setText((position+1) + "/" + count);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        currentItem = mViewPager.getCurrentItem();
//        switch (state) {
//            case 0://No operation
//                if (currentItem == 0) {
//                    mViewPager.setCurrentItem(count, false);
//                } else if (currentItem == count + 1) {
//                    mViewPager.setCurrentItem(1, false);
//                }
//                break;
//            case 1://start Sliding
//                if (currentItem == count + 1) {
//                    mViewPager.setCurrentItem(1, false);
//                } else if (currentItem == 0) {
//                    mViewPager.setCurrentItem(count, false);
//                }
//                break;
//            case 2://end Sliding
//                break;
//        }
    }


    class PicturePagerAdapter
            extends PagerAdapter
    {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    /**
     * 返回真实的位置
     *
     * @param position
     * @return 下标从0开始
     */
    public int toRealPosition(int position) {
        int realPosition = (position - 1) % count;
        if (realPosition < 0) { realPosition += count; }
        return realPosition;
    }


}
