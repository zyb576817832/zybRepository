package com.ebon.platform.service;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 4214933678063275426L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
