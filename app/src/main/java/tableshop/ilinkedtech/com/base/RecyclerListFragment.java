package tableshop.ilinkedtech.com.base;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments
 *  @文件名:   RecyclerListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 10:26
 *  @描述：    TODO
 */

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.MyProducAdapter;
import tableshop.ilinkedtech.com.adapters.ShowFilterTagAdapter;
import tableshop.ilinkedtech.com.beans.RulesItem;
import tableshop.ilinkedtech.com.beans.events.BackToHome;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.consts.FilterTagJson;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.factorys.FragmentFactory;
import tableshop.ilinkedtech.com.ishop.ChooseCarActivity;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.WrapContentLinearLayoutManager;
import tableshop.ilinkedtech.com.views.flowlayout.TagFlowLayout;

@SuppressLint("ValidFragment")
public abstract class RecyclerListFragment<ADAPTER extends BaseRecyclerAdapter>
        extends IShopBaseFragment
        implements SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.recy)
    public RecyclerView       mRecy;
  //  @BindView(R.id.swipe_refresh_widget)
   // public SwipeRefreshLayout mSwipeRefreshWidget;
    @BindView(R.id.rl_empty_view)
    RelativeLayout mRlEmptyView;

    public RecyclerView.LayoutManager layoutManager;
    public ADAPTER                    adapter;
    public boolean isLoadDataFromNet = false;
    public boolean isLoadMore        = true;
    @BindView(R.id.ll_back_to_home)
    LinearLayout mLlBackToHome;
    @BindView(R.id.iv_holder)
    ImageView    mIvHolder;
    @BindView(R.id.tv_holder)
    TextView     mTvHolder;
    @BindView(R.id.btn_confirm)
    Button       mBtnConfirm;
    @BindView(R.id.search_layout)
    public RelativeLayout mSearchLayout;

    public int pageNumber = 0;
    @BindView(R.id.btn_selete_brand)
    public Button        mBtnSeleteBrand;
    @BindView(R.id.flowlayout_series_fileter)
    public TagFlowLayout mFlowlayoutSeriesFileter;
    @BindView(R.id.flowlayout_price_fileter)
    public TagFlowLayout mFlowlayoutPriceFileter;
    @BindView(R.id.flowlayout_seats_fileter)
    public TagFlowLayout mFlowlayoutSeatsFileter;
    @BindView(R.id.rl_head_layout)
    public RelativeLayout mRlHeadLayout;
    Unbinder unbinder;

    private ArrayList<RulesItem> seriesFilterDatas;
    private ArrayList<RulesItem> priceFilterDatas;
    private ArrayList<RulesItem> seatsFilterDatas;
    public ShowFilterTagAdapter mSeriesFilterAdapter;
    public ShowFilterTagAdapter mPriceFilterAdapter;
    public ShowFilterTagAdapter mSeatsFilterAdapter;


    public RecyclerListFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_recycle_list_fragment;
    }

    @Override
    protected void initView() {
        mLlBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome();
            }
        });
    }


    private void backToHome() {
        defaultEventBus.post(BackToHome.newInstance(FragmentFactory.FRAGMENT_HOME));
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
        getActivity().finish();
        UIUtils.activityBackToMain(getActivity());
    }

    @Override
    protected void initEvent() {
        adapter = setRecycleAdapter();
        layoutManager = setRecycleLayoutManager();

      //  GridLayoutManager layoutManagerG =new WrapContentLinearLayoutManager(getContext(),2);

     //   WrapContentLinearLayoutManager layoutManager =new WrapContentLinearLayoutManager(getContext(),1);
        mRecy.setLayoutManager(layoutManager);
         mRecy.setAdapter(adapter);
        //mRecy.setAdapter(new MyProducAdapter(getActivity(),new ArrayList<ProductItemBean>()));

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isLoadMore) {
                        //TODO 加载更多
                        isLoadMore = false;
                        if (!isFragmentApiCall) {
                            isFragmentApiCall = true;
                            pageNumber++;
                            adapter.notifyDataSetChanged();
                            Log.e("runhere","setitemdaun2222");
                           setItemsData();
                        }
                    }
                }


            }

        });


