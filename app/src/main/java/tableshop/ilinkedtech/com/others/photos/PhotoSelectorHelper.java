package tableshop.ilinkedtech.com.others.photos;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;

/*
 *  @项目名：  ICan2--kuangjia
 *  @创建者:   Wilson
 *  @包名：    com.ilinkedtech.others.photos
 *  @文件名:   PhotoSelectorHelper
 *  @创建时间:  2017/4/14 16:02
 *  @描述：    TODO
 */
public class PhotoSelectorHelper {

	private AlbumController albumController;
	private Handler         handler;

	public PhotoSelectorHelper(Context context) {
		albumController = new AlbumController(context);
		handler=new Handler(Looper.getMainLooper());
	}

	public void getReccentPhotoList(final OnLoadPhotoListener listener) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				final List<String> photos = albumController.getCurrent();
				handler.post(new Runnable() {
					@Override
					public void run() {
						listener.onPhotoLoaded(photos);
					}
				});

			}
		}).start();
	}

	/** 获取相册列表 */
	public void getAlbumList(final OnLoadAlbumListener listener) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				final List<AlbumModel> albums = albumController.getAlbums();
				handler.post(new Runnable() {
					@Override
					public void run() {
						listener.onAlbumLoaded(albums);
					}
				});
			}
		}).start();
	}

	/** 获取单个相册下的所有照片信息 */
	public void getAlbumPhotoList(final String name, final OnLoadPhotoListener listener) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				final List<String> photos = albumController.getAlbum(name);
				handler.post(new Runnable() {
					@Override
					public void run() {
						listener.onPhotoLoaded(photos);
					}
				});
			}
		}).start();
	}



	/** 获取本地图库照片回调 */
	public interface OnLoadPhotoListener {
		 void onPhotoLoaded(List<String> photos);
	}

	/** 获取本地相册信息回调 */
	public interface OnLoadAlbumListener {
		 void onAlbumLoaded(List<AlbumModel> albums);
	}

}

