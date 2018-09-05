package com.niraj.exception;

//@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class FutureTransactionDateException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FutureTransactionDateException(String message) {
	    super(message);
	  }

}
