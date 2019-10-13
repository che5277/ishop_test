package tableshop.ilinkedtech.com.viewholders;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.viewholders
 *  @文件名:   MyViewHolder
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 12:54
 *  @描述：    TODO
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseViewHolder;
import tableshop.ilinkedtech.com.views.channelview.OnDragVHListener;
/**
 * 我的频道
 */
public class MyViewHolder extends BaseViewHolder implements OnDragVHListener {
    public TextView  textView;
    public ImageView imgEdit;
    public View      rlMyItemLayout;
    public MyViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.tv);
        imgEdit = (ImageView) itemView.findViewById(R.id.img_edit);
        rlMyItemLayout = itemView.findViewById(R.id.rl_my_item_layout);
    }

    @Override
    protected void clear() {

    }

    /**
     * item 被选中时
     */
    @Override
    public void onItemSelected() {
        textView.setBackgroundResource(R.drawable.bg_channel_p);
    }

    /**
     * item 取消选中时
     */
    @Override
    public void onItemFinish() {
        textView.setBackgroundResource(R.drawable.bg_channel);
    }
}
