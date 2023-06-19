package com.monster.content.uploader.rest.api.service;

import java.util.List;

import com.monster.content.uploader.rest.api.entity.AssetType;

/**
 * @author SivakumarK
 *
 */
public interface IAssetTypeService {
	
	public String createRecord(AssetType assetType);
	public String updateRecord(AssetType assetType);
	public String deleteRecordById(int assetTypeId);
	public List<AssetType> retrieveAllRecords();
	public AssetType retrieveById(int assetTypeId);
	public void clearCache();
	public String bulkDelete(List<Integer> assetTypeId);
	public String bulkCreate(List<AssetType> assetType);
	
}
