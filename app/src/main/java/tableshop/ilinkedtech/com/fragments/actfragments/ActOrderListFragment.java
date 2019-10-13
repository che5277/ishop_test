package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActOrderListFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/8/8 14:51
 *  @描述：    TODO 我的订单 视图
 */

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.base.TabBaseFragment;
import tableshop.ilinkedtech.com.beans.events.SeletedShop;
import tableshop.ilinkedtech.com.beans.reques.JsonDataBean;
import tableshop.ilinkedtech.com.beans.responbeans.LoginMenberResponBean;
import tableshop.ilinkedtech.com.beans.responbeans.OrderListResponBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.fragments.OrderListCarFragment;
import tableshop.ilinkedtech.com.fragments.OrderListProductFragment;
import tableshop.ilinkedtech.com.ishop.LoginActivity;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

@SuppressLint("ValidFragment")
@Deprecated
public class ActOrderListFragment
        extends TabBaseFragment
{

    private OrderListCarFragment     mOrderListCarFragment;
    private OrderListProductFragment mOrderListProductFragment;

    public static ActFavoriteFragment newInstance() {
        ActFavoriteFragment fragment = new ActFavoriteFragment();
        Bundle          args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultEventBus.register(this);
    }

    @Override
    public void addDatas(List<IShopBaseFragment> viewList, List<String> titleList) {
        mOrderListCarFragment = OrderListCarFragment.newInstance();
        mOrderListProductFragment = OrderListProductFragment.newInstance();

        viewList.add(mOrderListCarFragment);
        viewList.add(mOrderListProductFragment);

        titleList.add("车辆订单");
        titleList.add("商品订单");


    }

    @Override
    public void refreshDatas() {
        super.refreshDatas();

        goToGetOrderListData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSeletedShop(SeletedShop seletedShop){
        goToGetOrderListData();
    }

    public void goToGetOrderListData() {
        if (!SharedStorage.mIsLogin){
            return;
        }
        JsonDataBean dataBean=new JsonDataBean();
        dataBean.userName= SharedStorage.getInstance(getContext()).getMobile();
        String json=new Gson().toJson(dataBean);
        showLoading();
        MyHttpUtils.postJson(0,getActivity(),true,Const.GET_PURCHASE_ORDER_LIST, null, json, new MysalesCallBack() {
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
                }

            }

            @Override
            public void onResponse(String response, int id) {

                if (!StringUtils.isEmpty(response)){
                    try {
                        OrderListResponBean orderListResponBean = new Gson().fromJson(response,
                                                                                      OrderListResponBean.class);
                        if (orderListResponBean!=null&&orderListResponBean.vehicleOrderDatas!=null&&orderListResponBean.vehicleOrderDatas.size()>0){
                            mOrderListCarFragment.refreshViewWithDatas(orderListResponBean.vehicleOrderDatas);
                        }
                        if (orderListResponBean!=null&&orderListResponBean.datas!=null&&orderListResponBean.datas.size()>0){
                            mOrderListProductFragment.refreshViewWithDatas(orderListResponBean.datas);
                        }
                    }catch (Exception e){
                        SharedStorage.mIsLogin=false;
                        defaultEventBus.post(new LoginMenberResponBean());
                    }

                }
            }

            @Override
            public void doAffterRequestCall() {

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


}
