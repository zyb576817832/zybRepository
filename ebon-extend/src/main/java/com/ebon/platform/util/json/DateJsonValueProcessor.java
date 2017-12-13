package com.ebon.platform.util.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

 
import net.sf.json.*;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {
	
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	
	private DateFormat dateFormat;
	
	public DateJsonValueProcessor(String datePattern) {
		if( null == datePattern) {
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		} else {
			dateFormat = new SimpleDateFormat(datePattern);
		}
	}

	@Override
	public Object processArrayValue(Object obj, JsonConfig jsonConfig) {
		return process(obj);
	}

	@Override
	public Object processObjectValue(String str, Object obj, JsonConfig jsonConfig) {
		return process(obj);
	}
	
	private Object process(Object obj) {
		return dateFormat.format((Date) obj);
	}

}
