package com.monster.content.uploader.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monster.content.uploader.rest.api.entity.AssetType;


@Repository
public interface AssetTypeRepository extends JpaRepository<AssetType, Integer> {

}
