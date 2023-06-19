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

import com.monster.content.uploader.rest.api.entity.Country;
import com.monster.content.uploader.rest.api.service.ICountryService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author SivakumarK
 * 
 * DESC: This is the entity class for Country Table
 *
 */

@RestController
@RequestMapping("/api/v1/country")
@Tag(name="Country", description = "Provides all the CRUD opration api's for Country Entity")
public class CountryController {
	
	@Autowired
	ICountryService countryService;
	private Logger logger = LoggerFactory.getLogger(CountryController.class);
	
	
	/**
	 * @param country
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will create a new record in Country Table, using save() of the JPARepository which is call in the CountryService Class
     *
	 */
	@PostMapping()
	public ResponseEntity<Object> addNewCountry(@RequestBody Country country){
		logger.info("Inside addNewCountry() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Addinging a reccord in Country Table");
        return new ResponseEntity<>(countryService.createRecord(country), header,HttpStatus.OK);
	}
	
	/**
	 * @param country
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Update a new record in Country Table, using save() of the JPARepository which is call in the CountryService Class.
	 * 		 May throw IdnotfoundException in CountryService class if Invalid Id provided.
	 */
	@PutMapping()
	public ResponseEntity<Object> updateCountry(@RequestBody Country country){
		logger.info("Inside updateCountry() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Updating a reccord in Country Table");
        return new ResponseEntity<>(countryService.updateRecord(country), header,HttpStatus.OK);
	}
	
	/**
	 * @param countryId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Delete a new record in Country Table, using save() of the JPARepository which is call in the CountryService Class.
	 * 		 May throw IdnotfoundException in CountryService class if Invalid Id provided.
	 */
	@DeleteMapping("/{countryId}")
	public ResponseEntity<Object> deleteCountry(@PathVariable("countryId") int countryId){
		logger.info("Inside deleteCountry() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Deleting a reccord in Country Table");
        return new ResponseEntity<>(countryService.deleteRecordById(countryId), header,HttpStatus.OK);
	}
	
	/**
	 * @return  Response entity with body(List of all AssetSubType from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will retrieve all the records from Country Table in the form of List<Country>
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Object> getAllCountry(){
		logger.info("Inside getAllCountry() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get all reccords from Country Table");
        return new ResponseEntity<>(countryService.retrieveAllRecords(), header,HttpStatus.OK);
	}
	
	/**
	 * @param countryId
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will provide one Country object based on requested Id
	 * 		  May throw IdnotfoundException in CountryService class if Invalid Id provided.
	 */	
	@GetMapping(value="/{countryId}", produces = { "application/json" })
	public ResponseEntity<Object> getCountryById(@PathVariable("countryId") int countryId){
		logger.info("Inside getCountryById() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get reccord by id from Country Table");
        return new ResponseEntity<>(countryService.retrieveById(countryId), header,HttpStatus.OK);
	}
	/**
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will make the local memory cache hashmap object(countryCache) to null
	 * 
	 */
	@PostMapping("/clear-cache")
	public ResponseEntity<Object> clearLocalCache(){
		logger.info("Inside clearLocalCache() of McuApiController");
	    HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Make the local cache object null");
	    countryService.clearCache();
	    return new ResponseEntity<>("Local cache cleared!!!", header,HttpStatus.OK);
	}
	
	/**
	 * @param country
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will add a list of Country records to Country table in a single api call
	 * 
	 */
	@PostMapping("/bulk-upload")
	public ResponseEntity<Object> createBulkRecords(@RequestBody List<Country> countrys){
		logger.info("Inside createBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Addinging a list of reccords in Asset Subtype Table");
	    return new ResponseEntity<>(countryService.bulkCreate(countrys), header,HttpStatus.OK);
	}
	
	/**
	 * @param countryId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will delete a list of Country records from Country table
	 */
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> deleteBulkRecords(@RequestBody List<Integer> countryIds){
		logger.info("Inside deleteBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Deleting a list of reccords from Asset Subtype Table");
	    return new ResponseEntity<>(countryService.bulkDelete(countryIds), header,HttpStatus.OK);
	}
	
}
