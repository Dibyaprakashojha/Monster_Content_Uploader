package com.monster.content.uploader.rest.api.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "BRAND")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer brandId;
	
	@Column(name="DISPLAY_NAME")
	private String brandDsName;
	
	@Column(name="NAME")
	private String brandName;
	
	@Column(name="SEQUENCE")
	private int brandSeqId;
	
	@Column(name="VALUE")
	private String brandValue;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "BRAND_COUNTRY_MAPPING",
			joinColumns = @JoinColumn(name="BRAND_ID_FK"),
			inverseJoinColumns = @JoinColumn(name = "COUNTRY_ID_FK")
	)
	private Set<Country> countries;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "BRAND_PRODUCT_LINE_MAPPING",
			joinColumns = @JoinColumn(name="BRAND_ID_FK"),
			inverseJoinColumns = @JoinColumn(name = "PRODUCT_LINE_ID_FK")
	)
	private Set<ProductLine> productLines;

	//Constructor without Id for create Process 
	public Brand(String brandDsName, String brandName, int brandSeqId, String brandValue, Set<Country> countries,
			Set<ProductLine> productLines) {
		super();
		this.brandDsName = brandDsName;
		this.brandName = brandName;
		this.brandSeqId = brandSeqId;
		this.brandValue = brandValue;
		this.countries = countries;
		this.productLines = productLines;
	}


	//Constructor only with Id for many to many Mapping
	public Brand(Integer brandId) {
		super();
		this.brandId = brandId;
	}

		
}
