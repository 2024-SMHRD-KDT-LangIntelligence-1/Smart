package com.smhrd.basic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecomVO {

	// 추천 번호
	private Integer reco_idx;

	// 회원 아이디
	private String id;

	// 도서 일련번호
	private Integer book_idx;

	public Integer getRecoIdx() {
		return reco_idx;
	}

	public void setRecoIdx(Integer reco_idx) {
		this.reco_idx = reco_idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBookIdx() {
		return book_idx;
	}

	public void setBookIdx(Integer book_idx) {
		this.book_idx = book_idx;
	}
}
