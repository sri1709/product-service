package com.iiht.productapp.exception;

/**Exception class*/
@SuppressWarnings("serial")
public class UnauthorizedException extends RuntimeException {
	/**
	 * @param message
	 */
	public UnauthorizedException(String message) {
		super(message);
	}

}
