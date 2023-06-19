package com.monster.content.uploader.rest.api.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.UseCase;
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.repository.UseCaseRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class UseCaseService implements IUseCaseService {
	
	@Autowired
	UseCaseRepository useCaseRepo;
	
	private Logger logger = LoggerFactory.getLogger(UseCaseService.class);
	
	// This is used as a cache storage to avoid multiple database query
    HashMap<Integer,UseCase> useCaseCache = new HashMap<>();

	/**
	* This method creates a record in the UseCase table. The data will be saved in the database and the cache will be cleared
	* 
	* @param useCase - The UseCase to be created
	* 
	* @return String indicating success or failure of the operation ( success or failure depends on the operation that was performed.
	*/
	@Override
	public String createRecord(UseCase useCase) {
		logger.info("Inside createRecord() of UseCaseService");
		//this method will add new record in the UseCase Table 
		useCaseRepo.save(useCase);
		clearCache();
		return "Record Saved Successfully";
	}

	/**
	* This method updates the record in the UseCase table. It is used to update the data in the database
	* 
	* @param useCase - The use case that needs to be updated
	* 
	* @return String indicating success or failure of the update ( success or failure depends on the operation that was performed.
	*/
	@Override
	public String updateRecord(UseCase useCase) {
		logger.info("Inside updateRecord() of UseCaseService");
		//this method will update the existing record in the UseCase Table 
		useCaseRepo.save(useCase);
		clearCache();
		return "Record Updated Successfully";
	}

	/**
	* Delete record by useCaseId. This method will update the existing record in the UseCase Table.
	* 
	* @param useCaseId - Id of record to delete
	* 
	* @return String status of operation Success or Error message in case of failure ( not found or not delete success )
	*/
	@Override
	public String deleteRecordById(int useCaseId) {
		logger.info("Inside deleteRecordById() of UseCaseService");
		//this method will update the existing record in the UseCase Table. If Id not found will throw exception
		useCaseRepo.findById(useCaseId).orElseThrow(()-> new IdNotFoundException());
		useCaseRepo.deleteById(useCaseId);
		clearCache();
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all records from assetSubtypeCache. If not in cache it will query the data db and get it
	* 
	* 
	* @return List of UseCase objects
	*/
	@Override
	public List<UseCase> retrieveAllRecords() {
		//this method will retrieve all the records from assetSubtypeCache. If data not available need to query the data db and get it.
		//This Caching of the data from database to the local Hashmap will not work in HA server architecture. 
		//To resolve this conflict we need to use "distributed cache" concept. (note: RedisCacheManager can be used)
				
		logger.info("Inside retrieveAllRecords() of AssetSubtypeService");
		List<UseCase> allRecords = new ArrayList<>();
		//checking the availability of data in assetSybtypeCache 
		// Returns all the use cases in the cache.
		if(useCaseCache==null || useCaseCache.isEmpty()) 
			{
				// Not in Cache and Query DB
				allRecords = useCaseRepo.findAll();
				useCaseCache = new HashMap<>();
				 for (UseCase astObj : allRecords) 
				 {
					 useCaseCache.put(astObj.getUseCaseId(), astObj);
			     }	
			}
		else 
			{
				// Data Available in Cache
				allRecords = new ArrayList<>(useCaseCache.values());
			}	
		return allRecords;
	}

	/**
	* Retrieves a use case based on Id. If data is not available in assetSybtypeCache it will query the data db and get it
	* 
	* @param useCaseId - Id of the data to retrieve
	* 
	* @return UseCase object with the data that was stored in assetSybtypeCache or the data in the
	*/
	@Override
	public UseCase retrieveById(int useCaseId) {
		
		//this method will retrieve all the records based on Id from UseCaseCache. If data not available need to query the data db and get it
		logger.info("Inside retrieveById() of UseCaseService");
				
		//checking the availability of data in assetSybtypeCache 
		// Get the data for the given UseCaseId from the database.
		if(useCaseCache==null|| useCaseCache.isEmpty()|| !useCaseCache.containsKey(useCaseId)) 
			{		
				//no data available in UseCaseCache
				List<UseCase> allRecords = useCaseRepo.findAll();
				//refresh UseCaseCache
				 for (UseCase astObj : allRecords) 
				 {
					 useCaseCache.put(astObj.getUseCaseId(), astObj);
			     }			 
				 //Check if the updated UseCaseCache has the requested Id if not through IdNotFound exception
				 // Returns the use case for the given ID.
				 if(useCaseCache.containsKey(useCaseId))
				 {
					 return useCaseCache.get(useCaseId);
				 }
				 else 
				 {
					 throw new IdNotFoundException("Requested ID="+useCaseId+" is not available in in the table");
				 }
			}
		else 
			{
				//Get data from UseCaseCache by Id 
				UseCase UseCase  = null;
				UseCase = useCaseCache.get(useCaseId);	
				//
				// Returns the use case identifier.
				if(UseCase==null)
				{
					throw new IdNotFoundException();
				}
				return UseCase;
			}				
	}
	/**
	* Clears the cache. This is called when we need to re - use an existing Case instance for a new
	*/
	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		useCaseCache = null;
	}

	/**
	* Delete all records for useCaseId. This method is called by bulkDeleteAction. If you want to delete more than one useCase use deleteAll method should be used
	* 
	* @param useCaseId - List of record ids to delete
	* 
	* @return String " Records Deleted successfully " if successful error message if not. Null will be returned if there is no error
	*/
	@Override
	public String bulkDelete(List<Integer> useCaseId) {
		// This method will delete a list of records at a time
		useCaseRepo.deleteAllById(useCaseId);
		clearCache();
		return "Records Deleted successfully";
	}

	/**
	* Bulk create use case. This method will save all data to the database and clear the cache. If there is an error it will return an error message
	* 
	* @param useCase - List of use case to be saved
	* 
	* @return Error message or null if no error was found in the data source ( s ) or the list of
	*/
	@Override
	public String bulkCreate(List<UseCase> useCase) {
		// This method will save bulk data at a time to the table
		useCaseRepo.saveAll(useCase);
		clearCache();
		return "Records to create list of records";
	}

}
