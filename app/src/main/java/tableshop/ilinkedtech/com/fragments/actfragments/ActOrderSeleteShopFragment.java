package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @文件名:   ActOrderSeleteShopFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/12/25 16:22
 *  @描述：    TODO 选店页面 集成地图
 */

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.ShopListAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.LocationEntity;
import tableshop.ilinkedtech.com.beans.ShopListBean;
import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;
import tableshop.ilinkedtech.com.beans.reques.OrderRequestBean;
import tableshop.ilinkedtech.com.beans.reques.OrderSeleteShopRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetShopListResponBean;
import tableshop.ilinkedtech.com.beans.responbeans.LoginMenberResponBean;
import tableshop.ilinkedtech.com.beans.responbeans.SeleteShopResponBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.servies.LocationService;
import tableshop.ilinkedtech.com.servies.Utils;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.recyles.RecycleViewDivider;
@SuppressLint("ValidFragment")
public class ActOrderSeleteShopFragment
        extends IShopBaseFragment
        implements View.OnClickListener
{
    OrderListItemBean mOrderListDataBean;
    @BindView(R.id.bmapView)
    MapView      mMapView;
    @BindView(R.id.recy)
    RecyclerView mRecy;
    @BindView(R.id.tv_test_local)
    TextView     mTvTestLocal;
    private BaiduMap        mBaiduMap;
    private LocationService locService;
    private LinkedList<LocationEntity> locationList = new LinkedList<LocationEntity>(); // 存放历史定位结果的链表，最大存放当前结果的前5次定位结果
    private LocationClient mLocClient;
    private ArrayList<ShopListBean> mListBeans;
    private ShopListAdapter mAdapter;
    public double latitude=22.529173d;
    public double longitude=113.942272d;

    public ActOrderSeleteShopFragment(OrderListItemBean json) {
        this.mOrderListDataBean = json;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_test_local:
                BDLocation local=new BDLocation();
                latitude=latitude+0.01d;
                longitude=longitude+0.01d;
                local.setLatitude(latitude);
                local.setLongitude(longitude);
                reSetLocationAlgorithm(local);
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_order_selete_shop;
    }

    @Override
    protected void initView() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));

        // 定位初始化
        //        mLocClient = new LocationClient(getContext());
        //        mLocClient.registerLocationListener(listener);
        //        LocationClientOption option = new LocationClientOption();
        //        option.setOpenGps(true); // 打开gps
        //        option.setCoorType("bd09ll"); // 设置坐标类型
        //        option.setScanSpan(1000);
        //        mLocClient.setLocOption(option);
        //        mLocClient.start();

        locService = ((MainApp) getActivity().getApplication()).locationService;
        LocationClientOption mOption = locService.getDefaultLocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        mOption.setCoorType("bd09ll");
        locService.setLocationOption(mOption);
        locService.registerListener(listener);
        locService.start();
    }

    @Override
    public void initEvent() {
        mTvTestLocal.setOnClickListener(this);
        mListBeans = new ArrayList<>();
        mAdapter = new ShopListAdapter(mListBeans, this);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        mRecy.setLayoutManager(manager);
        mRecy.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        mRecy.setAdapter(mAdapter);
    }

    @Override
    public void refreshDatas() {
        gotoGetShopList();
    }

    private void gotoGetShopList() {
        OrderRequestBean requestBean =new OrderRequestBean();
        requestBean.latitude=latitude+"";
        requestBean.longitude=longitude+"";
        String json=new Gson().toJson(requestBean);
        MyHttpUtils.postJson(0,getActivity(),true,Const.GET_SHOP_LIST, null, json, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
                if (id==MyHttpUtils.INVALID_TOKEN){
                    AlertUtils.showErrorDialog(getActivity(),
                                               UIUtils.getString(R.string.登录超时请重新登录));
                }
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    GetShopListResponBean getShopListResponBean =new Gson().fromJson(response,GetShopListResponBean.class);
                    if (getShopListResponBean!=null&&getShopListResponBean.shopList!=null){
                        mListBeans.addAll(getShopListResponBean.shopList);
                        mAdapter.notifyDataSetChanged();

                    }

                }catch (Exception e){
                    SharedStorage.mIsLogin=false;
                    EventBus.getDefault().post(new LoginMenberResponBean());
                }

            }

            @Override
            public void doAffterRequestCall() {

            }
        });
    }


    /***
     * 定位结果回调，在此方法中处理定位结果
     */
    BDAbstractLocationListener listener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub

            if (location != null && (location.getLocType() == 161 || location.getLocType() == 66)) {
                reSetLocationAlgorithm(location);
            }
        }

    };

    public void reSetLocationAlgorithm(BDLocation location) {
        Message locMsg = locHander.obtainMessage();
        Bundle  locData;
        locData = Algorithm(location);
        if (locData != null) {
            locData.putParcelable("loc", location);
            locMsg.setData(locData);
            locHander.sendMessage(locMsg);
        }
    }


    public void goToSeleteShop(String tabSysShopId){
        if (!isFragmentApiCall&&!StringUtils.isEmpty(tabSysShopId)&&!StringUtils.isEmpty(mOrderListDataBean.vehicleSalesUid)) {
            OrderSeleteShopRequestBean getShopListResponBean = new OrderSeleteShopRequestBean();
            OrderRequestBean           orderRequestBean      = new OrderRequestBean();
            orderRequestBean.tabSysShopId = tabSysShopId;
            orderRequestBean.vehicleSalesUid = mOrderListDataBean.vehicleSalesUid;
            ArrayList<OrderRequestBean> arrayList =new ArrayList<>();
            arrayList.add(orderRequestBean);
            getShopListResponBean.datas=arrayList;
            String json=new Gson().toJson(getShopListResponBean);
            isFragmentApiCall=true;
            MyHttpUtils.postJson(0, getActivity(), true, Const.SAVE_SALES_AREA, null, json, new MysalesCallBack() {
                @Override
                public void onError(Exception e, int id) {
                    if (id==MyHttpUtils.INVALID_TOKEN){
                        SharedStorage.mIsLogin=false;
                        EventBus.getDefault().post(new LoginMenberResponBean());
                        AlertUtils.showErrorDialog(getActivity(),
                                                   UIUtils.getString(R.string.登录超时请重新登录));
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    if (!StringUtils.isEmpty(response)){
                        try {
                            SeleteShopResponBean responBean =new Gson().fromJson(response,SeleteShopResponBean.class);
                            if (responBean!=null){
                                if (responBean.status.equals("1")){
                                    AlertUtils.showErrorDialog(getContext(),
                                                               UIUtils.getString(R.string.保存成功),
                                                               new DialogInterface.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(DialogInterface dialog,
                                                                                       int which)
                                                                   {
                                                                       getActivity().finish();
                                                                       UIUtils.activityBackToMain(getActivity());
                                                                   }
                                                               });
                                }else {
                                    AlertUtils.showErrorDialog(getContext(),UIUtils.getString(R.string.保存失败));
                                }
                            }
                        }catch (Exception e){
                            AlertUtils.showErrorDialog(getContext(),UIUtils.getString(R.string.系统出错));
                        }

                    }

                }

                @Override
                public void doAffterRequestCall() {
                    isFragmentApiCall=false;
                }
            });
        }

    }


    /***
     * 平滑策略代码实现方法，主要通过对新定位和历史定位结果进行速度评分，
     * 来判断新定位结果的抖动幅度，如果超过经验值，则判定为过大抖动，进行平滑处理,若速度过快，
     * 则推测有可能是由于运动速度本身造成的，则不进行低速平滑处理 ╭(●｀∀´●)╯
     *
     * @param location
     * @return Bundle
     */
    private Bundle Algorithm(BDLocation location) {
        Bundle locData  = new Bundle();
        double curSpeed = 0;
        if (locationList.isEmpty() || locationList.size() < 2) {
            LocationEntity temp = new LocationEntity();
            temp.location = location;
            temp.time = System.currentTimeMillis();
            locData.putInt("iscalculate", 0);
            locationList.add(temp);
        } else {
            if (locationList.size() > mListBeans.size()) { locationList.removeFirst(); }
            double score = 0;
            for (int i = 0; i < locationList.size(); ++i) {
                LatLng lastPoint = new LatLng(locationList.get(i).location.getLatitude(),
                                              locationList.get(i).location.getLongitude());
                LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
                double distance = DistanceUtil.getDistance(lastPoint, curPoint);
                curSpeed = distance / (System.currentTimeMillis() - locationList.get(i).time) / 1000;
                score += curSpeed * Utils.EARTH_WEIGHT[i];
            }
            if (score > 0.00000999 && score < 0.00005) { // 经验值,开发者可根据业务自行调整，也可以不使用这种算法
                location.setLongitude((locationList.get(locationList.size() - 1).location.getLongitude() + location.getLongitude()) / 2);
                location.setLatitude((locationList.get(locationList.size() - 1).location.getLatitude() + location.getLatitude()) / 2);
                locData.putInt("iscalculate", 1);
            } else {
                locData.putInt("iscalculate", 0);
            }
            LocationEntity newLocation = new LocationEntity();
            newLocation.location = location;
            newLocation.time = System.currentTimeMillis();
            locationList.add(newLocation);

        }
        return locData;
    }

    /***
     * 接收定位结果消息，并显示在地图上
     */
    private Handler locHander = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            try {
                BDLocation location = msg.getData()
                                         .getParcelable("loc");
                int        iscal    = msg.getData()
                                         .getInt("iscalculate");
                if (location != null) {
                    reSetLocation(location, iscal);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    };

    List<OverlayOptions> overlayList=new ArrayList<>();
    private void reSetLocation(BDLocation location, int iscal) {
        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
        // 构建Marker图标
        BitmapDescriptor bitmap = null;
        if (iscal == 0) {
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark); // 非推算结果
        } else {
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_focuse_mark); // 推算结果
        }

        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point)
                                                   .icon(bitmap);
        // 在地图上添加Marker，并显示
//        mBaiduMap.addOverlay(option);
        overlayList.add(option);
        mBaiduMap.addOverlays(overlayList);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
    }

    @Override
    public void onDestroy() {
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locService.unregisterListener(listener);
        locService.stop();
        mMapView.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();

    }


}
