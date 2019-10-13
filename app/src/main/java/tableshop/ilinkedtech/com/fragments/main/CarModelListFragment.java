package tableshop.ilinkedtech.com.fragments.main;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.main
 *  @文件名:   CarModelListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:44
 *  @描述：    TODO 搜索车列表
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.adapters.CarModelListAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListRequestBean;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.db.DBJsonHelper;
import tableshop.ilinkedtech.com.protocols.CarModelListProtocol;
import tableshop.ilinkedtech.com.utils.StringUtils;

@SuppressLint("ValidFragment")
public class CarModelListFragment
        extends RecyclerListFragment {


    public static CarModelListFragment newInstance(CarModelListRequestBean carModelListRequestBean) {
        CarModelListFragment fragment = new CarModelListFragment(carModelListRequestBean);
        Bundle               args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    private ArrayList<CarModelListItemBean> mArrayList;
    CarModelListRequestBean carModelListRequestBean;

    public CarModelListFragment(CarModelListRequestBean carModelListRequestBean) {
        this();
        this.carModelListRequestBean=carModelListRequestBean;
    }

    public CarModelListFragment(){
        super();
    }
    @Override
    protected BaseRecyclerAdapter setRecycleAdapter() {
        mArrayList = new ArrayList<>();
        return new CarModelListAdapter(mArrayList, getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        return manager;
    }

    @Override
    public void refreshDatas() {
        if (mArrayList==null||isFragmentApiCall||(!isLoadDataFromNet&&mArrayList.size()>0)){
            MainApp.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRefreshWidget(false);
                    isFragmentApiCall=false;
                    if (adapter!=null&&mArrayList!=null) {
                        if (mArrayList.size()==0&&!isFragmentApiCall) {
                            onRefresh();
                        }else {
                            adapter.notifyDataSetChanged();
                            showEmptyView(mArrayList.size() == 0);
                        }
                    }
                }
            }, 1000);
            return;
        }

        isFragmentApiCall=true;
        if (carModelListRequestBean==null) {
            carModelListRequestBean = new CarModelListRequestBean();
        }

        CarModelListProtocol carModelListProtocol =new CarModelListProtocol(getActivity(),
                                                                            carModelListRequestBean,isLoadDataFromNet) {
            @Override
            public void doOnSusses(ArrayList<CarModelListItemBean> dataBean, int id) {
                if (mArrayList!=null) {
                    mArrayList.clear();
                    if (dataBean != null && dataBean.size() > 0) {
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
                isFragmentApiCall=false;
                setRefreshWidget(false);
            }
        };

    }

    private boolean goToGetDataFromLocal(CarModelListRequestBean carModelListRequestBean) {
        final Gson gson = new Gson();
        String body=gson.toJson(carModelListRequestBean);
        String apiKey = Const.GET_VEHICLE + body;
        String jsonData = DBJsonHelper.mdKeyAndGetJsonData(apiKey);
        if (!StringUtils.isEmpty(jsonData)){
            try {
                ArrayList<CarModelListItemBean> dataBean = StringUtils.jsonToArrayObj(jsonData,CarModelListItemBean.class);
                if (dataBean!=null&&dataBean.size()>0) {
                    if (!mArrayList.containsAll(dataBean)){
                        mArrayList.addAll(dataBean);
                    }
                    adapter.notifyDataSetChanged();
                    setRefreshWidget(false);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
