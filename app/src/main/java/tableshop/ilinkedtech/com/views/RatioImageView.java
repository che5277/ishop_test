package tableshop.ilinkedtech.com.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import tableshop.ilinkedtech.com.R;


/*
 *  @文件名:   RatioImageView
 *  @创建者:   Wilson
 *  @创建时间:  2018/4/8 15:01
 *  @描述：    TODO
 */

@SuppressLint("AppCompatCustomView")
public class RatioImageView
        extends ImageView
{

    private float ratio;

    public RatioImageView(Context context) {
        this(context, null);

    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
            //根据属性名称获取对应的值，属性名称的格式为类名_属性名
            ratio = typedArray.getFloat(R.styleable.RatioImageView_ratio, 0.0f);
            typedArray.recycle();
        }
        if (ratio==0){
            ratio=9.00f/16.00f;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽度的模式和尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取高度的模式和尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //宽确定，高不确定
        if(widthMode == MeasureSpec.EXACTLY&&heightMode!=MeasureSpec.EXACTLY&&ratio!=0){
            heightSize = (int) (widthSize*ratio+0.5f);//根据宽度和比例计算高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }else if(widthMode!=MeasureSpec.EXACTLY&&heightMode==MeasureSpec.EXACTLY&ratio!=0){
            widthSize = (int) (heightSize/ratio+0.5f);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.EXACTLY);
        }else{
            throw new RuntimeException("无法设定宽高比");
        }
        //必须调用下面的两个方法之一完成onMeasure方法的重写，否则会报错
        //        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }
    /**
     * 设置宽高比
     * @param ratio
     */
    public void setRatio(float ratio){
        this.ratio = ratio;
    }


}
