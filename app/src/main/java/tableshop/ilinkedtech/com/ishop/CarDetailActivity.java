package tableshop.ilinkedtech.com.ishop;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com
 *  @文件名:   CarDetailActivity
 *  @创建者:   Derek
 *  @创建时间:  2017/7/11 11:32
 *  @描述：    车辆详情界面
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.CarDetailListAdapter;
import tableshop.ilinkedtech.com.adapters.ShowStringTagAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseActivity;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.beans.reques.ObjRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.SearchResponBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.DBJsonHelper;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.loader.GlideImageLoader;
import tableshop.ilinkedtech.com.protocols.GetSearchListProtocolForObj;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.ShareUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.TimeUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;
import tableshop.ilinkedtech.com.views.dialogs.RxDialogShapeLoading;
import tableshop.ilinkedtech.com.views.jzvd.JZVideoPlayer;
import tableshop.ilinkedtech.com.views.jzvd.JZVideoPlayerStandard;
import tableshop.ilinkedtech.com.views.recyclegallery.CardScaleHelper;
import tableshop.ilinkedtech.com.wxapi.WXPayEntryActivity;


public class CarDetailActivity
        extends IShopBaseActivity
        implements View.OnClickListener
{
    private static final String TAG = "CarDetailActivity";
    @BindView(R.id.iv_favorite)
    ImageView             mIvFavorite;
    @BindView(R.id.toolbar)
    Toolbar               mToolbar;
    @BindView(R.id.tv_brand)
    TextView              mTvBrand;
    @BindView(R.id.tv_series)
    TextView              mTvSeries;
    @BindView(R.id.iv_bodyColor)
    ImageView             mIvBodyColor;
    @BindView(R.id.iv_interiorColor)
    ImageView             mIvInteriorColor;
    @BindView(R.id.tv_size)
    TextView              mTvSize;
    @BindView(R.id.tv_seat)
    TextView              mTvSeat;
    @BindView(R.id.tv_door)
    TextView              mTvDoor;
    @BindView(R.id.tv_fuel)
    TextView              mTvFuel;
    @BindView(R.id.tv_tanmittion)
    TextView              mTvTanmittion;
    @BindView(R.id.tv_engine)
    TextView              mTvEngine;
    @BindView(R.id.tv_motor)
    TextView              mTvMotor;
    @BindView(R.id.tv_drive)
    TextView              mTvDrive;
    @BindView(R.id.recy)
    RecyclerView          mRecy;
    @BindView(R.id.btn_add_to_compare)
    Button                mBtnAddToCompare;
    @BindView(R.id.btn_buy)
    Button                mBtnBuy;
    @BindView(R.id.tv_configure_holder)
    TextView              mTvConfigureHolder;
    @BindView(R.id.tv_outside_holder)
    TextView              mTvOutsideHolder;
    @BindView(R.id.tv_inside_holder)
    TextView              mTvInsideHolder;
    @BindView(R.id.tv_more_interest_holder)
    TextView              mTvMoreInterestHolder;
    @BindView(R.id.tv_price)
    TextView              mTvPrice;
    @BindView(R.id.iv_default)
    ImageView             mIvDefault;
    @BindView(R.id.tv_model_name)
    TextView              mTvModelName;
    @BindView(R.id.banner_outside)
    Banner                mBannerOutside;
    @BindView(R.id.banner_interie)
    Banner                mBannerInterie;
    @BindView(R.id.tv_car_stye)
    TextView              mTvCarStye;
    @BindView(R.id.tv_outside_corlor_name)
    TextView              mTvOutsideCorlorName;
    @BindView(R.id.tv_inside_corlor_name)
    TextView              mTvInsideCorlorName;
    @BindView(R.id.tv_video_holder)
    TextView              mTvVideoHolder;
    @BindView(R.id.iv_share)
    ImageView             mIvShare;
    @BindView(R.id.tv_oil_supply)
    TextView              mTvOilSupply;
    @BindView(R.id.tv_hub_size)
    TextView              mTvHubSize;
    @BindView(R.id.tv_tank_capacity)
    TextView              mTvTankCapacity;
    @BindView(R.id.tv_configure)
    TextView              mTvConfigure;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout    mRefreshLayout;
    @BindView(R.id.tv_create_time)
    TextView              mTvCreateTime;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard mVideoplayer;
    @BindView(R.id.tv_supplier_id)
    TextView              mTvSupplierId;
    @BindView(R.id.ll_bottom_layout)
    LinearLayout          mLlBottomLayout;
    private ArrayList<CarDetailBean>  mArrayList;
    private CarDetailListAdapter      mAdapter;
    private CarDetailBean             mCarDetailBean;
    private ArrayList<CarDetailBean>  constrastList;
    private List<String>              mInsideImg;
    private List<String>              mOutsideImg;
    private List<String>              mShowBannerImg;
    private LinearLayout.LayoutParams mImageViewLP;
    private LinearLayout.LayoutParams mTextViewLP;
    private ArrayList<String>         mTagItems;
    private ShowStringTagAdapter      mTagAdapter;


    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI               api;
    private CardScaleHelper      mCardScaleHelper;
    private RxDialogShapeLoading mRxDialogShapeLoading;
    private String               mVehicleUid;
    private String               vehicleFlag;


    @Override
    public int getLayoutViewId() {
        return R.layout.car_detail_activity_new;
    }


    @Override
    public void initView() {

        mBtnAddToCompare.setOnClickListener(this);
        mBtnBuy.setOnClickListener(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                UIUtils.activityBackToMain(CarDetailActivity.this);
            }
        });
        mTextViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
        mTextViewLP.setMargins(15, 0, 0, 0);
        mImageViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                     ViewGroup.LayoutParams.WRAP_CONTENT);
        mImageViewLP.setMargins(10, 5, 10, 5);

        mShowBannerImg = new ArrayList<>();

        mVehicleUid = getIntent().getStringExtra(KeyConst.BundleIntentKey.VEHICLE_ID);
        vehicleFlag = getIntent().getStringExtra(KeyConst.BundleIntentKey.VEHICLE_FLAG);
        String basehost = getIntent().getStringExtra(KeyConst.BundleIntentKey.BASE_HOST);

        if (!StringUtils.isEmpty(basehost)) {
            Const.BASE_HOST = Const.getBaseUrl(basehost) + Const.PROJECK_NAME;
            mLlBottomLayout.setVisibility(View.GONE);
        }

        defaultEventBus.register(this);
        mIvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCarDetailBean != null) {
                    mCarDetailBean.isFavorite = !mCarDetailBean.isFavorite;
                    view.setActivated(mCarDetailBean.isFavorite);
                    DBJsonHelper.upDateFavoriteList(mCarDetailBean,
                                                    mCarDetailBean.isFavorite
                                                    ? KeyConst.LocalData.ORIENTATION_ADD
                                                    : KeyConst.LocalData.ORIENTATION_DEL);
                }
            }
        });
    }




    @Override
    public void initEvent() {
        mRefreshLayout.setEnableOverScrollDrag(true);
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                callToGetCarDetail();
            }
        });

        mBannerInterie.setImageLoader(new GlideImageLoader());
        mBannerOutside.setImageLoader(new GlideImageLoader());
        mBannerInterie.setDelayTime(5000);
        mBannerOutside.setDelayTime(6000);
        mBannerOutside.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showOutsidePictures(position);
            }
        });
        mBannerInterie.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showInteriePictures(position);
            }
        });

        if (!StringUtils.isEmpty(mVehicleUid)) {
            LogUtils.i(TAG, "-----mVehicleUid:" + mVehicleUid);
            mRefreshLayout.autoRefresh();
        } else {
            String json = getIntent().getStringExtra(KeyConst.BundleIntentKey.DATA_JSON);
            try {
                mCarDetailBean = new Gson().fromJson(json, CarDetailBean.class);
                refreshViewWithResponse();
            } catch (Exception e) {

            }
        }
        mBtnBuy.setEnabled(false);

    }

    //    @OnClick(R.id.banner_interie)
    public void showInteriePictures(int position) {
        goToPictureView(mInsideImg, position);
    }

    //    @OnClick(R.id.banner_outside)
    public void showOutsidePictures(int position) {
        goToPictureView(mOutsideImg, position);
    }


    public void goToPictureView(List<String> datas, int position) {
        PictureViewActivity.startPictureViewAct(this, (ArrayList<String>) datas, position);
    }


    @OnClick(R.id.iv_share)
    public void shareToWx() {
        if (mCarDetailBean != null) {
            String msg = StringUtils.checkString(mCarDetailBean.modelName);
            String carName = StringUtils.checkString(mCarDetailBean.brandName) + " | " + StringUtils.checkString(
                    mCarDetailBean.seriesName) + " | " + StringUtils.checkString(mCarDetailBean.modelName);
            ShareUtils.showShareListViewNew(this,
                                            carName,
                                            mCarDetailBean.vehicleFeatures,
                                            mCarDetailBean.seriesDefaultImageURL,
                                            Const.WEB_CAR_DETAIL_URL,
                                            mVehicleUid);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean) {
        if (payResultBean.resultState == PayResultBean.TYPE_SUSSECE) {
            mBtnBuy.setEnabled(false);
        }
    }


    private void refreshViewWithResponse() {
        ArrayList<CarDetailBean> favoriteList = DBJsonHelper.getFavoriteList();
        if (favoriteList != null) {
            mIvFavorite.setActivated(favoriteList.contains(mCarDetailBean));
        }
        if (constrastList == null || constrastList.size() == 0) {
            constrastList = DBJsonHelper.getContrastList();
        }
        if (mCarDetailBean != null) {
            initData();
            refreshView();
            addToCompareFlag = constrastList.contains(mCarDetailBean);
            refrestCompareButton();
        }
    }


    private void iniVideo() {
        mVideoplayer.setUp(mCarDetailBean.seriesShowVideoURL, JZVideoPlayer.SCREEN_WINDOW_NORMAL);
        Bitmap firstBitmap = ImageLoadUtils.getVideoFirstBitmap(mCarDetailBean.seriesShowVideoURL);
        if (firstBitmap != null) {
            mVideoplayer.thumbImageView.setImageBitmap(firstBitmap);
        } else {
            ImageLoadUtils.setImageSource(this,
                                          mCarDetailBean.seriesDefaultImageURL,
                                          mVideoplayer.thumbImageView,
                                          null);
        }


    }

    private void refreshView() {

        if (StringUtils.isEmpty(mCarDetailBean.flag) || mCarDetailBean.flag.equals(KeyConst.VehicleSellableState.ON_SALES_VEHICLE + "")) {
            mBtnBuy.setEnabled(true);
        } else {
            mBtnBuy.setEnabled(false);
        }

        mInsideImg = new ArrayList<>();
        mOutsideImg = new ArrayList<>();
        if (mCarDetailBean.insideSmallImgUrl != null) {
            mInsideImg.addAll(mCarDetailBean.insideSmallImgUrl);
        }
        if (mCarDetailBean.outsideSmallImgUrl != null) {
            mOutsideImg.addAll(mCarDetailBean.outsideSmallImgUrl);
        }
        if (mInsideImg != null && mInsideImg.size() > 0) {
            mTvInsideHolder.setVisibility(View.VISIBLE);
            mBannerInterie.setVisibility(View.VISIBLE);
            mBannerInterie.setImages(mInsideImg);
            //banner设置方法全部调用完毕时最后调用
            mBannerInterie.start();
        } else {
            mTvInsideHolder.setVisibility(View.GONE);
            mBannerInterie.setVisibility(View.GONE);
        }
        if (mOutsideImg != null && mOutsideImg.size() > 0) {
            mTvOutsideHolder.setVisibility(View.VISIBLE);
            mBannerOutside.setVisibility(View.VISIBLE);
            mBannerOutside.setImages(mOutsideImg);
            //banner设置方法全部调用完毕时最后调用
            mBannerOutside.start();
        } else {
            mTvOutsideHolder.setVisibility(View.GONE);
            mBannerOutside.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(mCarDetailBean.seriesDefaultImageURL)) {
            ImageLoadUtils.into(mIvDefault, mCarDetailBean.seriesDefaultImageURL);
        }
        if (!StringUtils.isEmpty(mCarDetailBean.vehicleFeatures)) {
            mTvConfigureHolder.setVisibility(View.VISIBLE);
            mTvConfigure.setVisibility(View.VISIBLE);
            mTvConfigure.setText(mCarDetailBean.vehicleFeatures);
        } else {
            mTvConfigureHolder.setVisibility(View.GONE);
            mTvConfigure.setVisibility(View.GONE);
        }
        mTvCreateTime.setText(TimeUtil.getTime(mCarDetailBean.createAt, TimeUtil.YYYY_MM_DD));
        mTvPrice.setText(mCarDetailBean.price + getResources().getString(R.string.万元));
        mTvBrand.setText(StringUtils.checkString(mCarDetailBean.brandName));
        mTvSeries.setText(" | " + StringUtils.checkString(mCarDetailBean.seriesName));
        mTvModelName.setText(StringUtils.checkString(mCarDetailBean.modelName));
        //        String carSize = "L:" + mCarDetailBean.bodyLength + "\n" + "W:" + mCarDetailBean.bodyWidth + "\n" + "H:" + mCarDetailBean.bodyHeight;
        String carSize = StringUtils.checkString(mCarDetailBean.lengthWidthHeight);
        mTvSize.setText(carSize);//长宽高
        mTvSeat.setText(StringUtils.checkString(mCarDetailBean.noOfSeat + ""));//座位
        mTvDoor.setText(StringUtils.checkString(mCarDetailBean.noOfDoors + ""));//车门
        mTvFuel.setText(StringUtils.checkString(mCarDetailBean.fuseSource));//燃油
        mTvTanmittion.setText(StringUtils.checkString(mCarDetailBean.gearBox));//变速
        mTvEngine.setText(StringUtils.checkString(mCarDetailBean.horsePower));//马力
        mTvDrive.setText(StringUtils.checkString(mCarDetailBean.engineType));//驱动
        mTvMotor.setText(StringUtils.checkString(mCarDetailBean.engineLayout));//排量
        mTvCarStye.setText(StringUtils.checkString(mCarDetailBean.category));//车型
        mTvOilSupply.setText(StringUtils.checkString(mCarDetailBean.oilSupply));//供油方式
        mTvHubSize.setText(StringUtils.checkString(mCarDetailBean.hubSize));//轮毂尺寸
        mTvTankCapacity.setText(StringUtils.checkString(mCarDetailBean.tankCapacity));//油箱容量
        mTvSupplierId.setText(StringUtils.checkString(mCarDetailBean.supplierIdentityNo));//供应商编号

        mTvOutsideCorlorName.setText("外 ：" + StringUtils.checkString(mCarDetailBean.bodyColorName));//车身颜色名字
        mTvInsideCorlorName.setText("内 ：" + StringUtils.checkString(mCarDetailBean.interiorColorName + ""));//内饰颜色名字

        Glide.with(UIUtils.getContext())
             .load(mCarDetailBean.interiorColorUrl)
             .into(mIvInteriorColor);

        Glide.with(UIUtils.getContext())
             .load(mCarDetailBean.bodyColorUrl)
             .into(mIvBodyColor);

        ImageLoadUtils.getImageFile(mCarDetailBean.seriesDefaultImageURL, null);
        //====================TODO text begin=========================
        //        ArrayList<String> arrObjKeyName = StringUtils.getArrObjKeyName(mCarDetailBean.feature);
        //        ArrayList<String> arrObjKeyName = new ArrayList<>();
        //        arrObjKeyName.add(StringUtils.checkString(mCarDetailBean.vehicleFeatures));
        //        //====================TODO text end===========================
        //        if (arrObjKeyName.size() > 0) {
        //            mTagItems.addAll(arrObjKeyName);
        //            mTagAdapter.notifyDataChanged();
        //        } else {
        //            mTvConfigure Holder.setVisibility(View.GONE);
        //        }

        if (!StringUtils.isEmpty(mCarDetailBean.seriesShowVideoURL)) {
            mTvVideoHolder.setVisibility(View.VISIBLE);
            //            mJcVideo.setVisibility(View.VISIBLE);
            mVideoplayer.setVisibility(View.VISIBLE);
            iniVideo();
        } else {
            mTvVideoHolder.setVisibility(View.GONE);
            //            mJcVideo.setVisibility(View.GONE);
            mVideoplayer.setVisibility(View.GONE);
        }
    }

    private void initData() {
        mArrayList = new ArrayList<>();
        mAdapter = new CarDetailListAdapter(mArrayList,
                                            this,
                                            CarDetailListAdapter.CAR_DETAIL_SEARCH_VIEW);
        if (mRecy.getWidth() > 0) {
            mAdapter.recyleWidth = mRecy.getWidth();
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(UIUtils.getContext(), 1);
        gridLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        gridLayoutManager.setInitialPrefetchItemCount(10);
        gridLayoutManager.setItemPrefetchEnabled(true);
        //        mRecy.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 2));
        mRecy.setLayoutManager(gridLayoutManager);

        mRecy.setAdapter(mAdapter);

        ListRequestBean carModelListRequestBean = new ListRequestBean();
        double          parsePrice              = Double.parseDouble(mCarDetailBean.price);
        double          temp                    = parsePrice * 0.2;
        //        carModelListRequestBean.lowestPrice = getRealPrice(mCarDetailBean.price - temp) + "";
        //        carModelListRequestBean.highestPrice = getRealPrice(mCarDetailBean.price + temp) + "";
        carModelListRequestBean.lowestPrice = (int) (parsePrice - temp) + "";
        carModelListRequestBean.highestPrice = (int) (parsePrice + temp) + "";

        GetSearchListProtocolForObj getSearchListProtocol = new GetSearchListProtocolForObj(this,
                                                                                            carModelListRequestBean)
        {

            @Override
            protected void doOnSusses(SearchResponBean dataBean, int id) {
                if (dataBean != null && dataBean.vehicleStockSingleViewList != null && dataBean.vehicleStockSingleViewList.size() > 0) {
                    if (mArrayList != null) {
                        //                        mArrayList.clear();
                        if (!mArrayList.containsAll(dataBean.vehicleStockSingleViewList)) {
                            mArrayList.addAll(dataBean.vehicleStockSingleViewList);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void doOnError(Exception e, int id) {

            }

            @Override
            public void doAffertRequest() {
                hideLoading();
            }
        };

    }


    @Override
    protected void onResume() {
        super.onResume();
        WXPayEntryActivity.isPayCalling = false;

    }

    private void callToGetCarDetail() {
        ObjRequestBean objRequestBean = new ObjRequestBean();
        objRequestBean.vehicleId = mVehicleUid;
        if (!StringUtils.isEmpty(vehicleFlag)) {
            objRequestBean.flag = vehicleFlag;
        }
        String json = new Gson().toJson(objRequestBean);
        //        showLoading();
        MyHttpUtils.postJson(Const.GET_VEHICLE_DETAILS_BY_UID, null, json, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {

                if (mRefreshLayout != null) { mRefreshLayout.finishRefresh(false); }
            }

            @Override
            public void onResponse(String response, int id) {
                if (mRefreshLayout != null) { mRefreshLayout.finishRefresh(true); }
                try {
                    if (!StringUtils.isEmpty(response)) {
                        ArrayList<CarDetailBean> carDetailBeans = StringUtils.jsonToArrayObj(
                                response,
                                CarDetailBean.class);
                        if (carDetailBeans != null && carDetailBeans.size() > 0) {
                            mCarDetailBean = carDetailBeans.get(0);
                            refreshViewWithResponse();
                        }
                    }
                } catch (Exception e) {
                    showSnackbar(UIUtils.getString(R.string.系统出错));
                }

            }

            @Override
            public void doAffterRequestCall() {
                hideLoading();
            }
        });
    }


    boolean addToCompareFlag = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_to_compare:
                //                showTestDialog();
                addToCompareFlag = !addToCompareFlag;
                showSnackbar(mRecy,
                             addToCompareFlag
                             ? getString(R.string.已加入对比)
                             : getString(R.string.已取消对比));
                refrestCompareButton();
                break;
            case R.id.btn_buy:
                gotoPayDetailAct();
                break;
        }
    }

    private void showTestDialog() {
        View view = View.inflate(this, R.layout.view_input_dialog, null);
        AlertUtils.showViewDialog(this, true, view, null);
    }

    private void gotoPayDetailAct() {
        if (SharedStorage.mIsLogin) {
            Intent intent = new Intent(this, SubmitOrderActivity.class);
            intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, new Gson().toJson(mCarDetailBean));
            startActivity(intent);
            UIUtils.activityAnimInt(this);
        } else {
            //TODO 未登录请先登录
            AlertUtils.showCancleableErrorDialog(this,
                                                 UIUtils.getString(R.string.您好请先登录),
                                                 UIUtils.getString(R.string.确定),
                                                 UIUtils.getString(R.string.取消),
                                                 new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog,
                                                                         int which)
                                                     {
                                                         Intent intent = new Intent(
                                                                 CarDetailActivity.this,
                                                                 LoginActivity.class);
                                                         startActivity(intent);
                                                         UIUtils.activityAnimInt(CarDetailActivity.this);
                                                     }
                                                 });
        }
    }


    private void refrestCompareButton() {
        if (addToCompareFlag) { //已加入对比 ，点击则 已取消对比
            mBtnAddToCompare.setText(getString(R.string.取消对比));
            DBJsonHelper.upDateContrastList(mCarDetailBean, KeyConst.LocalData.ORIENTATION_ADD);
        } else {
            mBtnAddToCompare.setText(getString(R.string.加入对比));
            DBJsonHelper.upDateContrastList(mCarDetailBean, KeyConst.LocalData.ORIENTATION_DEL);
        }
    }


    @Override
    public void onBackPressed() {
        //        if (JCVideoPlayer.backPress()) {
        //            return;
        //        }
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //        JCVideoPlayer.releaseAllVideos();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
