package com.monster.content.uploader.rest.api.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.ProductLine;
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.repository.ProductLineRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class ProductLineService implements IProductLineService {
	
	@Autowired
	ProductLineRepository productLineRepo;
	
	private Logger logger = LoggerFactory.getLogger(ProductLineService.class);
	
	// This is used as a cache storage to avoid multiple database query
    HashMap<Integer,ProductLine> productLineCache = new HashMap<>();

	/**
	* This method creates a record in ProductLine table. It is used to create product line in database.
	* 
	* @param productLine - ProductLine object to be saved. It is used to create record in database.
	* 
	* @return String message indicating success of operation. It is used in UI to display message to user and get response
	*/
	@Override
	public String createRecord(ProductLine productLine) {
		logger.info("Inside createRecord() of ProductLineService");
		//this method will add new record in the ProductLine Table 
		productLineRepo.save(productLine);
		clearCache();
		return "Record Saved Successfully";
	}

	/**
	* This method updates the record in ProductLine table. It is used to update the product line information in the database
	* 
	* @param productLine - ProductLine that needs to be updated
	* 
	* @return String that represents success of operation ( updated record is returned in String variable ) or failure of operation ( failed
	*/
	@Override
	public String updateRecord(ProductLine productLine) {
		logger.info("Inside updateRecord() of ProductLineService");
		//this method will update the existing record in the ProductLine Table 
		productLineRepo.save(productLine);
		clearCache();
		return "Record Updated Successfully";
	}

	/**
	* Delete record by ProductLine ID. This method will update the record in ProductLine Table. If Id not found will throw exception
	* 
	* @param productLineId - ProductLine ID to be deleted
	* 
	* @return String status of operation Successfully deleted record or Error in case of failure ( IdNotFoundException ) Example : " Record Deleted Successfully
	*/
	@Override
	public String deleteRecordById(int productLineId) {
		logger.info("Inside deleteRecordById() of ProductLineService");
		//this method will update the existing record in the ProductLine Table. If Id not found will throw exception
		productLineRepo.findById(productLineId).orElseThrow(()-> new IdNotFoundException());
		productLineRepo.deleteById(productLineId);
		clearCache();
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all ProductLine records from AssetSubtypeService. It is used to retrieve all data from DB and cache
	* 
	* 
	* @return List of all ProductLine
	*/
	@Override
	public List<ProductLine> retrieveAllRecords() {
		//this method will retrieve all the records from assetSubtypeCache. If data not available need to query the data db and get it.
		//This Caching of the data from database to the local Hashmap will not work in HA server architecture. 
		//To resolve this conflict we need to use "distributed cache" concept. (note: RedisCacheManager can be used)
				
		logger.info("Inside retrieveAllRecords() of AssetSubtypeService");
		List<ProductLine> allRecords = new ArrayList<>();
		//checking the availability of data in assetSybtypeCache 
		// Cache the product line data for all products in the database.
		if(productLineCache==null || productLineCache.isEmpty()) 
			{
				// Not in Cache and Query DB
				allRecords = productLineRepo.findAll();
				productLineCache = new HashMap<>();
				 for (ProductLine astObj : allRecords) 
				 {
					 productLineCache.put(astObj.getPrdLineId(), astObj);
			     }	
			}
		else 
			{
				// Data Available in Cache
				allRecords = new ArrayList<>(productLineCache.values());
			}	
		return allRecords;
	}

	/**
	* This method retrieves productLine by Id from cache or database. If data not available in cache or database does not contain the requested id it will get data from database
	* 
	* @param productLineId - Id of productLine to retrieve
	* 
	* @return ProductLine with specified id from cache or database throws IdNotFoundException if not available in cache or database does not
	*/
	@Override
	public ProductLine retrieveById(int productLineId) {
		
		//this method will retrieve all the records based on Id from ProductLineCache. If data not available need to query the data db and get it
		logger.info("Inside retrieveById() of ProductLineService");
				
		//checking the availability of data in assetSybtypeCache 
		// Returns the data for the given Id in the ProductLineCache.
		if(productLineCache==null|| productLineCache.isEmpty()|| !productLineCache.containsKey(productLineId)) 
			{		
				//no data available in ProductLineCache
				List<ProductLine> allRecords = productLineRepo.findAll();
				//refresh ProductLineCache
				 for (ProductLine astObj : allRecords) 
				 {
					 productLineCache.put(astObj.getPrdLineId(), astObj);
			     }			 
				 //Check if the updated ProductLineCache has the requested Id if not through IdNotFound exception
				 // Returns the product line with the given ID.
				 if(productLineCache.containsKey(productLineId))
				 {
					 return productLineCache.get(productLineId);
				 }
				 else 
				 {
					 throw new IdNotFoundException("Requested ID="+productLineId+" is not available in in the table");
				 }
			}
		else 
			{
				//Get data from ProductLineCache by Id 
				ProductLine ProductLine  = null;
				ProductLine = productLineCache.get(productLineId);	
				//
				// if ProductLine is null.
				if(ProductLine==null)
				{
					throw new IdNotFoundException();
				}
				return ProductLine;
			}				
	}
	/**
	* Clears the cache. This is called when the user clicks on the " Clear " button in the menu
	*/
	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		productLineCache = null;
	}

	/**
	* Delete a list of product lines. This method is used to delete multiple product lines at once. The data is passed as a list of integers
	* 
	* @param productLineId - List of integers that represent the id's of the product lines to delete
	* 
	* @return String that represents the result of the operation " Records Deleted successfully ". It returns a message saying that the records were
	*/
	@Override
	public String bulkDelete(List<Integer> productLineId) {
		// This method will delete a list of records at a time
		productLineRepo.deleteAllById(productLineId);
		clearCache();
		return "Records Deleted successfully";
	}

	/**
	* This method will create a list of productLine in the database. The data will be saved at a time to the table
	* 
	* @param productLine - List of productLine to be saved
	* 
	* @return String to be shown on the screen with action = bulkCreate ; sa = " Records to create list of records "
	*/
	@Override
	public String bulkCreate(List<ProductLine> productLine) {
		// This method will save bulk data at a time to the table
		productLineRepo.saveAll(productLine);
		clearCache();
		return "Records to create list of records";
	}


	
}
