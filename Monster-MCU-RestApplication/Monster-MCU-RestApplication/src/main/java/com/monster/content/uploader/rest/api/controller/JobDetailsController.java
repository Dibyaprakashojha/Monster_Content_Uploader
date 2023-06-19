package com.monster.content.uploader.rest.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monster.content.uploader.rest.api.entity.JobDetails;
import com.monster.content.uploader.rest.api.service.IJobDetailsService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author SivakumarK
 * 
 * DESC: This is the entity class for JobDetails Table
 *
 */

@RestController
@RequestMapping("/api/v1/job-details")
@Tag(name="JobDetails", description = "Provides all the CRUD opration api's for JobDetails Entity")
public class JobDetailsController {
	
	@Autowired
	IJobDetailsService jobDetailsService;
	private Logger logger = LoggerFactory.getLogger(JobDetailsController.class);
	
	/**
	 * @param jobDetails
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will create a new record in JobDetails Table, using save() of the JPARepository which is call in the JobDetailsService Class
     *
	 */
	@PostMapping()
	public ResponseEntity<Object> addNewJobDetails(@RequestBody JobDetails jobDetails){
		logger.info("Inside addNewJobDetails() of McuApiController");		
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Addinging a reccord in JobDetails Table");
        return new ResponseEntity<>(jobDetailsService.createRecord(jobDetails), header,HttpStatus.OK);
	}
	
	/**
	 * @param jobDetails
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Update a new record in JobDetails Table, using save() of the JPARepository which is call in the JobDetailsService Class.
	 * 		 May throw IdnotfoundException in JobDetailsService class if Invalid Id provided.
	 */
	@PutMapping()
	public ResponseEntity<Object> updateJobDetails(@RequestBody JobDetails jobDetails){
		logger.info("Inside updateJobDetails() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Updating a reccord in JobDetails Table");
        return new ResponseEntity<>(jobDetailsService.updateRecord(jobDetails), header,HttpStatus.OK);
	}
	
	/**
	 * @param jobDetailsId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Delete a new record in JobDetails Table, using save() of the JPARepository which is call in the JobDetailsService Class.
	 * 		 May throw IdnotfoundException in JobDetailsService class if Invalid Id provided. 
	 */
	@DeleteMapping("/job-details/{jobDetailsId}")
	public ResponseEntity<Object> deleteJobDetails(@PathVariable("jobDetailsId") int jobDetailsId){
		logger.info("Inside deleteJobDetails() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Deleting a reccord in JobDetails Table");
        return new ResponseEntity<>(jobDetailsService.deleteRecordById(jobDetailsId), header,HttpStatus.OK);
	}
	
	/**
	 * @return  Response entity with body(List of all AssetSubType from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will retrieve all the records from JobDetails Table in the form of List<JobDetails>
	 */
//	public ResponseEntity<Object> getAllJobDetails(@CookieValue(value = "OTDSTicket",required = false) String OTDSTicket ){
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Object> getAllJobDetails(){
//        if(OTDSTicket != null && !("".equals(OTDSTicket))){
        	logger.info("Inside getAllJobDetails() of McuApiController");
    		HttpHeaders header = new HttpHeaders();
       		header.add("Desc", "Get all reccords from JobDetails Table");
            return new ResponseEntity<>(jobDetailsService.retrieveAllRecords(), header,HttpStatus.OK);
        }
//        else{
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//        }

	
	
	/**
	 * @param jobDetailsId
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will provide one JobDetails object based on requested Id
	 * 		  May throw IdnotfoundException in JobDetailsService class if Invalid Id provided.
	 */
	@GetMapping(value="/{jobDetailsId}", produces = { "application/json" })
	public ResponseEntity<Object> getJobDetailsById(@PathVariable("jobDetailsId") int jobDetailsId){
		logger.info("Inside getJobDetailsById() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get reccord by id from JobDetails Table");
        return new ResponseEntity<>(jobDetailsService.retrieveById(jobDetailsId), header,HttpStatus.OK);
	}
	
}
