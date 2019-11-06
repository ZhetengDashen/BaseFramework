package com.baseeasy.commonlibrary.mytool.time;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {

	/**
	 * @author 创建人：王志强 创建时间：2014年1月27日10:08:20
	 * 
	 * @return 当前时间 yyyy-MM-dd
	 * 
	 */

	@SuppressLint("SimpleDateFormat")
	public static String getnow_time() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);

		return str;
	}
	public static String getnow_time_seconds() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);

		return str;
	}
	/**
	 * @author 创建人：王志强 创建时间： 2014年1月27日10:11:35
	 * 
	 * @return 间隔天数
	 * 
	 */

	public static int getGapCount(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime()
				.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * @author 创建人：王志强 创建时间：2015年10月26日 18:37:49
	 * 
	 * @return 判断连个时间的大小 0相同 返回 1 itme1大 返回2 item2大 3 出错
	 * 
	 */
	public static int is_time(String itme1, String item2) {

		try {
			int a = Integer.parseInt(itme1.replace(":", ""));
			int b = Integer.parseInt(item2.replace(":", ""));
			if (a == b) {
				return 0;
			}
			if (a > b) {
				return 1;
			} else {
				return 2;
			}

		} catch (Exception e) {
			return 3;
		}

	}

	/**
	 * 计算宝宝年龄
	 * 
	 * @author 王志强 二〇一五年十一月七日 15:47:46
	 * 
	 *
	 * 
	 * @return 1岁7个月2天
	 * **/
	public static String get_years_old2(String startDateStr) {

		String aa = remainDateToString(startDateStr, getnow_time());
		return aa;

	}

	private static Calendar calS = Calendar.getInstance();
	private static Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");// 定义整则表达式

	/**
	 * 计算剩余时间
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String remainDateToString(String startDateStr,
                                            String endDateStr) {
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		calS.setTime(startDate);
		int startY = calS.get(Calendar.YEAR);
		int startM = calS.get(Calendar.MONTH);
		int startD = calS.get(Calendar.DATE);
		int startDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);

		calS.setTime(endDate);
		int endY = calS.get(Calendar.YEAR);
		int endM = calS.get(Calendar.MONTH);
		// 处理2011-01-10到2011-01-10，认为服务为一天
		int endD = calS.get(Calendar.DATE) + 1;
		int endDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);

		StringBuilder sBuilder = new StringBuilder();
		if (endDate.compareTo(startDate) < 0) {
			return sBuilder.append("过期").toString();
		}
		int lday = endD - startD;
		if (lday < 0) {
			endM = endM - 1;
			lday = startDayOfMonth + lday;
		}
		// 处理天数问题，如：2011-01-01 到 2013-12-31 2年11个月31天 实际上就是3年
		if (lday == endDayOfMonth) {
			endM = endM + 1;
			lday = 0;
		}
		int mos = (endY - startY) * 12 + (endM - startM);
		int lyear = mos / 12;
		int lmonth = mos % 12;
		if (lyear > 0) {
			sBuilder.append(lyear + "岁");
		}
		if (lmonth > 0) {
			sBuilder.append(lmonth + "个月");
		}

		// 假如是小于一岁的时候，
		if (lyear == 0) {
			// sBuilder.append(lday+"天");
			if (lday > 0) {
				sBuilder.append(lday - 1 + "天");
			}
		} else {
			if (lday > 0) {
				sBuilder.append(lday + "天");
			}
		}
		return sBuilder.toString();
	}

	/*
	 * 转换 dataAndTime 2013-12-31 23:59:59 到 date 2013-12-31
	 */
	public static String getDate(String dateAndTime) {
		if (dateAndTime != null && !"".equals(dateAndTime.trim())) {
			Matcher m = p.matcher(dateAndTime);
			if (m.find()) {
				return dateAndTime.subSequence(m.start(), m.end()).toString();
			}
		}
		return "data error";
	}

	/**
	 * @author 王志强 二〇一五年十一月七日 15:47:46
	 * 
	 * @param years_old
	 *            格式 20050531
	 * @return hashMap "years",年 "month",月 "day",天
	 * **/
	public static HashMap<String, String> get_years_old(String years_old) {
		HashMap<String, String> hashMap = new HashMap<String, String>();

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String str = formatter.format(curDate);

			int ret[] = getDateLength(str, years_old);

			System.out.println(ret[0] + ":" + ret[1] + ":" + ret[2]);
			hashMap.put("years", ret[0] + "");
			hashMap.put("month", ret[1] + "");
			hashMap.put("day", ret[2] + "");
		} catch (Exception e) {
			return null;
		}

		return hashMap;
	}

	static int[] getDateLength(String fromDate, String toDate) {
		Calendar c1 = getCal(fromDate);
		Calendar c2 = getCal(toDate);
		int[] p1 = { c1.get(Calendar.YEAR), c1.get(Calendar.MONTH),
				c1.get(Calendar.DAY_OF_MONTH) };
		int[] p2 = { c2.get(Calendar.YEAR), c2.get(Calendar.MONTH),
				c2.get(Calendar.DAY_OF_MONTH) };
		return new int[] {
				p2[0] - p1[0],
				p2[0] * 12 + p2[1] - p1[0] * 12 - p1[1],
				(int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600 * 1000)) };
	}

	static Calendar getCal(String date) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Integer.parseInt(date.substring(0, 4)),
				Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));
		return cal;
	}

	/**
	 * 毫秒数转换成时间
	 * 
	 * @author 王志强 二〇一六年一月十一日 11:38:13
	 * 
	 * @param mss
	 *            毫秒数
	 * 
	 * @return
	 * **/

	public static String conversion_time(long mss) {

		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;

		// return days + " days " + hours + " hours " + minutes + " minutes "
		// + seconds + " seconds ";
		String secondss = seconds + "";
		String minutess = minutes + "";
		if (seconds < 10) {
			secondss = "0" + secondss;

		}
		if (minutes < 10) {
			minutess = "0" + minutess;

		}
		return minutess + ":" + secondss;
	}
}
