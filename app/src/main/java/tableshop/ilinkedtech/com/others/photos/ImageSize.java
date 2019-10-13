package tableshop.ilinkedtech.com.others.photos;

/*
 *  @项目名：  ICan2--kuangjia
 *  @创建者:   Wilson
 *  @包名：    com.ilinkedtech.others.photos
 *  @文件名:   ImageSize
 *  @创建时间:  2017/4/14 16:02
 *  @描述：    TODO
 */
public class ImageSize {
	private int width;
	private int height;
	public ImageSize() {
	}

	public ImageSize(int width, int height) {
		this.width = width;
		this.height = height;
	}



	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}