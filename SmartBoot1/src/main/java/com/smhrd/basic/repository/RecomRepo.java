package com.smhrd.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.BookEntity;
import com.smhrd.basic.entity.RecomEntity;


@Repository
public interface RecomRepo extends JpaRepository<RecomEntity, Long>{

	@Query("SELECT b FROM RecomEntity r " +
           "JOIN BookEntity b ON r.bookIdx = b.bookIdx " +
           "WHERE r.id = :id")
    List<BookEntity> findRecommendedBooksByUserId(@Param("id") String id);

}
