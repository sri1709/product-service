package com.iiht.productapp.exception;

public class ProductServiceCustomException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	  private String errorCode;
	public ProductServiceCustomException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;

	}
}
