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
@Table(name = "DEPARTMENT")
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Department {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dptId;
	
	@Column(name="DISPLAY_NAME")
	private String dptDsName;
	
	@Column(name="NAME")
	private String dptName;
	
	@Column(name="SEQUENCE")
	private int dptSeqId;
	
	@Column(name="VALUE")
	private String dptValue;
	
	@Column(name="CODE")
	private String dptCode;

	//Constructor without Id for Create Process 
	public Department(String dptDsName, String dptName, int dptSeqId, String dptValue, String dptCode) {
		super();
		this.dptDsName = dptDsName;
		this.dptName = dptName;
		this.dptSeqId = dptSeqId;
		this.dptValue = dptValue;
		this.dptCode = dptCode;
	}
	
	

}
