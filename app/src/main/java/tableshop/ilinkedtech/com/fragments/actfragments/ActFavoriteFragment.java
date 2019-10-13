package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActFavoriteFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 14:51
 *  @描述：    TODO 收藏
 */

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.adapters.CarDetailListAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.db.DBJsonHelper;

public class ActFavoriteFragment
        extends RecyclerListFragment
{
    public static ActFavoriteFragment newInstance() {
        ActFavoriteFragment fragment = new ActFavoriteFragment();
        Bundle          args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<CarDetailBean> mArrayList;

    @Override
    protected BaseRecyclerAdapter setRecycleAdapter() {
        mArrayList = new ArrayList<>();
        emptyToHome=true;
        return new CarDetailListAdapter(mArrayList, getActivity(),0);
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        return manager;
    }

    @Override
    public void setRefreshWidget(boolean isShow) {
        super.setRefreshWidget(isShow);
        if (!isShow){
            if (adapter!=null&&mArrayList!=null) {
                ((CarDetailListAdapter) adapter).favoriteList = null;
                adapter.notifyDataSetChanged();
                showEmptyView(mArrayList.size() == 0);
                isFragmentApiCall=false;
            }
        }

    }

    @Override
    public void refreshDatas() {

        ArrayList<CarDetailBean> favoriteList = DBJsonHelper.getFavoriteList();
        if (favoriteList!=null) {
            mArrayList.clear();
            mArrayList.addAll(favoriteList);
        }
        setRefreshWidget(false);
    }
}
