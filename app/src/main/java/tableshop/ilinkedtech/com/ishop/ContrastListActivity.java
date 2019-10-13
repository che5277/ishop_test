package tableshop.ilinkedtech.com.ishop;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.ishop
 *  @文件名:   ContrastViewActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 10:50
 *  @描述：    TODO 对比车辆列表
 */

import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActContrastListFragment;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class ContrastListActivity
        extends BaseActionBarActivity {
    private ActContrastListFragment mActContrastListFragment;

    @Override
    protected void onResume() {
        super.onResume();
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(getString(R.string.PK));
        mTvToolbarTitle.setText(getString(R.string.车型对比));
    }

    @Override
    protected IShopBaseFragment getShowFragment() {
        mActContrastListFragment = ActContrastListFragment.newInstance();
        return mActContrastListFragment;
    }

    @Override
    public void doRightTextViewAction() {
        super.doRightTextViewAction();
        ArrayList<CarDetailBean> showData = mActContrastListFragment.getShowData();
        if (showData!=null&&showData.size()>1)
            gotoContrastViewAct(showData);
        else
            showSnackbar(getString(R.string.请至少选择两辆车进行对比));
    }

    private void gotoContrastViewAct(ArrayList<CarDetailBean> showData) {
        Intent intent =new Intent(this,ContrastViewActivity.class);
        intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON,new Gson().toJson(showData));
        startActivity(intent);
        UIUtils.activityAnimInt(this);
    }
}
