package tableshop.ilinkedtech.com.utils.imag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import tableshop.ilinkedtech.com.callBacks.MyICanFileCallBack;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.FileUtil;
import tableshop.ilinkedtech.com.utils.LogUtils;
import tableshop.ilinkedtech.com.utils.MD5Util;
import tableshop.ilinkedtech.com.utils.MyHttpUtils;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.UIUtils;

/*
 *  @项目名：  ICan2 
 *  @包名：    com.ilinkedtech.others
 *  @文件名:   ImageLoadUtils
 *  @创建者:   Wilson
 *  @创建时间:  2017/4/4 13:59
 *  @描述：    TODO
 */
public class ImageLoadUtils {
    private static final String TAG = "ImageLoadUtils";

    public static void setImageSource(Activity mActivity, String url, ImageView imageView, Object object){
        into(imageView,url);
    }
    public static void into(ImageView imageView, Bitmap key) {
        if (key!=null&&key.getAllocationByteCount()>10) {
            imageView.setImageBitmap(key);
        }
    }
    public static void into(ImageView imageView, String url) {
//        into(UIUtils.getContext(), imageView, url, KeyConst.CarImageType.FIT_CENTER);
                Glide.with(UIUtils.getContext()).load(url).fitCenter().into(imageView);
    }

    public static void into(ImageView imageView, String key, int scaleType) {
        into(UIUtils.getContext(), imageView, key, scaleType);
    }

    public static void into(Context context, ImageView imageView, String imageUrl, int scaleType) {
        if (StringUtils.isEmpty(imageUrl)||imageView==null) {
            return;
        }
        String path=FileUtil.getImgFilePath(MD5Util.MD5(imageUrl));
        File                file = new File(path);
        if (file.exists() && file.length() > 10) {//TODO centerCrop  fitCenter
            DrawableTypeRequest load = Glide.with(context)
                                            .load(file);
            intoView(imageView, scaleType, load);
        } else {
            getImageFile(imageUrl,imageView);
        }

    }

    public static void getImageFile(String imageUrl,
                                    final ImageView imageView)
    {
        MyHttpUtils.getFile(imageUrl, new MyICanFileCallBack(FileUtil.getImageDir(),MD5Util.MD5(imageUrl)+FileUtil.SUFFIX_JPG) {
            @Override
            public void onResponse(File respone, int id) {
                if (respone!=null&&imageView!=null)
                    Glide.with(UIUtils.getContext()).load(respone).fitCenter().into(imageView);
            }

            @Override
            public void onError(Exception e, int id) {

            }
        });
    }


    public static Bitmap getImageBitmap(String url,int width,int height){
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(UIUtils.getContext())
                          .load(url)
                          .asBitmap()
                          .fitCenter()
                          .into(width,height)
                          .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static void intoView(ImageView imageView, int scaleType, DrawableTypeRequest load) {
        if (load != null) {
            switch (scaleType) {
                case KeyConst.CarImageType.FIT_CENTER:
                    load.fitCenter()
                        .into(imageView);
                    break;
                case KeyConst.CarImageType.CENTER_CROP:
                    load.centerCrop()
                        .into(imageView);
                    break;
                default:
                    load.into(imageView);
                    break;

            }
        }
    }


    /**
     * 将本地图片转bitmap
     * @param filePath
     * @return
     */
    public static Bitmap getBitmap(String filePath) {
        if (!StringUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file != null && file.exists()&&file.length() > 10) {
                return BitmapFactory.decodeFile(filePath);
            }
        }
        return null;

    }


    /**
     * 保存图片
     * @param bm Bitmap转File
     * @return
     */
    public static File saveBitmap(Bitmap bm, String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Uri uri = Uri.fromFile(f);
            UIUtils.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            LogUtils.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            f = null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            f = null;
        }
        return f;
    }

    public static Bitmap stringtoBitmap(String string){
        //将字符串转换成Bitmap类型
        Bitmap bitmap =null;
        try {
            if (!StringUtils.isEmpty(string)) {
                byte[] bitmapArray;
                bitmapArray = Base64.decode(string, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static File base64stringToFile(String base64string, String filePath) {
        if (StringUtils.isEmpty(base64string)){
            return null;
        }
        return saveBitmap(stringtoBitmap(base64string),filePath);
    }

    public static Bitmap getBitMap(String url,int width,int height) {
        if (StringUtils.isEmpty(url)){
            return null;
        }
        Bitmap bitmap =null;
        String key =MD5Util.MD5(url);
        String filePath=FileUtil.getImgFilePath(key);
        File file =new File(filePath);
        if (file!=null&&file.exists()){
            bitmap = getBitmap(filePath);
        }
        if (bitmap!=null){
            return bitmap;
        }
        try {
            bitmap = Glide.with(UIUtils.getContext())
                          .load(url)
                          .asBitmap()
                          .fitCenter()
                          .into(width,height)
                          .get();
            saveBitmap(bitmap.copy(bitmap.getConfig(), false),filePath);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.toString());
        }
        return bitmap;
    }



    public static Bitmap getVideoFirstBitmap(String videoUrl){
        Bitmap bitmap=null;
        //TODO 获取视频的第一张图片
        if (!StringUtils.isEmpty(videoUrl)) {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(videoUrl, new HashMap<String, String>());
            bitmap = media.getFrameAtTime();
        }
        return bitmap;
    }
}
