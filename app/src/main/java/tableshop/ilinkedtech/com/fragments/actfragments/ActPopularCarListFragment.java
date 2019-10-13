package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActPopularCarListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/8 14:51
 *  @描述：    TODO 编辑之选车辆列表 视图
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.adapters.CarDetailListAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.beans.reques.ObjRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetBrandList;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.views.recyles.RecycleViewDivider;

@SuppressLint("ValidFragment")
public class ActPopularCarListFragment
        extends RecyclerListFragment
{

    private static final String TAG = "ActPopularCarListFragment";
    public ImageView             mIvRight;
    private CarModelListItemBean mCarModelListItemBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultEventBus.register(this);
    }

    public static ActPopularCarListFragment newInstance() {
        ActPopularCarListFragment fragment = new ActPopularCarListFragment();
        Bundle                    args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public String state;

    public void setCarModelListItemBean(CarModelListItemBean carModelListItemBean){
        this.mCarModelListItemBean = carModelListItemBean;
        if (mCarModelListItemBean!=null){
            state=mCarModelListItemBean.state;
        }
        pageNumber=0;

    }
    private ArrayList<CarDetailBean> mArrayList;

    @Override
    protected BaseRecyclerAdapter setRecycleAdapter() {
        mArrayList = new ArrayList<>();
        emptyToHome=true;
        mRecy.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        return new CarDetailListAdapter(mArrayList, getActivity(),CarDetailListAdapter.CAR_DETAIL_POPULAR_VIEW);
    }

    @Override
    protected RecyclerView.LayoutManager setRecycleLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
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
    }


    //车辆购买成功，刷新品牌数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean) {
        if (payResultBean != null) {
            if (payResultBean.resultState == PayResultBean.TYPE_SUSSECE) {
                mArrayList.clear();
                refreshDatas();
            }
        }
    }

    @Override
    public void refreshDatas() {
        if (!StringUtils.isEmpty(state)){
            ObjRequestBean objRequestBean =new ObjRequestBean();
            objRequestBean.state=state;
            String json=new Gson().toJson(objRequestBean);
            setRefreshWidget(true);
            MyHttpUtils.postJson(0, getActivity(), false, Const.GET_POPULAR_VEHICLE, null, json, new MysalesCallBack() {
                @Override
                public void onError(Exception e, int id) {
                    setRefreshWidget(false);
                }

                @Override
                public void onResponse(String response, int id) {
                    if (!StringUtils.isEmpty(response)) {
                        try {

                            GetBrandList getBrandList = new Gson().fromJson(
                                    response,
                                    GetBrandList.class);
                            if (getBrandList != null && getBrandList.popularVehicle != null) {
                                    List<CarModelListItemBean> vehicleStockSingleViewList = getBrandList.popularVehicle;
                                    if (vehicleStockSingleViewList != null && vehicleStockSingleViewList.size()>0)
                                    {
                                        CarModelListItemBean carModelListItemBean = vehicleStockSingleViewList.get(
                                                0);
                                        if (carModelListItemBean!=null&&carModelListItemBean.vehicleStockSingleViewList.size()>0) {
                                            if (!mArrayList.containsAll(carModelListItemBean.vehicleStockSingleViewList)) {
                                                mArrayList.addAll(carModelListItemBean.vehicleStockSingleViewList);
                                            }
                                        }
                                    }

                            }

                        } catch (JsonSyntaxException e) {

                        } catch (IllegalStateException e) {

                        }

                    }
                    setRefreshWidget(false);

                }

                @Override
                public void doAffterRequestCall() {

                }
            });
        }
    }

}
