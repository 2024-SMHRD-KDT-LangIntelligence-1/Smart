package com.smhrd.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.RetentionEntity;

@Repository
public interface RetentionRepo extends JpaRepository<RetentionEntity, Long>{
}