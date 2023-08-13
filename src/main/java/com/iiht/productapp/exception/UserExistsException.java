package com.iiht.productapp.exception;

@SuppressWarnings("serial")
public class UserExistsException extends RuntimeException {
	/**
	 * @param message
	 */
	public UserExistsException(String message) {
		super(message);
	}

	
}
