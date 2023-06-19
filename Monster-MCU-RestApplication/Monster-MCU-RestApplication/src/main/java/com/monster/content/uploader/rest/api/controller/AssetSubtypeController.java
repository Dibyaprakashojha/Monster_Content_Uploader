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

import com.monster.content.uploader.rest.api.entity.AssetSubtype;
import com.monster.content.uploader.rest.api.service.IAssetSubtypeService;

import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * @author SivakumarK
 *
 */
@RestController
@RequestMapping("/api/v1/asset-subtype")
@Tag(name="AssetSubtype", description = "Provides all the CRUD opration api's for AssetSubtype Entity")
public class AssetSubtypeController {
	
	@Autowired
	IAssetSubtypeService assetSubTypeService;
	
	private Logger logger = LoggerFactory.getLogger(AssetSubtypeController.class);
	

	/**
	 * @param assetSubTypeDto
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will create a new record in AssetSubtype Table, using save() of the JPARepository which is call in the AssetSubtypeService Class
	 */
	@PostMapping()
	public ResponseEntity<Object> addNewAssetSubtype(@RequestBody AssetSubtype assetSubType){
		logger.info("Inside addNewAssetSubtype() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Addinging a reccord in Asset Subtype Table");
        return new ResponseEntity<>(assetSubTypeService.createRecord(assetSubType), header,HttpStatus.OK);
	}
	
	/**
	 * @param assetSubType
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Update a new record in AssetSubtype Table, using save() of the JPARepository which is call in the AssetSubtypeService Class.
	 * 		 May throw IdnotfoundException in AssetSubTypeService class if Invalid Id provided.
	 */
	@PutMapping()
	public ResponseEntity<Object> updateAssetSubtype(@RequestBody AssetSubtype assetSubType){
		logger.info("Inside updateAssetSubtype() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Updating a reccord in Asset Subtype Table");
        return new ResponseEntity<>(assetSubTypeService.updateRecord(assetSubType), header,HttpStatus.OK);
	}
	
	/**
	 * @param assetSubTypeId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Delete a new record in AssetSubtype Table, using save() of the JPARepository which is call in the AssetSubtypeService Class.
	 * 		 May throw IdnotfoundException in AssetSubTypeService class if Invalid Id provided. 
	 */
	@DeleteMapping("/{assetSubtypeId}")
	public ResponseEntity<Object> deleteAssetSubtype(@PathVariable("assetSubtypeId") int assetSubTypeId){
		logger.info("Inside deleteAssetSubtype() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Deleting a reccord in Asset Subtype Table");
        return new ResponseEntity<>(assetSubTypeService.deleteRecordById(assetSubTypeId), header,HttpStatus.OK);
	}
	
	/**
	 * @return Response entity with body(List of all AssetSubType from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will retrieve all the records from AssetSubtype Table in the form of List<AssetSubtype>
	 * 
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Object> getAllAssetSubtype(){
		logger.info("Inside getAllAssetSubtype() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get all reccords from Asset Subtype Table");
        return new ResponseEntity<Object>(assetSubTypeService.retrieveAllRecords(), header,HttpStatus.OK);
	}
	
	/**
	 * @param assetSubTypeId
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will provide one AssetSubType object based on requested Id
	 * 		  May throw IdnotfoundException in AssetSubTypeService class if Invalid Id provided. 
	 */
	@GetMapping(value ="/{assetSubtypeId}", produces = { "application/json" })
	public ResponseEntity<Object> getAssetSubtypeById(@PathVariable("assetSubtypeId") int assetSubTypeId){
		logger.info("Inside getAssetSubtypeById() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get reccord by id from Asset Subtype Table");
        return new ResponseEntity<>(assetSubTypeService.retrieveById(assetSubTypeId), header,HttpStatus.OK);
	}
	
	
	/**
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will make the local memory cache hashmap object(assetSubtypeCache) to null
	 * 
	 */
	@PostMapping("/clear-cache")
	public ResponseEntity<Object> clearLocalCache(){
		logger.info("Inside clearLocalCache() of McuApiController");
	    HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Make the local cache object null");
	    assetSubTypeService.clearCache();
	    return new ResponseEntity<>("Local cache cleared!!!", header,HttpStatus.OK);
	}
	
	/**
	 * @param assetSubType
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will add a list of AssetSubtype records to AssetSubtype table in a single api call
	 * 
	 */
	@PostMapping("/bulk-upload")
	public ResponseEntity<Object> createBulkRecords(@RequestBody List<AssetSubtype> assetSubType){
		logger.info("Inside createBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Addinging a list of reccords in Asset Subtype Table");
	    return new ResponseEntity<>(assetSubTypeService.bulkCreate(assetSubType), header,HttpStatus.OK);
	}
	
	/**
	 * @param assetSubTypeId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will delete a list of AssetSubtype records from AssetSubtype table
	 */
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> deleteBulkRecords(@RequestBody List<Integer> assetSubTypeId){
		logger.info("Inside deleteBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Deleting a list of reccords from Asset Subtype Table");
	    return new ResponseEntity<>(assetSubTypeService.bulkDelete(assetSubTypeId), header,HttpStatus.OK);
	}

}
