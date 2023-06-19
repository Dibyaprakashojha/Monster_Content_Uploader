package com.monster.content.uploader.rest.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.Country;
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.repository.CountryRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class CountryService implements ICountryService {
	
	@Autowired
	CountryRepository countryRepo;
	
	private Logger logger = LoggerFactory.getLogger(CountryService.class);
	
	// This is used as a cache storage to avoid multiple database query
    HashMap<Integer,Country> countryCache = new HashMap<>();

	/**
	* This method creates a record in the Country table. The record will be added as new row in the Country table
	* 
	* @param country - Country object to be added
	* 
	* @return String message to be displayed to the user after the operation is completed. If the operation is successful the message will be " Record Saved Successfully "
	*/
	@Override
	public String createRecord(Country country) {
		logger.info("Inside createRecord() of CountryService");
		//this method will add new record in the Country Table 
		countryRepo.save(country);
		clearCache();
		return "Record Saved Successfully";
	}

	/**
	* This method updates the record in the Country table. It is used to update the Country with the data that has been entered by the user
	* 
	* @param country - Country object that needs to be updated
	* 
	* @return String status of the
	*/
	@Override
	public String updateRecord(Country country) {
		logger.info("Inside updateRecord() of CountryService");
		//this method will update the existing record in the Country Table 
		countryRepo.save(country);
		clearCache();
		return "Record Updated Successfully";
	}

	/**
	* Delete record by CountryId. This method will update the Country Table. If Id not found will throw exception
	* 
	* @param countryId - Id of record to delete
	* 
	* @return String indicating success or failure of the operation. It will return " Record Deleted Successfully " if record deleted
	*/
	@Override
	public String deleteRecordById(int countryId) {
		logger.info("Inside deleteRecordById() of CountryService");
		//this method will update the existing record in the Country Table. If Id not found will throw exception
		countryRepo.findById(countryId).orElseThrow(()-> new IdNotFoundException());
		countryRepo.deleteById(countryId);
		clearCache();
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all records from AssetSubtypeService and stores them in cache for future use. This method is called in order to retrieve all records from database
	* 
	* 
	* @return List of Country objects
	*/
	@Override
	public List<Country> retrieveAllRecords() {
		//this method will retrieve all the records from assetSubtypeCache. If data not available need to query the data db and get it.
		//This Caching of the data from database to the local Hashmap will not work in HA server architecture. 
		//To resolve this conflict we need to use "distributed cache" concept. (note: RedisCacheManager can be used)
				
		logger.info("Inside retrieveAllRecords() of AssetSubtypeService");
		List<Country> allRecords = new ArrayList<>();
		//checking the availability of data in assetSybtypeCache 
		// Returns all countries in the database.
		if(countryCache==null || countryCache.isEmpty()) 
			{
				// Not in Cache and Query DB
				allRecords = countryRepo.findAll();
				countryCache = new HashMap<>();
				 for (Country astObj : allRecords) 
				 {
					 countryCache.put(astObj.getCountryId(), astObj);
			     }	
			}
		else 
			{
				// Data Available in Cache
				allRecords = new ArrayList<>(countryCache.values());
			}	
		return allRecords;
	}

	/**
	* This method retrieves Country by Id. If data is not available in assetSybtypeCache it will query the db and get the data
	* 
	* @param countryId - Id of the Country to be retrieved
	* 
	* @return Country object with the CountryId as parameter or null if not found ( IdNotFoundException will be thrown in case of not
	*/
	@Override
	public Country retrieveById(int countryId) {
		
		//this method will retrieve all the records based on Id from CountryCache. If data not available need to query the data db and get it
		logger.info("Inside retrieveById() of CountryService");
				
		//checking the availability of data in assetSybtypeCache 
		// Get the Country object for the specified CountryId
		if(countryCache==null|| countryCache.isEmpty()|| !countryCache.containsKey(countryId)) 
			{		
				//no data available in CountryCache
				List<Country> allRecords = countryRepo.findAll();
				//refresh CountryCache
				 for (Country astObj : allRecords) 
				 {
					 countryCache.put(astObj.getCountryId(), astObj);
			     }			 
				 //Check if the updated CountryCache has the requested Id if not through IdNotFound exception
				 // Returns the country object for the given country ID.
				 if(countryCache.containsKey(countryId))
				 {
					 return countryCache.get(countryId);
				 }
				 else 
				 {
					 throw new IdNotFoundException("Requested ID="+countryId+" is not available in in the table");
				 }
			}
		else 
			{
				//Get data from CountryCache by Id 
				Country Country  = null;
				Country = countryCache.get(countryId);	
				//
				// if country is null or country is null
				if(Country==null)
				{
					throw new IdNotFoundException();
				}
				return Country;
			}				
	}
	/**
	* Clears the cache. This is called when the user clicks the clear button in the navigation bar to force a re
	*/
	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		countryCache = null;
	}

	/**
	* Delete a list of records by country id. This method is used for bulk delete in case of multiple records
	* 
	* @param countryId - List of ids of records to delete
	* 
	* @return String indicating success or failure of the bulk delete operation. If successful the content of the response will be " Records Deleted successfully "
	*/
	@Override
	public String bulkDelete(List<Integer> countryId) {
		// This method will delete a list of records at a time
		countryRepo.deleteAllById(countryId);
		clearCache();
		return "Records Deleted successfully";
	}

	/**
	* Bulk create a list of countries. This method will save all data to the database and clear the cache
	* 
	* @param country - List of countries to be saved
	* 
	* @return String to display to the user after completing the bulk operation null if successful error message if not ( in case of error
	*/
	@Override
	public String bulkCreate(List<Country> country) {
		// This method will save bulk data at a time to the table
		countryRepo.saveAll(country);
		clearCache();
		return "Records to create list of records";
	}

	
}
