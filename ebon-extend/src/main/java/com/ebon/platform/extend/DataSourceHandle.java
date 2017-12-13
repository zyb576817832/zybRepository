package com.ebon.platform.extend;

public class DataSourceHandle {
	
	private static final ThreadLocal contextHolder = new ThreadLocal();
	
	public static String getDataSourceType() {
		return (String)contextHolder.get();
	}
	
	public static void setDataSourceType(String type) {
		contextHolder.set(type);
	}
	
	public static void removeType() {
		contextHolder.remove();
	}

}
