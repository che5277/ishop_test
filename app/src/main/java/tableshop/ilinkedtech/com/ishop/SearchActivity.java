package tableshop.ilinkedtech.com.ishop;

/*
 *  @项目名：  iShop
 *  @包名：    ishop.ilinkedtech.com
 *  @文件名:   SearchActivity
 *  @创建者:   Administrator
 *  @创建时间:  2017/7/12 15:48
 *  @描述：    按品牌、车系、车型、价格、座位的搜索Activity
 */

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.CarDetailListAdapter;
import tableshop.ilinkedtech.com.adapters.FilterAdapter;
import tableshop.ilinkedtech.com.base.IShopBaseActivity;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.beans.reques.SearchRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.SearchResponBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.protocols.GetSearchListProtocolForObj;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.MaterialRangeSlider;

public class SearchActivity
        extends IShopBaseActivity
        implements View.OnClickListener,
                   TextView.OnEditorActionListener,
                   PopupWindow.OnDismissListener,
                   SwipeRefreshLayout.OnRefreshListener,
                   MaterialRangeSlider.RangeSliderListener
{
    private static final String TAG = "SearchActivity";
    @BindView(R.id.iv_search)
    ImageView           mIvSearch;
    @BindView(R.id.et_search)
    EditText            mEtSearch;
    @BindView(R.id.rl_searchLayout)
    RelativeLayout      mRlSearchLayout;
    @BindView(R.id.rv_result)
    RecyclerView        mRvResult;
    @BindView(R.id.toolbar)
    Toolbar             mToolbar;
    @BindView(R.id.tv_sort_type)
    TextView            mTvSortType;
    @BindView(R.id.tv_sort_order)
    TextView            mTvSortOrder;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout  mSwipeRefreshWidget;
    @BindView(R.id.rl_empty_view)
    RelativeLayout      mRlEmptyView;
    @BindView(R.id.min_price_txt)
    TextView            mMinPriceTxt;
    @BindView(R.id.max_price_txt)
    TextView            mMaxPriceTxt;
    @BindView(R.id.price_slider)
    MaterialRangeSlider mPriceSlider;
    @BindView(R.id.rl_material_mark)
    LinearLayout        mRlMaterialMark;
    @BindView(R.id.rl_price_slider_layout)
    RelativeLayout      mRlPriceSliderLayout;
    @BindView(R.id.tv_add_button)
    TextView            mTvAddButton;
    @BindView(R.id.search_type_holder)
    TextView            mSearchTypeHolder;
    @BindView(R.id.all_price_txt)
    TextView            mAllPriceTxt;

    private ListView      mListView;
    private List<String>  mFilterType;
    private List<String>  mFilterOrder;
    private List<String>  mFilterSearch;
    private FilterAdapter mFilterTypeAdapter;
    private FilterAdapter mFilterOrderAdapter;

    private ArrayList<CarDetailBean> mArrayList;
    private CarDetailListAdapter     mAdapter;

    private String      mSearchText;
    private View        mInflatePPWindow;
    private PopupWindow mFilterPopupWindow;
    private boolean isPull_To_Refresh = false;
    private String  mType             = "BRAND";
    private String  mOrder            = "ASC";

    private              int seekBarType = 0;
    private static final int PP_SEARCH   = 0;
    private static final int PP_TYPE     = 1;
    private static final int PP_ORDER    = 2;

    private static final int TYPE_PRICE = 0;
    private static final int TYPE_SEAT  = 1;

    private static final int MIN_SEAT = 1;
    private static final int MAX_SEAT = 7;

    private static final int MIN_PRICE = 10;
    private static final int MAX_PRICE = 150;
    private int           mMaxValue;
    private int           mMinValue;
    private String        mTemper;//单位符号
    private FilterAdapter mFilterSearchAdapter;
    private TextView      mTvPpTitle;

    @Override
    public int getLayoutViewId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        return R.layout.search_activity_new;
    }

    @Override
    public void initView() {
        mInflatePPWindow = View.inflate(UIUtils.getContext(), R.layout.filter_list_ppwindow, null);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                UIUtils.activityBackToMain(SearchActivity.this);
            }
        });
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorAccent,
                                                    R.color.colorPrimary,
                                                    R.color.colorPrimaryDark,
                                                    android.R.color.holo_orange_light);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        mSwipeRefreshWidget.setProgressViewOffset(false,
                                                  0,
                                                  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                                                  24,
                                                                                  getResources().getDisplayMetrics()));
        mArrayList = new ArrayList<>();
        mAdapter = new CarDetailListAdapter(mArrayList, this, CarDetailListAdapter.CAR_DETAIL_NORMAL_VIEW);
        mRvResult.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 2));
        mRvResult.setAdapter(mAdapter);

        mEtSearch.setOnEditorActionListener(this);
        mFilterSearch = new ArrayList<>();
        mFilterType = new ArrayList<>();
        mFilterOrder = new ArrayList<>();
        mFilterSearch.add(getResources().getString(R.string.名称));
        mFilterSearch.add(getResources().getString(R.string.价格));
        mFilterSearch.add(getResources().getString(R.string.座位));

        mFilterType.add(getResources().getString(R.string.品牌));
        mFilterType.add(getResources().getString(R.string.车系));
        mFilterType.add(getResources().getString(R.string.车型));
        mFilterType.add(getResources().getString(R.string.价格));
        mFilterType.add(getResources().getString(R.string.座位));

        mFilterOrder.add(getResources().getString(R.string.升序));
        mFilterOrder.add(getResources().getString(R.string.降序));
        mFilterSearchAdapter = new FilterAdapter(UIUtils.getContext(), mFilterSearch);
        mFilterTypeAdapter = new FilterAdapter(UIUtils.getContext(), mFilterType);
        mFilterOrderAdapter = new FilterAdapter(UIUtils.getContext(), mFilterOrder);
        mTvSortType.setOnClickListener(this);
        mTvSortOrder.setOnClickListener(this);
        mSearchTypeHolder.setOnClickListener(this);
        mTvAddButton.setOnClickListener(this);
        //TODO 双滑轮
        mPriceSlider.setRangeSliderListener(this);
        showSeekBarLayout(false);
    }


    //刷新搜索条件 是否显示seekBar
    private void showSeekBarLayout(boolean isShow) {
        mPriceSlider.setVisibility(View.GONE);
        mPriceSlider.reset();
        mPriceSlider.invalidate();
        if (isShow) {
            mRlMaterialMark.setVisibility(View.VISIBLE);
            mPriceSlider.setVisibility(View.VISIBLE);
            mRlSearchLayout.setVisibility(View.GONE);
            mTemper = seekBarType == TYPE_PRICE
                      ? getString(R.string.万)
                      : getString(R.string.座);
            if (seekBarType == TYPE_PRICE) {
                //选价格
                mPriceSlider.setMin(MIN_PRICE);
                mPriceSlider.setMax(MAX_PRICE);
                mMinValue = MIN_PRICE * 2;
                mMaxValue = MAX_PRICE * 2 / 3;
            } else {
                //选座位
                mPriceSlider.setMin(MIN_SEAT);
                mPriceSlider.setMax(MAX_SEAT);
                mMinValue = MIN_SEAT * 2;
                mMaxValue = MIN_SEAT * 4;
            }
            mPriceSlider.setStartingMinMax(mMinValue, mMaxValue);
        } else {
            mRlMaterialMark.setVisibility(View.GONE);
            mPriceSlider.setVisibility(View.GONE);
            mRlSearchLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sort_type:
                showPPWindow(view, PP_TYPE);
                break;
            case R.id.tv_sort_order:
                showPPWindow(view, PP_ORDER);
                break;
            case R.id.search_type_holder:
                showPPWindow(view, PP_SEARCH);
                break;
            case R.id.tv_add_button:
                //TODO 请求搜索结果
                callToFilter(mSearchText, mOrder, mType);
                break;
        }
    }

    private boolean isSearch = false;

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

            //do something;
            if (!isSearch) {
                isSearch = true;
                SearchRequestBean bean = new SearchRequestBean();
                mSearchText = textView.getText()
                                      .toString();
                bean.searchText = mSearchText;
                bean.sortType = mType;
                bean.sortOrder = mOrder;
                callToFilter(mSearchText, mOrder, mType);
            }
            return true;
        }
        return false;
    }


    private void showPPWindow(View view, int ppType) {
        if (mFilterPopupWindow == null) {
            mTvPpTitle = (TextView) mInflatePPWindow.findViewById(R.id.tv_pp_title);
            mFilterPopupWindow = new PopupWindow(mInflatePPWindow,
                                                 view.getWidth(),
                                                 WindowManager.LayoutParams.WRAP_CONTENT);
            mFilterPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_ppwindow_shape_bg));
            setBackgroundAlpha(0.8f);
            //设置popupWindow,当点击popupWindow外面的时候可以消失
            mFilterPopupWindow.setFocusable(true);
            mFilterPopupWindow.setOutsideTouchable(true);
            mFilterPopupWindow.setOnDismissListener(this);
            findPopupWindowViewAndSetData(mInflatePPWindow, ppType);
        } else {
            setBackgroundAlpha(0.8f);
            findPopupWindowViewAndSetData(mInflatePPWindow, ppType);
            mFilterPopupWindow.setWidth(view.getWidth());
        }
        if (ppType == PP_SEARCH) {
            mTvPpTitle.setText(getString(R.string.搜索条件));
        } else {
            mTvPpTitle.setText(getString(R.string.排序条件));
        }
        mFilterPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void findPopupWindowViewAndSetData(View inflate, int ppType) {
        if (mListView == null) {
            mListView = (ListView) inflate.findViewById(R.id.lv_list);
        }
        if (ppType == PP_TYPE) {
            mListView.setAdapter(mFilterTypeAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view,
                                        int position,
                                        long id)
                {
                    mTvSortType.setText(mFilterType.get(position));
                    mSearchText = mEtSearch.getText()
                                           .toString()
                                           .trim();
                    isPull_To_Refresh = false;
                    switch (position) {
                        case 0:
                            mType = KeyConst.ListD.FileterTag.SortType.BRAND;
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                        case 1:
                            mType = KeyConst.ListD.FileterTag.SortType.SERIES;
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                        case 2:
                            mType = KeyConst.ListD.FileterTag.SortType.MODEL;
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                        case 3:
                            mType = KeyConst.ListD.FileterTag.SortType.PRICE;
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                        case 4:
                            mType = KeyConst.ListD.FileterTag.SortType.SEAT;
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                    }
                    mFilterPopupWindow.dismiss();
                }
            });
        } else if (ppType == PP_ORDER) {
            mListView.setAdapter(mFilterOrderAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view,
                                        int position,
                                        long id)
                {
                    mTvSortOrder.setText(mFilterOrder.get(position));
                    mSearchText = mEtSearch.getText()
                                           .toString()
                                           .trim();
                    isPull_To_Refresh = false;
                    switch (position) {
                        case 0:
                            mOrder = KeyConst.ListD.FileterTag.SortOrder.ASC;
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                        case 1:
                            mOrder = KeyConst.ListD.FileterTag.SortOrder.DESC;
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                    }
                    mFilterPopupWindow.dismiss();
                }
            });
        } else {
            mListView.setAdapter(mFilterSearchAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view,
                                        int position,
                                        long id)
                {
                    mSearchTypeHolder.setText(mFilterSearch.get(position));
                    isPull_To_Refresh = false;
                    switch (position) {
                        case 0:
                            showSeekBarLayout(false);
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                        case 1:
                            seekBarType = TYPE_PRICE;
                            showSeekBarLayout(true);
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                        case 2:
                            seekBarType = TYPE_SEAT;
                            showSeekBarLayout(true);
                            callToFilter(mSearchText, mOrder, mType);
                            break;
                    }
                    mFilterPopupWindow.dismiss();
                }
            });
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不加这行代码半透明不起作用
        getWindow().setAttributes(lp);
    }

    private void callToFilter(String searchText, String sortOrder, String sortType) {
        if (!isPull_To_Refresh) {
//            showLoading();
        }
        hintKbTwo();
        ListRequestBean bean = new ListRequestBean();
        if (mRlSearchLayout.isShown()) {
            mSearchText = mEtSearch.getText()
                                   .toString()
                                   .trim();
            bean.searchText = mSearchText;
        } else {
            if (seekBarType == TYPE_PRICE) {
                if (mMinValue == MAX_PRICE) {
                    bean.lowestPrice = (mMaxValue * 10000) + "";
                } else if (mMaxValue == MIN_PRICE) {
                    bean.highestPrice = (mMinValue * 10000) + "";
                } else {
                    bean.highestPrice = (mMaxValue * 10000) + "";
                    bean.lowestPrice = (mMinValue * 10000) + "";
                }

            } else {
                if (mMinValue == MAX_SEAT) {
                    bean.leastNoOfSeat = mMaxValue + "";
                } else if (mMaxValue == MIN_SEAT) {
                    bean.mostNoOfSeat = mMinValue + "";
                } else {
                    bean.mostNoOfSeat = mMaxValue + "";
                    bean.leastNoOfSeat = mMinValue + "";
                }
            }
        }
        if (!StringUtils.isEmpty(sortType)) {
            bean.sortOrder = sortOrder;
            bean.sortType = sortType;
        }
        setRefreshWidget(true);
        mArrayList.clear();
        GetSearchListProtocolForObj getSearchListProtocol = new GetSearchListProtocolForObj(this, bean) {

            @Override
            protected void doOnSusses(SearchResponBean dataBean, int id) {
                if (dataBean!=null&&dataBean.vehicleStockSingleViewList!=null&&dataBean.vehicleStockSingleViewList.size()>0){
                    if (dataBean != null) {
                        mArrayList.addAll(dataBean.vehicleStockSingleViewList);
                    }
                }
                showEmptyView(mArrayList.size() == 0);
            }

            @Override
            public void doOnError(Exception e, int id) {
                showEmptyView(true);
            }

            @Override
            public void doAffertRequest() {
                super.doAffertRequest();
                hideLoading();
                setRefreshWidget(false);
                isSearch = false;
            }
        };
    }


    public void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                            InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onDismiss() {
        // popupWindow隐藏时恢复屏幕正常透明度
        setBackgroundAlpha(1.0f);
    }

    @Override
    public void onRefresh() {
        isPull_To_Refresh = true;
        if (mArrayList.size() == 0) {
            showEmptyView(true);
            setRefreshWidget(false);
        } else {
            callToFilter(mSearchText, mOrder, mType);
            setRefreshWidget(false);
        }
        //去获取数据 如果有数据，则显示；没有数据 显示空视图
        //设置SwipeRefreshView消失
    }

    public void setRefreshWidget(boolean isShow) {
        if (mSwipeRefreshWidget != null) {
            mSwipeRefreshWidget.setRefreshing(isShow);
        }
    }

    public void showEmptyView(boolean isShow) {
        if (mRlEmptyView != null && mSwipeRefreshWidget != null) {
            mRlEmptyView.setVisibility(isShow
                                       ? View.VISIBLE
                                       : View.GONE);
            mRvResult.setVisibility(isShow
                                    ? View.GONE
                                    : View.VISIBLE);
        }
        if (mAdapter!=null)
            mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onMaxChanged(int newValue) {
        mMaxValue = newValue;
        refreshPriceView();
    }

    @Override
    public void onMinChanged(int newValue) {
        mMinValue = newValue;
        refreshPriceView();
    }


    private void refreshPriceView() {
        if (mPriceSlider.getMin() == mMinValue && mPriceSlider.getMax() == mMaxValue) {
            mAllPriceTxt.setText(getString(R.string.全部));
            mMaxPriceTxt.setText("");
            mMinPriceTxt.setText("");
        } else {
            mAllPriceTxt.setText(" ");
            if (mPriceSlider.getMin() == mMinValue) {
                mMaxPriceTxt.setText("");
                mMinPriceTxt.setText(String.valueOf(mMaxValue) + mTemper + "以下");
            } else if (mPriceSlider.getMax() == mMaxValue) {
                mMinPriceTxt.setText("");
                mMaxPriceTxt.setText(String.valueOf(mMinValue) + mTemper + "以上");
            } else {
                mMaxPriceTxt.setText(String.valueOf(mMaxValue) + mTemper);
                mAllPriceTxt.setText("~");
                mMinPriceTxt.setText(String.valueOf(mMinValue) + mTemper);
            }
        }
    }
}
