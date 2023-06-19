/**
 * 
 */
package com.monster.content.uploader.rest.api.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.ToString;

/**
 * @author SumanD
 *
 */

public class JobIndexQueueMessage {

	@Override
	public String toString() {
		return "jobId=" + jobId + ",operation=" + operation + ",createdDate=" + createdDate;
	}

	private String jobId;
	private String operation;
	private String createdDate;

	public JobIndexQueueMessage() {
	}

	public JobIndexQueueMessage(String jobId, String operation, String createdDate) {
		this.jobId = jobId;
		this.operation = operation;
		this.createdDate = createdDate;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {

		this.createdDate = createdDate;
	}

}
