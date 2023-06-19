package com.monster.content.uploader.rest.api.service;

import java.util.List;

import com.monster.content.uploader.rest.api.entity.UseCase;

/**
 * @author SivakumarK
 *
 */
public interface IUseCaseService {
	
	public String createRecord(UseCase useCase);
	public String updateRecord(UseCase useCase);
	public String deleteRecordById(int useCaseId);
	public List<UseCase> retrieveAllRecords();
	public UseCase retrieveById(int useCaseId);
	public void clearCache();
	public String bulkDelete(List<Integer> useCaseId );
	public String bulkCreate(List<UseCase> useCase);
	
}
