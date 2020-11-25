package com.aiocdwacs.awacscloudauthserver.controller;

public class InvalidPhoneFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidPhoneFormatException() {
		super();
	}

	public InvalidPhoneFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidPhoneFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPhoneFormatException(String message) {
		super(message);
	}

	public InvalidPhoneFormatException(Throwable cause) {
		super(cause);
	}
}
