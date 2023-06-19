package com.monster.content.uploader.rest.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.AssetSubtype;
import com.monster.content.uploader.rest.api.entity.AssetType;
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.repository.AssetSubtypeRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class AssetSubtypeService implements IAssetSubtypeService {
	
	@Autowired
	AssetSubtypeRepository assetSubTypeRepo;
	
	@Autowired
	IAssetTypeService assetTypeService;
	
	private Logger logger = LoggerFactory.getLogger(AssetSubtypeService.class);
	
	// This is used as a cache storage to avoid multiple database query
	HashMap<Integer,AssetSubtype> assetSubtypeCache = new HashMap<>();
	
//	AssetSubtypeUpdateMaper mapper = new AssetSubtypeUpdateMaper();
	
	
	/**
	* This method will create record in AssetSubtype table. After creation it will clear cache. This method will be called when user press create button
	* 
	* @param assetSubtype - Object of AssetSubtype class which contains all data needed to create record
	* 
	* @return String with status of
	*/
	@Override
	public String createRecord(AssetSubtype assetSubtype) {
		//this method will create record in AssetSubtype table
		logger.info("Inside createRecord() of AssetSubtypeService");
		AssetSubtype assteSubtypeResult   = new AssetSubtype();
		assteSubtypeResult =	assetSubTypeRepo.save(assetSubtype);
		System.out.println("assetSubTypr Id:="+assteSubtypeResult.getAssetSubtypeId());
		clearCache();
		return "Record Saved Successfully";
	}

	/**
	* This method updates an asset subtype in the database based on the values passed in. It is used to update the record in the AssetSubtype table
	* 
	* @param assetSubtype - The AssetSubtype object to be updated
	* 
	* @return String status of the
	*/
	@Override
	public String updateRecord(AssetSubtype assetSubtype) {
		//this method will update an existing record based in AssetSubtype table
		logger.info("Inside updateRecord() of AssetSubtypeService \n AssetSubtupe Input:----"+assetSubtype);
		
        AssetSubtype retrievedAssetSubtype = retrieveById(assetSubtype.getAssetSubtypeId());
		
		//setting all the new values to assetSubtypeObject
        retrievedAssetSubtype.setAssetSubtypeDsName(assetSubtype.getAssetSubtypeDsName());
        retrievedAssetSubtype.setAssetSubtypeName(assetSubtype.getAssetSubtypeName());
        retrievedAssetSubtype.setAssetSubtypeSeqId(assetSubtype.getAssetSubtypeSeqId());
        retrievedAssetSubtype.setAssetSubtypeValue(assetSubtype.getAssetSubtypeValue());
		
		int assetTypeId = assetSubtype.getAssetType().getAssetTypeId();
		AssetType assetType = assetTypeService.retrieveById(assetTypeId);
		retrievedAssetSubtype.setAssetType(assetType);
		logger.info("Asset subtype Mapper exicuted. UpdateObject===="+retrievedAssetSubtype);
		assetSubTypeRepo.save(retrievedAssetSubtype);
		logger.info("Save Successfull");
		clearCache();
		logger.info("Cache cleared");
		return "Record Updated Successfully";
	}


	/**
	* Delete record based on assetSubtypeId and clear cache. This method will be called by AssetSubtypeService
	* 
	* @param assetSubtypeId - Id of record to delete
	* 
	* @return String " Record Deleted Successfully " if record deleted successfully otherwise error message will be returned to the client
	*/
	@Override
	public String deleteRecordById(int assetSubtypeId) {
		//this method will delete a record based on the provided Id from AssetSubtype table
		logger.info("Inside deleteRecordById() of AssetSubtypeService");
		AssetSubtype assetSubtype = assetSubTypeRepo.findById(assetSubtypeId).orElseThrow(()-> new IdNotFoundException());
		assetSubTypeRepo.delete(assetSubtype);
		clearCache();
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all AssetSubtype records from DB and cache if not available. This method is called in order to retrieve all records from DB
	* 
	* 
	* @return List of AssetSubtype objects
	*/
	@Override
	public List<AssetSubtype> retrieveAllRecords() {
		//this method will retrieve all the records from assetSubtypeCache. If data not available need to query the data db and get it.
		//This Caching of the data from database to the local Hashmap will not work in HA server architecture. 
		//To resolve this conflict we need to use "distributed cache" concept. (note: RedisCacheManager can be used)
		
		logger.info("Inside retrieveAllRecords() of AssetSubtypeService");
		List<AssetSubtype> allRecords = new ArrayList<>();
		//checking the availability of data in assetSybtypeCache 
		// Returns all asset subtype records in cache.
		if(assetSubtypeCache==null || assetSubtypeCache.isEmpty()) 
		{
			// Not in Cache and Query DB
			allRecords = assetSubTypeRepo.findAll();
			assetSubtypeCache = new HashMap<>();
			 for (AssetSubtype astObj : allRecords) 
			 {
				 assetSubtypeCache.put(astObj.getAssetSubtypeId(), astObj);
		     }
			
		}
		else 
		{
			// Data Available in Cache
			allRecords = new ArrayList<>(assetSubtypeCache.values());
		}
		
		return allRecords;
	}

	/**
	* Retrieves AssetSubtype based on Id. If data not available in assetSybtypeCache it will query the data db and get it
	* 
	* @param assetSubtypeId - Id of the AssetSubtype to retrieve
	* 
	* @return AssetSubtype with the specified Id if it exists in the data db otherwise throws IdNotFoundException to indicate that the assetSubtype does not
	*/
	@Override
	public AssetSubtype retrieveById(int assetSubtypeId) {
		//this method will retrieve all the records based on Id from assetSubtypeCache. If data not available need to query the data db and get it
		logger.info("Inside retrieveById() of AssetSubtypeService");
		
		//checking the availability of data in assetSybtypeCache 
		// Get the data for the given assetSubtypeId from the database
		if(assetSubtypeCache==null|| assetSubtypeCache.isEmpty()|| !assetSubtypeCache.containsKey(assetSubtypeId)) 
		{
			//no data available in assetSubtypeCache
			List<AssetSubtype> allRecords = assetSubTypeRepo.findAll();
			//refresh assetSubtypeCache
			 for (AssetSubtype astObj : allRecords) 
			 {
				 assetSubtypeCache.put(astObj.getAssetSubtypeId(), astObj);
		     }
			 
			 //Check if the updated assetSubtypeCache has the requested Id if not through IdNotFound exception
			 // Returns the asset subtype for the given asset subtype ID.
			 if(assetSubtypeCache.containsKey(assetSubtypeId))
			 {
				 return assetSubtypeCache.get(assetSubtypeId);
			 }
			 else 
			 {
				 throw new IdNotFoundException("Requested ID="+assetSubtypeId+" is not available in in the table");
			 }
		}
		else 
		{
			//Get data from assetSubtypeCache by Id 
			AssetSubtype assetSubtype  = null;
			assetSubtype = assetSubtypeCache.get(assetSubtypeId);	
			//
			// throw an exception if assetSubtype is null.
			if(assetSubtype==null)
			{
				throw new IdNotFoundException();
			}
			return assetSubtype;
		}
		
	}

	/**
	* Clears the cache. This is called when we need to re - render a type in the assetSubtype
	*/
	@Override
	public void clearCache() {
		// This method will clear the data's in the assetSubtypeCache.
		assetSubtypeCache = null;
	}

	/**
	* Delete a list of assetSubtype records. This method is used to delete multiple assetSubtypes at once
	* 
	* @param assetSubtypeId - List of assetSubtype ids to delete
	* 
	* @return String " Records Deleted successfully " if successful null if not. Error message if there was an error performing the
	*/
	@Override
	public String bulkDelete(List<Integer> assetSubtypeId) {
		// This method will delete a list of records at a time
		assetSubTypeRepo.deleteAllById(assetSubtypeId);
		clearCache();
		return "Records Deleted successfully";
	}

	/**
	* Bulk create a list of assetSubtypes. This will save all the data to the database and clear the cache
	* 
	* @param assetSubtypes - List of assetSubtypes to be saved
	* 
	* @return String message to display on completion of the action. null will be returned on success or an error message
	*/
	@Override
	public String bulkCreate(List<AssetSubtype> assetSubtypes) {
		// This method will save bulk data at a time to the table
		assetSubTypeRepo.saveAll(assetSubtypes);
		clearCache();
		return "Records to create list of records";
	}
	
}
