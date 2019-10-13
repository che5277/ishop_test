package tableshop.ilinkedtech.com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MD5Util {
	
	
	private static final String           KEY_PREFIX ="PAOJIAO_FIND_PASSWORD";
	private static final SimpleDateFormat FMT        =new SimpleDateFormat("yyyy-MM-dd HH:00:00");
	
	
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
                                               "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	/***
	 * 将字符串处理成MD5值.
	 * @param str1
	 * @return
	 */
	public static String MD5(String str1) {
		
		byte[] btInput=str1.getBytes();
		
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {

			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}
	
	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resSb.append(byteToHexString(b[i]));
		}
		return resSb.toString();
	}
	
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	
	
	public static String getMD5StrForUc(String str) {
		String resStr = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resStr = byteArrayToHexString(md.digest(str.getBytes("utf-8")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resStr;
	}

	public static String getMD5Str(String str) {
		String resStr = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resStr = byteArrayToHexString(md.digest(str.getBytes("utf-8")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resStr;
	}
	
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}
	
	public static byte getMD5FirstByte(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[]        b  =md.digest(str.getBytes());
			if (b != null && b.length > 0) {
				return b[0];
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (Byte) null;
	}

	public static String encodePWD(String userId, String pwd){
		return getMD5Str(userId.toUpperCase()+":"+pwd);
	}
	
  public static String getFileMD5(String fn) {
    return getFileMD5(new File(fn));
  }
	
	public static String getFileMD5(File file) {
    FileInputStream fis = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      fis = new FileInputStream(file);
      byte[] buffer = new byte[8192];
      int length = -1;
      while ((length = fis.read(buffer)) != -1) {
        md.update(buffer, 0, length);
      }
      fis.close();
      return byteArrayToHexString(md.digest());
    } catch (Exception ex) {
      return null;
    } 
	}
	
	/**
	 * 加密一个小时内有效的邮箱验证Key.
	 * @param userName
	 * @return
	 */
	public static String encodeKey(String userName){
		String date =FMT.format(new Date());
		return getMD5Str(KEY_PREFIX+"_"+userName+"_"+date);
	}

	public static void main(String[] args) {
		
		//System.out.println(encodeKey("zhangbaobao"));
		
		
		System.out.println(getMD5StrForUc("uid=604597price=10.0orderNo=PG_1414122151303remark=购买100元宝status=5subject=购买100元宝gameId=65payTime=20141024114231ext=6z4KVxtqAMcQubJQbtMqZQGGGvTTzWSvo"));
//		
//	  System.out.println("mohwst="+ getMD5Str("100000"));
//	  System.out.println("mohwst="+ (getMD5FirstByte("100000")&0x7f));
	}
	
	
	

}
