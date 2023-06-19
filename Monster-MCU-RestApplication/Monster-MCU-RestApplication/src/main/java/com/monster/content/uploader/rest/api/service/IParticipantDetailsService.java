package com.monster.content.uploader.rest.api.service;

import java.util.List;

import com.monster.content.uploader.rest.api.entity.ParticipantDetails;

/**
 * @author SivakumarK
 *
 */
public interface IParticipantDetailsService {
	
	public String createRecord(ParticipantDetails participantDetail);
	public String updateRecord(ParticipantDetails participantDetail);
	public String deleteRecordById(int participantDetailsId);
	public List<ParticipantDetails> retrieveAllRecords();
	public ParticipantDetails retrieveById(int participantDetailsId);
	public void clearCache();
	public String bulkDelete(List<Integer> psDetailsId );
	public String bulkCreate(List<ParticipantDetails> participantDetails);
}
