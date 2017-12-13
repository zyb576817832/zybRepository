package com.ebon.platform.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}
	
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	
	public static <T> T getBean(String name, Class<T> type) {
		return applicationContext.getBean(name, type);
	}

}
