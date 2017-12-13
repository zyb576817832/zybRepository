package com.ebon.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 驼峰命名法(CamelCase)和下划线风格(UnderScoreCase)字符串之间的转换工具类
 * 
 * @author string
 * 
 */
public class CamelCaseUtils {
	
	private static char SEPARATOR = '_';
	
	/**
	 * 大写转下划线的算法 驼峰转 下划线
	 * @param param
	 * @return String
	 */
	public static String camelToUnderlineCase(String param) {
		String s = "";
        Pattern  p = Pattern.compile("[A-Z]");
        if(StringUtil.isNotNull(param)){
        	StringBuilder builder = new StringBuilder(param);
	        Matcher mc = p.matcher(param);
	        int i = 0;
	        while(mc.find()) {
	            builder.replace(mc.start() + i, mc.end() + i, "_"+mc.group().toLowerCase());
	            i ++;
	        }
	        if('_' == builder.charAt(0)) {
	            builder.deleteCharAt(0);
	        }
	        s = builder.toString();
        }
        return s;
    }  
	
	/**
	 * 驼峰转 下划线 并转换成大写
	 * @param param
	 * @return String
	 */
	public static String camelToUnderlineToUpperCase(String param) {
		String s = camelToUnderlineCase(param);
		return s.toUpperCase();
	}
	
	/**
	 * 驼峰式字符串转separator式,separator为你传进来的参数
	 * @param param
	 * @param separator
	 * @return String
	 */
	public static String camelToSeparatorCase(String param, String separator) {
		if (param == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < param.length(); i++) {
			char c = param.charAt(i);
			boolean nextUpperCase = true;
			if (i < (param.length() - 1)) {
				nextUpperCase = Character.isUpperCase(param.charAt(i + 1));
			}
			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(separator);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}
	
	/**
	 * 驼峰式字符串转separator式,separator为你传进来的参数,并转换成大写
	 * @param param
	 * @param separator
	 * @return String
	 */
	public static String camelToSeparatorToUpperCase(String param, String separator) {
		String s = camelToSeparatorCase(param, separator);
		return s.toUpperCase();
	}
	
	/**
	 * 下划线式字符串转驼峰式，首字母小写
	 * @param s
	 * @return String
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 下划线式字符串转驼峰式，首字母大写
	 * @param s
	 * @return String
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
}
