package com.aiocdwacs.awacscloudauthserver.controller;

public class InvalidEmailFormatException extends Exception {

	private static final long serialVersionUID = -3999212004229206334L;

	public InvalidEmailFormatException() {
		super();
	}

	public InvalidEmailFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidEmailFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEmailFormatException(String message) {
		super(message);
	}

	public InvalidEmailFormatException(Throwable cause) {
		super(cause);
	}
}
