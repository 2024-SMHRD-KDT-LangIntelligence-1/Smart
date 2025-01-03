package com.smhrd.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.BookEntity;
import com.smhrd.basic.entity.RecomEntity;

@Repository
public interface RecomRepo extends JpaRepository<RecomEntity, String>{

	// 인기도서
	List<BookEntity> findByBestSeller(String title);
	
	@Query("SELECT b FROM BookEntity b WHERE b.title = true")
	List<BookEntity> findRecommendedBooks();
	
}
