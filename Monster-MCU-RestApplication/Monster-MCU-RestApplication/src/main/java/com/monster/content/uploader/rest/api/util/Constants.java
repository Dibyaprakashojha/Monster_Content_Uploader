/**
 * 
 */
package com.monster.content.uploader.rest.api.util;

/**
 * @author SumanD
 *
 */
public enum Constants {
	

	
	JOB_ID("job_id"),
	JOB_NAME("job_name"),
	BRAND("brand"),
	COUNTRY("country"),
	DEPARTMENT_NAME("department_name"),
	PRODUCT_LINE("product_line"),
	JOB_STATUS("job_status"),
	CREATED_DATE("created_date"),
	USER_ID("user_id"),
	USER_NAME("user_name");
	
	
	private String value;
	Constants(final String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}

}
