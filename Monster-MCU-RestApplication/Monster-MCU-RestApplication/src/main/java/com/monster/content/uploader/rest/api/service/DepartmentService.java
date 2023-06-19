package com.monster.content.uploader.rest.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.Department;
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.repository.DepartmentRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class DepartmentService implements IDepartmentService {
	
	@Autowired
	DepartmentRepository departmentRepo;
	
	private Logger logger = LoggerFactory.getLogger(DepartmentService.class);
	
	// This is used as a cache storage to avoid multiple database query
    HashMap<Integer,Department> departmentCache = new HashMap<>();

	/**
	* This method creates a Department record in the data source. It is used to create the data source from the data source
	* 
	* @param department - the record to be created
	* 
	* @return String message to be displayed to the user after the operation is completed. Successful or not. Failure will return null
	*/
	@Override
	public String createRecord(Department department) {
		logger.info("Inside createRecord() of DepartmentService");
		//this method will add new record in the Department Table 
		departmentRepo.save(department);
		clearCache();
		return "Record Saved Successfully";
	}

	/**
	* This method updates the record in the Department table. It is used to update the department record
	* 
	* @param department - the record to be updated
	* 
	* @return String status of the operation - SUCCESS or FAILURE depending on the outcome of the update operation. In case of success the message is " Record Updated Successfully
	*/
	@Override
	public String updateRecord(Department department) {
		logger.info("Inside updateRecord() of DepartmentService");
		//this method will update the existing record in the Department Table 
		departmentRepo.save(department);
		clearCache();
		return "Record Updated Successfully";
	}

	/**
	* Delete record by Department ID. This method will update the record in the Department Table. If record not found an exception will be thrown
	* 
	* @param departmentId - Id of record to be deleted
	* 
	* @return String status of operation Successfully deleted record or Error in case of failure ( IdNotFoundException ) Example : DELETE / api / departments /
	*/
	@Override
	public String deleteRecordById(int departmentId) {
		logger.info("Inside deleteRecordById() of DepartmentService");
		//this method will update the existing record in the Department Table. If Id not found will throw exception
		departmentRepo.findById(departmentId).orElseThrow(()-> new IdNotFoundException());
		departmentRepo.deleteById(departmentId);
		clearCache();
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all department records from assetSubtypeCache. If not in cache it will query the data db and get it
	* 
	* 
	* @return List of Department objects
	*/
	@Override
	public List<Department> retrieveAllRecords() {
		//this method will retrieve all the records from assetSubtypeCache. If data not available need to query the data db and get it.
		//This Caching of the data from database to the local Hashmap will not work in HA server architecture. 
		//To resolve this conflict we need to use "distributed cache" concept. (note: RedisCacheManager can be used)
				
		logger.info("Inside retrieveAllRecords() of AssetSubtypeService");
		List<Department> allRecords = new ArrayList<>();
		//checking the availability of data in assetSybtypeCache 
		// Cache the department data in the database
		if(departmentCache==null || departmentCache.isEmpty()) 
			{
				// Not in Cache and Query DB
				allRecords = departmentRepo.findAll();
				departmentCache = new HashMap<>();
				 for (Department astObj : allRecords) 
				 {
					 departmentCache.put(astObj.getDptId(), astObj);
			     }	
			}
		else 
			{
				// Data Available in Cache
				allRecords = new ArrayList<>(departmentCache.values());
			}	
		return allRecords;
	}

	/**
	* This method retrieves Department by Id. If data is not available in assetSybtypeCache it will query the db and get the data
	* 
	* @param departmentId - Id of the Department to retrieve
	* 
	* @return Department with data from DB or null if not found ( IdNotFoundException exception will be thrown in case of not
	*/
	@Override
	public Department retrieveById(int departmentId) {
		
		//this method will retrieve all the records based on Id from DepartmentCache. If data not available need to query the data db and get it
		logger.info("Inside retrieveById() of DepartmentService");
				
		//checking the availability of data in assetSybtypeCache 
		// Get the data for the Department with the given Id
		if(departmentCache==null|| departmentCache.isEmpty()|| !departmentCache.containsKey(departmentId)) 
			{		
				//no data available in DepartmentCache
				List<Department> allRecords = departmentRepo.findAll();
				//refresh DepartmentCache
				 for (Department astObj : allRecords) 
				 {
					 departmentCache.put(astObj.getDptId(), astObj);
			     }			 
				 //Check if the updated DepartmentCache has the requested Id if not through IdNotFound exception
				 // Returns the department object for the given departmentId.
				 if(departmentCache.containsKey(departmentId))
				 {
					 return departmentCache.get(departmentId);
				 }
				 else 
				 {
					 throw new IdNotFoundException("Requested ID="+departmentId+" is not available in in the table");
				 }
			}
		else 
			{
				//Get data from DepartmentCache by Id 
				Department Department  = null;
				Department = departmentCache.get(departmentId);	
				//
				// if the department is null.
				if(Department==null)
				{
					throw new IdNotFoundException();
				}
				return Department;
			}				
	}
	/**
	* Clears the cache. This is called when we need to re - create a department in the case of a change
	*/
	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		departmentCache = null;
	}

	/**
	* Delete records by department id. This method is used for bulk delete of records in the database.
	* 
	* @param departmentId - List of department id to delete
	* 
	* @return String indicating success or
	*/
	@Override
	public String bulkDelete(List<Integer> departmentId) {
		// This method will delete a list of records at a time
		departmentRepo.deleteAllById(departmentId);
		clearCache();
		return "Records Deleted successfully";
	}

	/**
	* This method will create a list of department in the database. It will save all data to the table
	* 
	* @param department - List of Department to be created
	* 
	* @return String to be shown on the screen with message of success or failure. If there is an error the message will be
	*/
	@Override
	public String bulkCreate(List<Department> department) {
		// This method will save bulk data at a time to the table
		departmentRepo.saveAll(department);
		clearCache();
		return "Records to create list of records";
	}

}
