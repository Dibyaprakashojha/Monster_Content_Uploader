package com.monster.content.uploader.rest.api.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "ASSET_TYPE")
@Entity

@AllArgsConstructor
@NoArgsConstructor
public class AssetType {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer assetTypeId;
	
	@Column(name="DISPLAY_NAME")
	private String assetTypeDsName;
	
	@Column(name="NAME")
	private String assetTypeName;
	
	@Column(name="SEQUENCE")
	private int assetTypeSeqId;
	
	@Column(name="VALUE")
	private String assetTypeValue;
	
//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "assetType", cascade = CascadeType.MERGE)
//	@JsonIgnore
//	private List<AssetSubtype> assetSubtypes;

	//Constructor without Id for create Process 
	public AssetType(String assetTypeDsName, String assetTypeName, int assetTypeSeqId, String assetTypeValue) {
		super();
		this.assetTypeDsName = assetTypeDsName;
		this.assetTypeName = assetTypeName;
		this.assetTypeSeqId = assetTypeSeqId;
		this.assetTypeValue = assetTypeValue;
		
	}
	
	
	
	

}
