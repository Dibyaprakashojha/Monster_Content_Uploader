package com.monster.content.uploader.rest.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.ParticipantDetails;
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.repository.ParticipantDetailsRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class ParticipantDetailsService implements IParticipantDetailsService {
	
	@Autowired
	ParticipantDetailsRepository participantDetailsRepo;
	
	private Logger logger = LoggerFactory.getLogger(ParticipantDetailsService.class);
	
	// This is used as a cache storage to avoid multiple database query
    HashMap<Integer,ParticipantDetails> participantDetailsCache = new HashMap<>();

	/**
	* This method creates a new record in ParticipantDetails table. It is used to create participant details from data source
	* 
	* @param participantDetails - data source of participant details
	* 
	* @return String message to be displayed to the user in case of success or error message to the user in case of
	*/
	@Override
	public String createRecord(ParticipantDetails participantDetails) {
		logger.info("Inside createRecord() of ParticipantDetailsService");
		//this method will add new record in the ParticipantDetails Table 
		participantDetailsRepo.save(participantDetails);
		clearCache();
		return "Record Saved Successfully";
	}

	/**
	* This method updates the record in ParticipantDetails table. It is used to update the record in the database
	* 
	* @param participantDetails - record to be updated in the database
	* 
	* @return String message to be displayed to the user that wants to see the success of the operation. If the operation is unsuccessful an error message will be
	*/
	@Override
	public String updateRecord(ParticipantDetails participantDetails) {
		logger.info("Inside updateRecord() of ParticipantDetailsService");
		//this method will update the existing record in the ParticipantDetails Table 
		participantDetailsRepo.save(participantDetails);
		clearCache();
		return "Record Updated Successfully";
	}

	/**
	* Delete record by participant details id. This method will update the existing record in the ParticipantDetails Table. If Id not found an exception will be thrown
	* 
	* @param participantDetailsId - Id of record to be deleted
	* 
	* @return String status of record deleted Successfully or Error message to display to the user ( not logged in or access denied
	*/
	@Override
	public String deleteRecordById(int participantDetailsId) {
		logger.info("Inside deleteRecordById() of ParticipantDetailsService");
		//this method will update the existing record in the ParticipantDetails Table. If Id not found will throw exception
		participantDetailsRepo.findById(participantDetailsId).orElseThrow(()-> new IdNotFoundException());
		participantDetailsRepo.deleteById(participantDetailsId);
		clearCache();
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all ParticipantDetails from AssetSubtypeService and stores it in local Hashmap for use in HBASE - 32
	* 
	* 
	* @return List of ParticipantDetails with id
	*/
	@Override
	public List<ParticipantDetails> retrieveAllRecords() {
		//this method will retrieve all the records from assetSubtypeCache. If data not available need to query the data db and get it.
		//This Caching of the data from database to the local Hashmap will not work in HA server architecture. 
		//To resolve this conflict we need to use "distributed cache" concept. (note: RedisCacheManager can be used)
				
		logger.info("Inside retrieveAllRecords() of AssetSubtypeService");
		List<ParticipantDetails> allRecords = new ArrayList<>();
		//checking the availability of data in assetSybtypeCache 
		// Returns all the ParticipantDetails objects in the database.
		if(participantDetailsCache==null || participantDetailsCache.isEmpty()) 
			{
				// Not in Cache and Query DB
				allRecords = participantDetailsRepo.findAll();
				participantDetailsCache = new HashMap<>();
				 for (ParticipantDetails astObj : allRecords) 
				 {
					 participantDetailsCache.put(astObj.getPsDetailsId(), astObj);
			     }	
			}
		else 
			{
				// Data Available in Cache
				allRecords = new ArrayList<>(participantDetailsCache.values());
			}	
		return allRecords;
	}

	/**
	* Retrieves ParticipantDetails by Id. This method will retrieve all the records based on Id from assetSybtypeCache
	* 
	* @param participantDetailsId - Id of ParticipantDetails to retrieve
	* 
	* @return ParticipantDetails object with the details retrieved from assetSybtypeCache or throw IdNotFoundException if not found
	*/
	@Override
	public ParticipantDetails retrieveById(int participantDetailsId) {
		
		//this method will retrieve all the records based on Id from ParticipantDetailsCache. If data not available need to query the data db and get it
		logger.info("Inside retrieveById() of ParticipantDetailsService");
				
		//checking the availability of data in assetSybtypeCache 
		// Get ParticipantDetails by Id from ParticipantDetailsCache
		if(participantDetailsCache==null|| participantDetailsCache.isEmpty()|| !participantDetailsCache.containsKey(participantDetailsId)) 
			{		
				//no data available in ParticipantDetailsCache
				List<ParticipantDetails> allRecords = participantDetailsRepo.findAll();
				//refresh ParticipantDetailsCache
				 for (ParticipantDetails astObj : allRecords) 
				 {
					 participantDetailsCache.put(astObj.getPsDetailsId(), astObj);
			     }			 
				 //Check if the updated ParticipantDetailsCache has the requested Id if not through IdNotFound exception
				 // Returns the participant details for the given participantDetailsId.
				 if(participantDetailsCache.containsKey(participantDetailsId))
				 {
					 return participantDetailsCache.get(participantDetailsId);
				 }
				 else 
				 {
					 throw new IdNotFoundException("Requested ID="+participantDetailsId+" is not available in in the table");
				 }
			}
		else 
			{
				//Get data from ParticipantDetailsCache by Id 
				ParticipantDetails ParticipantDetails  = null;
				ParticipantDetails = participantDetailsCache.get(participantDetailsId);	
				//
				// if ParticipantDetails is null.
				if(ParticipantDetails==null)
				{
					throw new IdNotFoundException();
				}
				return ParticipantDetails;
			}				
	}
	/**
	* Clears the cache. This is called when there is a change in participant details or the user is re - logged
	*/
	@Override
	public void clearCache() {
		participantDetailsCache = null;
	}

	/**
	* Delete a list of ParticipantDetails by id. This is a bulk delete operation and will clear the cache
	* 
	* @param participantDetailsId - the id of the participants to delete
	* 
	* @return String success message indicating success or failure of the delete operation ( success is determined by the value of return
	*/
	@Override
	public String bulkDelete(List<Integer> participantDetailsId) {
		// This method will delete a list of records at a time
		participantDetailsRepo.deleteAllById(participantDetailsId);
		clearCache();
		return "Records Deleted successfully";
	}

	/**
	* This method takes a list of ParticipantDetails and saves them to the database. The data will be stored in a table with primary key " id "
	* 
	* @param participantDetails - The list of ParticipantDetails to be saved
	* 
	* @return String message to be displayed on the screen with success or failure of the operation. It is recommended that you return the message to the
	*/
	@Override
	public String bulkCreate(List<ParticipantDetails> participantDetails) {
		// This method will save bulk data at a time to the table
		participantDetailsRepo.saveAll(participantDetails);
		clearCache();
		return "Records to create list of records";
	}

}
