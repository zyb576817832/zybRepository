package com.ebon.platform.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseAction {
	
	protected static final Log log = LogFactory.getFactory().getInstance(BaseAction.class);

	@RequestMapping("exception")
	public void throwException() {
		throw new RuntimeException("This is the runtime exception");
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody String handleException(Exception ex) {
		return ex.getMessage();
	}

}
