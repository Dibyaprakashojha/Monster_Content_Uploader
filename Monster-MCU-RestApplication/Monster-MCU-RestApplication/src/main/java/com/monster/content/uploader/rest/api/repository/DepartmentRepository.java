package com.monster.content.uploader.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monster.content.uploader.rest.api.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
