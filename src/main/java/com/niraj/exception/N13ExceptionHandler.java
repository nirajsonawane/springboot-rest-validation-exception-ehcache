package com.niraj.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;

@ControllerAdvice
@RestController
public class N13ExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logg = LoggerFactory.getLogger(N13ExceptionHandler.class);
	@ExceptionHandler(value = { FutureTransactionDateException.class })
	public ResponseEntity<Void> handleFutureTransactionDateException(FutureTransactionDateException ex,
			WebRequest request) {
		logg.info("FutureTransactionDateException");
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.build();
	}

	@ExceptionHandler(value = { OlderTransactionDateException.class })
	public ResponseEntity<Void> handleOlderTransactionDateException(OlderTransactionDateException ex,
			WebRequest request) {
		logg.error("OlderTransactionDateException");
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT	)
				.build();
	}

	/**
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { JsonParseException.class })
	public ResponseEntity<Void> handleJsonParseException(JsonParseException ex, WebRequest request) {

		logg.info("JsonParseException");
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.build();
	}

	/**
	 * Note : Do not add @ExceptionHandler(HttpMessageNotReadableException.class) on
	 * this method, As it will create ambiguity to Spring as handleException also
	 * has same ExcptionHandlr Annotation
	 */

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable cause = getRootCause(ex);

		if (cause != null && (cause instanceof JsonParseException ||  ( cause.getMessage() !=null && cause.getMessage().contains("Cannot construct instance of")) ) ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.build();

		}		
		
		else
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.build();
	}

	private Throwable getRootCause(HttpMessageNotReadableException ex) {
		Throwable cause = ex.getCause();
		if(cause==null)
			return ex;
		while (cause.getCause() != null) {
			logg.info(cause.getCause().toString());			
			cause = cause.getCause();
		}
		return cause;
	}

}
