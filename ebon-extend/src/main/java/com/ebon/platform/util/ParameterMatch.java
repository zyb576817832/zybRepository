package com.ebon.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * 入参元素匹配
 * @author Administrator
 *
 */
public class ParameterMatch {
	/**
	 * 判断时间是否符合yyyy-MM-dd类型
	 * @param dateStr
	 * @return
	 */
	public static boolean dateFormatYYYYMMDD(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(dateStr);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
