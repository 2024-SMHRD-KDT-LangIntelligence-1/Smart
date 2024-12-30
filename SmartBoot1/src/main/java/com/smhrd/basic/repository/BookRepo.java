package com.smhrd.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.basic.entity.BookEntity;

public interface BookRepo extends JpaRepository<BookEntity, Long>{
	List<BookEntity> findByBestSeller(String bestSeller);
}