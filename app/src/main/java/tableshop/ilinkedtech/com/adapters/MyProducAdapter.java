package tableshop.ilinkedtech.com.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.beans.main.ProductItemBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.ProductDetailActivity;
import tableshop.ilinkedtech.com.utils.RxTextUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.ProductItemHolder;

/**
 * Created by TO on 2018/4/26.
 */

public class MyProducAdapter extends RecyclerView.Adapter<MyProducAdapter.MyViewHold> {

    private Activity mActivity;
    private List<ProductItemBean> mList;
    private List<ProductItemBean> mCallList;
    private Map<String,ProductItemBean> map;
    private SparseBooleanArray mCheckStates= new SparseBooleanArray();


    public MyProducAdapter(Activity activity , List<ProductItemBean> list){
        mActivity=activity;
        mList=list;
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_view,null);

        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHold myViewHold, final int i) {


            final ProductItemBean productItemBean = mList.get(i);
            if (productItemBean == null) {
                return;
            }
            if (myViewHold.choice.getTag(i) == null) {
                myViewHold.choice.setTag(i);

            }

            myViewHold.choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos = (int) buttonView.getTag();
                    if (mCallList == null)
                        mCallList = new ArrayList<>();

                    if (map == null)
                        map = new HashMap<>();


                    if (isChecked) {
                        map.put(pos + "i", productItemBean);
                        mCheckStates.put(pos, true);
                        mCallList.add(productItemBean);
                    } else {
                      //  map.remove(pos + "i", productItemBean);

                        Iterator<Map.Entry<String,ProductItemBean>> it=map.entrySet().iterator();
                        while (it.hasNext()){

                            Map.Entry<String,ProductItemBean> entry = it.next();
                            if (entry.getKey().equals(pos+""))
                                it.remove();
                        }


                        mCheckStates.delete(pos);
                        mCallList.remove(productItemBean);
                    }
                    EventBus.getDefault().post(map);

                }
            });
            if (mCheckStates != null && mCheckStates.size() > 0) {
                myViewHold.choice.setChecked(mCheckStates.get(i, false));
            }

            Glide.with(myViewHold.mIvIcon.getContext())
                    .load(productItemBean.productDefaultImgUrl)
                    .error(R.drawable.noproduct)
                    .into(myViewHold.mIvIcon);

            RxTextUtils.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                    .append("¥ ")
                    .append(productItemBean.sellingPrice + "").setForegroundColor(UIUtils.getColor(R.color.price_color)).setProportion(1.2f)
                    .into(myViewHold.mTvPrice);
            myViewHold.mTvProductName.setText(StringUtils.checkString(productItemBean.productName));

            myViewHold.mLlContentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   goToProductDetailAct(productItemBean);
                }
            });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHold extends RecyclerView.ViewHolder{

        public CheckBox choice;
        ImageView mIvIcon;
        ImageView    mIvProductState;
        TextView mTvProductName;
        TextView     mTvPrice;
        public LinearLayout mLlContentLayout;
        public MyViewHold(View itemView) {
            super(itemView);
            choice=itemView.findViewById(R.id.cb_choice);
            mIvIcon=itemView.findViewById(R.id.iv_icon);
            mIvProductState=itemView.findViewById(R.id.iv_product_state);
            mTvProductName=itemView.findViewById(R.id.tv_product_name);
            mTvPrice=itemView.findViewById(R.id.tv_price);
            mLlContentLayout=itemView.findViewById(R.id.ll_content_layout);

        }
    }

    private void goToProductDetailAct(ProductItemBean productItemBean) {
        Intent intent =new Intent(mActivity, ProductDetailActivity.class);
        intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON,productItemBean);
        intent.putExtra(KeyConst.BundleIntentKey.PRODUCT_ID, productItemBean.uid);
        mActivity.startActivity(intent);
        UIUtils.activityAnimInt(mActivity);
    }

}
