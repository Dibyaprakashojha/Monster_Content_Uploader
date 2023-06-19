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

import com.monster.content.uploader.rest.api.entity.Department;
import com.monster.content.uploader.rest.api.service.IDepartmentService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author SivakumarK
 * 
 * DESC: This is the entity class for Department Table
 *
 */

@RestController
@RequestMapping("/api/v1/department")
@Tag(name="Department", description = "Provides all the CRUD opration api's for Department Entity")
public class DepartmentController {
	
	@Autowired
	IDepartmentService departmentService;
	private Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	/**
	 * @param department
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will create a new record in Department Table, using save() of the JPARepository which is call in the DepartmentService Class
     *
	 */
	@PostMapping()
	public ResponseEntity<Object> addNewDepartment(@RequestBody Department department){
		logger.info("Inside addNewDepartment() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Addinging a reccord in Department Table");
        return new ResponseEntity<>(departmentService.createRecord(department), header,HttpStatus.OK);
	}
	
	/**
	 * @param department
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Update a new record in Department Table, using save() of the JPARepository which is call in the DepartmentService Class.
	 * 		 May throw IdnotfoundException in DepartmentService class if Invalid Id provided.
	 */
	@PutMapping()
	public ResponseEntity<Object> updateDepartment(@RequestBody Department department){
		logger.info("Inside updateDepartment() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Updating a reccord in Department Table");
        return new ResponseEntity<>(departmentService.updateRecord(department), header,HttpStatus.OK);
	}
	
	
	/**
	 * @param departmentId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Delete a new record in Department Table, using save() of the JPARepository which is call in the DepartmentService Class.
	 * 		 May throw IdnotfoundException in DepartmentService class if Invalid Id provided. 
	 */
	@DeleteMapping("/{departmentId}")
	public ResponseEntity<Object> deleteDepartment(@PathVariable("departmentId") int departmentId){
		logger.info("Inside deleteDepartment() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Deleting a reccord in Department Table");
        return new ResponseEntity<>(departmentService.deleteRecordById(departmentId), header,HttpStatus.OK);
	}
	
	/**
	 * @return  Response entity with body(List of all AssetSubType from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will retrieve all the records from Department Table in the form of List<Department>
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Object> getAllDepartment(){
		logger.info("Inside getAllDepartment() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get all reccords from Department Table");
        return new ResponseEntity<>(departmentService.retrieveAllRecords(), header,HttpStatus.OK);
	}
	
	/**
	 * @param departmentId
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will provide one Department object based on requested Id
	 * 		  May throw IdnotfoundException in DepartmentService class if Invalid Id provided.
	 */
	@GetMapping(value="/{departmentId}", produces = { "application/json" })
	public ResponseEntity<Object> getDepartmentById(@PathVariable("departmentId") int departmentId){
		logger.info("Inside getDepartmentById() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get reccord by id from Department Table");
        return new ResponseEntity<>(departmentService.retrieveById(departmentId), header,HttpStatus.OK);
	}
	/**
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will make the local memory cache hashmap object(departmentCache) to null
	 * 
	 */
	@PostMapping("/clear-cache")
	public ResponseEntity<Object> clearLocalCache(){
		logger.info("Inside clearLocalCache() of McuApiController");
	    HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Make the local cache object null");
	    departmentService.clearCache();
	    return new ResponseEntity<>("Local cache cleared!!!", header,HttpStatus.OK);
	}
	
	/**
	 * @param department
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will add a list of Department records to Department table in a single api call
	 * 
	 */
	@PostMapping("/bulk-upload")
	public ResponseEntity<Object> createBulkRecords(@RequestBody List<Department> departments){
		logger.info("Inside createBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Addinging a list of reccords in Asset Subtype Table");
	    return new ResponseEntity<>(departmentService.bulkCreate(departments), header,HttpStatus.OK);
	}
	
	/**
	 * @param departmentId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will delete a list of Department records from Department table
	 */
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> deleteBulkRecords(@RequestBody List<Integer> departmentIds){
		logger.info("Inside deleteBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Deleting a list of reccords from Asset Subtype Table");
	    return new ResponseEntity<>(departmentService.bulkDelete(departmentIds), header,HttpStatus.OK);
	}
	
}
