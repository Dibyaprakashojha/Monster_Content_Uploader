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
public class Job {
	@JsonProperty("job_id")
	Integer job_id;
	@JsonProperty("job_name")
	String job_name;
	@JsonProperty("job_status")
	String job_status;
	@JsonProperty("department_name")
	String department_name;

}
