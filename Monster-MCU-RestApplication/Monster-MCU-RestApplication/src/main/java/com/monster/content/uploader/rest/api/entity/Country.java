package com.monster.content.uploader.rest.api.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Table(name = "COUNTRY")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Country {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer countryId;
	
	@Column(name="DISPLAY_NAME")
	private String countryDsName;
	
	@Column(name="NAME")
	private String countryName;
	
	@Column(name="SEQUENCE")
	private int countrySeqId;
	
	@Column(name="VALUE")
	private String countryValue;
	
	@Column(name="CODE")
	private String countryCode;
	
	@ManyToMany(mappedBy = "countries", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Brand> brands;

	//Constructor without Id for Create Process 
	public Country(String countryDsName, String countryName, int countrySeqId, String countryValue, String countryCode,
			Set<Brand> brands) {
		super();
		this.countryDsName = countryDsName;
		this.countryName = countryName;
		this.countrySeqId = countrySeqId;
		this.countryValue = countryValue;
		this.countryCode = countryCode;
		this.brands = brands;
	}

	//Constructor only with Id for many to many Mapping
	public Country(Integer countryId) {
		super();
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", countryDsName=" + countryDsName + ", countryName=" + countryName
				+ ", countrySeqId=" + countrySeqId + ", countryValue=" + countryValue + ", countryCode=" + countryCode
				+ "]";
	}
	
	
	
	
	

}
