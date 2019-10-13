package tableshop.ilinkedtech.com.ishop;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.ishop
 *  @文件名:   ContrastViewActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 10:50
 *  @描述：    TODO 进行车辆对比
 */

import android.content.Intent;
import android.view.View;

import org.json.JSONException;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.beans.main.CarModelListItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActContrastPKFragment;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

public class ContrastViewActivity
        extends BaseActionBarActivity {
    public static ArrayList<CarModelListItemBean> favoriteList =new ArrayList<>();
    private ActContrastPKFragment mActContrastListFragment;

    @Override
    protected void onResume() {
        super.onResume();
        String stringExtra = getIntent().getStringExtra(KeyConst.BundleIntentKey.DATA_JSON);
        if (!StringUtils.isEmpty(stringExtra)){
            try {
                ArrayList<CarDetailBean> carDetailBeen = StringUtils.jsonToArrayObj(stringExtra,
                                                                                    CarDetailBean.class);
                if (carDetailBeen!=null&&carDetailBeen.size()>0){
                    mActContrastListFragment.refreshDatas(carDetailBeen);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(getString(R.string.编辑));
        mTvToolbarTitle.setText(getString(R.string.车型对比));
    }

    @Override
    protected IShopBaseFragment getShowFragment() {
        mActContrastListFragment = ActContrastPKFragment.newInstance();
        return mActContrastListFragment;
    }

    @Override
    public void doRightTextViewAction() {
        super.doRightTextViewAction();
        gotoContrastViewAct();

    }
    private void gotoContrastViewAct() {
        Intent intent =new Intent(this, ContrastListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        UIUtils.activityBackToMain(this);
    }
}
