package com.smhrd.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.basic.entity.RecomEntity;

public interface RecomRepo extends JpaRepository<RecomEntity, Integer>{
	
}
