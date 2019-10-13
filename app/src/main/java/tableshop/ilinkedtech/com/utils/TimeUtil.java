package tableshop.ilinkedtech.com.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 * 
 * @author wilson
 * 
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {

	public static final String YYYYMMDD         ="yyyyMMdd";//hh:12小时制  HH:24小时制
	public static final String YYYY_MM_DD_HH_MM ="yyyy-MM-dd HH:mm";//hh:12小时制  HH:24小时制
	public static final String MM_DD_HH_MM      ="MM/dd HH:mm";//hh:12小时制  HH:24小时制
	public static final String HH_MM            ="HH:mm";//hh:12小时制  HH:24小时制
	public static final String YYYY_MM_DD       ="yyyy-MM-dd";
	public static final String YYYY_MM          = "yyyy-MM";
	public static final String TIME_ZONE        = "GMT+8";//设置时间显示的时区

	//获取当前的时间
	public static long getCurrentTime(){
		return System.currentTimeMillis();
	}


	//string类型long值时间转 24小时制string类型时间	默认:YYYY_MM_DD_HH_MM
	public static String getTime(String stringTime) {
		long time=0;
		if (!StringUtils.isEmpty(stringTime)){
			time= Long.parseLong(stringTime);
			return getTimeLong2String(time);
		}else {
			return "";
		}
	}


	//将string类型long值时间转 24小时制string类型时间
	public static String getTime(String stringTime, String dateFormat) {
		long time=0;
		if (!StringUtils.isEmpty(stringTime)){
			time= Long.parseLong(stringTime);
			return getTimeLong2String(time, dateFormat);
		}else {
			return "";
		}
	}

	//long类型时间转 24小时制string类型时间
	public static String getTimeLong2String(long time) {
		return getTime(new Date(time), YYYY_MM_DD_HH_MM);
	}

	//将long值时间转 24小时制string类型时间
	public static String getTimeLong2String(long time, String dateFormat) {
		return getTime(new Date(time), dateFormat);
	}


	public static String getTime(Date time) {
		return getTime(time,YYYY_MM_DD_HH_MM);
	}


	/**
	 * TODO 所有格式化时间都调用次方法
	 * @param time
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(Date time, String dateFormat) {
		String formatstr = YYYY_MM_DD_HH_MM;
		if (!TextUtils.isEmpty(dateFormat)) {
			formatstr=dateFormat;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatstr);//hh:12小时制  HH:24小时制
			format.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
			return format.format(time);
		}catch (Exception e){
			return "";
		}

	}

	/**
	 * TODO 根据数字格式的时间转成long类型	例如：2017-05-08 ==> 1494518400000
	 * @param date
	 * @param dateFormat
     * @return 所有转long值时间都调用次方法
     */
	public static Long getLongTime(String date, String dateFormat){
		long millionSeconds = 0;//毫秒
		if (StringUtils.isEmpty(dateFormat)){
			dateFormat= YYYY_MM_DD_HH_MM;//默认24小时制
		}
		if (StringUtils.isEmpty(date)){
			return millionSeconds;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
			millionSeconds = sdf.parse(date).getTime();
		} catch (ParseException e) {
		}
		return millionSeconds;
	}


	public static String getHourAndMin(long time) {
		return getTime(new Date(time), HH_MM);
	}

	public static String getChatTime(long timesamp) {
		timesamp=getCurrentTime();
		String           result = "";
		SimpleDateFormat sdf    = new SimpleDateFormat("dd");
		sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
		Date today    = new Date(System.currentTimeMillis());
		Date otherDay = new Date(timesamp);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		switch (temp) {
		case 0:
			result = "今天 " + getHourAndMin(timesamp);
			break;
		case 1:
			result = "昨天 " + getHourAndMin(timesamp);
			break;
		case 2:
			result = "前天 " + getHourAndMin(timesamp);
			break;

		default:
			result = getTimeLong2String(timesamp);
			break;
		}

		return result;
	}

	public static int getMinuteTime(String arrive) {
		String[] split  = arrive.split(" ")[1].split(":");
		int      hours  = Integer.parseInt(split[0])*60;
		int      minius = Integer.parseInt(split[1]);
		return hours+minius;
	}

	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if ( 0 < timeD && timeD < 800) {//800
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
