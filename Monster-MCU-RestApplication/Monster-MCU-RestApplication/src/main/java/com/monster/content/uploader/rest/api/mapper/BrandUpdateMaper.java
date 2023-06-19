package com.monster.content.uploader.rest.api.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monster.content.uploader.rest.api.entity.Brand;
import com.monster.content.uploader.rest.api.entity.Country;
import com.monster.content.uploader.rest.api.entity.ProductLine;
import com.monster.content.uploader.rest.api.service.IBrandService;

@Component
public class BrandUpdateMaper {

	@Autowired
	IBrandService brandService;

	Brand brand = new Brand();
	

	public Brand BrandMapper(Brand brandInputObj) {
		System.out.println("Inside brandMpper()\n"+brandInputObj);
		//Getting existing obj
		brand= brandService.retrieveById(brandInputObj.getBrandId());
		
		//setting all the new values to brandObject
		brand.setBrandDsName(brandInputObj.getBrandDsName());
		brand.setBrandName(brandInputObj.getBrandName());
		brand.setBrandSeqId(brandInputObj.getBrandSeqId());
		brand.setBrandValue(brandInputObj.getBrandValue());
		if(brandInputObj.getCountries()!=null) {
			Set<Country> input = brandInputObj.getCountries();
			Set<Country> reterived = brandInputObj.getCountries();
			Set<Country> mergedSet = new HashSet<>();
			mergedSet.addAll(input);
			mergedSet.addAll(reterived);	
		}
		if(brandInputObj.getProductLines()!=null) 
		{
			Set<ProductLine> input = brandInputObj.getProductLines();
			Set<ProductLine> reterived = brandInputObj.getProductLines();
			Set<ProductLine> mergedSet = new HashSet<>();
			mergedSet.addAll(input);
			mergedSet.addAll(reterived);	
		}		
		return brand;
		
	}


}
