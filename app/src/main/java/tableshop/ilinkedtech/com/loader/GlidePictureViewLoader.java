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
import android.view.View;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoaderInterface;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.views.MagicImageView;

public class GlidePictureViewLoader
        implements ImageLoaderInterface<View>
{
    @Override
    public void displayImage(Context context, Object path, View view) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */

        //Glide 加载图片简单用法
        MagicImageView imageView=view.findViewById(R.id.magic_view);
        Glide.with(context).load(path).into(imageView);
    }




    @Override
    public View createImageView(Context context) {
        View view =View.inflate(UIUtils.getContext(), R.layout.picture_view, null);
        return view;
    }
}
