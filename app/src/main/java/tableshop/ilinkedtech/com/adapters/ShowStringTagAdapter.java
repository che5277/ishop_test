package tableshop.ilinkedtech.com.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.flowlayout.FlowLayout;
import tableshop.ilinkedtech.com.views.flowlayout.TagAdapter;
import tableshop.ilinkedtech.com.views.flowlayout.TagFlowLayout;

/*
 *  @项目名：  trunk 
 *  @包名：    com.ilinkedtech.adapters
 *  @文件名:   ShowStringTagAdapter
 *  @创建者:   Wilson
 *  @创建时间:  2017/5/5 11:35
 *  @描述：    TODO    标签的adapter
 */
public class ShowStringTagAdapter
        extends TagAdapter<String>
{
    TagFlowLayout  mIdFlowlayout;
    public static int FILTER =0;
    public static int SHOW=2;
    private int type;
    public ShowStringTagAdapter(ArrayList<String> datas, TagFlowLayout mIdFlowlayout) {
        super(datas);
        this.mIdFlowlayout=mIdFlowlayout;
    }

    public ShowStringTagAdapter(String[] datas) {
        super(datas);
    }

    public void setType(int type){
        this.type=type;
    }

    @Override
    public View getView(FlowLayout parent, int position, String rulesItem) {
        TextView tv =null;
        if (type==SHOW){
            tv = (TextView) LayoutInflater.from(UIUtils.getContext())
                                          .inflate(R.layout.tv_show, mIdFlowlayout, false);
        }else {
            tv = (TextView) LayoutInflater.from(UIUtils.getContext())
                                          .inflate(R.layout.tv_filter, mIdFlowlayout, false);
        }
        tv.setText(rulesItem);
        return tv;
    }
}
