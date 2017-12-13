package com.ebon.platform.util;

import java.util.UUID;

public class Guid {
	
	/**
	 * 获取Guid
	 * 
	 * @return
	 */
	public static String getGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
}
