package com.beyondsoft.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * Date: 12-4-23
 * Time: 下午2:35
 *
 * @author zhe.yangz
 */
public class DateTimeUtil {
	
	public static final String TAG = "DateTimeUtil";
	
    public static final String DEFAULT_DATA_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    
    /**
     *
     * @param milliseconds 毫秒数
     * @return 简单的默认格式日期时间
     */
    public static String getSimpleDatetime(long milliseconds) {
        Date date = new Date(milliseconds);
        DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
        return sdf.format(date);
    }

    /**
     * "yyyy-MM-dd HH:mm:ss Z"
     * @param milliseconds 毫秒数
     * @return 默认上述格式的时间
     */
    public static String getDefaultDatetime(long milliseconds) {
        Date date = new Date(milliseconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        return sdf.format(date);
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"
     * @param milliseconds 毫秒数
     * @return 默认上述格式的时间
     */
    public static String getTimestampDatetime(long milliseconds) {
        Date date = new Date(milliseconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * "20100315072741000-0700" =>  "yyyy-MM-dd HH:mm:ss Z"
     * ex. 可用于Ocean返回的时间格式的转换
     * @param strTime
     * @return
     */
    public static String getTimeString(String strTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSSZ");
            Date d = formatter.parse(strTime);
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

            return df1.format(d);
        } catch (ParseException e) {
            return strTime;
        } catch (NullPointerException npe) {
            return "";
        }
    }
    
	/**
	 * 将时间戳转成日期字符
	 * 
	 * @param longtime
	 * @return
	 */
	public static String getStringTime2Date(String StringTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(Long.parseLong(StringTime)));
	}

	/**
	 * 将时间戳转成日期字符
	 * 
	 * @param longtime
	 * @return
	 */
	public static String getLongTime2Date(long longtime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(longtime));
	}
	

	/**
	 * 将日期字符转成时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static long getDate2LongTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d = sdf.parse(time);
			return d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}
	
	/**
	 * 将时间戳转成日期字符
	 * 
	 * @param longtime
	 * @return
	 */
	public static String getLongTime3Date(long longtime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return getChineseDate(sdf.format(new Date(longtime)));
	}

	/**
	 * 将日期字符转成时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static String getDate2ChineseTime(String time) {
		String[] timeSplit = time.split("-");
		String date = timeSplit[1] + "月" + timeSplit[2] + "日";
		return date;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(currentTime);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回长时间字符串格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateLongLen() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(currentTime);
	}

	/**
	 * 转换时间
	 * 
	 * @return 返回长时间字符串格式yyyy-MM-dd HH:mm
	 */
	public static String getDateToString(Date mDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(mDate);
	}

	/**
	 * 转换时间
	 * 
	 * @return 返回长时间字符串格式yyyy年MM月dd日
	 */
	public static String getDateToChToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		return formatter.format(date);
	}
	
	/**
	 * 根据time获取现在时间
	 * 
	 * @return
	 */
	public static String getStringDateforPhoto(long time) {
		Date currentTime = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return formatter.format(currentTime);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return
	 */
	public static String getStringDateforPhoto() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return formatter.format(currentTime);
	}

	/**
	 * 获取前天时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getBeforeYesterday() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 2);

		String beforeYesterday = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return beforeYesterday;
	}

	/**
	 * 获取昨天时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getYesterday() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String yesterDay = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return yesterDay;
	}

	/**
	 * 获取明天时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getTomorrow() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * 获取后天时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getDayAfterTomorrow() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 2);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * 判断时间是否属于本周
	 * 
	 * @return
	 */
	public static int getWeekOfDay(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		sdf.format(cal.getTime());
		String fileTime = sdf.format(cal.getTime());
		cal.setTime(new Date());
		String currentTime = sdf.format(cal.getTime());
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		// Logs.e("所在周星期一的日期是:"+imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// Logs.e("所在周星期日的日期是:"+imptimeEnd);
		// Logs.e("文件的日期是:"+fileTime);
		// Logs.e("今天的日期是:"+currentTime);
		int result = fileTime.compareTo(currentTime);
		// Logs.e("比较结果:"+result);
		if (result != 0) {
			result = fileTime.compareTo(imptimeBegin);
			if (result == 0) {
				result = 2;
			}
			// Logs.i("比较结果:"+result);
		}
		return result;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateLong() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		return formatter.format(currentTime);
	}

