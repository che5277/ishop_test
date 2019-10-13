package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   ProductListAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.ProductDetailActivity;
import tableshop.ilinkedtech.com.servies.Utils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.ProductItemHolder;

public class ProductListAdapter
        extends BaseRecyclerAdapter<ProductItemBean>  {

    private Activity                        mActivity;
    private ArrayList<ProductItemBean>        favoriteList;
    ProductItemBean productItemBean;
    public ProductListAdapter(ArrayList<ProductItemBean> mArrayList) {
        super(mArrayList);
    }

    public ProductListAdapter(ArrayList<ProductItemBean> arrayList, Activity activity) {
        super(arrayList);
        mArrayList=arrayList;
        mActivity=activity;

    }
    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View              view                    =View.inflate(UIUtils.getContext(), R.layout.item_product_view, null);
        ProductItemHolder productItemHolder =new ProductItemHolder(view);
        return productItemHolder;
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
        ProductItemHolder    productItemHolder =(ProductItemHolder)holder;
          productItemBean    = mArrayList.get(position);
        if (productItemBean ==null) {
            return;
        }
        productItemHolder.refreshProductViewWithDatas(productItemBean);
        productItemHolder.mLlContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProductDetailAct(productItemBean);
            }
        });

    }

    private void goToProductDetailAct(ProductItemBean productItemBean) {
        Intent intent =new Intent(mActivity, ProductDetailActivity.class);
        intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON,productItemBean);
        intent.putExtra(KeyConst.BundleIntentKey.PRODUCT_ID, productItemBean.uid);
        mActivity.startActivity(intent);
        UIUtils.activityAnimInt(mActivity);
    }

}


