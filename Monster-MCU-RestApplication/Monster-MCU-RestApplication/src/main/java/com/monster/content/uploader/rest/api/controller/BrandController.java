package com.monster.content.uploader.rest.api.controller;

import java.util.List;
import java.util.Set;

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

import com.monster.content.uploader.rest.api.entity.Brand;
import com.monster.content.uploader.rest.api.entity.Country;
import com.monster.content.uploader.rest.api.entity.ProductLine;
import com.monster.content.uploader.rest.api.service.IBrandService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author SivakumarK
 * 
 * DESC: This is the entity class for Brand Table
 *
 */

@RestController
@RequestMapping("/api/v1/brand")
@Tag(name="Brand", description = "Provides all the CRUD opration api's for Brand Entity")
public class BrandController {
	
	@Autowired
	IBrandService brandService;
	
	private Logger logger = LoggerFactory.getLogger(BrandController.class);
	
	/**
	 * @param brand
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will create a new record in Brand Table, using save() of the JPARepository which is call in the BrandService Class
     *
	 */
	@PostMapping()
	public ResponseEntity<Object> addNewBrand(@RequestBody Brand brand){
		logger.info("Inside addNewBrand() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Addinging a reccord in Brand Table");
        return new ResponseEntity<>(brandService.createRecord(brand), header,HttpStatus.OK);
	}
	
	/**
	 * @param brand
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Update a new record in Brand Table, using save() of the JPARepository which is call in the BrandService Class.
	 * 		 May throw IdnotfoundException in BrandService class if Invalid Id provided.
	 */
	@PutMapping()
	public ResponseEntity<Object> updateBrand(@RequestBody Brand brand){
		logger.info("Inside updateBrand() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Updating a reccord in Brand Table");
        return new ResponseEntity<>(brandService.updateRecord(brand), header,HttpStatus.OK);
	}
	
	/**
	 * @param brandId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will Delete a new record in Brand Table, using save() of the JPARepository which is call in the BrandService Class.
	 * 		 May throw IdnotfoundException in BrandService class if Invalid Id provided. 
	 */
	@DeleteMapping("/{brandId}")
	public ResponseEntity<Object> deleteBrand(@PathVariable("brandId") int brandId){
		logger.info("Inside deleteBrand() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Deleting a reccord in Brand Table");
        return new ResponseEntity<>(brandService.deleteRecordById(brandId), header,HttpStatus.OK);
	}
	
	/**
	 * @return  Response entity with body(List of all AssetSubType from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will retrieve all the records from Brand Table in the form of List<Brand>
	 */
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Object> getAllBrand(){
		logger.info("Inside getAllBrand() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get all reccords from Brand Table");
        return new ResponseEntity<>(brandService.retrieveAllRecords(), header,HttpStatus.OK);
	}
	
	/**
	 * @param brandId
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will provide one Brand object based on requested Id
	 * 		  May throw IdnotfoundException in BrandService class if Invalid Id provided.
	 */
	@GetMapping(value="/{brandId}", produces = { "application/json" })
	public ResponseEntity<Object> getBrandById(@PathVariable("brandId") int brandId){
		logger.info("Inside getBrandById() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Get reccord by id from Brand Table");
        return new ResponseEntity<>(brandService.retrieveById(brandId), header,HttpStatus.OK);
	}
	
	
	/**
	 * @param brandId
	 * @param countrList
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will update the mapping for Brand and Country entity
	 */
	@PutMapping("/{brandId}/country")
	public ResponseEntity<Object> updateCountryMapping(@PathVariable int brandId, @RequestBody Set<Country> countrList){
		logger.info("Inside updateCountryMapping() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Update record with the countery mapping");
		return new ResponseEntity<>(brandService.updateBrandCountryMapping(brandId, countrList), header,HttpStatus.OK);
			
	}
	
	/**
	 * @param brandId
	 * @param productLine
	 * @return Response entity with body(one AssetSubType object based on Id from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will update the mapping for Brand and ProductLine entity
	 */
	@PutMapping("/{brandId}/productLine")
	public ResponseEntity<Object> updateProductLineMapping(@PathVariable int brandId, @RequestBody Set<ProductLine> productLineList){
		logger.info("Inside updateProductLineMapping() of McuApiController");
		HttpHeaders header = new HttpHeaders();
   		header.add("Desc", "Update record with the countery mapping");
		return new ResponseEntity<>(brandService.updateBrandProductLineMapping(brandId, productLineList), header,HttpStatus.OK);
			
	}
	
	/**
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will make the local memory cache hashmap object(brandCache) to null
	 * 
	 */
	@PostMapping("/clear-cache")
	public ResponseEntity<Object> clearLocalCache(){
		logger.info("Inside clearLocalCache() of McuApiController");
	    HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Make the local cache object null");
	    brandService.clearCache();
	    return new ResponseEntity<>("Local cache cleared!!!", header,HttpStatus.OK);
	}
	
	/**
	 * @param brand
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will add a list of Brand records to Brand table in a single api call
	 * 
	 */
	@PostMapping("/bulk-upload")
	public ResponseEntity<Object> createBulkRecords(@RequestBody List<Brand> brands){
		logger.info("Inside createBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Addinging a list of reccords in Asset Subtype Table");
	    return new ResponseEntity<>(brandService.bulkCreate(brands), header,HttpStatus.OK);
	}
	
	/**
	 * @param brandId
	 * @return Response entity with body(success message from service), header(Custom description) and Http status OK
	 * 
	 * DESC: This method will delete a list of Brand records from Brand table
	 */
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> deleteBulkRecords(@RequestBody List<Integer> brandIds){
		logger.info("Inside deleteBulkRecords() of McuApiController");
		HttpHeaders header = new HttpHeaders();
	    header.add("Desc", "Deleting a list of reccords from Asset Subtype Table");
	    return new ResponseEntity<>(brandService.bulkDelete(brandIds), header,HttpStatus.OK);
	}	
	
}
