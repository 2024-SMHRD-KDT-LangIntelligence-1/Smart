package com.smhrd.basic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookVO {

	// 도서 일련번호
	private Long BOOK_IDX;

	// 제목
	private String TITLE;

	// 저자
	private String AUTHOR;

	// 출판사
	private String PUBLISHER;

	// ISBN
	private String ISBN;

	// 가격
	private Integer PRICE;

	// 책소개
	private String BOOK_DESC;

	// 도서분류코드
	private String BOOK_CODE;

	// 베스트 셀러
	private String BEST_SELLER;

	// 장르
	private String GENRE;

	// 라벨
	private String LABEL;

	// 도서 이미지
	private String BOOK_IMG;

}
