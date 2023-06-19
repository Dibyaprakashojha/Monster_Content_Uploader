//package com.monster.content.uploader.rest.api.mapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.monster.content.uploader.rest.api.entity.AssetSubtype;
//import com.monster.content.uploader.rest.api.entity.AssetType;
//import com.monster.content.uploader.rest.api.service.AssetSubtypeService;
//import com.monster.content.uploader.rest.api.service.IAssetSubtypeService;
//import com.monster.content.uploader.rest.api.service.IAssetTypeService;
//
//@Component
//public class AssetSubtypeUpdateMaper {
//
//	@Autowired
//	IAssetTypeService assetTypeService;
//	
//	
//	IAssetSubtypeService assetSubtypeService = new AssetSubtypeService();
//
//	AssetSubtype assetSubtype = new AssetSubtype();
//	
//	AssetType assetType =  new AssetType();
//	
//
//	public AssetSubtype AssetSubtypeMapper(AssetSubtype assetSubtypeInputObj) {
//		System.out.println("Inside assetSubtypeMpper()\n"+assetSubtypeInputObj);
//		//Getting existing obj
//		int id = assetSubtypeInputObj.getAssetSubtypeId();
//		assetSubtype = assetSubtypeService.retrieveById(id);
//		
//		//setting all the new values to assetSubtypeObject
//		assetSubtype.setAssetSubtypeDsName(assetSubtypeInputObj.getAssetSubtypeDsName());
//		assetSubtype.setAssetSubtypeName(assetSubtypeInputObj.getAssetSubtypeName());
//		assetSubtype.setAssetSubtypeSeqId(assetSubtypeInputObj.getAssetSubtypeSeqId());
//		assetSubtype.setAssetSubtypeValue(assetSubtypeInputObj.getAssetSubtypeValue());
//		
//		int assetTypeId = assetSubtypeInputObj.getAssetType().getAssetTypeId();
//		assetType = assetTypeService.retrieveById(assetTypeId);
//		assetSubtype.setAssetType(assetType);
//		System.out.println("end of the Mapping**********\n updatedObject:\n"+assetSubtype);
//		return assetSubtype;
//		
//	}
//
//
//}
