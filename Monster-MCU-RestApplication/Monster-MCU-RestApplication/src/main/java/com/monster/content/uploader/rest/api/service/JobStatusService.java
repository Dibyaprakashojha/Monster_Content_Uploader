package com.monster.content.uploader.rest.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.JobStatus;
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.repository.JobStatusRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class JobStatusService implements IJobStatusService {
	
	@Autowired
	JobStatusRepository jobStatusRepo;
	
	private Logger logger = LoggerFactory.getLogger(JobStatusService.class);
	
	// This is used as a cache storage to avoid multiple database query
    HashMap<Integer,JobStatus> jobStatusCache = new HashMap<>();

	/**
	* This method creates a record in the JobStatus table. The record will be saved in the database and the #getCache () cache will be cleared
	* 
	* @param jobStatus - the record to be saved
	* 
	* @return String message to be displayed to the user after the record has been saved to the database or null if
	*/
	@Override
	public String createRecord(JobStatus jobStatus) {
		logger.info("Inside createRecord() of JobStatusService");
		//this method will add new record in the JobStatus Table 
		jobStatusRepo.save(jobStatus);
		clearCache();
		return "Record Saved Successfully";
	}

	/**
	* Updates the record in the database. This method is called when the user clicks the update button in the job status service
	* 
	* @param jobStatus - the record to be updated
	* 
	* @return String message to be displayed to the user on the screen to confirm the operation was successful or not. Null message will be displayed
	*/
	@Override
	public String updateRecord(JobStatus jobStatus) {
		logger.info("Inside updateRecord() of JobStatusService");
		//this method will update the existing record in the JobStatus Table 
		jobStatusRepo.save(jobStatus);
		clearCache();
		return "Record Updated Successfully";
	}

	/**
	* Delete record by id. This method will update the record in the JobStatus table if Id not found.
	* 
	* @param jobStatusId - Id of record to delete. It's the primary key
	* 
	* @return String message to be displayed
	*/
	@Override
	public String deleteRecordById(int jobStatusId) {
		logger.info("Inside deleteRecordById() of JobStatusService");
		//this method will update the existing record in the JobStatus Table. If Id not found will throw exception
		jobStatusRepo.findById(jobStatusId).orElseThrow(()-> new IdNotFoundException());
		jobStatusRepo.deleteById(jobStatusId);
		clearCache();
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all records from AssetSubtypeService and stores them in cache for future use. This method is called in order to retrieve all records from AssetSubtypeService
	* 
	* 
	* @return List of JobStatus objects
	*/
	@Override
	public List<JobStatus> retrieveAllRecords() {
		//this method will retrieve all the records from assetSubtypeCache. If data not available need to query the data db and get it.
		//This Caching of the data from database to the local Hashmap will not work in HA server architecture. 
		//To resolve this conflict we need to use "distributed cache" concept. (note: RedisCacheManager can be used)
				
		logger.info("Inside retrieveAllRecords() of AssetSubtypeService");
		List<JobStatus> allRecords = new ArrayList<>();
		//checking the availability of data in assetSybtypeCache 
		// Cache the job status data.
		if(jobStatusCache==null || jobStatusCache.isEmpty()) 
			{
				// Not in Cache and Query DB
				allRecords = jobStatusRepo.findAll();
				jobStatusCache = new HashMap<>();
				 for (JobStatus astObj : allRecords) 
				 {
					 jobStatusCache.put(astObj.getJobStatusId(), astObj);
			     }	
			}
		else 
			{
				// Data Available in Cache
				allRecords = new ArrayList<>(jobStatusCache.values());
			}	
		return allRecords;
	}

	/**
	* Retrieves data from assetSybtypeCache based on the Id passed in and returns it. If data not available in assetSybtypeCache it will query the data db and get the data
	* 
	* @param jobStatusId - Id of the JobStatus to retrieve
	* 
	* @return JobStatus object with the data passed in and its Id if it exists in assetSybtypeCache otherwise it will return
	*/
	@Override
	public JobStatus retrieveById(int jobStatusId) {
		
		//this method will retrieve all the records based on Id from JobStatusCache. If data not available need to query the data db and get it
		logger.info("Inside retrieveById() of JobStatusService");
				
		//checking the availability of data in assetSybtypeCache 
		// Returns the data for the specified job status.
		if(jobStatusCache==null|| jobStatusCache.isEmpty()|| !jobStatusCache.containsKey(jobStatusId)) 
			{		
				//no data available in JobStatusCache
				List<JobStatus> allRecords = jobStatusRepo.findAll();
				//refresh JobStatusCache
				 for (JobStatus astObj : allRecords) 
				 {
					 jobStatusCache.put(astObj.getJobStatusId(), astObj);
			     }			 
				 //Check if the updated JobStatusCache has the requested Id if not through IdNotFound exception
				 // Returns the job status object for the given job status ID.
				 if(jobStatusCache.containsKey(jobStatusId))
				 {
					 return jobStatusCache.get(jobStatusId);
				 }
				 else 
				 {
					 throw new IdNotFoundException("Requested ID="+jobStatusId+" is not available in in the table");
				 }
			}
		else 
			{
				//Get data from JobStatusCache by Id 
				JobStatus JobStatus  = null;
				JobStatus = jobStatusCache.get(jobStatusId);	
				//
				// Returns the status of the job.
				if(JobStatus==null)
				{
					throw new IdNotFoundException();
				}
				return JobStatus;
			}				
	}
	/**
	* Clears the cache. Should be called when JobStatus#isFinished () returns false to avoid re - fetching
	*/
	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		jobStatusCache = null;
	}

	/**
	* Delete a list of jobStatus records. This is a bulk delete operation and will clear the cache before deleting the records
	* 
	* @param jobStatusId - the list of jobStatus ids to delete
	* 
	* @return a String indicating success or failure of the delete operation. It is important to note that the return value does not include a message in the message
	*/
	@Override
	public String bulkDelete(List<Integer> jobStatusId) {
		// This method will delete a list of records at a time
		jobStatusRepo.deleteAllById(jobStatusId);
		clearCache();
		return "Records Deleted successfully";
	}

	/**
	* Bulk create a list of job status records. This method is used to create a list of job status records in the database
	* 
	* @param jobStatus - List of job status records to be created
	* 
	* @return String message to display on completion of the action. A null value indicates success or a non - null message to
	*/
	@Override
	public String bulkCreate(List<JobStatus> jobStatus) {
		// This method will save bulk data at a time to the table
		jobStatusRepo.saveAll(jobStatus);
		clearCache();
		return "Records to create list of records";
	}

	
}
