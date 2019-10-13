package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   ProductDetailActivity
 *  @创建者:   Wilson
 *  @创建时间:  2018/2/5 11:38
 *  @描述：    TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActProductDetailFragment;

public class ProductDetailActivity extends BaseActionBarActivity{

    private ActProductDetailFragment mDetailFragment;

    @Override
    protected IShopBaseFragment getShowFragment() {
        ProductItemBean productItemBean = getIntent().getParcelableExtra(KeyConst.BundleIntentKey.DATA_JSON);
        String     mProductUid      = getIntent().getStringExtra(KeyConst.BundleIntentKey.PRODUCT_ID);
        mDetailFragment = new ActProductDetailFragment(
                productItemBean,
                mProductUid);
        return mDetailFragment;
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.drawable.share);
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void doRightImgAction() {
        if (mDetailFragment != null) {
            mDetailFragment.shareProduct();
        }
    }




}
