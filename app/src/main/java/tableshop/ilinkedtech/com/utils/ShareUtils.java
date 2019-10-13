package tableshop.ilinkedtech.com.utils;

/*
 *  @项目名：  iShop 
 *  @包名：    ishop.ilinkedtech.com.utils
 *  @文件名:   ShareUtils
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/14 20:12
 *  @描述：    TODO 第三方分享工具类
 */

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.callBacks.IShopShareListener;
import tableshop.ilinkedtech.com.consts.Const;

public class ShareUtils {


    private static final String TAG = "ShareUtils";

    /**
     * @param mActivity
     * @param msg 做短信分享用的
     * @param shareURL  分享图片的url
     * @param vinNo 用于获取分享的url
     * @param carName 微信分享的标题
     */
    public static void showShareListView(Activity mActivity, String msg, String shareURL,String vinNo,String carName){
        File file=new File(FileUtil.getImgFilePath(shareURL));
        UMImage imageurl = new UMImage(mActivity,file);
        if (file.exists()){
            imageurl.setThumb(new UMImage(mActivity, file));
        }else {
            imageurl.setThumb(new UMImage(mActivity, R.mipmap.icon));
        }
        //====================TODO share url begin===========================
        if (!StringUtils.isEmpty(vinNo)){
            shareURL= Const.getBaseUrl(Const.BASE_HOST)+Const.WEB_CAR_DETAIL_URL+vinNo;
        }else {
            shareURL=Const.getBaseUrl(Const.BASE_HOST)+Const.WEB_CAR_DETAIL_URL;
        }
        LogUtils.d(TAG,"share url:"+shareURL);
        UMWeb umWeb=new UMWeb(shareURL);
        umWeb.setTitle(StringUtils.checkString(carName));
        umWeb.setThumb(imageurl);
        umWeb.setDescription("想获取更多详细的优惠信息请下载"+mActivity.getString(R.string.app_title)+" 手机客户端");
        //====================TODO share url end===========================
        new ShareAction(mActivity).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE,SHARE_MEDIA.SMS,SHARE_MEDIA.EMAIL)
                                  .setPlatform(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform)
                                  .withText(msg)
                                  .withMedia(umWeb)
                                  .setCallback(new IShopShareListener()).open();
    }


    public static void showShareListViewNew(Activity mActivity,String titleName, String descriptionStr,String shareImageURL, String main_url,String key){
        File file=new File(FileUtil.getImgFilePath(MD5Util.MD5(shareImageURL)));
        UMImage imageurl = new UMImage(mActivity,file);
        if (file.exists()){
            imageurl.setThumb(new UMImage(mActivity, file));
        }else {
            imageurl.setThumb(new UMImage(mActivity, R.mipmap.icon));
        }
        //====================TODO share url begin===========================
        if (!StringUtils.isEmpty(key)){
            main_url= Const.getBaseUrl(Const.BASE_HOST)+main_url+key;
        }else {
            main_url=Const.getBaseUrl(Const.BASE_HOST)+main_url;
        }
        LogUtils.d(TAG,"share url:"+shareImageURL);
        UMWeb umWeb=new UMWeb(main_url);
        umWeb.setTitle(StringUtils.checkString(titleName));
        umWeb.setThumb(imageurl);
        if (StringUtils.isEmpty(descriptionStr)){
            descriptionStr="想获取更多详细的优惠信息请下载"+mActivity.getString(R.string.app_title)+" 手机客户端";
        }
        umWeb.setDescription(descriptionStr);
        //====================TODO share url end===========================
        new ShareAction(mActivity).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE,SHARE_MEDIA.SMS,SHARE_MEDIA.EMAIL)
                                  .setPlatform(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform)
                                  .withMedia(umWeb)
                                  .setCallback(new IShopShareListener()).open();
    }

}
