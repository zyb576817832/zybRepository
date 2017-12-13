package com.ebon.platform.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;


public class StructureConvert {
	
	public static <T> Object mapConvertToObject(Map<String, Object> sourceMap, Class<T> targetObjectClass) throws Exception {
		T t = targetObjectClass.newInstance();
		Set<String> keySet = sourceMap.keySet();
		for (String key : keySet) {
			Object value = sourceMap.get(key);
			Field[] fields = targetObjectClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals(key)) {
					field.setAccessible(true);
					field.set(t, value);
					field.setAccessible(false);
				}
			}
		}
		return t;
	}
	
}
