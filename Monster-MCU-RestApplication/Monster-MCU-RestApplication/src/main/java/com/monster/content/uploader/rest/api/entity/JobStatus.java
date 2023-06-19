package com.monster.content.uploader.rest.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "JOB_STATUS")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class JobStatus {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobStatusId;
	
	@Column(name="JOB_STATUS")
	private String jobStatusName;
	
	@Column(name="STATUS_CODE")
	private int jobStatusCode;

	//Constructor without Id for create Process 
	public JobStatus(String jobStatusName, int jobStatusCode) {
		super();
		this.jobStatusName = jobStatusName;
		this.jobStatusCode = jobStatusCode;
	}
	
	

}
