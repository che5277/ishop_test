package tableshop.ilinkedtech.com.fragments.main;

/*
 *  @文件名:   HomeFragment
 *  @创建者:   Wilson
 *  @创建时间:  2018/1/22 10:55
 *  @描述：    TODO 选车
 */

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cc.solart.turbo.OnItemClickListener;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.HotBrandListAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.events.BaseUrlChangeBean;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.events.SeriesListFilterBean;
import tableshop.ilinkedtech.com.beans.events.UpDateHotBrandData;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetBrandList;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.ishop.SeriesListActivity;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.recyles.RecycleViewOffSetDivider;
import tableshop.ilinkedtech.com.views.sidebar.AdapterContactCity;
import tableshop.ilinkedtech.com.views.sidebar.LetterComparator;
import tableshop.ilinkedtech.com.views.sidebar.PinnedHeaderDecoration;
import tableshop.ilinkedtech.com.views.sidebar.WaveSideBarView;

public class ChooseCarFragment
        extends IShopBaseFragment
{
    private static final String TAG = "ChooseCarFragment";
    @BindView(R.id.recycler_view)
    RecyclerView    mRecyclerView;
    @BindView(R.id.side_view)
    WaveSideBarView mSideBarView;
    Unbinder unbinder;
    @BindView(R.id.hot_brand_recycle)
    RecyclerView mHotBrandRecycle;
    Unbinder unbinder1;
    private AdapterContactCity       mAllBrandAdapter;
    private HotBrandListAdapter      mHotBrandAdapter;
    private List<CarDetailBean>      mValueBeans;
    private ArrayList<CarDetailBean> mHotValueBeans;
    private SharedStorage            mStorage;


    public int fromType=0;

    public static int FROM_ACT=1;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_choose_car;
    }

    @Override
    protected void initView() {
        mStorage=SharedStorage.getInstance(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);

        LinearLayoutManager hotBrandManager = new LinearLayoutManager(getContext());
        hotBrandManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHotBrandRecycle.setLayoutManager(hotBrandManager);

        RecycleViewOffSetDivider recycleViewDivider = new RecycleViewOffSetDivider();
        recycleViewDivider.setItemOffSets(0,0,10,0);
        mHotBrandRecycle.addItemDecoration(recycleViewDivider);

        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                try {
                    int pos = mAllBrandAdapter.getLetterPosition(letter);

                    if (pos != -1) {
                        mRecyclerView.scrollToPosition(pos);
                        LinearLayoutManager mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                        mLayoutManager.scrollToPositionWithOffset(pos, 0);
                    }
                }catch (Exception e){

                }

            }
        });

        defaultEventBus.register(this);

    }


    public void getDatas(){
        getBrandList();
//        getHotBrandList();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void getBrandList() {
        String json = "{}";
        isFragmentApiCall=true;
        MyHttpUtils.postJson(KeyConst.CacheType.OFFLINE_WORK,
                             getActivity(),
                             false,
                             Const.GET_VEHICLE_BRAND_CODE_ON_SALES,
                             null,
                             json,
                             new MysalesCallBack() {
                                 @Override
                                 public void onError(Exception e, int id) {
                                     String sharedData = mStorage.getStringSharedData(KeyConst.SharePreKey.ALL_BRAND_LIST);
                                     if (!StringUtils.isEmpty(sharedData)) {

                                         doAffterGetAllBrandListData(sharedData);
                                     }

                                 }

                                 @Override
                                 public void onResponse(String response, int id) {

                                     if (!StringUtils.isEmpty(response)) {

                                         doAffterGetAllBrandListData(response);
                                     }
                                 }

                                 @Override
                                 public void doAffterRequestCall() {
                                     isFragmentApiCall=false;
                                 }
                             });
    }

    private void doAffterGetAllBrandListData(String response) {
        try {
            GetBrandList getBrandList = new Gson().fromJson(
                    response,
                    GetBrandList.class);
            if (getBrandList.brandPage != null && getBrandList.brandPage.size() > 0) {
                mStorage.putStringSharedData(KeyConst.SharePreKey.ALL_BRAND_LIST,response);
                if (mValueBeans == null) {
                    mValueBeans = new ArrayList<>();
                }else {
                    mValueBeans.clear();
                }
//                CarDetailBean firstHead = new CarDetailBean();
//                firstHead.pys = "#";
//                firstHead.type = 1;
//                mValueBeans.add(firstHead);
//                CarDetailBean valueBeanAll = new CarDetailBean();
//                valueBeanAll.pys = "#";
//                valueBeanAll.brandName = "不限品牌";
//                mValueBeans.add(valueBeanAll);

                GetBrandList.BrandPageBean           brandPageBean = null;
                CarDetailBean valueBean     = null;
                for (int i = 0; i < getBrandList.brandPage.size(); i++)
                {
                    brandPageBean = getBrandList.brandPage.get(i);
                    valueBean = new CarDetailBean();
                    valueBean.pys = brandPageBean.key;
                    valueBean.type = 1;
                    mValueBeans.add(valueBean);
                    if (brandPageBean.value != null && brandPageBean.value.size() > 0) {
                        for (int j = 0; j < brandPageBean.value.size(); j++)
                        {
                            valueBean = brandPageBean.value.get(j);
                            valueBean.pys = brandPageBean.key;
                            mValueBeans.add(valueBean);
                        }
                    }
                }
                if (mRecyclerView!=null) {
                    initListData();
                }
            }

        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    private void doAffterGetHotBrandListData(String response) {
        try {
            GetBrandList getBrandList = new Gson().fromJson(
                    response,
                    GetBrandList.class);
            if (getBrandList.topBrandPage != null && getBrandList.topBrandPage.size() > 0) {
                mStorage.putStringSharedData(KeyConst.SharePreKey.HOT_BRAND_LIST,response);
                if (mHotValueBeans == null) {
                    mHotValueBeans = new ArrayList<>();
                }else {
                    mHotValueBeans.clear();
                }
                mHotValueBeans.addAll(getBrandList.topBrandPage);
                if (mHotBrandAdapter==null) {
                    mHotBrandAdapter = new HotBrandListAdapter(getActivity(), mHotValueBeans);
                    mHotBrandRecycle.setAdapter(mHotBrandAdapter);
                }
                mHotBrandAdapter.fromType=fromType;
                mHotBrandAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {

        }
    }

    private void initListData() {
        LogUtils.e(TAG, "valueBeans:" + mValueBeans.size());
        Collections.sort(mValueBeans, new LetterComparator());
        mAllBrandAdapter = new AdapterContactCity(getContext(), mValueBeans);
        mAllBrandAdapter.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                if (mAllBrandAdapter.getItemViewType(position)==0) {
                    if (fromType==FROM_ACT){
                        setActForResultData(mValueBeans.get(position));
                    }else {
                        goToSerieseListAct(mValueBeans.get(position));
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mAllBrandAdapter);
        mAllBrandAdapter.notifyDataSetChanged();

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpDateHotBrandData(UpDateHotBrandData upDateHotBrandData){
        if (upDateHotBrandData!=null&&!StringUtils.isEmpty(upDateHotBrandData.responseStr)){
            doAffterGetHotBrandListData(upDateHotBrandData.responseStr);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseUrlChangeBean(BaseUrlChangeBean changeBean){
        getBrandList();
    }


    //车辆购买成功，刷新品牌数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean){
        if (payResultBean!=null){
            if (payResultBean.resultState==PayResultBean.TYPE_SUSSECE){
                getBrandList();
            }
        }
    }

    private void setActForResultData(CarDetailBean carDetailBean) {
        SeriesListFilterBean filterMessegeBean = SeriesListFilterBean.newInstance();
        filterMessegeBean.isShowFilterLayout="1";
        EventBus.getDefault().post(filterMessegeBean);

        Intent intent =new Intent();
        intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON,carDetailBean);
        getActivity().setResult(KeyConst.RequestCode.RESULT_CODE_SELETE_BRAND,intent);
        getActivity().finish();
    }

    private void goToSerieseListAct(CarDetailBean carDetailBean) {
        if (carDetailBean!=null) {
            ListRequestBean listRequestBean = new ListRequestBean();
            listRequestBean.brandId = carDetailBean.brandId;
            listRequestBean.brandName= carDetailBean.brandName;
//            String dataJson = new Gson().toJson(listRequestBean);
            
            Intent intent   = new Intent(getActivity(), SeriesListActivity.class);
            intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, listRequestBean);
            startActivity(intent);
            UIUtils.activityAnimInt(getActivity());
        }
    }




    @Override
    protected void initEvent() {

    }

    @Override
    public void refreshDatas() {
        getDatas();
    }

}
