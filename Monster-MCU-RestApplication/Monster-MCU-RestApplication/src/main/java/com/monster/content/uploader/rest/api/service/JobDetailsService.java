package com.monster.content.uploader.rest.api.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.monster.content.uploader.rest.api.exception.IdNotFoundException;
import com.monster.content.uploader.rest.api.model.SolrModel;
import com.monster.content.uploader.rest.api.repository.JobDetailsRepository;
import com.monster.content.uploader.rest.api.repository.ParticipantDetailsRepository;

/**
 * @author SivakumarK
 *
 */
@Service
public class JobDetailsService implements IJobDetailsService {

	@Autowired
	JobDetailsRepository jobDetailsRepo;

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
	ParticipantDetailsRepository pdRepo;

	@Autowired
	SolrModel solrModel;
	
	@Autowired
	IAzureQueueService azureQueueService;

	
	JobDetails jobDetails = new JobDetails();

	/**
	* Creates a record in the job table. This is a blocking call and will return when the record is created or an error occurs
	* 
	* @param jobDetails - job details to be saved
	* 
	* @return message to be displayed to the user in the status bar or null if everything is OK or an error
	*/
	@Override
	public String createRecord(JobDetails jobDetails) {

		// upload into the table
		JobDetails jobInfo = jobDetailsRepo.save(jobDetails);
		jobInfo.getJobId();
		

		LocalDateTime localDateTime = jobInfo.getCreatedDate();
		ZoneId zoneId = ZoneId.of("UTC");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String utcDate = localDateTime.atZone(zoneId).format(formatter);

		try {
			azureQueueService.sendMessageToQueue(jobInfo.getJobId().toString(),"Create",utcDate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "problem in Sending message in queue";
		}

		return "Record Saved Successfully";
	}

	/**
	* Updates a record in the database. This is used to update the JobDetails object with the values from the object
	* 
	* @param jobDetails - the object to be updated
	* 
	* @return the id of the record that was updated or null if the record wasn't found in the database
	*/
	@Override
	public String updateRecord(JobDetails jobDetails) {
		
		//Getting existing jobDetails object for replacing with the new value
		JobDetails jobDetailsUpdateObj = retrieveById(jobDetails.getJobId());
			
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

		JobDetails responseObj = jobDetailsRepo.save(jobDetailsUpdateObj);

		LocalDateTime localDateTime = responseObj.getCreatedDate();
		ZoneId zoneId = ZoneId.of("UTC");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String utcDate = localDateTime.atZone(zoneId).format(formatter);

		try {
			azureQueueService.sendMessageToQueue(responseObj.getJobId().toString(),"Update",utcDate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "problem in Sending message in queue";
		}
		
		return "Record Updated Successfully";
	}

	/**
	* Deletes record by job details id. This method is used to delete record from Azure storage. The record will be deleted in local time and in Azure queue
	* 
	* @param jobDetailsId - id of record to delete
	* 
	* @return String indicating success or failure of the delete operation. It can be " Record Deleted Successfully " or
	*/
	@Override
	public String deleteRecordById(int jobDetailsId) {
		JobDetails jobDetails= jobDetailsRepo.findById(jobDetailsId).orElseThrow(() -> new IdNotFoundException());
		jobDetailsRepo.deleteById(jobDetailsId);
		LocalDateTime localDateTime = jobDetails.getCreatedDate();
		ZoneId zoneId = ZoneId.of("UTC");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String utcDate = localDateTime.atZone(zoneId).format(formatter);

		try {
			azureQueueService.sendMessageToQueue( String.valueOf(jobDetailsId),"Delete",utcDate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "problem in Sending message in queue";
		}
		return "Record Deleted Successfully";
	}

	/**
	* Retrieves all job details. This is used to populate the list of jobs that have been created by the JobManager.
	* 
	* 
	* @return a list of all job details in the database ( never null ). The list may be empty if no jobs have been created
	*/
	@Override
	public List<JobDetails> retrieveAllRecords() {
		List<JobDetails> allRecords = jobDetailsRepo.findAll();
		return allRecords;
	}

	/**
	* Retrieves JobDetails by id. This method is used to retrieve JobDetails from database. If record doesn't exist it throws IdNotFoundException
	* 
	* @param jobDetailsId - id of job details to retrieve
	* 
	* @return JobDetails with id specified in parameter jobDetailsId or exception if record does not exist in database or is
	*/
	@Override
	public JobDetails retrieveById(int jobDetailsId) {
		System.out.println("jobid-----------> "+jobDetailsId);
		JobDetails recordById = jobDetailsRepo.findById(jobDetailsId).orElseThrow(() -> new IdNotFoundException());
		System.out.println("recordById----------> "+recordById);
		return recordById;
	}

}
