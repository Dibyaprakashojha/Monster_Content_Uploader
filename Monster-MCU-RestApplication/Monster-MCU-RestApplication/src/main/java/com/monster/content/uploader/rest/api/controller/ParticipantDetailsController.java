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

import com.monster.content.uploader.rest.api.entity.ParticipantDetails;
import com.monster.content.uploader.rest.api.service.IParticipantDetailsService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/participant-details")
@Tag(name="ParticipantDetails", description = "Provides all the CRUD opration api's for ParticipantDetails Entity")
public class ParticipantDetailsController {
	
	@Autowired
	IParticipantDetailsService participantDetailsService;
	private Logger logger = LoggerFactory.getLogger(ParticipantDetailsController.class);
	
	/**
	 * @param participantDetails
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will create a new record in ParticipantDetails Table, using save() of the JPARepository which is call in the ParticipantDetailsService Class
     *
	 */
	@PostMapping()
	public ResponseEntity<Object> addNewParticipantDetails(@RequestBody ParticipantDetails participantDetails){
		logger.info("Inside addNewParticipantDetails() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Addinging a reccord in ParticipantDetails Table");
        return new ResponseEntity<>(participantDetailsService.createRecord(participantDetails), header,HttpStatus.OK);
	}
	
	/**
	 * @param participantDetails
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Update a new record in ParticipantDetails Table, using save() of the JPARepository which is call in the ParticipantDetailsService Class.
	 * 		 May throw IdnotfoundException in ParticipantDetailsService class if Invalid Id provided.
	 */
	@PutMapping()
	public ResponseEntity<Object> updateParticipantDetails(@RequestBody ParticipantDetails participantDetails){
		logger.info("Inside updateParticipantDetails() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Updating a reccord in ParticipantDetails Table");
        return new ResponseEntity<>(participantDetailsService.updateRecord(participantDetails), header,HttpStatus.OK);
	}
	
	/**
	 * @param participantDetailsId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Delete a new record in ParticipantDetails Table, using save() of the JPARepository which is call in the ParticipantDetailsService Class.
	 * 		 May throw IdnotfoundException in ParticipantDetailsService class if Invalid Id provided. 
	 */
	@DeleteMapping("/{participantDetailsId}")
	public ResponseEntity<Object> deleteParticipantDetails(@PathVariable("participantDetailsId") int participantDetailsId){
		logger.info("Inside deleteParticipantDetails() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Deleting a reccord in ParticipantDetails Table");
        return new ResponseEntity<>(participantDetailsService.deleteRecordById(participantDetailsId), header,HttpStatus.OK);
	}
	
	/**
	 * @return  Response entity with body(List of all AssetSubType from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will retrieve all the records from ParticipantDetails Table in the form of List<ParticipantDetails>
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Object> getAllParticipantDetails(){
		logger.info("Inside getAllParticipantDetails() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get all reccords from ParticipantDetails Table");
        return new ResponseEntity<>(participantDetailsService.retrieveAllRecords() , header,HttpStatus.OK);
	}
	/**
	 * @param participantDetailsId
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will provide one ParticipantDetails object based on requested Id
	 * 		  May throw IdnotfoundException in ParticipantDetailsService class if Invalid Id provided.
	 */
	@GetMapping(value ="/{participantDetailsId}", produces = { "application/json" })
	public ResponseEntity<Object> getParticipantDetailsById(@PathVariable("participantDetailsId") int participantDetailsId){
		logger.info("Inside getParticipantDetailsById() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get reccord by id from ParticipantDetails Table");
        return new ResponseEntity<>(participantDetailsService.retrieveById(participantDetailsId), header,HttpStatus.OK);
	}
	/**
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will make the local memory cache hashmap object(participantDetailsCache) to null
	 * 
	 */
	@PostMapping("/clear-cache")
	public ResponseEntity<Object> clearLocalCache(){
		logger.info("Inside clearLocalCache() of McuApiController");
	    HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Make the local cache object null");
	    participantDetailsService.clearCache();
	    return new ResponseEntity<>("Local cache cleared!!!", header,HttpStatus.OK);
	}
	
	/**
	 * @param participantDetails
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will add a list of ParticipantDetails records to ParticipantDetails table in a single api call
	 * 
	 */
	@PostMapping("/bulk-upload")
	public ResponseEntity<Object> createBulkRecords(@RequestBody List<ParticipantDetails> participantDetailss){
		logger.info("Inside createBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Addinging a list of reccords in Asset Subtype Table");
	    return new ResponseEntity<>(participantDetailsService.bulkCreate(participantDetailss), header,HttpStatus.OK);
	}
	
	/**
	 * @param participantDetailsId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will delete a list of ParticipantDetails records from ParticipantDetails table
	 */
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> deleteBulkRecords(@RequestBody List<Integer> participantDetailsIds){
		logger.info("Inside deleteBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Deleting a list of reccords from Asset Subtype Table");
	    return new ResponseEntity<>(participantDetailsService.bulkDelete(participantDetailsIds), header,HttpStatus.OK);
	}
	
}
