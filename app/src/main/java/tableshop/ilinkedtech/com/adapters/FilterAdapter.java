package tableshop.ilinkedtech.com.adapters;

/*
 *  @项目名：  trunk 
 *  @包名：    com.ilinkedtech.adapters
 *  @文件名:   FirstSourceAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/10 18:17
 *  @描述：    TODO
 */

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tableshop.ilinkedtech.com.R;

public class FilterAdapter
        extends BaseAdapter
{
    private Context                                                                          mContext;
    private List<String> mList ;

    public FilterAdapter(Context context,
                         List<String> list)
    {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(18);
        textView.setPadding(0,10,0,10);
        String value = mList.get(position);
        textView.setTextColor(mContext.getResources().getColor(R.color.snackbar_bg));
        textView.setText(value);
        return textView;
    }

}
