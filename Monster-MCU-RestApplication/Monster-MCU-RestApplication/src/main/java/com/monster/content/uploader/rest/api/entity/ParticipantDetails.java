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
@Table(name = "PARTICIPANT_DETAILS")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDetails {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer psDetailsId;
	
	@Column(name="DISPLAY_NAME")
	private String psDetailsDsName;
	
	@Column(name="EMAIL")
	private String psDetailsEmail;

	//Constructor without Id for create Process 
	public ParticipantDetails(String psDetailsDsName, String psDetailsEmail) {
		super();
		this.psDetailsDsName = psDetailsDsName;
		this.psDetailsEmail = psDetailsEmail;
	}
	
	
	
}
