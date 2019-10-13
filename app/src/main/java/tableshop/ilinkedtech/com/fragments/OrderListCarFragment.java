package tableshop.ilinkedtech.com.fragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   OrderListCarFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/8 14:51
 *  @描述：    TODO 我的订单 视图
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.adapters.OrderListAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;
import tableshop.ilinkedtech.com.views.recyles.RecycleViewDivider;

@SuppressLint("ValidFragment")
public class OrderListCarFragment
        extends RecyclerListFragment
{

    public static OrderListCarFragment newInstance() {
        OrderListCarFragment fragment = new OrderListCarFragment();
        Bundle          args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<OrderListItemBean> mArrayList;

    @Override
    protected BaseRecyclerAdapter setRecycleAdapter() {
        mArrayList = new ArrayList<>();
        emptyToHome=false;
        mRecy.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        //mSwipeRefreshWidget.setEnabled(false);
        return new OrderListAdapter(mArrayList, getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        return manager;
    }


    public void refreshViewWithDatas(List<OrderListItemBean> datas){
        if (adapter!=null&&!mArrayList.containsAll(datas)) {
            mArrayList.clear();
            mArrayList.addAll(datas);
            adapter.notifyDataSetChanged();
            showEmptyView(mArrayList.size() == 0);
        }

    }
    @Override
    public void setRefreshWidget(boolean isShow) {
        super.setRefreshWidget(isShow);
        if (!isShow){
            if (adapter!=null&&mArrayList!=null) {
//                OrderListItemBean detailBean = new OrderListItemBean();
//                mArrayList.add(detailBean);
                adapter.notifyDataSetChanged();
                showEmptyView(mArrayList.size() == 0);
                isFragmentApiCall=false;
            }
        }

    }

    @Override
    public void refreshDatas() {

    }
}
