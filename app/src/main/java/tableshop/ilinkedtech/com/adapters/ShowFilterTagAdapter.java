package tableshop.ilinkedtech.com.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.beans.RulesItem;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.flowlayout.FlowLayout;
import tableshop.ilinkedtech.com.views.flowlayout.TagAdapter;
import tableshop.ilinkedtech.com.views.flowlayout.TagFlowLayout;

/*
 *  @项目名：  trunk 
 *  @包名：    com.ilinkedtech.adapters
 *  @文件名:   CustomerTagAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/5/5 11:35
 *  @描述：    TODO    标签的adapter
 */
public class ShowFilterTagAdapter
        extends TagAdapter<RulesItem>
{
    TagFlowLayout mIdFlowlayout;
    public static int FILTER =0;
    public static int SHOW=2;
    public static int PRODUCT_TYPE=3;
    private int type;
    public ShowFilterTagAdapter(ArrayList<RulesItem> datas, TagFlowLayout mIdFlowlayout) {
        super(datas);
        this.mIdFlowlayout=mIdFlowlayout;
    }

    public ShowFilterTagAdapter(RulesItem[] datas) {
        super(datas);
    }

    public void setType(int type){
        this.type=type;
    }

    @Override
    public View getView(FlowLayout parent, int position, RulesItem rulesItem) {
        TextView tv =null;
        if (type==SHOW){
            tv = (TextView) LayoutInflater.from(UIUtils.getContext())
                                          .inflate(R.layout.tv_show, mIdFlowlayout, false);
        }
        else if (type==PRODUCT_TYPE){
            tv = (TextView) LayoutInflater.from(UIUtils.getContext())
                                          .inflate(R.layout.tv_filter_product_detail, mIdFlowlayout, false);
        }else {
            tv = (TextView) LayoutInflater.from(UIUtils.getContext())
                                          .inflate(R.layout.tv_filter, mIdFlowlayout, false);
        }
        if (rulesItem!=null) {
            tv.setText(StringUtils.checkString(rulesItem.displayText));
        }
        return tv;
    }
}
