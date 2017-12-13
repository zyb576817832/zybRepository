package com.ebon.v2.eai.base.handler;

import org.xml.sax.helpers.DefaultHandler;

import com.ebon.v2.eai.base.model.BatchInfo;

/**
 * 继承sax的handler，用来自定义xml解析规则
 * @author G
 */
public abstract class BaseHandler extends DefaultHandler {

	public abstract BatchInfo getBatchInfo();
	
}