	/**
	 * 根据年和月算出该月有多少天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthOfDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DATE, 1);
		long time = c.getTimeInMillis();

		c.set(Calendar.MONTH, (month + 1));
		long nexttime = c.getTimeInMillis();

		long cha = nexttime - time;
		int s = (int) (cha / (24 * 60 * 60 * 1000));

		return s;
	}
	
	
	public static String GetYearForDate(Date date)
	{
		String tmp = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int yearInt = calendar.get(Calendar.YEAR);
		tmp = String.valueOf(yearInt);
		return tmp;
	}
	
	
	public static String GetMonthForDate(Date date)
	{
		String tmp = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int monthInt = calendar.get(Calendar.MONTH) + 1;
		tmp = String.valueOf(monthInt);
		return tmp;
	}
	
	public static String GetDayForDay(Date date)
	{
		String tmp = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		tmp = String.valueOf(day);
		if (tmp.length() == 1) {
			tmp = "0"+tmp;
		}
		return tmp;
	}
	
	/**
	 * 得到两个日期间隔的天数
	 */
	public static int getDaysBetween (String beginDate, String endDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date bDate = format.parse(beginDate);
		Date eDate = format.parse(endDate);
		Calendar d1 = new GregorianCalendar();
		d1.setTime(bDate);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(eDate);
		        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		        int y2 = d2.get(Calendar.YEAR);
		        if (d1.get(Calendar.YEAR) != y2) {
		            d1 = (Calendar) d1.clone();
		            do {
		                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
		                d1.add(Calendar.YEAR, 1);
		            } while (d1.get(Calendar.YEAR) != y2);
		           
		        }
		        return days;
    }
	
	/**
	 * 比较两个时间
	 * @param year1
	 * @param month1
	 * @param day1
	 * @param year2
	 * @param month2
	 * @param day2
	 * @return
	 */
	public static int complateDay(int year1, int month1, int day1, int year2,
			int month2, int day2) {
		Calendar xmas = new GregorianCalendar(year1, month1, day1);
		Calendar newYear = new GregorianCalendar(year2, month2, day2);
		// 两个日期相差的毫秒数
		long timeDiffMillis = newYear.getTimeInMillis() - xmas.getTimeInMillis();
		// 两个日期相差的天
		long diffDays = timeDiffMillis / (1000 * 60 * 60 * 24);
		
		if (diffDays > 0) {
			if (diffDays > 365) {
				System.out.println("-------------查询时间不能大于一年--------");
				return 1;
			}
		} else {
			System.out.println("-------------后面时间小于前面时间--------");
			return -1;
		}
		System.out.println("-------------在区间内--------");
		return (int) diffDays;
	}
	
	public static int getIntervalDays(Date fDate, Date oDate) {
	       if (null == fDate || null == oDate) {
	           return -1;
	       }
	       long intervalMilli = oDate.getTime() - fDate.getTime();
	       return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }
	
	/**
	 * 返回指定的时间格式
	 * 
	 * @param date
	 * @return
	 */
	public static String getChineseDate(String date) {
		String[] split_str = date.split("-");
		String date_str = split_str[0] + "年" + split_str[1] + "月"+split_str[2]+"日";
		return date_str;
	}

	/**
	 * 返回指定的日期
	 */
	public static String getSpecificDate(String date, int flag) {
		int sperator;
		if (StringUtil.isEmpty(date)) {
			return "";
		} else {
			if (0 == flag) {//年
				String year = date.substring(0, 4);
//				System.out.println("年是:" + year);
				return year;
			} else if (1 == flag) {//月
				sperator = date.indexOf("-");
				String month = date.substring(sperator + 1, sperator + 3);
//				System.out.println("月是:" + month);
				return month;
			} else if (2 == flag) {//日
				sperator = date.lastIndexOf("-");
				String day = date.substring(sperator + 1, sperator + 3);
//				System.out.println("日是:" + day);
				return day;
			} else if (3 == flag) {//时
				sperator = date.indexOf("T");
				String hour = date.substring(sperator + 1, sperator + 3);
//				System.out.println("时是:" + hour);
				return hour;
			} else if (4 == flag) {//分
				sperator = date.indexOf(":");
				String minute = date.substring(sperator + 1, sperator + 3);
//				System.out.println("分是:" + minute);
				return minute;
			} else if (5 == flag) {//秒
				sperator = date.lastIndexOf(":");
				String second = date.substring(sperator + 1, sperator + 3);
//				System.out.println("秒是:" + second);
				return second;
			} else {
				return date.replace('T', ' ');
			}
		}
	}
	
	/**
	 * 时间处理处理, 当时间小于10时,在前面机上0
	 * @param time 具体数字
	 * @return 返回结果
	 */
	public static String parseTime(String time){
		String result;
		int parameter = Integer.valueOf(time);
		if (parameter >= 10){
			result = String.valueOf(parameter);
		} else{
			result = "0" + parameter;
		}
		return result;
	}
	
	/**
	 * 得到格式化时间
	 * 
	 * @param timeInSeconds
	 * @return
	 */
	public static String getFormatTimeMsg(int timeInSeconds) {
		int hours, minutes, seconds;
		hours = timeInSeconds / 3600;
		timeInSeconds = timeInSeconds - (hours * 3600);
		minutes = timeInSeconds / 60;
		timeInSeconds = timeInSeconds - (minutes * 60);
		seconds = timeInSeconds;

		String minStr = String.valueOf(minutes);
		String secStr = String.valueOf(seconds);

		if (minStr.length() == 1)
			minStr = "0" + minStr;
		if (secStr.length() == 1)
			secStr = "0" + secStr;

		return (minStr + "分" + secStr + "秒");
	}
	

    public static String format(Date date) {
        String retString = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATA_TIME_FORMAT);
            retString = format.format(date);
        } catch (Exception e) {
            Logs.e(TAG, e.toString());
            e.printStackTrace();
        }
        return retString;
    }
}
