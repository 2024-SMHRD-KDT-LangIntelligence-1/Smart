package com.smhrd.basic.entity;

import com.smhrd.basic.model.RecomVO;

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
@Table(name = "tb_recommendation")
public class RecomEntity {

    private Long recoIdx;
	private String id;
	private String title;
	@Id
	private Integer bookIdx;
	private String bookTitle;
	
	public RecomEntity(RecomVO vo) {
		this.recoIdx = vo.getReco_idx();
		this.id = vo.getID();
		this.title = vo.getTitle();
		this.bookIdx = vo.getBook_idx();
		this.bookTitle = vo.getBook_title();
	}
	
}