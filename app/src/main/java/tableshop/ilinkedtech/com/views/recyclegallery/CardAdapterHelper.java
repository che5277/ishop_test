package tableshop.ilinkedtech.com.views.recyclegallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import tableshop.ilinkedtech.com.utils.UIUtils;

/**
 * adapter中调用onCreateViewHolder, onBindViewHolder
 * Created by jameson on 9/1/16.
 */
public class CardAdapterHelper {
    private int mPagePadding = 15;
    private int mShowLeftCardWidth = 15;

    public void onCreateViewHolder(ViewGroup parent, View itemView) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
//        lp.width = parent.getWidth() - ScreenUtil.dip2px(itemView.getContext(), 2 * (mPagePadding + mShowLeftCardWidth));
        lp.width = parent.getWidth() - UIUtils.dp2Px(2 * (mPagePadding + mShowLeftCardWidth));
        itemView.setLayoutParams(lp);
    }

    public void onBindViewHolder(View itemView, final int position, int itemCount) {
        int padding = UIUtils.dp2Px( mPagePadding);
        itemView.setPadding(padding, 0, padding, 0);
        int leftMarin = position == 0 ? padding + UIUtils.dp2Px(mShowLeftCardWidth) : 0;
        int rightMarin = position == itemCount - 1 ? padding + UIUtils.dp2Px(mShowLeftCardWidth) : 0;
        setViewMargin(itemView, leftMarin, 0, rightMarin, 0);
    }

    private void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }
}
