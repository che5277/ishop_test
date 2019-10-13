package tableshop.ilinkedtech.com.others.photos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import java.io.File;

/*
 *  @项目名：  ICan2--kuangjia
 *  @创建者:   Wilson
 *  @包名：    com.ilinkedtech.others.photos
 *  @文件名:   PictureUtil
 *  @创建时间:  2017/4/14 16:02
 *  @描述：    TODO 系统相片处理帮助类
 */
public class PictureUtil {


	/**
	 * 照相
	 * @param context 上下文
	 * @param requestCode 请求码
	 * @return 照片地址的uri
	 */
	public static Uri takePhoto(Activity context, int requestCode) {
		Intent        intent   = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用android自带的照相机
		ContentValues values   = new ContentValues();
		Uri           photoUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		context.startActivityForResult(intent,requestCode);
		return photoUri;
	}

	/**
	 * 自定义照相路径
	 * @param context
	 * @param uri
	 * @param requestCode
	 */
	public static void takePhoto(Activity context, Uri uri, int requestCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用android自带的照相机
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		context.startActivityForResult(intent,requestCode);
	}

	/**
	 * 从相册选取一张照片
	 * @param context 上下文
	 * @param requestCode 请求码
	 */
	public static void pickPhoto(Activity context, int requestCode) {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		context.startActivityForResult(intent, requestCode);
	}


	/**
	 * 裁切图片
	 * @param context 上下文
	 * @param srcUri 原始图片所在uri
	 * @param dstUri 裁切过后要保存到的uri，如果srcuri和desuri一致，将会被覆盖
	 * @param outputXY 输出尺寸
	 * @param requestCode 请求码
	 */
	public static void cropPhoto(Activity context, Uri srcUri, Uri dstUri, int outputXY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(srcUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputXY);
		intent.putExtra("outputY", outputXY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, dstUri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		context.startActivityForResult(intent, requestCode);
	}


	/**
	 * 裁切返回bitmap
	 * @param context 上下文
	 * @param srcUri 源uri
	 * @param outputXY 输出像素
	 * @param requestCode 请求码
     */
	public static void cropPhotoRetrunBitmap(Activity context, Uri srcUri, int outputXY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(srcUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputXY);
		intent.putExtra("outputY", outputXY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		context.startActivityForResult(intent, requestCode);
	}

	public static void cropPhotoRetrunBitmap(Activity context, String path, int outputXY, int requestCode) {

		File   mTempFile  = new File(path);
		Intent intent     = new Intent("com.android.camera.action.CROP");
		Uri    contentUri =null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", mTempFile);
			intent.setDataAndType(contentUri, "image/*");
		} else {
			intent.setDataAndType(Uri.fromFile(mTempFile), "image/*");
		}
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputXY);
		intent.putExtra("outputY", outputXY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

		context.startActivityForResult(intent, requestCode);

	}
	public static void cropPhotoRetrunBitmap(Activity context, boolean isCircle, String path, int outputXY, int requestCode) {

		File   mTempFile  = new File(path);
		Intent intent     = new Intent("com.android.camera.action.CROP");
		Uri    contentUri =null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", mTempFile);
			intent.setDataAndType(contentUri, "image/*");
		} else {
			intent.setDataAndType(Uri.fromFile(mTempFile), "image/*");
		}
		if (isCircle){
			//TODO 选择头像
			intent.putExtra("crop", "true");//
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", outputXY);
			intent.putExtra("outputY", outputXY);
		}else {
			//TODO 选择证件
			intent.putExtra("crop", "true");//
			intent.putExtra("aspectX", 0.4);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 180);
			intent.putExtra("outputY", 270);
		}

		intent.putExtra("scale", true);//是否允许缩放
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

		context.startActivityForResult(intent, requestCode);

	}



	public static void cropPhotoRetrunBitmap(Fragment context, boolean isCircle, String path, int outputXY, int requestCode) {

		File mTempFile = new File(path);
		Intent intent = new Intent("com.android.camera.action.CROP");
		Uri contentUri=null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			contentUri = FileProvider.getUriForFile(context.getContext(), context.getContext().getPackageName() + ".fileprovider", mTempFile);
			intent.setDataAndType(contentUri, "image/*");
		} else {
			intent.setDataAndType(Uri.fromFile(mTempFile), "image/*");
		}
		if (isCircle){
			//TODO 选择头像
			intent.putExtra("crop", "true");//
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", outputXY);
			intent.putExtra("outputY", outputXY);
		}else {
			//TODO 选择证件
			intent.putExtra("crop", "true");//
			intent.putExtra("aspectX", 0.4);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 180);
			intent.putExtra("outputY", 270);
		}

		intent.putExtra("scale", true);//是否允许缩放
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

		context.startActivityForResult(intent, requestCode);

	}




	/**
	 * 插入bitmap到相册
	 * @param context 上下文
	 * @param bitmap bitmap
	 */
	public static void insertBitmapToGallery(Activity context, Bitmap bitmap) {
		MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", "");
	}


/*


	*/
/**
	 * 把bitmap转换成String
	 * @param filePath 图片路径
	 * @param reqWidth 预期宽度
	 * @param reqHeight 预期高度
	 * @return byte
	 *//*

	public static String bitmapToString(String filePath,int reqWidth,int reqHeight) {
		Bitmap bm = BitmapUtil.decodeSmallBitmap(filePath, reqWidth, reqHeight);
		return Base64.encodeToString(BitmapUtil.bitmapToByte(bm), Base64.DEFAULT);
		
	}

	*/
/**
	 * 把bitmap转换成Byte
	 * @param filePath 图片路径
	 * @param reqWidth 预期宽度
	 * @param reqHeight 预期高度
	 * @return byte
	 *//*

	public static byte[] bitmapToByte(String filePath,int reqWidth,int reqHeight) {
		Bitmap bm = BitmapUtil.decodeSmallBitmap(filePath, reqWidth, reqHeight);
		return BitmapUtil.bitmapToByte(bm);

	}
*/


	/**
	 * 通知图库刷新
	 * @param context 上下文
	 * @param path 路径
	 */
	public static void notifyGallery(Context context, String path) {
		File f          = new File(path);
		Uri  contentUri = Uri.fromFile(f);
		notifyGallery(context,contentUri);
	}

	/**
	 * 通知图库刷新
	 * @param context 上下文
	 * @param uri 注意uri必须为file类型 Uri.fromFile();
	 */
	public static void notifyGallery(Context context, Uri uri) {
		Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		mediaScanIntent.setData(uri);
		context.sendBroadcast(mediaScanIntent);
	}



}
