package com.monster.content.uploader.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monster.content.uploader.rest.api.entity.UseCase;

@Repository
public interface UseCaseRepository extends JpaRepository<UseCase, Integer> {

}
