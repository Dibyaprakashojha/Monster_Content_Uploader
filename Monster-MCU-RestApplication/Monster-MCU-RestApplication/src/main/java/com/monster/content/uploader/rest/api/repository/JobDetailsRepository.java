package com.monster.content.uploader.rest.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monster.content.uploader.rest.api.entity.JobDetails;

@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetails, Integer> {

	@Query(value = "SELECT * FROM JOB_DETAILS jd WHERE jd.CREATED_BY_FK = :createdById", nativeQuery = true)
	List<JobDetails> findByCreatedBy(int createdById);
}
