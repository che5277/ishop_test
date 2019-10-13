package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   OrderSeleteShopActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO 选店
 */

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.ShopListAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseActivity;
import tableshop.ilinkedtech.com.beans.ShopListBean;
import tableshop.ilinkedtech.com.beans.events.SeleteShopMsgBean;
import tableshop.ilinkedtech.com.beans.events.SeletedShop;
import tableshop.ilinkedtech.com.beans.main.OrderListItemBean;
import tableshop.ilinkedtech.com.beans.reques.OrderRequestBean;
import tableshop.ilinkedtech.com.beans.reques.OrderSeleteShopRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetShopListResponBean;
import tableshop.ilinkedtech.com.beans.responbeans.SeleteShopResponBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.map.GPSUtil;
import tableshop.ilinkedtech.com.utils.map.SensorEventHelper;
import tableshop.ilinkedtech.com.views.recyles.RecycleViewDivider;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class OrderSeleteShopAmapAct
        extends IShopBaseActivity
        implements LocationSource, AMapLocationListener, AMap.OnMyLocationChangeListener
{

    @BindView(R.id.map)
    MapView      mapView;
    @BindView(R.id.recy)
    RecyclerView mRecy;
    private AMap                      aMap;
    private MarkerOptions             markerOption;
    private SensorEventHelper         mSensorHelper;
    private OnLocationChangedListener mListener;
    private AMapLocationClient        mlocationClient;
    private AMapLocationClientOption  mLocationOption;
    private boolean                   mFirstFix;
    private Circle                    mCircle;
    private Marker                    mLocMarker;
    private MyLocationStyle           myLocationStyle;

    private ArrayList<ShopListBean> mListBeans;
    private ShopListAdapter         mAdapter;
    private OrderListItemBean       mOrderListDataBean;
    private List<Marker>     mMarkers;
    private UiSettings              mUiSettings;

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_map_amap;
    }

    @Override
    public void initView() {
//        mTvToolbarTitle.setText(UIUtils.getString(R.string.选择店面));
        mOrderListDataBean = getIntent().getParcelableExtra(KeyConst.BundleIntentKey.DATA_JSON);
        if (mOrderListDataBean==null) {
            mOrderListDataBean = new OrderListItemBean();
            mOrderListDataBean.vehicleSalesUid = "aabb";
        }

        OrderSeleteShopAmapActPermissionsDispatcher.onPermissionNeedsWithPermissionCheck(this);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mListBeans = new ArrayList<>();
        mAdapter = new ShopListAdapter(mListBeans);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        mRecy.setLayoutManager(manager);
        mRecy.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecy.setAdapter(mAdapter);
        defaultEventBus.register(this);
    }

    /**
     * 初始化AMap对象
     */
    private void initAmap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
//        22.5227210000,113.9357220000  软件基地高德坐标
        LatLng localLatLng=new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localLatLng,18));
        mSensorHelper = new SensorEventHelper(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);// 设置地图logo显示在右下方
        mUiSettings.setCompassEnabled(false);//设置地图默认的指南针是否显示
        mUiSettings.setZoomControlsEnabled(false);//设置地图默认的缩放按钮是否显示
        mUiSettings.setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        // 如果要设置定位的默认状态，可以在此处进行设置
//        myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_default));
//        // 设置定位的类型为 跟随模式
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW));

        //设置SDK 自带定位消息监听
