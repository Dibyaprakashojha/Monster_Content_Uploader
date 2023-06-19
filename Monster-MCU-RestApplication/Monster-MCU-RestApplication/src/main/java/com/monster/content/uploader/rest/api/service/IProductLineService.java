package com.monster.content.uploader.rest.api.service;

import java.util.List;

import com.monster.content.uploader.rest.api.entity.ProductLine;

/**
 * @author SivakumarK
 *
 */
public interface IProductLineService {
	
	public String createRecord(ProductLine productLine);
	public String updateRecord(ProductLine productLine);
	public String deleteRecordById(int productLineId);
	public List<ProductLine> retrieveAllRecords();
	public ProductLine retrieveById(int productLineId);
	public void clearCache();
	public String bulkDelete(List<Integer> productLineId );
	public String bulkCreate(List<ProductLine> productLine);

	
}
