package com.monster.content.uploader.rest.api.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "JOB_DETAILS")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class JobDetails {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobId;
	
	@Column(name="BUSINESS_ID")
	private int businessId;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="BRAND_ID_FK")
	private Brand brand;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="PRODUCT_LINE_ID_FK")
	private ProductLine productLine;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="COUNTRY_ID_FK")
	private Country country;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="DEPARTMENT_ID")
	private Department department;
	
	@Column(name="ALBUM_NAME")
	private String albumName;
	
	@Column(name="SAP_MATERIAL_NUMBER")
	private int sapMaterialNumber;
	
	@Column(name="EVENT_DATE")
	private LocalDateTime eventDateTime;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="USE_CASE_ID_FK")
	private UseCase useCase;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="ASSET_TYPE_ID_FK")
	private AssetType assetType;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="ASSET_SUBTYPE_ID_FK")
	private AssetSubtype assetSubType;
	
	@Column(name="COMMENTS")
	private String comments;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="CREATED_BY_FK")
	private ParticipantDetails createdBy;
	
	@Column(name="CREATED_DATE")
	
	private LocalDateTime createdDate;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="LAST_MODIFIED_BY_FK")
	private ParticipantDetails lastModifedBy;
	
	@Column(name="LAST_MODIFIED_DATE")
	private LocalDateTime lastModifiedDate;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="JOB_STATUS_ID_FK")
	private JobStatus jobStatus;
	
	@Column(name="JOB_NAME")
	private String jobName;

	//Constructor with out ID for Create new record
	public JobDetails(int businessId, Brand brand, ProductLine productLine, Country country, Department department,
			String albumName, int sapMaterialNumber, LocalDateTime eventDateTime, UseCase useCase, AssetType assetType,
			AssetSubtype assetSubType, String comments, ParticipantDetails createdBy, LocalDateTime createdDate,
			ParticipantDetails lastModifedBy, LocalDateTime lastModifiedDate, JobStatus jobStatus, String jobName) {
		super();
		this.businessId = businessId;
		this.brand = brand;
		this.productLine = productLine;
		this.country = country;
		this.department = department;
		this.albumName = albumName;
		this.sapMaterialNumber = sapMaterialNumber;
		this.eventDateTime = eventDateTime;
		this.useCase = useCase;
		this.assetType = assetType;
		this.assetSubType = assetSubType;
		this.comments = comments;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifedBy = lastModifedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.jobStatus = jobStatus;
		this.jobName = jobName;
	}
	

	
	
	
}
