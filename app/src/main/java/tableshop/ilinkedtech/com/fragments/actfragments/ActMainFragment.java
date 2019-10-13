package tableshop.ilinkedtech.com.fragments.actfragments;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.fragments.actfragments
 *  @文件名:   ActMainFragment
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 14:51
 *  @描述：    TODO 首页 主要视图
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.base.TabBaseFragment;
import tableshop.ilinkedtech.com.beans.ChannelEntityBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListRequestBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.DBJsonBean;
import tableshop.ilinkedtech.com.db.DBJsonHelper;
import tableshop.ilinkedtech.com.fragments.main.CaDetailListFragment;
import tableshop.ilinkedtech.com.fragments.main.CarModelListFragment;
import tableshop.ilinkedtech.com.ishop.ChannelViewActivity;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class ActMainFragment extends TabBaseFragment {

    public static String positionText="";

    public static ActMainFragment newInstance() {
        ActMainFragment fragment = new ActMainFragment();
        Bundle       args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void addDatas(List<IShopBaseFragment> viewList, List<String> titleList) {

        CarModelListRequestBean chooseCarRequest =new CarModelListRequestBean();
        viewList.add(CaDetailListFragment.newInstance(false));
        viewList.add(CarModelListFragment.newInstance(chooseCarRequest));

        titleList.add(getString(R.string.推荐));
        titleList.add(getString(R.string.买车));

        refreshTabView();
    }

    @Override
    public void doOnViewPageSeleted(int position) {
        super.doOnViewPageSeleted(position);
        positionText=mTitleList.get(position);
    }

    private void refreshTabView() {
        DBJsonBean jsonBean = DBJsonHelper.getJsonBean(KeyConst.DBDataKey.KET_DB_CHANNEL_LIST);
        if (jsonBean != null && !StringUtils.isEmpty(jsonBean.getData())) {
            try {
                ArrayList<ChannelEntityBean> localChanelList = StringUtils.jsonToArrayObj(jsonBean.getData(),
                                                                                          ChannelEntityBean.class);
                ChannelEntityBean       brandSalesItemBean =null;
                if (localChanelList!=null&&localChanelList.size()>0){
                    if (mViewList.size()>2) {
                        mViewList = mViewList.subList(0, 2);
                        mTitleList = mTitleList.subList(0, 2);
                    }
                    for (int i = 0; i < localChanelList.size(); i++) {
                        brandSalesItemBean = localChanelList.get(i);
                        mViewList.add(new CarModelListFragment(brandSalesItemBean.requestBean));
                        mTitleList.add(StringUtils.checkString(brandSalesItemBean.description));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mIvAdd.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTitleList!=null&&mTitleList.size()>0){
            mPosition=mTitleList.indexOf(positionText);
        }
        refreshDatas();
    }

    @Override
    public void doAddIConAction() {
        Intent intent = new Intent(getContext(), ChannelViewActivity.class);
        startActivity(intent);
        UIUtils.activityAnimInt(getActivity());
    }
}
