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

import com.monster.content.uploader.rest.api.entity.AssetType;
import com.monster.content.uploader.rest.api.service.IAssetTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author SivakumarK
 * 
 * DESC: This is the entity class for AssetType Table
 *
 */
@RestController
@RequestMapping("/api/v1/asset-type")
@Tag(name="AssetType", description = "Provides all the CRUD opration api's for AssetType Entity")
public class AssetTypeController {
	
	@Autowired
	IAssetTypeService assetTypeService;
	
	private Logger logger = LoggerFactory.getLogger(AssetSubtypeController.class);
	

	/**
	 * @param assetType
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will create a new record in AssetType Table, using save() of the JPARepository which is call in the AssetTypeService Class
	 *
	 */
	@PostMapping()
	public ResponseEntity<Object> addNewAssetType(@RequestBody AssetType assetType){
		logger.info("Inside addNewAssetType() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Addinging a reccord in Asset Type Table");
        return new ResponseEntity<>(assetTypeService.createRecord(assetType), header,HttpStatus.OK);
	}
	
	/**
	 * @param assetType
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Update a new record in AssetType Table, using save() of the JPARepository which is call in the AssetTypeService Class.
	 * 		 May throw IdnotfoundException in AssetTypeService class if Invalid Id provided.
	 */
	@PutMapping()
	public ResponseEntity<Object> updateAssetType(@RequestBody AssetType assetType){
		logger.info("Inside updateAssetType() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Updating a reccord in Asset Type Table");
        return new ResponseEntity<>(assetTypeService.updateRecord(assetType), header,HttpStatus.OK);
	}
	
	/**
	 * @param assetTypeId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Delete a new record in AssetType Table, using save() of the JPARepository which is call in the AssetTypeService Class.
	 * 		 May throw IdnotfoundException in AssetTypeService class if Invalid Id provided. 
	 */
	@DeleteMapping("/{assetTypeId}")
	public ResponseEntity<Object> deleteAssetType(@PathVariable("assetTypeId") int assetTypeId){
		logger.info("Inside deleteAssetType() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Deleting a reccord in Asset Type Table");
        return new ResponseEntity<>(assetTypeService.deleteRecordById(assetTypeId), header,HttpStatus.OK);
	}
	
	/**
	 * @return Response entity with body(List of all AssetSubType from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will retrieve all the records from AssetType Table in the form of List<AssetType>
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Object> getAllAssetType(){
		logger.info("Inside getAllAssetType() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get all reccords from Asset Type Table");
        return new ResponseEntity<>(assetTypeService.retrieveAllRecords(), header,HttpStatus.OK);
	}
	
	/**
	 * @param assetTypeId
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will provide one AssetType object based on requested Id
	 * 		  May throw IdnotfoundException in AssetTypeService class if Invalid Id provided. 
	 */
	@GetMapping(value = "/{assetTypeId}", produces = { "application/json" })
	public ResponseEntity<Object> getAssetTypeById(@PathVariable("assetTypeId") int assetTypeId){
		logger.info("Inside getAssetTypeById() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get reccord by id from Asset Type Table");
        return new ResponseEntity<>(assetTypeService.retrieveById(assetTypeId), header,HttpStatus.OK);
	}
	
	/**
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will make the local memory cache hashmap object(assetTypeCache) to null
	 * 
	 */
	@PostMapping("/clear-cache")
	public ResponseEntity<Object> clearLocalCache(){
		logger.info("Inside clearLocalCache() of McuApiController");
	    HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Make the local cache object null");
	    assetTypeService.clearCache();
	    return new ResponseEntity<>("Local cache cleared!!!", header,HttpStatus.OK);
	}
	
	/**
	 * @param assetType
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will add a list of AssetType records to AssetType table in a single api call
	 * 
	 */
	@PostMapping("/bulk-upload")
	public ResponseEntity<Object> createBulkRecords(@RequestBody List<AssetType> assetSubType){
		logger.info("Inside createBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Addinging a list of reccords in Asset Subtype Table");
	    return new ResponseEntity<>(assetTypeService.bulkCreate(assetSubType), header,HttpStatus.OK);
	}
	
	/**
	 * @param assetTypeId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will delete a list of AssetType records from AssetType table
	 */
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> deleteBulkRecords(@RequestBody List<Integer> assetSubTypeId){
		logger.info("Inside deleteBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Deleting a list of reccords from Asset Subtype Table");
	    return new ResponseEntity<>(assetTypeService.bulkDelete(assetSubTypeId), header,HttpStatus.OK);
	}
	
}
