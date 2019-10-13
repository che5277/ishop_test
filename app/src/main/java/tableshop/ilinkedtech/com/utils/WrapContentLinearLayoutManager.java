package tableshop.ilinkedtech.com.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by TO on 2018/4/26.
 */

public class WrapContentLinearLayoutManager extends GridLayoutManager{
    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WrapContentLinearLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public WrapContentLinearLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        }catch (Exception e){
            Log.e("problem", "meet a IOOBE in RecyclerView");

        }
    }
}
