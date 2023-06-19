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
@Table(name = "PRODUCT_LINE")
@Entity

@AllArgsConstructor
@NoArgsConstructor
public class ProductLine {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prdLineId;
	
	@Column(name="DISPLAY_NAME")
	private String prdLineDsName;
	
	@Column(name="NAME")
	private String prdLineName;
	
	@Column(name="SEQUENCE")
	private int prdLineSeqId;
	
	@Column(name="VALUE")
	private String prdLineValue;
	
	@ManyToMany(mappedBy = "productLines", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Brand> brands;

	//Constructor without Id for create Process 
	public ProductLine(String prdLineDsName, String prdLineName, int prdLineSeqId, String prdLineValue,
			Set<Brand> brands) {
		super();
		this.prdLineDsName = prdLineDsName;
		this.prdLineName = prdLineName;
		this.prdLineSeqId = prdLineSeqId;
		this.prdLineValue = prdLineValue;
		this.brands = brands;
	}

	//Constructor only with Id for many to many Mapping
	public ProductLine(Integer prdLineId) {
		super();
		this.prdLineId = prdLineId;
	}

	@Override
	public String toString() {
		return "ProductLine [prdLineId=" + prdLineId + ", prdLineDsName=" + prdLineDsName + ", prdLineName="
				+ prdLineName + ", prdLineSeqId=" + prdLineSeqId + ", prdLineValue=" + prdLineValue + "]";
	}
	
	
	

}

