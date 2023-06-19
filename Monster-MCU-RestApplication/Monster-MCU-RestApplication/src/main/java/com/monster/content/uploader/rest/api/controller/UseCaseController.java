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

import com.monster.content.uploader.rest.api.entity.ProductLine;
import com.monster.content.uploader.rest.api.entity.UseCase;
import com.monster.content.uploader.rest.api.service.IUseCaseService;

import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/v1/use-case")
@Tag(name="UseCase", description = "Provides all the CRUD opration api's for UseCase Entity")
public class UseCaseController {
	
	@Autowired
	IUseCaseService useCaseService;
	private Logger logger = LoggerFactory.getLogger(ProductLine.class);
	
	/**
	 * @param useCase
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will create a new record in UseCase Table, using save() of the JPARepository which is call in the UseCaseService Class
     *
	 */
	@PostMapping()
	public ResponseEntity<Object> addNewUseCase(@RequestBody UseCase useCase){
		logger.info("Inside addNewUseCase() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Addinging a reccord in UseCase Table");
        return new ResponseEntity<>(useCaseService.createRecord(useCase), header,HttpStatus.OK);
	}
	
	/**
	 * @param useCase
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Update a new record in UseCase Table, using save() of the JPARepository which is call in the UseCaseService Class.
	 * 		 May throw IdnotfoundException in UseCaseService class if Invalid Id provided.
	 */
	@PutMapping()
	public ResponseEntity<Object> updateUseCase(@RequestBody UseCase useCase){
		logger.info("Inside updateUseCase() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Updating a reccord in UseCase Table");
        return new ResponseEntity<>(useCaseService.updateRecord(useCase), header,HttpStatus.OK);
	}
	
	/**
	 * @param useCaseId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Delete a new record in UseCase Table, using save() of the JPARepository which is call in the UseCaseService Class.
	 * 		 May throw IdnotfoundException in UseCaseService class if Invalid Id provided. 
	 */
	@DeleteMapping("/{useCaseId}")
	public ResponseEntity<Object> deleteUseCase(@PathVariable("useCaseId") int useCaseId){
		logger.info("Inside deleteUseCase() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Deleting a reccord in UseCase Table");
        return new ResponseEntity<>(useCaseService.deleteRecordById(useCaseId), header,HttpStatus.OK);
	}
	
	/**
	 * @return  Response entity with body(List of all AssetSubType from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will retrieve all the records from UseCase Table in the form of List<UseCase>
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Object> getAllUseCase(){
		logger.info("Inside getAllUseCase() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get all reccords from UseCase Table");
        return new ResponseEntity<>(useCaseService.retrieveAllRecords(), header,HttpStatus.OK);
	}
	
	/**
	 * @param useCaseId
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will provide one UseCase object based on requested Id
	 * 		  May throw IdnotfoundException in UseCaseService class if Invalid Id provided.
	 */
	@GetMapping(value = "/{useCaseId}", produces = { "application/json" })
	public ResponseEntity<Object> getUseCaseById(@PathVariable("useCaseId") int useCaseId){
		logger.info("Inside getUseCaseById() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get reccord by id from UseCase Table");
        return new ResponseEntity<>(useCaseService.retrieveById(useCaseId), header,HttpStatus.OK);
	}
	/**
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will make the local memory cache hashmap object(useCaseCache) to null
	 * 
	 */
	@PostMapping("/clear-cache")
	public ResponseEntity<Object> clearLocalCache(){
		logger.info("Inside clearLocalCache() of McuApiController");
	    HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Make the local cache object null");
	    useCaseService.clearCache();
	    return new ResponseEntity<>("Local cache cleared!!!", header,HttpStatus.OK);
	}
	
	/**
	 * @param useCase
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will add a list of UseCase records to UseCase table in a single api call
	 * 
	 */
	@PostMapping("/bulk-upload")
	public ResponseEntity<Object> createBulkRecords(@RequestBody List<UseCase> useCases){
		logger.info("Inside createBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Addinging a list of reccords in Asset Subtype Table");
	    return new ResponseEntity<>(useCaseService.bulkCreate(useCases), header,HttpStatus.OK);
	}
	
	/**
	 * @param useCaseId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will delete a list of UseCase records from UseCase table
	 */
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> deleteBulkRecords(@RequestBody List<Integer> useCaseIds){
		logger.info("Inside deleteBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Deleting a list of reccords from Asset Subtype Table");
	    return new ResponseEntity<>(useCaseService.bulkDelete(useCaseIds), header,HttpStatus.OK);
	}
	
}
