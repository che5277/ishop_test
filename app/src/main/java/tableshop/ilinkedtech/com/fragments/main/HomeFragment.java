package tableshop.ilinkedtech.com.fragments.main;

/*
 *  @文件名:   HomeFragment
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/22 10:55
 *  @描述：    TODO  首页
 */

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.CarDetailListAdapter;
import tableshop.ilinkedtech.com.adapters.CarModelPopularListAdapter;
import tableshop.ilinkedtech.com.adapters.FilterListAdapter;
import tableshop.ilinkedtech.com.adapters.HotBrandListAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.RulesItem;
import tableshop.ilinkedtech.com.beans.events.BaseUrlChangeBean;
import tableshop.ilinkedtech.com.beans.events.FilterItemTypeBean;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.events.UpDateHotBrandData;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListRequestBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetBrandList;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.FilterTagJson;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.loader.GlideViewLoader;
import tableshop.ilinkedtech.com.protocols.GetRecommendListProtocol;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.dialogs.RxDialogShapeLoading;
import tableshop.ilinkedtech.com.views.recyles.RecycleViewOffSetDivider;

public class HomeFragment
        extends IShopBaseFragment
{

    private static final String TAG = "HomeFragment";
    @BindView(R.id.banner_recommend)
    public Banner       mBannerRecommend;
    @BindView(R.id.recycle_brand)
    public RecyclerView mRecyclerBrand;
    @BindView(R.id.recycle_filter)
    public RecyclerView mRecyclerFilter;
    @BindView(R.id.recycle_edit_car)
    public RecyclerView mRecyclerPopularCar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.recycle_hot_car)
    RecyclerView mRecycleHotCar;
    @BindView(R.id.recycle_sold_car)
    RecyclerView mRecycleSoldCar;
    Unbinder unbinder1;
    private RxDialogShapeLoading            mRxDialogShapeLoading;
    private SharedStorage                   mStorage;
    private ArrayList<CarDetailBean>        mBrandBeans;
    private ArrayList<CarModelListItemBean> mPopularDatas;
    private HotBrandListAdapter             mBrandAdapter;
    private ArrayList<RulesItem>            mFilterDatas;
    private FilterListAdapter               mFilterListAdapter;
    private CarModelPopularListAdapter      mPopularCarListAdapter;
    private GlideViewLoader                 mGlideViewLoader;
    private ArrayList<CarDetailBean>        mHotCarDatas;
    private ArrayList<CarDetailBean>        mSoldCarDatas;
    private CarDetailListAdapter mHotCarListAdapter;
    private CarDetailListAdapter mSoldCarListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        defaultEventBus.register(this);
    }

    @Override
    protected void initEvent() {
        mStorage = SharedStorage.getInstance(getContext());

        mBannerRecommend.isAutoPlay(true);
        mBannerRecommend.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                LogUtils.e("setOnBannerListener:" + position);
            }
        });

        //设置 Header 为 Material样式
        //        mRefreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲

        mRefreshLayout.setEnableOverScrollDrag(true);
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (!isFragmentApiCall) {
//                    OkHttpUtils.getInstance().cancelTag(getActivity());
                    iniDatas();
                }
            }
        });

        initHotBrandEvent();

        initEditCarEvent();

        initHotCarEvent();

        initSoldCarEvent();

        iniRemondCarEvent();

        startToRefresh();

        initLoCalFilter();
    }



    private void initHotCarEvent() {
        mHotCarDatas=new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mHotCarListAdapter = new CarDetailListAdapter(mHotCarDatas,
                                                      getActivity(),
                                                      0);
        mRecycleHotCar.setLayoutManager(manager);
        mRecycleHotCar.setAdapter(mHotCarListAdapter);
        mRecycleHotCar.setNestedScrollingEnabled(false);
    }

    private void initSoldCarEvent() {
        mSoldCarDatas=new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mSoldCarListAdapter = new CarDetailListAdapter(mSoldCarDatas,
                                                       getActivity(),
                                                       0);
        mRecycleSoldCar.setLayoutManager(manager);
        mRecycleSoldCar.setAdapter(mSoldCarListAdapter);
        mRecycleSoldCar.setNestedScrollingEnabled(false);
    }

    private void iniRemondCarEvent() {
        mRecommendDataBeans = new ArrayList<>();
        mGlideViewLoader = new GlideViewLoader();
        mBannerRecommend.setImageLoader(mGlideViewLoader);
        mBannerRecommend.setImages(mRecommendDataBeans);
        mBannerRecommend.setNestedScrollingEnabled(false);
        mBannerRecommend.start();
    }

    private void initHotBrandEvent() {
        mBrandBeans = new ArrayList<>();
        mBrandAdapter = new HotBrandListAdapter(getActivity(), mBrandBeans);
        mRecyclerBrand.setAdapter(mBrandAdapter);
        LinearLayoutManager hotBrandManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                //TODO 禁止竖向滑动，解决和ScrollView抢滑动事件导致滑动卡顿的问题
                return false;
            }
        };
        hotBrandManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerBrand.setLayoutManager(hotBrandManager);
        //        mRecyclerBrand.setNestedScrollingEnabled(false);
    }

    private void iniDatas() {
        getRecommendListDatas();
        getHotBrandList();
        iniEditCarDatas();

        getHotCarDatas();
        getSoldCarDatas();
    }



    public void startToRefresh() {
        mRefreshLayout.autoRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseUrlChangeBean(BaseUrlChangeBean changeBean) {
        startToRefresh();
    }

    //车辆购买成功，刷新品牌数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean) {
        if (payResultBean != null) {
            if (payResultBean.resultState == PayResultBean.TYPE_SUSSECE) {
                startToRefresh();
            }
        }
    }

    //编辑之选
    private void initEditCarEvent() {
        if (mPopularDatas == null) { mPopularDatas = new ArrayList<>(); }
        mPopularCarListAdapter = new CarModelPopularListAdapter(mPopularDatas, getActivity());
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                //TODO 禁止竖向滑动，解决和ScrollView抢滑动事件导致滑动卡顿的问题
                return false;
            }
        };
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerPopularCar.setLayoutManager(manager);
        mRecyclerPopularCar.setAdapter(mPopularCarListAdapter);

    }

    private void iniEditCarDatas() {
        if (mPopularDatas != null) {
            mPopularDatas.clear();
            mPopularCarListAdapter.notifyDataSetChanged();
        }
        ListRequestBean listRequestBean = new ListRequestBean();
        String          requestJson     = new Gson().toJson(listRequestBean);
        MyHttpUtils.postJson(Const.GET_POPULAR_VEHICLE,
                             true,
                             null,
                             requestJson,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {

                                 }

                                 @Override
                                 public void onResponse(String response, int id) {

                                     if (!StringUtils.isEmpty(response)) {
                                         try {

                                             GetBrandList getBrandList = new Gson().fromJson(
                                                     response,
                                                     GetBrandList.class);
                                             if (getBrandList != null && getBrandList.popularVehicle != null) {
                                                 if (mPopularCarListAdapter != null && mPopularDatas != null) {
                                                     List<CarModelListItemBean> vehicleStockSingleViewList = getBrandList.popularVehicle;
                                                     if (vehicleStockSingleViewList != null && !mPopularDatas.containsAll(
                                                             vehicleStockSingleViewList))
                                                     {
                                                         mPopularDatas.addAll(
                                                                 vehicleStockSingleViewList);
                                                     }

                                                 }
                                             }

                                         } catch (JsonSyntaxException e) {

                                         } catch (IllegalStateException e) {

                                         }

                                     }
                                     mPopularCarListAdapter.notifyDataSetChanged();
                                 }

                                 @Override
                                 public void doAffterRequestCall() {
                                     isFragmentApiCall = false;
                                     hideLoading();
                                 }
                             });
    }


    private void getHotCarDatas() {
        if (mHotCarDatas != null) {
            mHotCarDatas.clear();
            mHotCarListAdapter.notifyDataSetChanged();
        }
        ListRequestBean listRequestBean = new ListRequestBean();
        listRequestBean.state=5+"";
        String          requestJson     = new Gson().toJson(listRequestBean);
        MyHttpUtils.postJson(0, getActivity(), true, Const.GET_POPULAR_VEHICLE, null, requestJson, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (!StringUtils.isEmpty(response)) {
                    GetBrandList getBrandList = new Gson().fromJson(response, GetBrandList.class);
                    if (getBrandList!=null&&getBrandList.popularVehicle!=null&&getBrandList.popularVehicle.size()>0){
                        CarModelListItemBean carModelListItemBean = getBrandList.popularVehicle.get(
                                0);
                        if (!mHotCarDatas.containsAll(carModelListItemBean.vehicleStockSingleViewList)) {
                            mHotCarDatas.addAll(carModelListItemBean.vehicleStockSingleViewList);
                            mHotCarListAdapter.notifyDataSetChanged();
                        }
                    }

                }

            }

            @Override
            public void doAffterRequestCall() {

            }
        });
    }

    private void getSoldCarDatas() {
        if (mSoldCarDatas != null) {
            mSoldCarDatas.clear();
            mSoldCarListAdapter.notifyDataSetChanged();
        }
        ListRequestBean listRequestBean = new ListRequestBean();
        String          requestJson     = new Gson().toJson(listRequestBean);
        MyHttpUtils.postJson(0, getActivity(), true, Const.DISPLAY_SOLD_VEHICLE, null, requestJson, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (!StringUtils.isEmpty(response)) {
                    GetBrandList getBrandList = new Gson().fromJson(response, GetBrandList.class);
                    if (getBrandList!=null&&getBrandList.popularVehicle!=null&&getBrandList.popularVehicle.size()>0){
                        CarModelListItemBean carModelListItemBean = getBrandList.popularVehicle.get(
                                0);
                        if (!mSoldCarDatas.containsAll(carModelListItemBean.vehicleStockSingleViewList)) {
                            mSoldCarDatas.addAll(carModelListItemBean.vehicleStockSingleViewList);
                            mSoldCarListAdapter.notifyDataSetChanged();
                        }
                    }

                }

            }

            @Override
            public void doAffterRequestCall() {

            }
        });
    }

    private void initLoCalFilter() {

        if (mFilterDatas == null) {
            mFilterDatas = new ArrayList<>();
        }
        RecycleViewOffSetDivider recycleViewDivider = new RecycleViewOffSetDivider();
        recycleViewDivider.setItemOffSets(0, 0, 28, 20);
        mRecyclerFilter.addItemDecoration(recycleViewDivider);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4) {
            @Override
            public boolean canScrollVertically() {
                //TODO 禁止竖向滑动，解决和ScrollView抢滑动事件导致滑动卡顿的问题
                return false;
            }
        };
        mRecyclerFilter.setLayoutManager(gridLayoutManager);
        mFilterListAdapter = new FilterListAdapter(mFilterDatas, getActivity());
        mRecyclerFilter.setAdapter(mFilterListAdapter);
        //        mRecyclerFilter.setNestedScrollingEnabled(false);
        initFilterDatas();

    }

    public static final int FILTER_LINE_ITEMS = 4;

    private void initFilterDatas() {
        String[] seriresfilterStrs = UIUtils.getStringArray(R.array.filter_tag_car_stye);
        String[] pricefilterStrs   = UIUtils.getStringArray(R.array.filter_tag_car_price);
        String[] seatfilterStrs    = UIUtils.getStringArray(R.array.filter_tag_car_seat_count);

        ArrayList<RulesItem> categoryRulesItems = StringUtils.jsonToArrayObj(FilterTagJson.categoryRequestBeansJson,
                                                                             RulesItem.class);
        ArrayList<RulesItem> priceRulesItems = StringUtils.jsonToArrayObj(FilterTagJson.priceRequestBeansJson,
                                                                          RulesItem.class);
        ArrayList<RulesItem> seatRulesItems = StringUtils.jsonToArrayObj(FilterTagJson.seatsRequestBeansJson,
                                                                         RulesItem.class);

        RulesItem          rulesItem          = null;
        FilterItemTypeBean filterItemTypeBean = null;
        int size = categoryRulesItems.size() > FILTER_LINE_ITEMS
                   ? FILTER_LINE_ITEMS
                   : categoryRulesItems.size();
        for (int i = 0; i < size; i++) {
            rulesItem = categoryRulesItems.get(i);
            filterItemTypeBean = new FilterItemTypeBean();
            filterItemTypeBean.itemType = KeyConst.ItemView.TYPE_CATEGORY;
            filterItemTypeBean.itemPosition = i;
            rulesItem.mFilterItemTypeBean = filterItemTypeBean;
            mFilterDatas.add(rulesItem);
        }
        size = priceRulesItems.size() > FILTER_LINE_ITEMS
               ? FILTER_LINE_ITEMS
               : priceRulesItems.size();
        for (int i = 0; i < size; i++) {
            rulesItem = priceRulesItems.get(i);
            filterItemTypeBean = new FilterItemTypeBean();
            filterItemTypeBean.itemType = KeyConst.ItemView.TYPE_PRICE;
            filterItemTypeBean.itemPosition = i;
            rulesItem.mFilterItemTypeBean = filterItemTypeBean;
            mFilterDatas.add(rulesItem);
        }
        size = seatRulesItems.size() > FILTER_LINE_ITEMS - 1
               ? FILTER_LINE_ITEMS - 1
               : seatRulesItems.size();
        for (int i = 0; i < size; i++) {
            rulesItem = seatRulesItems.get(i);
            filterItemTypeBean = new FilterItemTypeBean();
            filterItemTypeBean.itemType = KeyConst.ItemView.TYPE_SEAT;
            filterItemTypeBean.itemPosition = i;
            rulesItem.mFilterItemTypeBean = filterItemTypeBean;
            mFilterDatas.add(rulesItem);
        }
        rulesItem = new RulesItem();
        rulesItem.requestBean = new ListRequestBean();
        rulesItem.displayText = UIUtils.getString(R.string.更多);
        mFilterDatas.add(rulesItem);
        mFilterListAdapter.notifyDataSetChanged();

    }

    ArrayList<CarDetailBean> mRecommendDataBeans;

    private void getHotBrandList() {
        String json = "{}";
        isFragmentApiCall = true;
        if (mBrandBeans.size() > 0) {
            mBrandBeans.clear();
            mBrandAdapter.notifyDataSetChanged();
        }
        MyHttpUtils.postJson(KeyConst.CacheType.OFFLINE_WORK,
                             getActivity(),
                             false,
                             Const.GET_VEHICLE_TOP_BRAND,
                             null,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                     String sharedData = mStorage.getStringSharedData(KeyConst.SharePreKey.HOT_BRAND_LIST);
                                     if (!StringUtils.isEmpty(sharedData)) {
                                         doAffterGetBrandListData(sharedData);
                                     }
                                 }

                                 @Override
                                 public void onResponse(String response, int id) {

                                     if (!StringUtils.isEmpty(response)) {
                                         doAffterGetBrandListData(response);
                                     }
                                 }

                                 @Override
                                 public void doAffterRequestCall() {
                                     isFragmentApiCall = false;
                                 }
                             });
    }

    private void doAffterGetBrandListData(String response) {
        try {
            UpDateHotBrandData upDateHotBrandData = UpDateHotBrandData.newInstance();
            upDateHotBrandData.responseStr = response;
            defaultEventBus.postSticky(upDateHotBrandData);

            if (mBrandBeans == null) {
                mBrandBeans = new ArrayList<>();
            }
            GetBrandList getBrandList = new Gson().fromJson(response, GetBrandList.class);
            if (getBrandList.topBrandPage != null && getBrandList.topBrandPage.size() > 0) {
                LogUtils.e(TAG, "topBrandPage:" + getBrandList.topBrandPage.size());
                mStorage.putStringSharedData(KeyConst.SharePreKey.HOT_BRAND_LIST, response);
                mBrandBeans.addAll(getBrandList.topBrandPage);
                mBrandAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {

        }
    }


    @Override
    public void refreshDatas() {

    }


    private void getRecommendListDatas() {
        CarModelListRequestBean requestBean = new CarModelListRequestBean();
        isFragmentApiCall = true;
        GetRecommendListProtocol recommendListProtocol = new GetRecommendListProtocol(getActivity(),
                                                                                      requestBean)
        {

            @Override
            public void doOnSusses(ArrayList<CarDetailBean> dataBean, int id) {
                if (mRefreshLayout != null) {
                    mRefreshLayout.finishRefresh(true);//传入false表示刷新失败
                }
                if (dataBean != null) {
                    mRecommendDataBeans = dataBean;
                    mBannerRecommend.update(mRecommendDataBeans);

                }
            }

            @Override
            public void doOnError(Exception e, int id) {
                if (mRefreshLayout != null) {
                    mRefreshLayout.finishRefresh(false);//传入false表示刷新失败
                }
            }

            @Override
            public void doAffertRequest() {
                hideLoading();
                isFragmentApiCall = false;
                if (mRefreshLayout != null) {
                    mRefreshLayout.finishRefresh(1000);//传入false表示刷新失败
                }
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
