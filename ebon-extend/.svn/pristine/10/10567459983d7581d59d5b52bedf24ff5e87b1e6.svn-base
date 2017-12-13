package com.ebon.platform.util;

import java.util.List;

public class StringUtil {
	
	public static boolean isNotNull(String str) {
		boolean b = false;
		if(null != str && str.trim().length() > 0) {
			b = true;
		}
		return b;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isNotNull4List(List list) {
		boolean b = false;
		if(null != list && list.size() > 0) {
			b = true;
		}
		return b;
	}
	
	public static boolean isNotNull4Object(Object obj) {
		boolean b = false;
		if(null != obj){
			b = true;
		}
		return b;
	}
	
	public static String toString4Object(Object obj) {
		String str = null;
		if(isNotNull4Object(obj)){
			str = String.valueOf(obj);
		} else {
			str = "";
		}
		return str;
	}

}
