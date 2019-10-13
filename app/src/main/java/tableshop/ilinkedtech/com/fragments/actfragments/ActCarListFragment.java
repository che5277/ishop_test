package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActCarListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/8 14:51
 *  @描述：    TODO 车辆列表 视图
 */

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.CarDetailListAdapter;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.base.RecyclerListFragment;
import tableshop.ilinkedtech.com.beans.events.PayResultBean;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetBrandList;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.CarDetailActivity;
import tableshop.ilinkedtech.com.ishop.LoginActivity;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

@SuppressLint("ValidFragment")
public class ActCarListFragment
        extends RecyclerListFragment
        implements View.OnClickListener
{

    private static final String TAG = "ActCarListFragment";
    public ImageView        mIvRight;
    private ListRequestBean mListRequestBean;
    private AutoCompleteTextView mAutoSort;
    private TextView mTvListTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSortView();

        defaultEventBus.register(this);

    }

    @NonNull
    private void initSortView() {
        View                 view                 =View.inflate(getContext(), R.layout.view_sort_layout, null);
        mAutoSort = view.findViewById(R.id.auto_sort);
        mTvListTitle = view.findViewById(R.id.tv_list_title);


        mAutoSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListRequestBean!=null){
                    mListRequestBean.sortType=KeyConst.ListD.FileterTag.SortType.PRICE;
                    mListRequestBean.sortOrder=position==0?KeyConst.ListD.FileterTag.SortOrder.DESC:KeyConst.ListD.FileterTag.SortOrder.ASC;
                    pageNumber=0;
                    mArrayList.clear();
                    adapter.notifyDataSetChanged();
                    refreshDatas();
                }
            }
        });

        mAutoSort.setOnClickListener(this);
        ArrayAdapter<String> mModelListAdapter = new ArrayAdapter<String>(getContext(),
                                                                          android.R.layout.simple_list_item_1,
                                                                          getResources().getStringArray(
                                                                                  R.array.sort_car_list_array));
        mAutoSort.setAdapter(mModelListAdapter);
        mAutoSort.setThreshold(1);

        mRlHeadLayout.addView(view);
    }

    public static ActCarListFragment newInstance() {
        ActCarListFragment fragment = new ActCarListFragment();
        Bundle             args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public void setListRequestBean(ListRequestBean listRequestBean){
        this.mListRequestBean = listRequestBean;
        pageNumber=0;
        if (mArrayList!=null&&adapter!=null) {
            mArrayList.clear();
            adapter.notifyDataSetChanged();
            setItemsData();
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultBean(PayResultBean payResultBean){
        if (payResultBean!=null){
            if (payResultBean.resultState==PayResultBean.TYPE_SUSSECE){
                if (adapter!=null) {
                    mArrayList.clear();
                    adapter.notifyDataSetChanged();
                }
                refreshDatas();
            }
        }
    }

    @Override
    public void setItemsData() {
        LogUtils.e(TAG,"setItemsData()");
        if (mListRequestBean ==null) {
            mListRequestBean = new ListRequestBean();
        }
        mListRequestBean.pageNumber=pageNumber+"";
        refreshDatas();
    }

    @Override
    public void refreshDatas() {
        LogUtils.e(TAG,"refreshDatas()");
        mListRequestBean.pageNumber=pageNumber+"";
        String requestJson=new Gson().toJson(mListRequestBean);
        isFragmentApiCall=true;
        setRefreshWidget(true);
        MyHttpUtils.postJson(Const.GET_VEHICLE_SALES,true, null, requestJson, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
                if (id==MyHttpUtils.INVALID_TOKEN){
                    AlertUtils.showErrorDialog(getContext(),
                                               UIUtils.getString(R.string.登录超时请重新登录),
                                               new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog,
                                                                       int which)
                                                   {
                                                       goToLoginAct();
                                                   }
                                               });
                }else {
                    setRefreshWidget(false);
                }

            }

            @Override
            public void onResponse(String response, int id) {

                if (!StringUtils.isEmpty(response)){
                    try {
                        GetBrandList getBrandList = new Gson().fromJson(response,
                                                                               GetBrandList.class);
                        if (getBrandList!=null&&getBrandList.vehicleSalesPage!=null){
                            if (adapter!=null&&mArrayList!=null) {
                                List<CarDetailBean> vehicleStockSingleViewList = getBrandList.vehicleSalesPage.vehicleStockSingleViewList;
                                if (vehicleStockSingleViewList!=null&&!mArrayList.containsAll(vehicleStockSingleViewList)) {
                                    if (vehicleStockSingleViewList.size()==1){
                                        CarDetailBean carDetailBean = vehicleStockSingleViewList.get(0);
                                        if (carDetailBean!=null) {
                                            Intent intent = new Intent(UIUtils.getContext(),
                                                                       CarDetailActivity.class);
                                            intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_ID,
                                                            carDetailBean.uid);
                                            intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_FLAG,carDetailBean.flag);
                                            startActivity(intent);
                                            getActivity().finish();
                                            UIUtils.activityAnimInt(getActivity());
                                            return;
                                        }
                                    }
                                    mArrayList.addAll(vehicleStockSingleViewList);
                                    isLoadMore=vehicleStockSingleViewList.size()> (KeyConst.ListD.MAX_RESULTS-1);
                                }
                                String text= UIUtils.getString(R.string.为您找到款型号);
                                mTvListTitle.setText(text.replace("%",getBrandList.vehicleSalesPage.count+""));
                            }
                        }

                    }catch (JsonSyntaxException e){

                    }catch (IllegalStateException e){

                    }
                    setRefreshWidget(false);

                }
            }

            @Override
            public void doAffterRequestCall() {
                isFragmentApiCall=false;
                hideLoading();
            }
        });

    }


    private void goToLoginAct() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        UIUtils.activityBackToMain(getActivity());
    }


    @Override
    public void startSearch() {
        super.startSearch();
        if (mIvRight!=null)
            mIvRight.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        mAutoSort.showDropDown();
    }
}
