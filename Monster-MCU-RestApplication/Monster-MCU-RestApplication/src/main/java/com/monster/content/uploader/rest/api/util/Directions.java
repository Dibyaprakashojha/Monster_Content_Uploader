/**
 * 
 */
package com.monster.content.uploader.rest.api.util;

/**
 * @author SumanD
 *
 */
public enum Directions {
	
	ASCENDING("asc"),
	DESCENDING("desc");
	
	private String value;

	Directions(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
