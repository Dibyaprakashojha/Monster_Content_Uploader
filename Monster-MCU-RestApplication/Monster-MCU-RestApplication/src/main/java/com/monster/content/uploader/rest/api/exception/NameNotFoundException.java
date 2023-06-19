package com.monster.content.uploader.rest.api.exception;

/**
 * DESC: Custom Exception which is thrown when the input ID not present in the DB
 * 
 * @author SivakumarK
 *
 */

public class NameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NameNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NameNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
