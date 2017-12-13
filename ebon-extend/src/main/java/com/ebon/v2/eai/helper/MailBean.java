package com.ebon.v2.eai.helper;

import java.util.Map;

public class MailBean {
	private String from;
	private String fromName;
	private String[] toEmails;

	private String subject;

	private Map data; // 邮件数据
	private String content ;// 邮件正文

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String[] getToEmails() {
		return toEmails;
	}

	public void setToEmails(String[] toEmails) {
		this.toEmails = toEmails;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String template) {
		this.content = template;
	}
}
