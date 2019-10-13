package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   ProductListAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.beans.reques.ObjRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.ObjResponsBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActShoppingCarFragment;
import tableshop.ilinkedtech.com.ishop.ProductDetailActivity;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.ProductItemHolder;
import tableshop.ilinkedtech.com.viewholders.ShoppingCarItemHolder;

public class ShoppingCarListAdapter
        extends BaseRecyclerAdapter<ProductItemBean>
{
    private ActShoppingCarFragment mActivity;
    public int viewFromType = VIEW_TYPE_PRODUCT_LIST;

    public static final int VIEW_TYPE_PRODUCT_LIST      = 0;//商品列表
    public static final int VIEW_TYPE_SHOPPING_CAR_LIST = 1;//购物车

    public ShoppingCarListAdapter(ArrayList<ProductItemBean> mArrayList) {
        super(mArrayList);
    }

    public ShoppingCarListAdapter(ArrayList<ProductItemBean> arrayList, ActShoppingCarFragment activity) {
        super(arrayList);
        mActivity = activity;

    }

    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewFromType) {
            case VIEW_TYPE_PRODUCT_LIST:
                view = View.inflate(UIUtils.getContext(), R.layout.item_product_view, null);
                break;
            case VIEW_TYPE_SHOPPING_CAR_LIST:
                view = View.inflate(UIUtils.getContext(), R.layout.item_shopping_car, null);
                break;
            default:
                view = View.inflate(UIUtils.getContext(), R.layout.item_product_view, null);
                break;
        }
        if (viewFromType==VIEW_TYPE_PRODUCT_LIST) {
            ProductItemHolder productItemHolder = new ProductItemHolder(view);
            return productItemHolder;
        }else {
            ShoppingCarItemHolder shoppingCarItemHolder = new ShoppingCarItemHolder(view);
            return shoppingCarItemHolder;
        }
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ProductItemBean productItemBean   = mArrayList.get(position);
        if (viewFromType==VIEW_TYPE_PRODUCT_LIST) {
            ProductItemHolder     productItemHolder = (ProductItemHolder) holder;
            if (productItemBean == null) {
                return;
            }
            productItemHolder.refreshProductViewWithDatas(productItemBean);
            productItemHolder.mLlContentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToProductDetailAct(productItemBean);
                }
            });
        }else if (viewFromType==VIEW_TYPE_SHOPPING_CAR_LIST) {
            ShoppingCarItemHolder     shoppingCarItemHolder = (ShoppingCarItemHolder) holder;
            shoppingCarItemHolder.refreshViewWithDatas(productItemBean);
            shoppingCarItemHolder.mLlContentLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertUtils.showErrorDialog(mActivity.getActivity(), "是否从购物车移除","确定","取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteFromeShoppingCar(position);
                        }
                    });
                    return false;
                }
            });
            shoppingCarItemHolder.mIvIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToProductDetailAct(productItemBean);
                }
            });

        }

    }

    private void deleteFromeShoppingCar(final int position) {
        final ObjRequestBean objRequestBean      =new ObjRequestBean();
        List<String>         shoppingCartUidList = new ArrayList<>();
        shoppingCartUidList.add(mArrayList.get(position).shoppingCartUid);
        objRequestBean.shoppingCartUidList=shoppingCartUidList;
        String json=new Gson().toJson(objRequestBean);
        MyHttpUtils.postJson(0, mActivity.getActivity(), true, Const.SHOPPING_DELETESHOPPINGCART, null, json, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                if (!StringUtils.isEmpty(response)){
                    ObjResponsBean objResponsBean =new Gson().fromJson(response,ObjResponsBean.class);
                    if (objResponsBean!=null&&objResponsBean.status.equals("1")){
                        mArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged (position,mArrayList.size());
                        if(position != mArrayList.size()){
                            notifyItemRangeChanged(position, mArrayList.size() - position);
                        }
                        mActivity.showEmptyView(mArrayList.size()==0);
                    }else {
                        ToastUtil.toast("从服务器移除失败");
                    }

                }
            }

            @Override
            public void doAffterRequestCall() {

            }
        });
    }



    private void goToProductDetailAct(ProductItemBean productItemBean) {
        Intent intent = new Intent(mActivity.getActivity(), ProductDetailActivity.class);
        intent.putExtra(KeyConst.BundleIntentKey.PRODUCT_ID, productItemBean.productUid);
        mActivity.startActivity(intent);
        UIUtils.activityAnimInt(mActivity.getActivity());
    }


}


