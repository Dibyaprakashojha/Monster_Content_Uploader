package com.monster.content.uploader.rest.api.exception;

/**
 * DESC: Custom Exception which is thrown when the input ID not present in the DB
 * 
 * @author SivakumarK
 *
 */

public class IdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IdNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IdNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
