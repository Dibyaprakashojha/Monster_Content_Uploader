package com.monster.content.uploader.rest.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monster.content.uploader.rest.api.entity.JobStatus;
import com.monster.content.uploader.rest.api.entity.ProductLine;
import com.monster.content.uploader.rest.api.service.IJobStatusService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author SivakumarK
 * 
 * DESC: This is the entity class for JobStatus Table
 *
 */

@RestController
@RequestMapping("/api/v1/job-status")
@Tag(name="JobStatus", description = "Provides all the CRUD opration api's for JobStatus Entity")
public class JobStatusController {
	
	@Autowired
	IJobStatusService jobStatusService;
	private Logger logger = LoggerFactory.getLogger(ProductLine.class);
	
	/**
	 * @param jobStatus
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will create a new record in JobStatus Table, using save() of the JPARepository which is call in the JobStatusService Class
     *
	 */
	@PostMapping()
	public ResponseEntity<Object> addNewJobStatus(@RequestBody JobStatus jobStatus){
		logger.info("Inside addNewJobStatus() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Addinging a reccord in JobStatus Table");
        return new ResponseEntity<>(jobStatusService.createRecord(jobStatus), header,HttpStatus.OK);
	}
	
	/**
	 * @param jobStatus
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Update a new record in JobStatus Table, using save() of the JPARepository which is call in the JobStatusService Class.
	 * 		 May throw IdnotfoundException in JobStatusService class if Invalid Id provided.
	 */
	@PutMapping()
	public ResponseEntity<Object> updateJobStatus(@RequestBody JobStatus jobStatus){
		logger.info("Inside updateJobStatus() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Updating a reccord in JobStatus Table");
        return new ResponseEntity<>(jobStatusService.updateRecord(jobStatus), header,HttpStatus.OK);
	}
	
	/**
	 * @param jobStatusId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Delete a new record in JobStatus Table, using save() of the JPARepository which is call in the JobStatusService Class.
	 * 		 May throw IdnotfoundException in JobStatusService class if Invalid Id provided. 
	 */
	@DeleteMapping("/{jobStatusId}")
	public ResponseEntity<Object> deleteJobStatus(@PathVariable("jobStatusId") int jobStatusId){
		logger.info("Inside deleteJobStatus() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Deleting a reccord in JobStatus Table");
        return new ResponseEntity<>(jobStatusService.deleteRecordById(jobStatusId), header,HttpStatus.OK);
	}
	
	/**
	 * @return  Response entity with body(List of all AssetSubType from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will retrieve all the records from JobStatus Table in the form of List<JobStatus>
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Object> getAllJobStatus(){
		logger.info("Inside getAllJobStatus() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get all reccords from JobStatus Table");
        return new ResponseEntity<>(jobStatusService.retrieveAllRecords(), header,HttpStatus.OK);
	}
	
	/**
	 * @param jobStatusId
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will provide one JobStatus object based on requested Id
	 * 		  May throw IdnotfoundException in JobStatusService class if Invalid Id provided.
	 */
	@GetMapping(value="/{jobStatusId}", produces = { "application/json" })
	public ResponseEntity<Object> getJobStatusById(@PathVariable("jobStatusId") int jobStatusId){
		logger.info("Inside getJobStatusById() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get reccord by id from JobStatus Table");
        return new ResponseEntity<>(jobStatusService.retrieveById(jobStatusId), header,HttpStatus.OK);
	}
	/**
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will make the local memory cache hashmap object(jobStatusCache) to null
	 * 
	 */
	@PostMapping("/clear-cache")
	public ResponseEntity<Object> clearLocalCache(){
		logger.info("Inside clearLocalCache() of McuApiController");
	    HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Make the local cache object null");
	    jobStatusService.clearCache();
	    return new ResponseEntity<>("Local cache cleared!!!", header,HttpStatus.OK);
	}
	
	/**
	 * @param jobStatus
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will add a list of JobStatus records to JobStatus table in a single api call
	 * 
	 */
	@PostMapping("/bulk-upload")
	public ResponseEntity<Object> createBulkRecords(@RequestBody List<JobStatus> jobStatuss){
		logger.info("Inside createBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Addinging a list of reccords in Asset Subtype Table");
	    return new ResponseEntity<>(jobStatusService.bulkCreate(jobStatuss), header,HttpStatus.OK);
	}
	
	/**
	 * @param jobStatusId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will delete a list of JobStatus records from JobStatus table
	 */
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> deleteBulkRecords(@RequestBody List<Integer> jobStatusIds){
		logger.info("Inside deleteBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Deleting a list of reccords from Asset Subtype Table");
	    return new ResponseEntity<>(jobStatusService.bulkDelete(jobStatusIds), header,HttpStatus.OK);
	}
}
