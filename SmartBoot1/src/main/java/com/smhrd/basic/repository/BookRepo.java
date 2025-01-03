package com.smhrd.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.BookEntity;

@Repository
public interface BookRepo extends JpaRepository<BookEntity, Long>{
	
	// 인기도서
	List<BookEntity> findByBestSeller(String bestSeller);
	List<BookEntity> findByTitleContainingOrAuthorContaining(String titleKeyword, String authorKeyword);
	
	// 도서관 ID로 해당 도서관의 보유 도서 목록 반환 	
	@Query("SELECT b, l.libNm FROM BookEntity b " +
	           "JOIN RetentionEntity r ON b.bookIdx = r.bookIdx " +
	           "JOIN LibraryEntity l ON r.libIdx = l.libIdx " +
	           "WHERE l.libIdx = :libIdx")
	List<Object[]> findBooksByLibraryIdWithLibraryName(@Param("libIdx") Integer libIdx);


	
}