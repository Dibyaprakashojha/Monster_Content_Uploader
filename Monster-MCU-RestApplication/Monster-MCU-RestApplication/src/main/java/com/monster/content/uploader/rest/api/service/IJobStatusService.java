package com.monster.content.uploader.rest.api.service;

import java.util.List;

import com.monster.content.uploader.rest.api.entity.JobStatus;

/**
 * @author SivakumarK
 *
 */
public interface IJobStatusService {
	
	public String createRecord(JobStatus jobStatus);
	public String updateRecord(JobStatus jobStatus);
	public String deleteRecordById(int jobStatusId);
	public List<JobStatus> retrieveAllRecords();
	public JobStatus retrieveById(int jobStatusId);
	public void clearCache();
	public String bulkDelete(List<Integer> jobStatusId );
	public String bulkCreate(List<JobStatus> jobStatus);
	
}
