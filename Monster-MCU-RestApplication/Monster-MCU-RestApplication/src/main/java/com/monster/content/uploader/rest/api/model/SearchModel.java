/**
 * 
 */
package com.monster.content.uploader.rest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SumanD
 *
 */
@Getter
@Setter
@ToString
public class SearchModel {


	@JsonProperty("keyword")
	String keyword;
	@JsonProperty("cursor")
	Cursor cursor;
	@JsonProperty("sort")
	Sort sort;
	
	

	public SearchModel(String keyword, Cursor cursor,Sort sort) {
		this.keyword = keyword;
		this.cursor = cursor;
		this.sort = sort;
	}
	
	public SearchModel() {
		super();
	}
}
