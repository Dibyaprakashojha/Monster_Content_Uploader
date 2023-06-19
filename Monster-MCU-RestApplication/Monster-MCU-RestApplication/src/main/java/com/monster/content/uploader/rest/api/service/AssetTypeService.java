/**
 * 
 */
package com.monster.content.uploader.rest.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.AssetType;
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.repository.AssetTypeRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class AssetTypeService implements IAssetTypeService {
	
	@Autowired
	AssetTypeRepository assetTypeRepo;
	
	private Logger logger = LoggerFactory.getLogger(AssetTypeService.class);
	
	// This is used as a cache storage to avoid multiple database query
    HashMap<Integer,AssetType> assetTypeCache = new HashMap<>();

	/**
	* This method creates a record in the AssetType table. It is used to create an asset type from data passed in
	* 
	* @param assetType - The asset type to be saved
	* 
	* @return String indicating success or failure of the save ( in case of error the message will be displayed to the user
	*/
	@Override
	public String createRecord(AssetType assetType) {
		logger.info("Inside createRecord() of AssetTypeService");
		//this method will add new record in the AssetType Table 
		assetTypeRepo.save(assetType);
		clearCache();
		return "Record Saved Successfully";
	}

	/**
	* This method updates the record in assetType table. It is used to update the record in the AssetType table
	* 
	* @param assetType - The record to be updated
	* 
	* @return String message indicating success or failure of the update ( success or failure depends on the operation that was performed
	*/
	@Override
	public String updateRecord(AssetType assetType) {
		logger.info("Inside updateRecord() of AssetTypeService");
		//this method will update the existing record in the AssetType Table 
		assetTypeRepo.save(assetType);
		clearCache();
		return "Record Updated Successfully";
	}

	/**
	* Delete record by asset type id. This method will update the record in AssetType Table. If Id not found will throw exception
	* 
	* @param assetTypeId - id of record to delete
	* 
	* @return String indicating success or failure of asset type deletion ( success or failure depends on implementation ) Example : deleteRecordById ( 2 )
	*/
	@Override
	public String deleteRecordById(int assetTypeId) {
		logger.info("Inside deleteRecordById() of AssetTypeService");
		//this method will update the existing record in the AssetType Table. If Id not found will throw exception
		assetTypeRepo.findById(assetTypeId).orElseThrow(()-> new IdNotFoundException());
		assetTypeRepo.deleteById(assetTypeId);
		clearCache();
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all AssetType records from database. If not available in cache it will query the data db and get it
	* 
	* 
	* @return List of AssetType objects
	*/
	@Override
	public List<AssetType> retrieveAllRecords() {
		//this method will retrieve all the records from assetSubtypeCache. If data not available need to query the data db and get it.
				//This Caching of the data from database to the local Hashmap will not work in HA server architecture. 
				//To resolve this conflict we need to use "distributed cache" concept. (note: RedisCacheManager can be used)
				
		logger.info("Inside retrieveAllRecords() of AssetSubtypeService");
				List<AssetType> allRecords = new ArrayList<>();
				//checking the availability of data in assetSybtypeCache 
				// Returns all asset types in cache.
				if(assetTypeCache==null || assetTypeCache.isEmpty()) 
				{
					// Not in Cache and Query DB
					allRecords = assetTypeRepo.findAll();
					assetTypeCache = new HashMap<>();
					 for (AssetType astObj : allRecords) 
					 {
						 assetTypeCache.put(astObj.getAssetTypeId(), astObj);
				     }
					
				}
				else 
				{
					// Data Available in Cache
					allRecords = new ArrayList<>(assetTypeCache.values());
				}
				
				return allRecords;
		
	}

	/**
	* Retrieves AssetType based on Id from assetSybtypeCache and assetSybtypeCache. If data not available in assetSybtypeCache it will query the data db and get the data
	* 
	* @param assetTypeId - Id of the AssetType to retrieve
	* 
	* @return AssetType with the specified AssetTypeId or null if not found ( IdNotFoundException will be thrown if not available
	*/
	@Override
	public AssetType retrieveById(int assetTypeId) {
		
		//this method will retrieve all the records based on Id from AssetTypeCache. If data not available need to query the data db and get it
				logger.info("Inside retrieveById() of AssetTypeService");
				
				//checking the availability of data in assetSybtypeCache 
				// Get the AssetType object for the requested Id
				if(assetTypeCache==null|| assetTypeCache.isEmpty()|| !assetTypeCache.containsKey(assetTypeId)) 
				{
					//no data available in AssetTypeCache
					List<AssetType> allRecords = assetTypeRepo.findAll();
					//refresh AssetTypeCache
					 for (AssetType astObj : allRecords) 
					 {
						 assetTypeCache.put(astObj.getAssetTypeId(), astObj);
				     }
					 
					 //Check if the updated AssetTypeCache has the requested Id if not through IdNotFound exception
					 // Returns the asset type for the specified asset type.
					 if(assetTypeCache.containsKey(assetTypeId))
					 {
						 return assetTypeCache.get(assetTypeId);
					 }
					 else 
					 {
						 throw new IdNotFoundException("Requested ID="+assetTypeId+" is not available in in the table");
					 }
				}
				else 
				{
					//Get data from AssetTypeCache by Id 
					System.out.println();
					AssetType AssetType  = null;
					AssetType = assetTypeCache.get(assetTypeId);	
					//
					// throw an exception if asset type is null
					if(AssetType==null)
					{
						throw new IdNotFoundException();
					}
					return AssetType;
				}
	}

	/**
	* Clears the asset type cache. This is called when we need to re - use an asset type for a new
	*/
	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		assetTypeCache = null;
	}

	/**
	* Delete all records by asset type id. This method is used to delete a list of records at a time
	* 
	* @param assetTypeId - List of asset type id
	* 
	* @return String indicating success or failure of the delete operation. The value can be " OK " to indicate success or " NOP " to indicate
	*/
	@Override
	public String bulkDelete(List<Integer> assetTypeId) {
		// This method will delete a list of records at a time
		assetTypeRepo.deleteAllById(assetTypeId);
		clearCache();
		return "Records Deleted successfully";
	}

	/**
	* Bulk create a list of asset types. This method will save all the data to the database and clear the cache
	* 
	* @param assetType - List of asset types to be saved
	* 
	* @return String message to display to the user explaining what went wrong with the action. null will be returned if everything went
	*/
	@Override
	public String bulkCreate(List<AssetType> assetType) {
		// This method will save bulk data at a time to the table
		assetTypeRepo.saveAll(assetType);
		clearCache();
		return "Records to create list of records";
	}

	
}
