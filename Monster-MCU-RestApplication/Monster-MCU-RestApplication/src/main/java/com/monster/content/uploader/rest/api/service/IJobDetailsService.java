package com.monster.content.uploader.rest.api.service;

import java.util.List;

import com.monster.content.uploader.rest.api.entity.JobDetails;

/**
 * @author SivakumarK
 *
 */
public interface IJobDetailsService {
	
	public String createRecord(JobDetails jobDetails);
	public String updateRecord(JobDetails jobDetails);
	public String deleteRecordById(int jobDetailsId);
	public List<JobDetails> retrieveAllRecords();
	public JobDetails retrieveById(int jobDetailsId);
	
}
