package tableshop.ilinkedtech.com.ishop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import tableshop.ilinkedtech.com.MainActivity;
import tableshop.ilinkedtech.com.MainApp;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.ProductDetailPagerAdpter;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetProductListBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.actfragments.ActProductDetailFragment;
import tableshop.ilinkedtech.com.utils.ListDataSave;
import tableshop.ilinkedtech.com.utils.StringUtils;

/**
 * Created by TO on 2018/4/28.
 */

public class ToProductDetailAct extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ViewPager viewPager ;
    List<ProductItemBean> list;
    FragmentManager manager;
    List<Fragment> mFragmentList;
    ProductDetailPagerAdpter pagerAdpter;
    ListDataSave dataSave;
    int index;
    boolean isContinue=false;
    boolean mIsTouch;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_view);
        EventBus.getDefault().register(this);

        initView();
        runItself();
        handler.postDelayed(runnable,20000);


    }

    private void initView(){

        dataSave=new ListDataSave(this,"show");
     //   list =getIntent().getParcelableArrayListExtra("product");
        ArrayList<ProductItemBean> list_choice =getIntent().getParcelableArrayListExtra("product");
        List<ProductItemBean> list_rember=dataSave.getDataList("showlist");


         if (list_choice!=null&&list_choice.size()>0) {
             list = list_choice;
             remberData(list);
             MainApp.isRemberData=false;
         }
         else if (list_rember!=null&&list_rember.size()>0){
            list =list_rember;
            MainApp.isRemberData=true;
         }
         else {
             Toast.makeText(this,"没有保存的商品数据！",Toast.LENGTH_SHORT).show();
             startActivity(new Intent(this, MainActivity.class));
             MainApp.isRemberData=false;
             finish();

         }


        viewPager=findViewById(R.id.product_viewpager);
        viewPager.addOnPageChangeListener(this);

        manager=getSupportFragmentManager();
        if (mFragmentList==null)
            mFragmentList=new ArrayList<>();

        if (list!=null&&list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                ActProductDetailFragment fragment =new ActProductDetailFragment(list.get(i),list.get(i).uid);
                mFragmentList.add(fragment);
            }
        }


        pagerAdpter =new ProductDetailPagerAdpter(manager,mFragmentList);
        viewPager.setAdapter(pagerAdpter);


    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

        switch (i){

            //=1在滑动~~，移除了handler的消息，避免在人为滑动的时候也自动滑动
            case ViewPager.SCROLL_STATE_DRAGGING:
                if (handler!=null&&runnable!=null) {
                    handler.removeCallbacks(runnable);
                    isContinue = true;
                }
                break;
            //=2滑动完毕，handler重新发送延时2S的消息
            case ViewPager.SCROLL_STATE_SETTLING:

                break;
            //=0空闲，判断handler是否有消息，如果为空，则开始发送消息
            case ViewPager.SCROLL_STATE_IDLE:
                if (handler!=null&&runnable!=null&&isContinue) {
                    handler.postDelayed(runnable, 20000);
                    isContinue=false;
                }

                break;
        }




    }

    private void runItself(){
        if (handler==null)
            handler =new Handler();
        if (runnable==null)
            runnable =new Runnable() {
                @Override
                public void run() {
                handler.postDelayed(this,20000);
                index =viewPager.getCurrentItem();
                if (index<mFragmentList.size()-1){
                    index++;
                    viewPager.setCurrentItem(index);
                }else if (index==(mFragmentList.size()-1)){
                    index=0;
                    viewPager.setCurrentItem(index);
                }

                }
            };
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getIsTouch(boolean isTouch){
        if (isTouch){
            Log.e("ontouchu","now---ontouchu");
            if (handler!=null&&runnable!=null) {
                handler.removeCallbacks(runnable);
                isContinue = true;
            }
        }
    }

    private void remberData(List<ProductItemBean> list){

        if (list.size()==0)
            return;

        dataSave.setDataList("showlist",list);
    }

}
