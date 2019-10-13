package tableshop.ilinkedtech.com.fragments.main;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.main
 *  @文件名:   CarModelListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:44
 *  @描述：    TODO 选车列表
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.adapters.CarDetailListAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListRequestBean;
import tableshop.ilinkedtech.com.protocols.GetRecommendListProtocol;

@SuppressLint("ValidFragment")
public class CaDetailListFragment
        extends RecyclerListFragment
{

    public static CaDetailListFragment newInstance(boolean isSmallItem) {
        CaDetailListFragment fragment = new CaDetailListFragment(isSmallItem);
        Bundle               args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<CarDetailBean> mArrayList;
    boolean isSmallItem;

    public CaDetailListFragment(boolean isSmallItem) {
        this();
        this.isSmallItem = isSmallItem;
    }
    public CaDetailListFragment(){
        super();
    }


    public void setArrayDatas(ArrayList<CarDetailBean> arrayDatas) {
        mArrayList = arrayDatas;
    }

    @Override
    protected BaseRecyclerAdapter setRecycleAdapter() {
        if (mArrayList == null) { mArrayList = new ArrayList<>(); }
        return new CarDetailListAdapter(mArrayList, getActivity(), isSmallItem);
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getContext(),
                                                          isSmallItem
                                                          ? 2
                                                          : 1);
        return manager;
    }


    @Override
    public void setRefreshWidget(boolean isShow) {
        super.setRefreshWidget(isShow);
        if (!isShow) {
            if (adapter != null && mArrayList != null) {
                ((CarDetailListAdapter) adapter).favoriteList = null;
                adapter.notifyDataSetChanged();
                showEmptyView(mArrayList.size() == 0);
            }
        }

    }

    @Override
    public void refreshDatas() {
        if (mArrayList == null || isFragmentApiCall) {
            MainApp.getHandler()
                   .postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           setRefreshWidget(false);
                       }
                   }, 1000);
            return;
        }
        if (!isSmallItem) {
            CarModelListRequestBean carModelListRequestBean = new CarModelListRequestBean();
            GetRecommendListProtocol carModelListProtocol = new GetRecommendListProtocol(getActivity(),
                                                                                         carModelListRequestBean)
            {
                @Override
                public void doOnSusses(ArrayList<CarDetailBean> dataBean, int id) {
                    if (mArrayList != null) {
//                        mArrayList.clear();
                        if (dataBean != null && dataBean.size() > 0&&!mArrayList.containsAll(dataBean)) {
                            mArrayList.addAll(dataBean);
                        }
                        adapter.notifyDataSetChanged();
                        showEmptyView(mArrayList.size() == 0);
                    }
                }

                @Override
                public void doOnError(Exception e, int id) {
                    if (mArrayList != null) {
                        showEmptyView(mArrayList.size() == 0);
                    }
                }

                @Override
                public void doAffertRequest() {
                    super.doAffertRequest();
                    isFragmentApiCall = false;
                    setRefreshWidget(false);
                }
            };
        } else {
            //TODO 直接传数据显示
            setRefreshWidget(false);
        }

    }
}
