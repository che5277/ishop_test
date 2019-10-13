package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActContrastPKFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 14:51
 *  @描述：    TODO 对比车辆进入PK页面列表
 */

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.adapters.ContrastViewAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;

public class ActContrastPKFragment
        extends RecyclerListFragment
{
    public static ActContrastPKFragment newInstance() {
        ActContrastPKFragment fragment = new ActContrastPKFragment();
        Bundle              args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<CarDetailBean> mArrayList;

    @Override
    protected BaseRecyclerAdapter setRecycleAdapter() {
        if (mArrayList==null)
            mArrayList = new ArrayList<>();
        isFragmentApiCall=true;
     //   mSwipeRefreshWidget.setEnabled(false);
        return new ContrastViewAdapter(mArrayList, getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return manager;
    }

    public void refreshDatas(ArrayList<CarDetailBean> contrastList) {

        mArrayList.clear();
        if (contrastList != null && !mArrayList.containsAll(contrastList)) {
            mArrayList.addAll(contrastList);
            adapter.notifyDataSetChanged();
        }
        showEmptyView(mArrayList.size()==0);
    }

    @Override
    public void refreshDatas() {
        setRefreshWidget(false);
    }

}
