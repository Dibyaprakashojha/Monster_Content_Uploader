package com.monster.content.uploader.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monster.content.uploader.rest.api.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

}
