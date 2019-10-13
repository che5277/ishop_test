package tableshop.ilinkedtech.com.views.sidebar;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cc.solart.turbo.BaseTurboAdapter;
import cc.solart.turbo.BaseViewHolder;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;


public class AdapterContactCity
        extends BaseTurboAdapter<CarDetailBean, BaseViewHolder> {

    public AdapterContactCity(Context context) {
        super(context);
    }

    public AdapterContactCity(Context context, List<CarDetailBean> data) {
        super(context, data);
    }

    @Override
    protected int getDefItemViewType(int position) {
        CarDetailBean city = getItem(position);
        return city.type;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new CityHolder(inflateItemView(R.layout.item_wave_contact, parent));
        else
            return new PinnedHolder(inflateItemView(R.layout.item_pinned_header, parent));
    }


    @Override
    protected void convert(BaseViewHolder holder, final CarDetailBean item) {
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
//            ImageLoadUtils.into(cityHolder.iv_Icon,item.seriesDefaultImageURL);
            Glide.with(UIUtils.getContext()).load(item.logoImageURL).error(R.drawable.default_logo).fitCenter().into(cityHolder.iv_Icon);

        }else {
            String letter = item.pys.substring(0, 1);
            ((PinnedHolder) holder).city_tip.setText(letter);
        }
    }

    public int getLetterPosition(String letter){
        for (int i = 0 ; i < getData().size(); i++){
            if(getData().get(i).type ==1 && getData().get(i).pys.equals(letter)){
                return i;
            }
        }
        return -1;
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
