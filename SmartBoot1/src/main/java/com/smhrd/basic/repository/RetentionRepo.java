package com.smhrd.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.RetentionEntity;

@Repository
public interface RetentionRepo extends JpaRepository<RetentionEntity, Long> {

    // 도서 제목 또는 저자를 기준으로 도서관명 포함 검색
    @Query("SELECT b, l.libNm FROM RetentionEntity r " +
           "JOIN BookEntity b ON r.bookIdx = b.bookIdx " +
           "JOIN LibraryEntity l ON r.libIdx = l.libIdx " +
           "WHERE b.title LIKE %:keyword% OR b.author LIKE %:keyword%")
    List<Object[]> findBooksWithLibraryByKeyword(String keyword);
}
