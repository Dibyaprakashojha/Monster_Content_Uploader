package com.monster.content.uploader.rest.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SivakumarK
 * 
 * DESC: This is the entity class for AssetSubtype Table
 *
 */
@Getter
@Setter
@ToString
@Table(name = "ASSET_SUBTYPE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AssetSubtype {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer assetSubtypeId;
	
	@Column(name="DISPLAY_NAME")
	private String assetSubtypeDsName;
	
	@Column(name="NAME")
	private String assetSubtypeName;
	
	@Column(name="SEQUENCE")
	private int assetSubtypeSeqId;
	
	@Column(name="VALUE")
	private String assetSubtypeValue;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="ASSET_TYPE_ID_FK")
	private AssetType assetType;

	//Constructor without Id for Create Process 
	public AssetSubtype(String assetSubtypeDsName, String assetSubtypeName, int assetSubtypeSeqId,
			String assetSubtypeValue, AssetType assetType) {
		super();
		this.assetSubtypeDsName = assetSubtypeDsName;
		this.assetSubtypeName = assetSubtypeName;
		this.assetSubtypeSeqId = assetSubtypeSeqId;
		this.assetSubtypeValue = assetSubtypeValue;
		this.assetType = assetType;
	}

}
