package tableshop.ilinkedtech.com.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by TO on 2018/4/26.
 */

public class MySwRfreshLayout extends SwipeRefreshLayout {
    public MySwRfreshLayout(@NonNull Context context) {
        super(context);
    }

    public MySwRfreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {

        View target =getChildAt(0);
        if (target instanceof AbsListView)
        {
            AbsListView absListView = (AbsListView) target;
            return absListView.getChildCount()>0&&(absListView.getFirstVisiblePosition()>0||absListView.getChildAt(0)
            .getTop()<absListView.getPaddingTop());

        }
        else
        {
            return ViewCompat.canScrollVertically(target,-1);
        }

    }
}
