package com.monster.content.uploader.rest.api.service;

import java.util.List;

import com.monster.content.uploader.rest.api.entity.AssetSubtype;

/**
 * @author SivakumarK
 *
 */
public interface IAssetSubtypeService {
	
	public String createRecord(AssetSubtype assetSubtype);
	public String updateRecord(AssetSubtype assetSubtype);
	public String deleteRecordById(int assetSubtypeId);
	public List<AssetSubtype> retrieveAllRecords();
	public AssetSubtype retrieveById(int assetSubtypeId);
	public void clearCache();
	public String bulkDelete(List<Integer> assetSubtypeId);
	public String bulkCreate(List<AssetSubtype> assetSubtype);
	
}
