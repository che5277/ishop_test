package tableshop.ilinkedtech.com.base;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments
 *  @文件名:   RecyclerListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 10:26
 *  @描述：    TODO
 */

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.utils.UIUtils;

@SuppressLint("ValidFragment")
public abstract class BaseSwipeRefreshFragment<ADAPTER extends BaseRecyclerAdapter>
        extends IShopBaseFragment
{


    public RecyclerView.LayoutManager layoutManager;
    public ADAPTER                    adapter;
    public boolean isLoadDataFromNet = false;
    public boolean isLoadMore        = true;
    public int     pageNumber        = 0;
    @BindView(R.id.iv_holder)
    ImageView          mIvHolder;
    @BindView(R.id.tv_holder)
    TextView           mTvHolder;
    @BindView(R.id.ll_back_to_home)
    LinearLayout       mLlBackToHome;
    @BindView(R.id.rl_empty_view)
    RelativeLayout     mRlEmptyView;
    @BindView(R.id.rl_head_layout)
    RelativeLayout     mRlHeadLayout;
    @BindView(R.id.recy)
    RecyclerView       mRecy;
    @BindView(R.id.refreshLayout)
    public SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;


    public BaseSwipeRefreshFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_swipe_refresh_fragment;
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
        getActivity().finish();
        UIUtils.activityBackToMain(getActivity());
    }

    @Override
    protected void initEvent() {
        adapter = setRecycleAdapter();
        layoutManager = setRecycleLayoutManager();
        mRecy.setLayoutManager(layoutManager);
        mRecy.setAdapter(adapter);
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
                            refreshDatas();
                        }
                    }
                }
            }

        });

        mRefreshLayout.setEnableOverScrollDrag(true);
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNumber=0;
                refreshDatas();
            }
        });

    }


    protected abstract ADAPTER setRecycleAdapter();

    protected abstract RecyclerView.LayoutManager setRecycleLayoutManager();


    @Override
    public void onResume() {
        isLoadDataFromNet = false;
        super.onResume();
    }


    public void showEmptyView(boolean isShow) {
        if (mRlEmptyView != null && mRefreshLayout != null) {
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


}