//        aMap.setOnMyLocationChangeListener(this);


        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (aMap != null) {
                    jumpPoint(marker);
                }
                return true;
            }
        });

    }

    public  double latitude  = 22.5227210000d;
    public  double longitude = 113.9358937740d;
    private LatLng latlng    = null;

    private int[] markers = {R.drawable.poi_marker_1,
                             R.drawable.poi_marker_2,
                             R.drawable.poi_marker_3,
                             R.drawable.poi_marker_4,
                             R.drawable.poi_marker_5,
                             R.drawable.poi_marker_6,
                             R.drawable.poi_marker_7,
                             R.drawable.poi_marker_8,
                             R.drawable.poi_marker_9,
                             R.drawable.poi_marker_10
    };

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {

        mMarkers = new ArrayList<>();
        ShopListBean        shopListBean =null;
        Bitmap bMap = null;
        BitmapDescriptor des =null;

        double tempLat,temLong;
        for (int i = 0; i < mListBeans.size(); i++) {
            bMap = BitmapFactory.decodeResource(this.getResources(),
                                                markers[i]);
            des = BitmapDescriptorFactory.fromBitmap(bMap);
            shopListBean = mListBeans.get(i);
            tempLat=Double.parseDouble(shopListBean.locationY);
            temLong=Double.parseDouble(shopListBean.locationX);

            latlng = new LatLng(tempLat, temLong);
            CoordinateConverter.CoordType mcoordtype = CoordinateConverter.CoordType.valueOf(CoordinateConverter.CoordType.GPS.name());
            latlng = GPSUtil.convert(latlng, mcoordtype);

            markerOption = new MarkerOptions().icon(des)
                                              .position(latlng)
                                              .title(shopListBean.shopName)
                                              .snippet(StringUtils.checkString(shopListBean.shopAddress))
                                              .draggable(true);
            Marker marker = aMap.addMarker(markerOption);
            mMarkers.add(marker);
        }

    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final LatLng markerLatlng = marker.getPosition();
//        final long   start        = SystemClock.uptimeMillis();
//        Projection   proj         = aMap.getProjection();
//        Point        markerPoint  = proj.toScreenLocation(markerLatlng);
//        markerPoint.offset(0, -10);
//        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
//        final long   duration    = 300;
//
//        final Interpolator interpolator = new BounceInterpolator();
//        MainApp.getHandler()
//               .post(new Runnable() {
//                   @Override
//                   public void run() {
//                       long elapsed = SystemClock.uptimeMillis() - start;
//                       float t = interpolator.getInterpolation((float) elapsed / duration);
//                       double lng = t * markerLatlng.longitude + (1 - t) * startLatLng.longitude;
//                       double lat = t * markerLatlng.latitude + (1 - t) * startLatLng.latitude;
//                       marker.setPosition(new LatLng(lat, lng));
//                       if (t < 1.0) {
//                           MainApp.getHandler()
//                                  .postDelayed(this, 16);
//                       }
//                   }
//               });

        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerLatlng, 18));
        marker.showInfoWindow();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSeleteShopMsgBean(SeleteShopMsgBean seleteShopMsgBean){
        if (seleteShopMsgBean!=null){
            switch (seleteShopMsgBean.msgWhat){
                case SeleteShopMsgBean.MSG_LOCATION:
                    String msg="";
                    if (aMap != null) {
                        if (mMarkers !=null) {
                            Marker marker = mMarkers.get(seleteShopMsgBean.position % mMarkers.size());
                            jumpPoint(marker);
                        }
                    }
                    break;
                case SeleteShopMsgBean.MSG_SELETE_SHOP:
                    goToSeleteShop(seleteShopMsgBean.tabSysShopId);
                    break;
            }
        }
    }



    private void gotoGetShopList() {
        OrderRequestBean requestBean =new OrderRequestBean();
//        requestBean.latitude=latitude+"";
//        requestBean.longitude=longitude+"";
        double[] doubles = GPSUtil.gcj02_To_Gps84(latitude, longitude);
        requestBean.latitude=doubles[0]+"";
        requestBean.longitude=doubles[1]+"";
        String json=new Gson().toJson(requestBean);
        showLoading();
        MyHttpUtils.postJson(0, this, true, Const.GET_SHOP_LIST, null, json, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                doAfterGetShopList(response);

            }

            @Override
            public void doAffterRequestCall() {
                hideLoading();
            }
        });
    }

    private void doAfterGetShopList(String response) {
        try {
            GetShopListResponBean getShopListResponBean =new Gson().fromJson(response, GetShopListResponBean.class);
            if (getShopListResponBean!=null&&getShopListResponBean.shopList!=null){
                mListBeans.addAll(getShopListResponBean.shopList);
                mAdapter.notifyDataSetChanged();
                addMarkersToMap();

            }

        }catch (Exception e){

        }
    }


    public  String testJson="{\"message\":null,\"status\":null,\"totalElements\":0,\"totalPages\":0,\"datas\":null,\"vehicleOrderDatas\":null,\"orderUid\":null,\"transactionId\":null,\"attach\":null,\"createAt\":null,\"orderQrCode\":null,\"shopList\":[{\"locationX\":\"113.93527600086298\",\"locationY\":\"22.52655323237564\",\"shopAddress\":\"深圳市南山区软件产业基地5C\",\"shopTitleUrl\":null,\"shopImageUrl\":null,\"shopOnlineTime\":null,\"distance\":\"7674272.0\",\"shopName\":\"5C展厅\",\"tabSysShopId\":\"247\"},{\"locationX\":\"113.93295016913693\",\"locationY\":\"22.527811950914238\",\"shopAddress\":\"深圳市南山区学府路95号怡化金融科技大厦15楼1508\",\"shopTitleUrl\":null,\"shopImageUrl\":null,\"shopOnlineTime\":null,\"distance\":\"7674291.0\",\"shopName\":\"办公室展厅\",\"tabSysShopId\":\"246\"},{\"locationX\":\"113.92878786121197\",\"locationY\":\"22.537482471779022\",\"shopAddress\":\"深圳市深圳大学\",\"shopTitleUrl\":null,\"shopImageUrl\":null,\"shopOnlineTime\":null,\"distance\":\"7674656.0\",\"shopName\":\"大学展厅\",\"tabSysShopId\":\"249\"},{\"locationX\":\"114.05602200419983\",\"locationY\":\"22.527567957515778\",\"shopAddress\":\"深圳市福田区福田口岸\",\"shopTitleUrl\":null,\"shopImageUrl\":null,\"shopOnlineTime\":null,\"distance\":\"7676262.0\",\"shopName\":\"口岸展厅\",\"tabSysShopId\":\"248\"}],\"totalAmount\":null}";

    public void goToSeleteShop(String tabSysShopId){
        if (!StringUtils.isEmpty(tabSysShopId)&&!StringUtils.isEmpty(mOrderListDataBean.vehicleSalesUid)) {
            OrderSeleteShopRequestBean getShopListResponBean = new OrderSeleteShopRequestBean();
            OrderRequestBean           orderRequestBean      = new OrderRequestBean();
            orderRequestBean.tabSysShopId = tabSysShopId;
            orderRequestBean.vehicleSalesUid = mOrderListDataBean.vehicleSalesUid;
            ArrayList<OrderRequestBean> arrayList =new ArrayList<>();
            arrayList.add(orderRequestBean);
            getShopListResponBean.datas=arrayList;
            String json=new Gson().toJson(getShopListResponBean);
            showLoading();
            MyHttpUtils.postJson(0, this, true, Const.SAVE_SALES_AREA, null, json, new MysalesCallBack() {
                @Override
                public void onError(Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    if (!StringUtils.isEmpty(response)){
                        try {
                            SeleteShopResponBean responBean =new Gson().fromJson(response, SeleteShopResponBean.class);
                            if (responBean!=null){
                                if (responBean.status.equals("1")){
                                    defaultEventBus.post(SeletedShop.newInstance());
                                    AlertUtils.showErrorDialog(OrderSeleteShopAmapAct.this,
                                                               UIUtils.getString(R.string.保存成功),
                                                               new DialogInterface.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(DialogInterface dialog,
                                                                                       int which)
                                                                   {
                                                                       OrderSeleteShopAmapAct.this.finish();
                                                                       UIUtils.activityBackToMain(OrderSeleteShopAmapAct.this);
                                                                   }
                                                               });
                                }else {
                                    AlertUtils.showErrorDialog(OrderSeleteShopAmapAct.this,UIUtils.getString(R.string.保存失败));
                                }
                            }
                        }catch (Exception e){
                            AlertUtils.showErrorDialog(OrderSeleteShopAmapAct.this,UIUtils.getString(R.string.系统出错));
                        }

                    }

                }

                @Override
                public void doAffterRequestCall() {
                    hideLoading();
                }
            });
        }

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
    }



    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
        mapView.onPause();

    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        deactivate();
        mapView.onDestroy();

        super.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                //                mLocationErrText.setVisibility(View.GONE);
                LatLng location = new LatLng(amapLocation.getLatitude(),
                                             amapLocation.getLongitude());
                latitude=location.latitude;
                longitude=location.longitude;
                if (!mFirstFix) {
                    mFirstFix = true;
                    gotoGetShopList();
                    addCircle(location, amapLocation.getAccuracy());//添加定位精度圆
                    addLocalMarker(location);//添加定位图标
                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                    if (aMap!=null)
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
                } else {
//                    mCircle.setRadius(amapLocation.getAccuracy());
                    mCircle.setCenter(location);
                    mLocMarker.setPosition(location);
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                ToastUtil.toast(errText);
            }
        }
    }

    @OnClick(R.id.iv_my_location)
    public void onMyLocationClike(){
        if (mLocMarker!=null) {
            LatLng latLng = mLocMarker.getPosition();
            if (aMap != null) aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (location!=null&&mLocMarker!=null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mCircle.setCenter(latLng);
            mLocMarker.setPosition(latLng);
            if (aMap != null) aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        }
    }


    private static final int    STROKE_COLOR         = Color.argb(180, 3, 145, 255);
    private static final int    FILL_COLOR           = Color.argb(10, 0, 0, 180);
    public static final  String LOCATION_MARKER_FLAG = "mylocation";

    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }

    private void addLocalMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
//        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
//                                                   R.drawable.navi_map_gps_locked);
//        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);

        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
        mLocMarker.setTitle("您的位置");
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OrderSeleteShopAmapActPermissionsDispatcher.onRequestPermissionsResult(this,
                                                                               requestCode,
                                                                               grantResults);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION,
                      Manifest.permission.ACCESS_COARSE_LOCATION,
                      Manifest.permission.READ_EXTERNAL_STORAGE,
                      Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionNeeds() {
        initAmap();
    }

    private boolean isFirstTimeCheckPermission=true;

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION,
                         Manifest.permission.ACCESS_COARSE_LOCATION,
                         Manifest.permission.READ_EXTERNAL_STORAGE,
                         Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionDenied() {
        if (isFirstTimeCheckPermission){
            isFirstTimeCheckPermission=false;
            OrderSeleteShopAmapActPermissionsDispatcher.onPermissionNeedsWithPermissionCheck(this);
        }
    }


}
