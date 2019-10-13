package tableshop.ilinkedtech.com.others.photos;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/*
 *  @项目名：  ICan2--kuangjia
 *  @创建者:   Wilson
 *  @包名：    com.ilinkedtech.others.photos
 *  @文件名:   SquareRelativeLayout
 *  @创建时间:  2017/4/14 16:02
 *  @描述：    TODO
 */
public class SquareRelativeLayout
        extends RelativeLayout
{
    public SquareRelativeLayout(Context context) {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
