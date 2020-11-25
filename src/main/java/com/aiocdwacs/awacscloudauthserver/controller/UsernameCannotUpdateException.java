package com.aiocdwacs.awacscloudauthserver.controller;

public class UsernameCannotUpdateException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsernameCannotUpdateException() {
		super();
	}

	public UsernameCannotUpdateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UsernameCannotUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameCannotUpdateException(String message) {
		super(message);
	}

	public UsernameCannotUpdateException(Throwable cause) {
		super(cause);
	}
}
