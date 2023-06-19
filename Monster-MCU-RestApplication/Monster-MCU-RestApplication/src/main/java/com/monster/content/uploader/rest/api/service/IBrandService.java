package com.monster.content.uploader.rest.api.service;

import java.util.List;
import java.util.Set;

import com.monster.content.uploader.rest.api.entity.AssetType;
import com.monster.content.uploader.rest.api.entity.Brand;
import com.monster.content.uploader.rest.api.entity.Country;
import com.monster.content.uploader.rest.api.entity.ProductLine;

/**
 * @author SivakumarK
 *
 */
public interface IBrandService {
	
	public String createRecord(Brand brand);
	public String updateRecord(Brand brand);
	public String deleteRecordById(int brandId);
	public List<Brand> retrieveAllRecords();
	public Brand retrieveById(int brandId);
	public Brand updateBrandCountryMapping(int brandId, Set<Country> countries);
	public Brand updateBrandProductLineMapping(int brandId, Set<ProductLine> productLine);
	public void clearCache();
	public String bulkDelete(List<Integer> brandId );
	public String bulkCreate(List<Brand> brand);
	
}
