package com.monster.content.uploader.rest.api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.monster.content.uploader.rest.api.model.ApiErrors;

/**
 * DESC: Will manually handle the exceptions and providing all the required info
 * when the exception occurred, by help of a model class named ApiErrors.
 * 
 * @author SivakumarK
 *
 */
@ControllerAdvice
public class GlobelExceptionHandler extends ResponseEntityExceptionHandler {

	
	/**
	 * @param ex
	 * @return: Will return the Exception to where it is called with all the
	 *          customized informations.
	 */
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<Object> IdNotFoundException(IdNotFoundException ex) {
		String message = "Id not Found";
		String error = "Requested Id is not present in the database!!!";
		ApiErrors apiError = new ApiErrors(LocalDateTime.now(), message, HttpStatus.NOT_FOUND,
				HttpStatus.NOT_FOUND.value(), error);
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", message);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(apiError);

	}
	
	/**
	 * @param ex
	 * @return: Will return the Exception to where it is called with all the
	 *          customized informations.
	 */
	@ExceptionHandler(NameNotFoundException.class)
	public ResponseEntity<Object> NameNotFoundException(NameNotFoundException ex) {
		String message = "Name not Found";
		String error = "Requested Name is not present in the database!!!";
		ApiErrors apiError = new ApiErrors(LocalDateTime.now(), message, HttpStatus.NOT_FOUND,
				HttpStatus.NOT_FOUND.value(), error);
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", message);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(apiError);

	}

}
