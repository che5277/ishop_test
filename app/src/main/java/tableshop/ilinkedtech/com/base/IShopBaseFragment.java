package tableshop.ilinkedtech.com.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.utils.LogUtils;

//import org.greenrobot.eventbus.EventBus;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.base
 *  @文件名:   IShopBaseFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/3/14 15:27
 *  @描述：    TODO
 */
@SuppressLint("ValidFragment")
public abstract class IShopBaseFragment
        extends Fragment
{
    public final String TAG = this.getClass().getSimpleName();
    public View mView;
    public boolean isFragmentApiCall =false;
    public boolean isUseButterKnife =true;
    private Unbinder mUnbinder;
    public  EventBus defaultEventBus;
    protected abstract int getLayoutId();

    public IShopBaseFragment(){
        super();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //可以直接注入view，也可以通过xml加载view
        View view =setContent();
        if (view!=null){
            mView=view;
        }else {
            mView = getActivity().getLayoutInflater()
                                 .inflate(getLayoutId(), null);
        }
        mUnbinder = ButterKnife.bind(this, mView);
        if (defaultEventBus == null) {
            defaultEventBus = EventBus.getDefault();
        }
        initView();
        initEvent();
        initVariable();
    }

    //如果从xml外提供视图，则需重写该方法
    public View setContent() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return mView;
    }


    /**
     * 在此方法需要先执行ButterKnife.bind(this, mView);
     */
    protected abstract void initView();

    protected abstract void initEvent();

    public abstract void refreshDatas();

    @Override
    public void onResume() {
        LogUtils.e("====onResume:", ""+getClass().getSimpleName());
        super.onResume();
//        isFragmentApiCall=false;

    }
    public IShopBaseActivity getBaseAct(){
        IShopBaseActivity baseActivity =null;
        FragmentActivity  activity     = getActivity();
        if (activity instanceof IShopBaseActivity){
            baseActivity = (IShopBaseActivity) activity;
        }
        return baseActivity;
    }

    public void showLoading() {
        hideLoading();
        if (getBaseAct()!=null){
            getBaseAct().showLoading();
        }
    }

    public void hideLoading() {
        if (getBaseAct()!=null){
            getBaseAct().hideLoading();
        }
    }
    @Override
    public void onPause() {
        LogUtils.e("====onPause:", ""+getClass().getSimpleName());
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        LogUtils.e("====onDestroyView:", ""+getClass().getSimpleName());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        if (defaultEventBus!=null&&defaultEventBus.isRegistered(this)) {
            defaultEventBus.unregister(this);
        }
        mView = getActivity().getLayoutInflater().inflate(R.layout.recycle_view, null);
        LogUtils.e("====onDestroy:", ""+getClass().getSimpleName());
        super.onDestroy();
    }


    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    public boolean isFragmentVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d(TAG, "setUserVisibleHint() -> isVisibleToUser: " + isVisibleToUser);
        if (mView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.e(this.isVisible()+"====onViewCreated:", ""+getClass().getSimpleName());

        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    public void onFragmentVisibleChange(boolean isVisible) {
        LogUtils.w(TAG, "onFragmentVisibleChange -> isVisible: " + isVisible);
        if (isVisible&&!isFragmentApiCall) {
            refreshDatas();
        }
    }

    public IShopBaseActivity getBaseActivity() {
       return (IShopBaseActivity)getActivity();
    }


}
