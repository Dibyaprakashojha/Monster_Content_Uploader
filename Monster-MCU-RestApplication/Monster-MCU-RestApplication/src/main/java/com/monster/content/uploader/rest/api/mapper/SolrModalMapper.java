package com.monster.content.uploader.rest.api.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monster.content.uploader.rest.api.entity.JobDetails;
import com.monster.content.uploader.rest.api.model.SolrModel;

/**
 * @author SivakumarK
 * 
 *         desc: This is mapper class used for sending filtered data to Solr for
 *         Indexing
 *
 */
@Component
public class SolrModalMapper {

	@Autowired
	SolrModel solrModel = new SolrModel();

	public SolrModel SolrCustomMapping(JobDetails jobDetails) {
		solrModel.setJob_id(jobDetails.getJobId());
		solrModel.setBrand(jobDetails.getBrand().getBrandDsName());
		solrModel.setCountry(jobDetails.getCountry().getCountryName());
		solrModel.setCreated_date(jobDetails.getCreatedDate());
		solrModel.setDepartment_name(jobDetails.getDepartment().getDptName());
		solrModel.setJob_name(jobDetails.getJobName());
		solrModel.setJob_status(jobDetails.getJobStatus().getJobStatusName());
		solrModel.setProduct_line(jobDetails.getProductLine().getPrdLineName());
		solrModel.setUser_name(jobDetails.getCreatedBy().getPsDetailsDsName());
		solrModel.setUser_id(jobDetails.getCreatedBy().getPsDetailsId());
		return solrModel;
	}

	public SolrModel SolrCustomMappingforId(JobDetails jobDetails) {
		solrModel.setJob_id(jobDetails.getBusinessId());
		return solrModel;

	}

}
