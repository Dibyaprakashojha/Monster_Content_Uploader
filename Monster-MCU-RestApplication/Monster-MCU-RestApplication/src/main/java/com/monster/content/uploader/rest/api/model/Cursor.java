/**
 * 
 */
package com.monster.content.uploader.rest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SumanD
 *
 */
@Getter
@Setter
public class Cursor {

	@JsonProperty("page_index")
	Integer pageIndex;
	@JsonProperty("page_size")
	Integer pageSize;
	@JsonProperty("total_records")
	Long totalRecords;
//	@JsonProperty("sort_direction")
//	String sortDirection;
//	@JsonProperty("user_id")
//	Integer userId;

	public Cursor(Integer pageIndex, Integer pageSize, Long totalRecords) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
	}

	public Cursor() {
		super();
	}

//	public Cursor(Integer pageIndex, Integer pageSize, Long totalRecords, String sortDirection) {
//		super();
//		this.pageIndex = pageIndex;
//		this.pageSize = pageSize;
//		this.totalRecords = totalRecords;
//		this.sortDirection = sortDirection;
//	}
}
