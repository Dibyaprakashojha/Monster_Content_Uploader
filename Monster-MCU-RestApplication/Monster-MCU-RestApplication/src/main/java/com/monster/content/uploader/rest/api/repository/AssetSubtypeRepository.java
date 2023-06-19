package com.monster.content.uploader.rest.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monster.content.uploader.rest.api.entity.AssetSubtype;


@Repository
public interface AssetSubtypeRepository extends JpaRepository<AssetSubtype, Integer> {
	
//	@Query(value="SELECT * FROM ASSET_SUBTYPE INNER JOIN ASSET_TYPE ON ASSET_SUBTYPE.ASSET_TYPE_ID_FK = ASSET_TYPE.ID", nativeQuery = true)
//	List<AssetSubtypeModel> getAllAssetSubtypeRecords();

}
