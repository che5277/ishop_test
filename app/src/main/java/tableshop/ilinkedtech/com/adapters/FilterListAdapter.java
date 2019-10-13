package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.adapters
 *  @文件名:   ShopListAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 15:59
 *  @描述：    TODO
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseRecyclerAdapter;
import tableshop.ilinkedtech.com.beans.RulesItem;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.SeriesListActivity;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.FilterListItemViewHolder;

public class FilterListAdapter
        extends BaseRecyclerAdapter<RulesItem> {
    Activity mActivity;
    public FilterListAdapter(ArrayList<RulesItem> mArrayList, Activity activity) {
        super(mArrayList);
        this.mActivity=activity;
    }

    //设置ViewHolder，并设置条目的点击事件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                     inflate                 = View.inflate(UIUtils.getContext(), R.layout.tv_filter_item, null);
        FilterListItemViewHolder orderListItemViewHolder =new FilterListItemViewHolder(inflate);
        return orderListItemViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==(mArrayList.size()-1)){
            return 1;
        }
        return super.getItemViewType(position);
    }

    //绑定视图，加载数据到视图，避免复用问题
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FilterListItemViewHolder filterListItemViewHolder =(FilterListItemViewHolder)holder;
        final RulesItem     rulesItem            = mArrayList.get(position);
        if (rulesItem!=null){
            filterListItemViewHolder.mTvContent.setText(StringUtils.checkString(rulesItem.displayText));
        }
        int itemViewType = getItemViewType(position);
        if (itemViewType==1){
            filterListItemViewHolder.mTvContent.setBackgroundColor(Color.TRANSPARENT);
            filterListItemViewHolder.mTvContent.setTextColor(UIUtils.getColor(R.color.tms_blue));

        }else {
            filterListItemViewHolder.mTvContent.setTextColor(UIUtils.getColor(R.color.car_color_text));
            filterListItemViewHolder.mTvContent.setBackground(UIUtils.getDrawable(R.drawable.selector_filter_bg));
        }
        filterListItemViewHolder.mTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(mActivity, SeriesListActivity.class);
                intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, rulesItem.requestBean);
                if (position==(mArrayList.size()-1)){
                    intent.putExtra(KeyConst.BundleIntentKey.SHOW_FILTER_VIEW, true);
                }else {
                    intent.putExtra(KeyConst.BundleIntentKey.POSITION, rulesItem.mFilterItemTypeBean);
                }
                mActivity.startActivity(intent);
                UIUtils.activityAnimInt(mActivity);
            }
        });

    }


}


