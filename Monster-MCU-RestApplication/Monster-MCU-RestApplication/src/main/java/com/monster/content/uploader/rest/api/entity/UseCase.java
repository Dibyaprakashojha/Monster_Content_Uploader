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
@Table(name = "USE_CASE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UseCase {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer useCaseId;
	
	@Column(name="DISPLAY_NAME")
	private String useCaseDsName;
	
	@Column(name="NAME")
	private String useCaseName;
	
	@Column(name="SEQUENCE")
	private int useCaseSeqId;

	//Constructor without Id for create Process 
	public UseCase(String useCaseDsName, String useCaseName, int useCaseSeqId) {
		super();
		this.useCaseDsName = useCaseDsName;
		this.useCaseName = useCaseName;
		this.useCaseSeqId = useCaseSeqId;
	}
	
	
	

}
