package com.monster.content.uploader.rest.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.Brand;
import com.monster.content.uploader.rest.api.entity.Country;
import com.monster.content.uploader.rest.api.entity.ProductLine;
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.repository.BrandRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class BrandService implements IBrandService {
	

	@Autowired
	BrandRepository brandRepo;
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	IProductLineService productLineService;
	
	private Logger logger = LoggerFactory.getLogger(BrandService.class);
	
	// This is used as a cache storage to avoid multiple database query
    HashMap<Integer,Brand> brandCache = new HashMap<>();

//    @Autowired
//    BrandUpdateMaper mapper;
    
	/**
	* This method creates a brand in Brand table. It is used to create brand in database. The brand is saved in table and cleared after saving
	* 
	* @param brand - brands that need to be saved
	* 
	* @return String message to be displayed to the user. Successful or not. Failure will indicate failure of saving
	*/
	@Override
	public String createRecord(Brand brand) {
		logger.info("Inside createRecord() of BrandService");
		//this method will add new record in the Brand Table 
		brandRepo.save(brand);
		clearCache();
		return "Record Saved Successfully";
	}

	/**
	* This method updates record in Brand table. It is used to update brand information in Brand table
	* 
	* @param brandInputObj - Object with data for update
	* 
	* @return String with result of
	*/
	@Override
	public String updateRecord(Brand brandInputObj) {
		logger.info("Inside updateRecord() of BrandService");
		//this method will update the existing record in the Brand Table 
		Brand brand= retrieveById(brandInputObj.getBrandId());
		
		//setting all the new values to brandObject
		brand.setBrandDsName(brandInputObj.getBrandDsName());
		brand.setBrandName(brandInputObj.getBrandName());
		brand.setBrandSeqId(brandInputObj.getBrandSeqId());
		brand.setBrandValue(brandInputObj.getBrandValue());
		// Returns a set of countries that are the countries of the brand input object.
		if(brandInputObj.getCountries()!=null) {
			Set<Country> input = brandInputObj.getCountries();
			Set<Country> reterived = brandInputObj.getCountries();
			Set<Country> mergedSet = new HashSet<>();
			mergedSet.addAll(input);
			mergedSet.addAll(reterived);	
		}
		// Returns the product lines of the brand input object.
		if(brandInputObj.getProductLines()!=null) 
		{
			Set<ProductLine> input = brandInputObj.getProductLines();
			Set<ProductLine> reterived = brandInputObj.getProductLines();
			Set<ProductLine> mergedSet = new HashSet<>();
			mergedSet.addAll(input);
			mergedSet.addAll(reterived);	
		}		
		brandRepo.save(brand);
		clearCache();
		return "Record Updated Successfully";
	}

	/**
	* Delete record by brandId. This method will update the Brand Table. If Id not found will throw exception
	* 
	* @param brandId - Id of record to delete
	* 
	* @return String indicating success or failure of the operation. It will return " Record Deleted Successfully " if record deleted
	*/
	@Override
	public String deleteRecordById(int brandId) {
		logger.info("Inside deleteRecordById() of BrandService");
		//this method will update the existing record in the Brand Table. If Id not found will throw exception
		brandRepo.findById(brandId).orElseThrow(()-> new IdNotFoundException());
		brandRepo.deleteById(brandId);
		clearCache();
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all Brand records from asset sybtype cache if not in cache or query DB. This method is thread safe
	* 
	* 
	* @return List of brands ( empty list if none
	*/
	@Override
	public List<Brand> retrieveAllRecords() {
		//this method will retrieve all the records from assetSubtypeCache. If data not available need to query the data db and get it.
				//This Caching of the data from database to the local Hashmap will not work in HA server architecture. 
				//To resolve this conflict we need to use "distributed cache" concept. (note: RedisCacheManager can be used)
				
		logger.info("Inside retrieveAllRecords() of BrandService");
				List<Brand> allRecords = new ArrayList<>();
				//checking the availability of data in assetSybtypeCache 
				// Cache the brand data in the database
				if(brandCache==null || brandCache.isEmpty()) 
				{
					// Not in Cache and Query DB
					allRecords = brandRepo.findAll();
					brandCache = new HashMap<>();
					 for (Brand astObj : allRecords) 
					 {
						 brandCache.put(astObj.getBrandId(), astObj);
				     }
					
				}
				else 
				{
					// Data Available in Cache
					allRecords = new ArrayList<>(brandCache.values());
				}
				
				return allRecords;
		
	}

	/**
	* Retrieves Brand by Id. This method will be used to retrieve all brands based on the Id
	* 
	* @param brandId - Id of the brand to retrieve
	* 
	* @return Brand object corresponding to the Id passed in if it exists in the brandCache otherwise it will return null
	*/
	@Override
	public Brand retrieveById(int brandId) {
		
		//this method will retrieve all the records based on Id from BrandCache. If data not available need to query the data db and get it
				logger.info("Inside retrieveById() of BrandService");
				
				//checking the availability of data in assetSybtypeCache 
				// Get the data for the given brand
				if(brandCache==null|| brandCache.isEmpty()|| !brandCache.containsKey(brandId)) 
				{
					logger.info("inside If of Brand Reterive By Id");
					//no data available in BrandCache
					List<Brand> allRecords = brandRepo.findAll();
					//refresh BrandCache
					 for (Brand astObj : allRecords) 
					 {
						 brandCache.put(astObj.getBrandId(), astObj);
				     }
					 System.out.println(allRecords);
					 //Check if the updated BrandCache has the requested Id if not through IdNotFound exception
					 // Returns the brand object for the given ID.
					 if(brandCache.containsKey(brandId))
					 {
						 return brandCache.get(brandId);
					 }
					 else 
					 {
						 throw new IdNotFoundException("Requested ID="+brandId+" is not available in in the table");
					 }
				}
				else 
				{
					//Get data from BrandCache by Id 
					logger.info("inside else of Brand Reterive By Id");
					Brand Brand  = null;
					Brand = brandCache.get(brandId);	
					//
					// Returns the brand of the brand.
					if(Brand==null)
					{
						throw new IdNotFoundException();
					}
					return Brand;
				}
	}

	/**
	* Clears the cache. This is called when we need to re - use an existing Brand for a new
	*/
	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		brandCache = null;
	}

	/**
	* Delete a list of brands and clear the cache. This is a bulk delete and should be used in conjunction with #bulkDelete ( List )
	* 
	* @param brandId - the id of the brands to delete
	* 
	* @return String " Records Deleted successfully " if successful otherwise error message is returned to the client ( not used in this method
	*/
	@Override
	public String bulkDelete(List<Integer> brandId) {
		// This method will delete a list of records at a time
		brandRepo.deleteAllById(brandId);
		clearCache();
		return "Records Deleted successfully";
	}

	/**
	* Bulk create brands in the database. This method is used to create brand records in the database.
	* 
	* @param brand - List of brands to be created. This is a list of brands that will be saved to the database
	* 
	* @return String message to display to
	*/
	@Override
	public String bulkCreate(List<Brand> brand) {
		// This method will save bulk data at a time to the table
		brandRepo.saveAll(brand);
		clearCache();
		return "Records to create list of records";
	}


	/**
	* Update the country mapping for a brand. This is used to add or remove countries from a brand.
	* 
	* @param brandId - the id of the brand to update.
	* @param countries - the set of countries to add. Each country must be associated with a brand.
	* 
	* @return the brand that was updated or null if the brand doesn't exist or there was an error updating
	*/
	@Override
	public Brand updateBrandCountryMapping(int brandId, Set<Country> countries) {
		Brand brand = brandRepo.findById(brandId).orElseThrow(()-> new IdNotFoundException());
		Set<Country> countrySet = new HashSet<>();
		for(Country countryObj : countries) {
		     Country country = countryService.retrieveById(countryObj.getCountryId());
		     countrySet.add(country);
		 }
		brand.setCountries(countrySet);
		Brand responsBrand = brandRepo.save(brand);
		clearCache();
		return responsBrand;
	}

	/**
	* Update the product line mapping for a brand. This is used to update the brand's product line mapping
	* 
	* @param brandId - Brand's id to update
	* @param productLine - Set of product line's to update
	* 
	* @return Brand with updated product line mapping or null if brand doesn't exist or cannot be updated due to
	*/
	@Override
	public Brand updateBrandProductLineMapping(int brandId, Set<ProductLine> productLine) {
		Brand brand = brandRepo.findById(brandId).orElseThrow(()-> new IdNotFoundException());
		Set<ProductLine> productLineSet = new HashSet<>();
		for(ProductLine productLineObj : productLine) {
		     ProductLine productLineReterieved = productLineService.retrieveById(productLineObj.getPrdLineId());
		     productLineSet.add(productLineReterieved);
		 }
		brand.setProductLines(productLineSet);
		Brand responsBrand = brandRepo.save(brand);
		clearCache();
		return responsBrand;
	}
}
