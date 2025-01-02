package com.smhrd.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.BookEntity;
import com.smhrd.basic.entity.LibraryEntity;

@Repository
public interface LibraryRepo extends JpaRepository<LibraryEntity, Long>{
	
	// 지역 선택
	@Query("SELECT DISTINCT b.regionNm FROM LibraryEntity b")
    List<String> findRegions();

	// 지역을 기준으로 도서관 반환
	List<LibraryEntity> findByRegionNm(String regionNm);
	
}

