package tableshop.ilinkedtech.com.ishop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tableshop.ilinkedtech.com.MainActivity;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.adapters.MyProducAdapter;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;
import tableshop.ilinkedtech.com.beans.reques.ObjRequestBean;
import tableshop.ilinkedtech.com.beans.responbeans.GetProductListBean;
import tableshop.ilinkedtech.com.callBacks.MysalesCallBack;
import tableshop.ilinkedtech.com.consts.Const;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.fragments.BackFragment;
import tableshop.ilinkedtech.com.utils.AlertUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.WrapContentLinearLayoutManager;
import tableshop.ilinkedtech.com.views.keyboard.EditView;
import tableshop.ilinkedtech.com.views.keyboard.SKeyboardView;

/**
 * Created by TO on 2018/4/27.
 */

public class ShowProductActivity extends AppCompatActivity implements View.OnClickListener {

    private ListRequestBean mListRequestBean;
    public int pageNumber = 0;
    private ArrayList<ProductItemBean> mArrayList;
    private ArrayList<ProductItemBean> toDetailList;
    RecyclerView recyclerView;
    TextView tv_determine;
    TextView tv_back;
    private EditView mEditView;
    private Dialog mViewDialog;
    public static final String password = "1508";



    public boolean isLoadMore        = true;
    MyProducAdapter adapter;


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_product);
        initview();
        initEvent();
    }


    private void initview(){
        EventBus.getDefault().register(this);
        tv_back=findViewById(R.id.show_product_back);
        tv_back.setOnClickListener(this);
        recyclerView=findViewById(R.id.rv_product);
        tv_determine=findViewById(R.id.tv_product_determine);
        tv_determine.setOnClickListener(this);
        mArrayList =new ArrayList<>();
        adapter =new MyProducAdapter(this,mArrayList);

    }


    private void SearchProduct(){

        LogUtils.e("searchproduct","startSearch");
        if (mListRequestBean ==null) {
            mListRequestBean = new ListRequestBean();
        }
        ObjRequestBean objRequestBean=new ObjRequestBean();
        mListRequestBean.locale= KeyConst.LanguageLocal.ZH_CN;
        mListRequestBean.category="";
        mListRequestBean.pageNumber=pageNumber+"";
        objRequestBean.data =mListRequestBean;
        String requestJson=new Gson().toJson(objRequestBean);
        //  mArrayList.clear();
        //  adapter.notifyDataSetChanged();
        MyHttpUtils.postJson(Const.PRODUCT_FIND,true, null, requestJson, new MysalesCallBack() {
            @Override
            public void onError(Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {

                Log.e("alllist","myway--list"+response.toString());
                if (!StringUtils.isEmpty(response)){
                    try {
                        GetProductListBean getAccessoryListBean = new Gson().fromJson(response,
                                GetProductListBean.class);
                        if (getAccessoryListBean.status.equals("1")){
                            if (getAccessoryListBean.datas!=null&&getAccessoryListBean.datas.size()>0){
                                mArrayList.clear();
                                if (!mArrayList.contains(getAccessoryListBean.datas)) {
                                    mArrayList.addAll(getAccessoryListBean.datas);
                                    adapter.notifyDataSetChanged();
                                    isLoadMore=getAccessoryListBean.datas.size()> (KeyConst.ListD.MAX_RESULTS-1);
                                }
                            }
                        }

                    }catch (JsonSyntaxException e){

                    }catch (IllegalStateException e){

                    }

                }
            }

            @Override
            public void doAffterRequestCall() {
            }
        });
    }


    protected void initEvent() {
        SearchProduct();

          WrapContentLinearLayoutManager layoutManager =new WrapContentLinearLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isLoadMore) {
                        //TODO 加载更多
                        isLoadMore = false;
                            pageNumber++;
                            adapter.notifyDataSetChanged();
                            SearchProduct();
                        }
                    }
                }


        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.show_product_back:

//                if (backFragment==null){
//                    backFragment=new BackFragment();
//                }
//                backFragment.CallBackSuccess(new BackFragment.IsSuccessCallBack() {
//                    @Override
//                    public void OnIsSuccessCallBack() {
//                        finish();
//                    }
//                });
//                backFragment.show(this.getFragmentManager(),"showgetback");


                View          view          = View.inflate(this, R.layout.dialog_passwored, null);
                SKeyboardView sKeyboardView = view.findViewById(R.id.s_keyboard_view);
                mEditView = view.findViewById(R.id.edit_view);

                LinearLayout llKeyboard = view.findViewById(R.id.ll_keyboard);
                mEditView.setEditView(llKeyboard, sKeyboardView, true);

                iniEditView();

                mViewDialog = AlertUtils.showViewDialog(this, true, view);
                mEditView.requestFocus();

                break;
            case R.id.tv_product_determine:
                if (toDetailList!=null&&toDetailList.size()>0) {
                    Intent intent = new Intent(this, ToProductDetailAct.class);
                    intent.putParcelableArrayListExtra("product", toDetailList);
                    startActivity(intent);
                }else
                    Toast.makeText(this,"请至少勾选一种商品！",Toast.LENGTH_SHORT).show();
                break;

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCheckData(Map<String,ProductItemBean> list){
        Log.e("event--","product--"+list.size());
        if (toDetailList==null)
            toDetailList= new ArrayList<>();
        toDetailList.clear();
        Iterator iter = list.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            ProductItemBean val = (ProductItemBean) entry.getValue();
            Log.e("event--","product-name-"+key+val.productName);
            toDetailList.add(val);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    private void iniEditView() {
        mEditView.setText("");
        mEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String msg = mEditView.getText()
                        .toString()
                        .trim();
                if (!StringUtils.isEmpty(msg) && msg.equals(password)) {
                    finish();

                //    UIUtils.activityBackToMain(MainActivity.this);
                }
            }
        });
    }


}
