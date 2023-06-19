/**
 * 
 */
package com.monster.content.uploader.rest.api.service;

import java.text.SimpleDateFormat;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.monster.content.uploader.rest.api.entity.JobDetails;
import com.monster.content.uploader.rest.api.model.Cursor;
import com.monster.content.uploader.rest.api.model.Job;
import com.monster.content.uploader.rest.api.model.JobIndexData;
import com.monster.content.uploader.rest.api.model.SearchModel;
import com.monster.content.uploader.rest.api.model.Sort;
import com.monster.content.uploader.rest.api.util.Constants;
import com.monster.content.uploader.rest.api.util.Directions;

/**
 * @author SumanD
 *
 */
@EnableAsync
@Service
public class StartIndexImpl implements IStartIndex {

	private final static Logger logger = LoggerFactory.getLogger(StartIndexImpl.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	IJobDetailsService jobDetailsService;

	@Autowired
	Environment environment;


	String solrURL = "";
	SolrClient client = null;
	SolrQuery query = new SolrQuery();
	JobIndexData jobIndexData = new JobIndexData();

	@Override
	public String test() {
		return "Welcome";
	}

	@Override
	public JobIndexData SearchJobIndex(SearchModel searchModel, String solrUrl) throws Exception {
		System.out.println("hii");
//		search.getCursor().getPageIndex();
//		search.getCursor().getPageSize();
		System.out.println("hii-1");
		System.out.println("**" + searchModel);
//		System.out.println("--"+search.getCursor().getPageSize());
		try {
			// Set the Solr URL to the value of the `solrUrl` variable.
			solrURL = solrUrl;

			// Print the Solr URL to the console for debugging purposes.
			System.out.println(solrURL);

			// Instantiate a new HttpSolrClient object with the specified Solr URL.
			client = new HttpSolrClient.Builder(solrURL).build();

			// Check if the `curser.getSortDirection()` variable contains the string "asc".
			if (searchModel.getSort().getDirection().equalsIgnoreCase(Directions.ASCENDING.getValue())) {
				// Set the sort order for the `job_id` field to ascending.
				query.setSort(Constants.JOB_ID.getValue(), ORDER.asc);
				
				System.out.println(query.getSortField());
			}

			// Check if the `curser.getSortDirection()` variable contains the string "desc".
			if (searchModel.getSort().getDirection().equalsIgnoreCase(Directions.DESCENDING.getValue())) {

				// Set the sort order for the `job_id` field to descending.
				query.setSort(Constants.JOB_ID.getValue(), ORDER.desc);
			}

			// Set the query string to "*".
			query.setQuery(searchModel.getSort().getField()+":"+searchModel.getKeyword());

			// Set the number of rows to return to the value of the `curser.getPageSize()`
			// variable.
			query.setRows(searchModel.getCursor().getPageSize());

			// Set the start index to the value of the `curser.getPageIndex()` variable.
			query.setStart(searchModel.getCursor().getPageIndex());

			// Execute the query and get the response.
			QueryResponse response = client.query(query);

			// Create a new Cursor object.
			Cursor cursor = new Cursor();

			// Set the page index of the cursor to the value of the
			// `response.getResults().getStart()` property.
			cursor.setPageIndex((int) response.getResults().getStart());

			// Set the page size of the cursor to the value of the `query.getRows()`
			// property.
			cursor.setPageSize(query.getRows());

			// Set the total number of records of the cursor to the value of the
			// `response.getResults().getNumFound()` property.
			cursor.setTotalRecords(response.getResults().getNumFound());

			// Set the data of the response entity to the results of the query.
			jobIndexData.setData(response.getResults());

			// Set the cursor of the response entity to the cursor object.
			jobIndexData.setCursor(cursor);
			
//			Sort sort=new Sort();
//			sort.setField(query.getSortField());
//			sort.setDirection(searchModel.getSort().getDirection());
						
			jobIndexData.setSort(searchModel.getSort());
		} catch (Exception e) {
			// Print the error message to the console.
			System.out.println("error --> " + e.getMessage());
		}

		// Return the response entity.
		return jobIndexData;

	}

	@Override
	public String indexData(String solrUrl, String inputdata) throws Exception {

//		String urlString = solrUrl;
		HttpSolrClient solr = new HttpSolrClient.Builder(solrUrl).build();

		System.out.println("Inside index");
		try {
			// Extract field-value pairs from the input data using regular expressions.
			// The regular expression matches a `[` followed by any number of characters
			// other than a `]` followed by a `]`.
			String pattern = "\\[([^\\]]+)\\]";
			// Replace all matches of the regular expression with the matched text.
			String fieldValueString = inputdata.replaceAll(pattern, "$1");
			// Split the field-value string into an array of strings, one for each
			// field-value pair.
			String[] fieldValues = fieldValueString.split(", ");

			// Create a Solr document.
			SolrInputDocument solrDoc = new SolrInputDocument();
			// Iterate over the field-value pairs.
			for (String fieldValue : fieldValues) {
				// Split the field-value pair into an array of strings, one for each field and
				// value.
				String[] parts = fieldValue.split("=");
				// Get the field name from the first part of the array.
				String field = parts[0].trim();
				// Get the field value from the second part of the array.
				String value = parts[1].trim();
				// Add the field and value to the Solr document.
				solrDoc.addField(field, value);
			}

			// Add the Solr document to the Solr server.
			solr.add(solrDoc);
			// Commit the changes to the Solr server.
			solr.commit();
		} catch (Exception e) {
			System.out.println("error --> " + e.getMessage());
		}

		System.out.println("success");
		return inputdata;
	}

	/*
	 * @Override public ResponseEntity searchDataDocker(String solrUrl) throws
	 * Exception { try { solrURL = solrUrl; System.out.println(solrURL); client =
	 * new HttpSolrClient.Builder(solrURL).build(); query.setQuery("*");
	 * QueryResponse response = client.query(query);
	 * 
	 * responseEntity.setData(response.getResults());
	 * 
	 * } catch (Exception e) { System.out.println("error --->" + e.getMessage()); }
	 * return responseEntity; }
	 */

	@Override
	public String deleteData(String solrUrl, String job_id) throws Exception {
		// Instantiate a new HttpSolrClient object with the specified Solr URL.
		HttpSolrClient solr = new HttpSolrClient.Builder(solrUrl).build();

		// Save the Solr URL in a class variable for later use.
		solrURL = solrUrl;

		// Print the Solr URL to the console for debugging purposes.
		System.out.println(solrURL);

		// Delete the document with the specified job ID from Solr.
		solr.deleteByQuery(Constants.JOB_ID.getValue() + ":" + job_id);

		// Commit the changes to Solr.
		solr.commit();

		// Return a success message.
		return "Deleted";
	}

	@Override
	public String updateData(String solrUrl, JobDetails jobDetails) throws Exception {

		try {
			// Get the Solr URL
			String solrURL = solrUrl;
			System.out.println("Solr URL: " + solrURL);

			// Create a Solr client
			HttpSolrClient client = new HttpSolrClient.Builder(solrURL).build();

			// Create a Solr query
			SolrQuery query = new SolrQuery();
			query.setQuery(Constants.JOB_ID.getValue() + ":" + jobDetails.getJobId());

			// Execute the query
			QueryResponse response = client.query(query);

			// Get the old document
			SolrDocument oldDoc = response.getResults().get(0);
			System.out.println("Old document: " + oldDoc);

			// Get the document ID
			String documentId = oldDoc.getFieldValue(Constants.JOB_ID.getValue()).toString();
			System.out.println("Document ID: " + documentId);

			// Create a new document
			SolrInputDocument newDoc = new SolrInputDocument();

			// Add the document fields
			newDoc.addField(Constants.JOB_ID.getValue(), jobDetails.getJobId());
			newDoc.addField(Constants.JOB_NAME.getValue(), jobDetails.getJobName());
			newDoc.addField(Constants.BRAND.getValue(), jobDetails.getBrand().getBrandDsName());
			newDoc.addField(Constants.COUNTRY.getValue(), jobDetails.getCountry().getCountryDsName());
			newDoc.addField(Constants.DEPARTMENT_NAME.getValue(), jobDetails.getDepartment().getDptDsName());
			newDoc.addField(Constants.PRODUCT_LINE.getValue(), jobDetails.getProductLine().getPrdLineDsName());
			newDoc.addField(Constants.JOB_STATUS.getValue(), jobDetails.getJobStatus().getJobStatusName());
			newDoc.addField(Constants.CREATED_DATE.getValue(), jobDetails.getCreatedDate().toString());
			newDoc.addField(Constants.USER_ID.getValue(), jobDetails.getUseCase().getUseCaseId());
			newDoc.addField(Constants.USER_NAME.getValue(), "Susan");
			newDoc.addField("id", oldDoc.getFieldValue("id").toString());

			// Update the document
			client.add(newDoc);
			client.commit();

			// Print the new document
			System.out.println("New document: " + newDoc);

			System.out.println(newDoc);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "Success";
	}

	@Override
	public String getJobDetailByJobId(Job job) throws Exception {
//		Job job=mcuRepo.
		return null;
	}

}
