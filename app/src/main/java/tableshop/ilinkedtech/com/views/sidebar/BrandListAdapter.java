package tableshop.ilinkedtech.com.views.sidebar;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cc.solart.turbo.BaseViewHolder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.responbeans.GetBrandList;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;


public class BrandListAdapter
        extends BaseRecyclerAdapter<GetBrandList.BrandPageBean.ValueBean>
{

//    public BrandListAdapter(Context context) {
//        super(context);
//    }

    public BrandListAdapter( ArrayList<GetBrandList.BrandPageBean.ValueBean> data) {
        super(data);
    }

//    @Override
//    protected int getDefItemViewType(int position) {
//        GetBrandList.BrandPageBean.ValueBean city = getItem(position);
//        return city.type;
//    }

    @Override
    public int getItemViewType(int position) {
        GetBrandList.BrandPageBean.ValueBean city = mArrayList.get(position);
        return city.type;
    }

//    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new CityHolder(View.inflate(UIUtils.getContext(),R.layout.item_wave_contact, parent));
        else
            return new PinnedHolder(View.inflate(UIUtils.getContext(),R.layout.item_pinned_header, parent));
    }


//    @Override
    protected void convert(BaseViewHolder holder, final GetBrandList.BrandPageBean.ValueBean item) {
        if (holder instanceof CityHolder) {
            CityHolder cityHolder = (CityHolder) holder;
            cityHolder.city_name.setText(StringUtils.checkString(item.brandName));
            if (!StringUtils.isEmpty(item.brandId)) {
                cityHolder.car_Count.setText("(" + item.count + ")");
                cityHolder.iv_Icon.setVisibility(View.VISIBLE);
            }else {
                cityHolder.car_Count.setText("");
                cityHolder.iv_Icon.setVisibility(View.GONE);
            }
        }else {
            String letter = item.pys.substring(0, 1);
            ((PinnedHolder) holder).city_tip.setText(letter);
        }
    }

    public int getLetterPosition(String letter){
        for (int i = 0 ; i < mArrayList.size(); i++){
            if(mArrayList.get(i).type ==1 && mArrayList.get(i).pys.equals(letter)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new CityHolder(View.inflate(UIUtils.getContext(),R.layout.item_wave_contact, parent));
        else
            return new PinnedHolder(View.inflate(UIUtils.getContext(),R.layout.item_pinned_header, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GetBrandList.BrandPageBean.ValueBean item = mArrayList.get(position);
        if (holder instanceof CityHolder) {
            CityHolder cityHolder = (CityHolder) holder;
            cityHolder.city_name.setText(StringUtils.checkString(item.brandName));
            if (!StringUtils.isEmpty(item.brandId)) {
                cityHolder.car_Count.setText("(" + item.count + ")");
                cityHolder.iv_Icon.setVisibility(View.VISIBLE);
            }else {
                cityHolder.car_Count.setText("");
                cityHolder.iv_Icon.setVisibility(View.GONE);
            }
            cityHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.toast(item.brandName+"3");
                }
            });
        }else {
            String letter = item.pys.substring(0, 1);
            ((PinnedHolder) holder).city_tip.setText(letter);
        }
    }

    class CityHolder extends BaseViewHolder {

        TextView  city_name;
        TextView  car_Count;
        ImageView iv_Icon;
        View ll_view;

        public CityHolder(View view) {
            super(view);
            city_name = itemView.findViewById(R.id.tv_brand_name);
            car_Count = itemView.findViewById(R.id.tv_brand_car_count);
            iv_Icon= itemView.findViewById(R.id.iv_icon);
//            ll_view= itemView.findViewById(R.id.ll_view);
        }
    }


    class PinnedHolder extends BaseViewHolder {

        TextView city_tip;

        public PinnedHolder(View view) {
            super(view);
            city_tip = itemView.findViewById(R.id.city_tip);
        }
    }
}
