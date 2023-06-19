/**
 * 
 */
package com.monster.content.uploader.rest.api.util;

/**
 * @author SumanD
 *
 */
public enum Operations {

	CREATED("Create"), UPDATED("Update"), DELETED("Delete");

	private String value;

	Operations(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
