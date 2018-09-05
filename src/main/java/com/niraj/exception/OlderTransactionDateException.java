package com.niraj.exception;

public class OlderTransactionDateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OlderTransactionDateException(String message) {
		super(message);
	}

}
