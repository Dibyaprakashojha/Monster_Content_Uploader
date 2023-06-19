package com.monster.content.uploader.rest.api.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monster.content.uploader.rest.api.entity.AssetSubtype;
import com.monster.content.uploader.rest.api.entity.AssetType;
import com.monster.content.uploader.rest.api.entity.Brand;
import com.monster.content.uploader.rest.api.entity.Country;
import com.monster.content.uploader.rest.api.entity.Department;
import com.monster.content.uploader.rest.api.entity.JobDetails;
import com.monster.content.uploader.rest.api.entity.JobStatus;
import com.monster.content.uploader.rest.api.entity.ParticipantDetails;
import com.monster.content.uploader.rest.api.entity.ProductLine;
import com.monster.content.uploader.rest.api.entity.UseCase;
import com.monster.content.uploader.rest.api.service.IAssetSubtypeService;
import com.monster.content.uploader.rest.api.service.IAssetTypeService;
import com.monster.content.uploader.rest.api.service.IBrandService;
import com.monster.content.uploader.rest.api.service.ICountryService;
import com.monster.content.uploader.rest.api.service.IDepartmentService;
import com.monster.content.uploader.rest.api.service.IJobDetailsService;
import com.monster.content.uploader.rest.api.service.IJobStatusService;
import com.monster.content.uploader.rest.api.service.IParticipantDetailsService;
import com.monster.content.uploader.rest.api.service.IProductLineService;
import com.monster.content.uploader.rest.api.service.IUseCaseService;

@Component
public class JobDetailsUpdateMapper {
	@Autowired
	IAssetSubtypeService assetSubTypeService;
	
	@Autowired
	IAssetTypeService assetTypeService;
	
	@Autowired
	IBrandService brandService;
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	IDepartmentService departmentService;
	
	
	@Autowired
	IParticipantDetailsService participantDetailsService;
	
	@Autowired
	IProductLineService productLineService;
	
	@Autowired
	IUseCaseService useCaseService;

	@Autowired
	IJobStatusService jobStatusService;
	
	@Autowired
	IJobDetailsService jobDetailsService;
	
	JobDetails jobDetailsUpdateObj = new JobDetails();
	
			
	public JobDetails jobDetailsMappaer(JobDetails jobDetails) {
		
		//Getting existing jobDetails object for replacing with the new value
		jobDetailsUpdateObj = jobDetailsService.retrieveById(jobDetails.getJobId());
		
						
		//Set Business Id
		jobDetailsUpdateObj.setBusinessId(jobDetails.getBusinessId());
		
		//Set Brand
		Brand brand = new Brand();
		brand = brandService.retrieveById(jobDetails.getBrand().getBrandId());
		jobDetailsUpdateObj.setBrand(brand);
				
		//Set Product Line
		ProductLine productLine = new ProductLine();
		productLine = productLineService.retrieveById(jobDetails.getProductLine().getPrdLineId());
		jobDetailsUpdateObj.setProductLine(productLine);
		
		//Set Country
		Country country = new Country();
		country = countryService.retrieveById(jobDetails.getCountry().getCountryId());
		jobDetailsUpdateObj.setCountry(country);
		
		//Set Department
		Department department = new Department();
		department = departmentService.retrieveById(jobDetails.getDepartment().getDptId());
		jobDetailsUpdateObj.setDepartment(department);
		
		//Set Album name
		jobDetailsUpdateObj.setAlbumName(jobDetails.getAlbumName());
		
		//Set SapMaterialNumber
		jobDetailsUpdateObj.setSapMaterialNumber(jobDetails.getSapMaterialNumber());
		
		//Set EventDate Time
		jobDetailsUpdateObj.setEventDateTime(jobDetails.getEventDateTime());
		
		//Set Use Case
		UseCase useCase = new UseCase();
		useCase = useCaseService.retrieveById(jobDetails.getUseCase().getUseCaseId());
		jobDetailsUpdateObj.setUseCase(useCase);
		
		//Set Asset type
		AssetType assetType = new AssetType();
		assetType = assetTypeService.retrieveById(jobDetails.getAssetType().getAssetTypeId());
		jobDetailsUpdateObj.setAssetType(assetType);
		
		//Set Asset Sub Type
		AssetSubtype assetSubtype = new AssetSubtype();
		assetSubtype = assetSubTypeService.retrieveById(jobDetails.getAssetSubType().getAssetSubtypeId());
		jobDetailsUpdateObj.setAssetSubType(assetSubtype);
		
		//Set Comments
		jobDetailsUpdateObj.setComments(jobDetails.getComments());
		
		//Set createedById
		ParticipantDetails participantDetails = new ParticipantDetails();
		participantDetails = participantDetailsService.retrieveById(jobDetails.getCreatedBy().getPsDetailsId());
		jobDetailsUpdateObj.setCreatedBy(participantDetails);
		participantDetails=null;
		
		//Set Created date
		jobDetailsUpdateObj.setCreatedDate(jobDetails.getCreatedDate());
		
		//Set Last modified
		participantDetails = participantDetailsService.retrieveById(jobDetails.getCreatedBy().getPsDetailsId());
		jobDetailsUpdateObj.setLastModifedBy(participantDetails);
		
		//Set Last Modified date
		jobDetailsUpdateObj.setLastModifiedDate(jobDetails.getLastModifiedDate());
		
		//Set Job Status
		JobStatus jobStatus = new JobStatus();
		jobStatus = jobStatusService.retrieveById(jobDetails.getJobStatus().getJobStatusId());
		jobDetailsUpdateObj.setJobStatus(jobStatus);
		
		//Set job name
		jobDetailsUpdateObj.setJobName(jobDetails.getJobName());
		
		return jobDetails;
	}

}
