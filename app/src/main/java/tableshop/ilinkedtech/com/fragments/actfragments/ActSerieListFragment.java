package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActSerieListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/8 14:51
 *  @描述：    TODO 车系列表 视图
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.adapters.CarModelListAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.events.FilterItemTypeBean;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.events.SeriesListFilterBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetBrandList;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.views.recyles.RecycleViewDivider;

@SuppressLint("ValidFragment")
public class ActSerieListFragment
        extends RecyclerListFragment
{

    private static final String TAG = "ActSerieListFragment";
    public  View            mFilterView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultEventBus.register(this);
        if (mFilterView!=null)
            mFilterView.setSelected(showFilterView);
        showFiterLayout(showFilterView);
        mSearchLayout.setVisibility(showFilterView?View.VISIBLE:View.GONE);
    }

    public static ActFavoriteFragment newInstance() {
        ActFavoriteFragment fragment = new ActFavoriteFragment();
        Bundle          args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    boolean showFilterView;
    public FilterItemTypeBean fromHomeSearchPositionBean;
    public void setListRequestBean(ListRequestBean listRequestBean,boolean showFilterView){
        this.mListRequestBean = listRequestBean;
        this.showFilterView = showFilterView;
        pageNumber=0;
        if (mArrayList!=null&&adapter!=null) {
            mArrayList.clear();
            adapter.notifyDataSetChanged();
            setItemsData();
        }
        showFiterLayout(showFilterView);
    }
    private ArrayList<CarModelListItemBean> mArrayList;



    @Override
    protected BaseRecyclerAdapter setRecycleAdapter() {
        mArrayList = new ArrayList<>();
        emptyToHome=true;
        mRecy.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        return new CarModelListAdapter(mArrayList, getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        return manager;
    }

    @Override
    public void setRefreshWidget(boolean isShow) {
        super.setRefreshWidget(isShow);
        if (!isShow){
            if (adapter!=null&&mArrayList!=null) {
                adapter.notifyDataSetChanged();
                showEmptyView(mArrayList.size() == 0);
                isFragmentApiCall=false;
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFilterMessegeBean(SeriesListFilterBean filterMessegeBean){
        if (filterMessegeBean!=null&&!StringUtils.isEmpty(filterMessegeBean.isShowFilterLayout)&&mSearchLayout!=null){
            showFilterView=filterMessegeBean.isShowFilterLayout.equals("1");
            showFiterLayout(showFilterView);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean){
        if (payResultBean!=null){
            if (payResultBean.resultState==PayResultBean.TYPE_SUSSECE){
                if (adapter!=null) {
                    mArrayList.clear();
                    adapter.notifyDataSetChanged();
                }
                refreshDatas();
            }
        }
    }


    @Override
    public void setItemsData() {
        LogUtils.e(TAG,"setItemsData()");
        if (mListRequestBean ==null) {
            mListRequestBean = new ListRequestBean();
        }
        mListRequestBean.pageNumber=pageNumber+"";
        refreshDatas();
    }

    @Override
    public void refreshDatas() {
        LogUtils.e(TAG,"refreshDatas()");
        if (mListRequestBean==null){
            mListRequestBean=new ListRequestBean();
        }
        mListRequestBean.pageNumber=pageNumber+"";
        String            requestJson =new Gson().toJson(mListRequestBean);
        setRefreshWidget(true);
        MyHttpUtils.postJson(Const.GET_VEHICLE_SERIES_CODE_ON_SALES,true, null, requestJson, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
                setRefreshWidget(false);
            }

            @Override
            public void onResponse(String response, int id) {

                if (!StringUtils.isEmpty(response)){
                    try {
                        GetBrandList getBrandList = new Gson().fromJson(response,
                                                                               GetBrandList.class);
                        if (getBrandList!=null&&getBrandList.seriesPage!=null&&getBrandList.seriesPage.size()>0){
                            if (adapter!=null&&mArrayList!=null) {
                                if (!mArrayList.containsAll(getBrandList.seriesPage)) {
                                    mArrayList.addAll(getBrandList.seriesPage);
                                }

                            }
                        }

                    }catch (JsonSyntaxException e){

                    }catch (IllegalStateException e){

                    }
                    setRefreshWidget(false);

                }
            }

            @Override
            public void doAffterRequestCall() {
                hideLoading();
            }
        });

    }

    @Override
    public void calltoGetSearchDatas() {
        pageNumber=0;
        mArrayList.clear();
        adapter.notifyDataSetChanged();
        refreshDatas();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshSearchLayout();
    }

    /**
     * 刷新搜索条目的状态，从主页跳过来的刷选条件
     */
    public void refreshSearchLayout(){
        if (mListRequestBean!=null){
            if (!StringUtils.isEmpty(mListRequestBean.brandName)) {
                mBtnSeleteBrand.setText(mListRequestBean.brandName);
            }
            if (fromHomeSearchPositionBean!=null){
                switch (fromHomeSearchPositionBean.itemType){
                    case KeyConst.ItemView.TYPE_CATEGORY:
                        mSeriesFilterAdapter.setSelectedList(fromHomeSearchPositionBean.itemPosition);
                        break;
                    case KeyConst.ItemView.TYPE_PRICE:
                        mPriceFilterAdapter.setSelectedList(fromHomeSearchPositionBean.itemPosition);
                        break;
                    case KeyConst.ItemView.TYPE_SEAT:
                        mSeatsFilterAdapter.setSelectedList(fromHomeSearchPositionBean.itemPosition);
                        break;
                }
            }



        }
    }

    @Override
    public void startSearch() {
        super.startSearch();
        if (mFilterView !=null)
            mFilterView.setSelected(false);
    }
}
