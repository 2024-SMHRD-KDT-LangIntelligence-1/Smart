package com.smhrd.basic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecomVO {

	private Long reco_idx;
	private String ID;
	private String title;
	private Integer book_idx;
	private String book_title;
	
	
}
