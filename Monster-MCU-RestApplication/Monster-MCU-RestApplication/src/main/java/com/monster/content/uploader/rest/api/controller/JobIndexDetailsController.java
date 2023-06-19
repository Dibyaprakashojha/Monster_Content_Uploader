/**
 * 
 */
package com.monster.content.uploader.rest.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.monster.content.uploader.rest.api.model.Job;
import com.monster.content.uploader.rest.api.model.JobIndexData;
import com.monster.content.uploader.rest.api.model.SearchModel;
import com.monster.content.uploader.rest.api.service.IAzureQueueService;
import com.monster.content.uploader.rest.api.service.IStartIndex;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author SumanD
 *
 */
@RestController
@RequestMapping("/api/v1")
@PropertySource("file:C:/MCUDetails.properties")
@Tag(name="Job Index",description="contains the api for job index")
@CrossOrigin("*")
public class JobIndexDetailsController {
	@Autowired
	IAzureQueueService azureQueueService;

	@Autowired
	Environment environment;

	@Autowired
	IStartIndex iStartIndex;

	@Value("${domainName}")
	private String solrUl;

	@Value("${TimeDelay}")
	private String TimeDelay;

	@RequestMapping(value = "/greet", method = RequestMethod.GET)
	String test() {
		return iStartIndex.test();
	}

	/**
	 * @param cursor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	JobIndexData searchQuery(@RequestBody SearchModel searchModel) throws Exception {
         System.out.println("controller search "+searchModel.toString());
		JobIndexData responseData = iStartIndex.SearchJobIndex(searchModel, solrUl);

		return responseData;
	}


	/**
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/index-data", method = RequestMethod.POST)
//	String addDataInSolr() throws Exception {
//		iStartIndex.indexData(environment.getProperty("domainName"), "hii");
//		return "Successfully Added the Message In Queue and indexed the data";
//	}

	/**
	 * @param jobId
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/delete-data", method = RequestMethod.DELETE, consumes = {
//			MediaType.APPLICATION_JSON_VALUE })
//	String deleteDataInSolr(@RequestBody String jobId) throws Exception {
//		iStartIndex.deleteData(solrUl, jobId);
//		return "Successfully Deleted the Job";
//	}

	/**
	 * @param job
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/update-data", method = RequestMethod.POST, consumes = {
//			MediaType.APPLICATION_JSON_VALUE })
//	String updateDataInSolr(@RequestBody Job job) throws Exception {
//		// startIndex.updateData(solrUl,job);
//		return "Successfully Updated Job";
//	}

}
