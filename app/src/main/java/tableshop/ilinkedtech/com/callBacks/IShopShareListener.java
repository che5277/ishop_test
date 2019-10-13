package tableshop.ilinkedtech.com.callBacks;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.callBacks
 *  @文件名:   IShopShareListener
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/14 20:13
 *  @描述：    TODO
 */

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import tableshop.ilinkedtech.com.utils.LogUtils;

public class IShopShareListener
        implements UMShareListener
{

    private static final String TAG="IShopShareListener";

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        LogUtils.e(TAG, "onResult share_media:"+share_media.toString());
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        if(throwable!=null){
            LogUtils.e(TAG, "throw:"+throwable.getMessage());
        }

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        LogUtils.e(TAG, "onCancel share_media:"+share_media.toString());
    }
}
