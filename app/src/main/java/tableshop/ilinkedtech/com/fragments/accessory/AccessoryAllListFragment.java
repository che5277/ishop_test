package tableshop.ilinkedtech.com.fragments.accessory;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActCarListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/8 14:51
 *  @描述：    TODO 商品 全部列表 视图
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.MyProducAdapter;
import tableshop.ilinkedtech.com.adapters.ProductListAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.beans.reques.ObjRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetProductListBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

@SuppressLint("ValidFragment")
public class AccessoryAllListFragment
        extends RecyclerListFragment
{

    private static final String TAG = "ActCarListFragment";
    public ImageView        mIvRight;
    private ListRequestBean mListRequestBean;
    private AutoCompleteTextView mAutoSort;
    private TextView mTvListTitle;
    private ArrayAdapter<String> mMModelListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSortView();

        refreshListCount(0);
    }

    @NonNull
    private void initSortView() {
        View                 view                 =View.inflate(getContext(), R.layout.view_sort_layout, null);
        mAutoSort = view.findViewById(R.id.auto_sort);
        mTvListTitle = view.findViewById(R.id.tv_list_title);

        mAutoSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListRequestBean!=null){
                    mListRequestBean.sortType=KeyConst.ListD.FileterTag.SortType.PRICE;
                    mListRequestBean.sortOrder=position==0?KeyConst.ListD.FileterTag.SortOrder.DESC:KeyConst.ListD.FileterTag.SortOrder.ASC;
                    pageNumber=0;
                    mArrayList.clear();
                    adapter.notifyDataSetChanged();
                    refreshDatas();
                }
            }
        });

        mAutoSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAutoSort.showDropDown();
            }
        });

        if (mMModelListAdapter==null) {
            mMModelListAdapter = new ArrayAdapter<String>(getContext(),
                                                          android.R.layout.simple_list_item_1,
                                                          getResources().getStringArray(R.array.sort_car_list_array));
        }
        mAutoSort.setThreshold(1);
        mAutoSort.setAdapter(mMModelListAdapter);
        mRlHeadLayout.addView(view);
    }



    public static AccessoryAllListFragment newInstance() {
        AccessoryAllListFragment fragment = new AccessoryAllListFragment();
        Bundle                   args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public void setListRequestBean(ListRequestBean listRequestBean){
        this.mListRequestBean = listRequestBean;
        pageNumber=0;
        if (mArrayList!=null&&adapter!=null) {
            mArrayList.clear();
            adapter.notifyDataSetChanged();
            Log.e("runhere","setitemsda1111111");
            setItemsData();
        }
    }
    private ArrayList<ProductItemBean> mArrayList;

    @Override
    protected BaseRecyclerAdapter setRecycleAdapter() {
        mArrayList = new ArrayList<>();
        emptyToHome=false;
        return new ProductListAdapter(mArrayList, getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        return manager;
    }

    @Override
    public void setRefreshWidget(boolean isShow) {
        super.setRefreshWidget(isShow);
        if (mArrayList!=null)
        LogUtils.e(TAG,"setRefreshWidget()  size:"+mArrayList.size());
        if (!isShow){
            if (adapter!=null&&mArrayList!=null) {
               adapter.notifyDataSetChanged();
                showEmptyView(mArrayList.size() == 0);
                isFragmentApiCall=false;
            }
        }

    }


    @Override
    public void setItemsData() {
        LogUtils.e(TAG,"setItemsData()");
        refreshDatas();
    }


    @Override
    public void refreshDatas() {
        LogUtils.e(TAG,"refreshDatas()");
        if (mListRequestBean ==null) {
            mListRequestBean = new ListRequestBean();
        }
        ObjRequestBean objRequestBean=new ObjRequestBean();
        mListRequestBean.locale=KeyConst.LanguageLocal.ZH_CN;
        mListRequestBean.category="";
        mListRequestBean.pageNumber=pageNumber+"";
        objRequestBean.data =mListRequestBean;
        String requestJson=new Gson().toJson(objRequestBean);
      //  mArrayList.clear();
      //  adapter.notifyDataSetChanged();
        isFragmentApiCall=true;
        //setRefreshWidget(true);
        MyHttpUtils.postJson(Const.PRODUCT_FIND,true, null, requestJson, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
                //setRefreshWidget(false);
            }

            @Override
            public void onResponse(String response, int id) {

                Log.e("alllist","all--list"+response.toString());
                if (!StringUtils.isEmpty(response)){
                    try {
                        GetProductListBean getAccessoryListBean = new Gson().fromJson(response,
                                                                                      GetProductListBean.class);
                        if (getAccessoryListBean.status.equals("1")){
                            if (getAccessoryListBean.datas!=null&&getAccessoryListBean.datas.size()>0){
                               // mArrayList.clear();
                                if (!mArrayList.contains(getAccessoryListBean.datas)) {
                                    mArrayList.addAll(getAccessoryListBean.datas);
                                    adapter.notifyDataSetChanged();
                                    isLoadMore=getAccessoryListBean.datas.size()> (KeyConst.ListD.MAX_RESULTS-1);
                                }
                            }
                            refreshListCount(getAccessoryListBean.count);
                        }

                    }catch (JsonSyntaxException e){

                    }catch (IllegalStateException e){

                    }
                    //setRefreshWidget(false);

                }
            }

            @Override
            public void doAffterRequestCall() {
                isFragmentApiCall=false;
                hideLoading();
            }
        });

    }

    private void refreshListCount(int count) {
        if (mTvListTitle!=null) {
            String text = UIUtils.getString(R.string.共有款商品);
            mTvListTitle.setText(text.replace("%", count + ""));
        }
    }

}
