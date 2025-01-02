package com.smhrd.basic.entity;

import com.smhrd.basic.model.BookVO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_BOOK") // 테이블 이름과 매핑
public class BookEntity {// entity 객체는 DB를 위한 객체
	
	@Id
	private Long bookIdx;
	private String title;
	private String author;
	private String publisher;
	private String isbn;
	private Integer price;
	private String bookDesc;
	private String bookCode;
	private String bestSeller;
	private String genre;
	private String label;
	private String bookImg;
	
	public BookEntity(BookVO param) {
	     this.bookIdx = param.getBOOK_IDX();
	     this.title = param.getTITLE();
	     this.author = param.getAUTHOR();
	     this.publisher = param.getPUBLISHER();
	     this.isbn = param.getISBN();
	     this.price = param.getPRICE();
	     this.bookDesc = param.getBOOK_DESC();
	     this.bookCode = param.getBOOK_CODE();
	     this.bestSeller = param.getBEST_SELLER();
	     this.genre = param.getGENRE();
	     this.label = param.getLABEL();
	     this.bookImg = param.getBOOK_IMG();
	 }
}
