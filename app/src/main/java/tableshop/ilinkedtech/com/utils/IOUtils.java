package tableshop.ilinkedtech.com.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtils.e("IO流的关闭"+e);
			}
		}
		return true;
	}
}
