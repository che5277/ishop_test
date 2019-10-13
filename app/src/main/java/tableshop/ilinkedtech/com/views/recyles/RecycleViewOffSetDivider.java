package tableshop.ilinkedtech.com.views.recyles;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import tableshop.ilinkedtech.com.utils.UIUtils;

/**
 * recycleview分割线
 */
public class RecycleViewOffSetDivider
        extends RecyclerView.ItemDecoration {





    //====================TODO wilson add begin=========================
    int left,top,right,bottom;
    public void setItemOffSets(int left,int top,int right,int bottom){
        DisplayMetrics dm = UIUtils.getResources().getDisplayMetrics();
        this.left= (int) (left/dm.density);
        this.top = (int) (top/dm.density);
        this.right = (int) (right/dm.density);
        this.bottom = (int) (bottom/dm.density);
    }
    //====================TODO wilson add end===========================

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(left, top, right, bottom);
    }

}