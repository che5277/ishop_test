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
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youth.banner.loader.ImageLoaderInterface;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.beans.main.CarDetailBean;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.ishop.CarDetailActivity;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.viewholders.CarListItemHolder;

public class GlideViewLoader
        implements ImageLoaderInterface<View>
{
    private CarListItemHolder mCarDetailListItemHolder;
    //    @Override
//    public void displayImage(Context context, Object path, ImageView imageView) {
//        /**
//         注意：
//         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
//         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
//         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
//         切记不要胡乱强转！
//         */
//
//        //Glide 加载图片简单用法
//        Glide.with(context).load(path).into(imageView);
//    }

    public Context mContext;

    @Override
    public void displayImage(Context context, Object path, View imageView) {
        this.mContext=context;
        mCarDetailListItemHolder = new CarListItemHolder(imageView);
        LogUtils.e("path:"+path.toString());
        if (path instanceof CarDetailBean){
            final CarDetailBean itemBean = (CarDetailBean) path;
            mCarDetailListItemHolder.refreshView(itemBean);
            mCarDetailListItemHolder.mContentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtil.toast(itemBean.modelName);
                    Intent intent = new Intent(UIUtils.getContext(), CarDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(KeyConst.BundleIntentKey.DATA_JSON, new Gson().toJson(
                            itemBean));
                    intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_ID, itemBean.uid);
                    intent.putExtra(KeyConst.BundleIntentKey.VEHICLE_FLAG,itemBean.flag);
                    mContext.startActivity(intent);
                }
            });
        }else {
            Glide.with(context)
                 .load(path)
                 .into(mCarDetailListItemHolder.mIvIcon);
        }

    }

    @Override
    public View createImageView(Context context) {
        View              view                    =View.inflate(UIUtils.getContext(), R.layout.item_recommend_car, null);
        return view;
    }
}
