package com.monster.content.uploader.rest.api.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DESC: Model class used during the Exception handling
 * 		 To store required details as a model class to provide it as response 
 * 
 * @author SivakumarK
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiErrors {
	LocalDateTime time;
	String message;
	HttpStatus status;
	int statusCode;
	String error;

	
}
