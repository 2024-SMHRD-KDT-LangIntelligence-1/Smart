package com.smhrd.basic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
	
	private String id;
	private String pw;
	private String birthdate;
	private String gender;
	private String job;
	private String preference;
	private String mood;
	private String join_dt;
	
}
