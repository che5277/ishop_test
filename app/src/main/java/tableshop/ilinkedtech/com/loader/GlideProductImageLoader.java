package tableshop.ilinkedtech.com.loader;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.loader
 *  @文件名:   GlideImageLoader
 *  @创建者:   Administrator
 *  @创建时间:  2017/7/17 21:24
 *  @描述：    TODO
 */

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import tableshop.ilinkedtech.com.R;

public class GlideProductImageLoader
        extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */

        //Glide 加载图片简单用法
//        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Glide.with(context).load(path).error(R.drawable.noproduct).into(imageView);
    }

//    @Override
//    public ImageView createImageView(Context context) {
//        RatioImageView ratioImageView =new RatioImageView(context);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        ratioImageView.setLayoutParams(layoutParams);
//        return ratioImageView;
//    }
}