//        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorAccent,
//                                                    R.color.colorPrimary,
//                                                    R.color.colorPrimaryDark,
//                                                    android.R.color.holo_orange_light);
//        mSwipeRefreshWidget.setOnRefreshListener(this);
//
//        mSwipeRefreshWidget.setProgressViewOffset(false,
//                                                  0,
//                                                  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                                                                                  24,
//                                                                                  getResources().getDisplayMetrics()));
        initFlowLayoutEvent();

    }

    private void initFlowLayoutEvent() {
        seriesFilterDatas = new ArrayList<>();
        mSeriesFilterAdapter = new ShowFilterTagAdapter(seriesFilterDatas,
                                                        mFlowlayoutSeriesFileter);
        mFlowlayoutSeriesFileter.setAdapter(mSeriesFilterAdapter);

        priceFilterDatas = new ArrayList<>();
        mPriceFilterAdapter = new ShowFilterTagAdapter(priceFilterDatas, mFlowlayoutPriceFileter);
        mFlowlayoutPriceFileter.setAdapter(mPriceFilterAdapter);

        seatsFilterDatas = new ArrayList<>();
        mSeatsFilterAdapter = new ShowFilterTagAdapter(seatsFilterDatas, mFlowlayoutSeatsFileter);
        mFlowlayoutSeatsFileter.setAdapter(mSeatsFilterAdapter);

        initFilterDatas();
    }

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
        RulesItem rulesItem = null;

        if (categoryRulesItems.size() > 0) {
            seriesFilterDatas.addAll(categoryRulesItems);
        } else {
            if (pricefilterStrs != null && pricefilterStrs.length > 0) {
                for (int i = 0; i < pricefilterStrs.length; i++) {
                    rulesItem = new RulesItem();
                    rulesItem.displayText = pricefilterStrs[i];
                    priceFilterDatas.add(rulesItem);
                }
            }
        }

        if (priceRulesItems.size() > 0) {
            priceFilterDatas.addAll(priceRulesItems);
        } else {
            if (pricefilterStrs != null && pricefilterStrs.length > 0) {
                for (int i = 0; i < pricefilterStrs.length; i++) {
                    rulesItem = new RulesItem();
                    rulesItem.displayText = pricefilterStrs[i];
                    priceFilterDatas.add(rulesItem);
                }
            }
        }
        if (seatRulesItems.size() > 0) {
            seatsFilterDatas.addAll(seatRulesItems);
        } else if (seatfilterStrs != null && seatfilterStrs.length > 0) {
            for (int i = 0; i < seatfilterStrs.length; i++) {
                rulesItem = new RulesItem();
                rulesItem.displayText = seatfilterStrs[i];
                seatsFilterDatas.add(rulesItem);
            }
        }

        mSeriesFilterAdapter.notifyDataChanged();
        mPriceFilterAdapter.notifyDataChanged();
        mSeatsFilterAdapter.notifyDataChanged();

    }

    public void setItemsData() {

    }

    protected abstract ADAPTER setRecycleAdapter();

    protected abstract RecyclerView.LayoutManager setRecycleLayoutManager();


    @Override
    public void onResume() {
        isLoadDataFromNet = false;
        super.onResume();
    }

    @Override
    public void onRefresh() {
        isLoadDataFromNet = true;
        pageNumber=0;
        if (!isFragmentApiCall) {
            refreshDatas();
        } else {
            setRefreshWidget(false);
        }
        MainApp.getHandler()
               .postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       isLoadDataFromNet = false;
                   }
               }, 100);
    }

    public void setRefreshWidget(boolean isShow) {
      //  if (mSwipeRefreshWidget != null) { mSwipeRefreshWidget.setRefreshing(isShow); }
    }

    public void showEmptyView(boolean isShow) {
        if (mRlEmptyView != null/* && mSwipeRefreshWidget != null */) {
            mRlEmptyView.setVisibility(isShow
                                       ? View.VISIBLE
                                       : View.GONE);
            mRecy.setVisibility(isShow
                                ? View.GONE
                                : View.VISIBLE);
            if (isShow && emptyToHome) {
                mLlBackToHome.setVisibility(View.VISIBLE);
            } else {
                mLlBackToHome.setVisibility(View.GONE);
            }
        }
    }

    public boolean emptyToHome;//TODO 无数据显示时，是否可以返回首页


    // 动画实际执行
    private void startPropertyAnim(final View view, final boolean isShow) {
        ObjectAnimator anim = null;
        if (isShow) {
            anim = ObjectAnimator.ofFloat(view, "scaleY", 0, 1f);
        } else {
            anim = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0);
        }
        anim.setDuration(300);

        // 回调监听
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (isShow) {
                    view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isShow) {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        // 正式开始启动执行动画
        anim.start();
    }


    public ListRequestBean mListRequestBean;

    @OnClick(R.id.btn_confirm)
    public void startSearch() {
        ListRequestBean seriesRequestBean = StringUtils.getTagRequestBean(seriesFilterDatas,
                                                                         mFlowlayoutSeriesFileter);
        ListRequestBean priceRequestBean = StringUtils.getTagRequestBean(priceFilterDatas,
                                                                         mFlowlayoutPriceFileter);
        ListRequestBean seatRequestBean = StringUtils.getTagRequestBean(seatsFilterDatas,
                                                                        mFlowlayoutSeatsFileter);
        if (mListRequestBean == null) {
            mListRequestBean = new ListRequestBean();
        }
        String brandName = mBtnSeleteBrand.getText()
                                     .toString()
                                     .trim();
        if (StringUtils.isEmpty(brandName)||brandName.equals(UIUtils.getString(R.string.选择品牌))){
            mListRequestBean.brandId=null;
        }
        if (seriesRequestBean != null) {
            mListRequestBean.category = seriesRequestBean.category;
        } else {
            mListRequestBean.category = null;
        }
        if (priceRequestBean != null) {
            mListRequestBean.lowestPrice = priceRequestBean.lowestPrice;
            mListRequestBean.highestPrice = priceRequestBean.highestPrice;
        } else {
            mListRequestBean.lowestPrice = null;
            mListRequestBean.highestPrice = null;
        }
        if (seatRequestBean != null) {
            mListRequestBean.leastNoOfSeat = seatRequestBean.leastNoOfSeat;
            mListRequestBean.mostNoOfSeat = seatRequestBean.mostNoOfSeat;
        } else {
            mListRequestBean.leastNoOfSeat = null;
            mListRequestBean.mostNoOfSeat = null;
        }
        calltoGetSearchDatas();
        showFiterLayout(false);

    }


    public void calltoGetSearchDatas() {

    }

    @OnClick(R.id.btn_selete_brand)
    public void goToSeleteBrand() {
        Intent intent = new Intent(getActivity(), ChooseCarActivity.class);
        getActivity().startActivityForResult(intent, KeyConst.RequestCode.RESULT_CODE_SELETE_BRAND);
    }


    /**
     * 是否显示筛选页面
     * @param isShow
     */
    public void showFiterLayout(boolean isShow) {
        if (mSearchLayout != null && mRecy != null) {
            startPropertyAnim(mSearchLayout, isShow);
        }
    }

}
