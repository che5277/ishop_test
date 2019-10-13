package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActContrastListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 14:51
 *  @描述：    TODO 对比车辆列表
 */

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.adapters.ContrastListAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.db.DBJsonHelper;

public class ActContrastListFragment
        extends RecyclerListFragment
{

    public static ActContrastListFragment newInstance() {
        ActContrastListFragment fragment = new ActContrastListFragment();
        Bundle                args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<CarDetailBean> mArrayList;

    @Override
    protected BaseRecyclerAdapter setRecycleAdapter() {
        mArrayList = new ArrayList<>();
        emptyToHome=true;
        return new ContrastListAdapter(mArrayList, getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        return manager;
    }

    /**
     * 获取需要展示的对比车辆
     * @return
     */
    public ArrayList<CarDetailBean> getShowData(){
        ArrayList<CarDetailBean> seletedItems= new ArrayList<>();
        if (mArrayList!=null&&mArrayList.size()>0){
            CarDetailBean detailBean = null;
            for (int i = 0; i < mArrayList.size(); i++) {
                detailBean = mArrayList.get(i);
                if (detailBean.isSeleted&&!seletedItems.contains(detailBean)){
                    seletedItems.add(detailBean);
                }
            }
        }
        return seletedItems;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void refreshDatas() {

        ArrayList<CarDetailBean> contrastList = DBJsonHelper.getContrastList();
        if (contrastList!=null) {
            mArrayList.clear();
            mArrayList.addAll(contrastList);
        }
        setRefreshWidget(false);
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
}
