/**
 * 
 */
package com.monster.content.uploader.rest.api.service;

import com.monster.content.uploader.rest.api.entity.JobDetails;
import com.monster.content.uploader.rest.api.model.Cursor;
import com.monster.content.uploader.rest.api.model.Job;
import com.monster.content.uploader.rest.api.model.JobIndexData;
import com.monster.content.uploader.rest.api.model.SearchModel;

/**
 * @author SumanD
 *
 */
public interface IStartIndex {
	
	String test();
	
	JobIndexData SearchJobIndex(SearchModel searchModel,String solrUrl) throws Exception;
			
	String indexData(String solrUrl,String input) throws Exception;
		
	String deleteData(String solrUrl,String job_id) throws Exception;	
	String updateData(String solrUrl,JobDetails jobDetails) throws Exception;
	
	String getJobDetailByJobId(Job job) throws Exception;
	

}
