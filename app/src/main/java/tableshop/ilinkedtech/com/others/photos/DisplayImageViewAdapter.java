package tableshop.ilinkedtech.com.others.photos;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

/*
 *  @项目名：  ICan2--kuangjia
 *  @创建者:   Wilson
 *  @包名：    com.ilinkedtech.others.photos
 *  @文件名:   DisplayImageViewAdapter
 *  @创建时间:  2017/4/14 16:02
 *  @描述：    TODO
 */
public abstract class DisplayImageViewAdapter<T> {
    public abstract void onDisplayImage(Context context, ImageView imageView, T t);

    public void onItemImageClick(Context context, int index, List<T> list) {

    }

    public void onImageCheckL(String path, boolean isChecked) {

    }



}