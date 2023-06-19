package com.monster.content.uploader.rest.api.service;

import java.util.List;

import com.monster.content.uploader.rest.api.entity.Country;

/**
 * @author SivakumarK
 *
 */
public interface ICountryService {
	
	public String createRecord(Country country);
	public String updateRecord(Country country);
	public String deleteRecordById(int countryId);
	public List<Country> retrieveAllRecords();
	public Country retrieveById(int countryId);
	public void clearCache();
	public String bulkDelete(List<Integer> countryId );
	public String bulkCreate(List<Country> countries);
	
}
