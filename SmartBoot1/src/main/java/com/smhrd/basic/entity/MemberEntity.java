package com.smhrd.basic.entity;

import java.time.LocalDate;

import com.smhrd.basic.model.MemberVO;

import jakarta.persistence.Column;
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
@Table(name = "TB_USER")
public class MemberEntity {// entity 객체는 DB를 위한 객체
	
	@Id
	private String id;
	
	@Column(nullable = false)
	private String pw;
	private String birthdate;
	
	@Column(length = 100)
	private String gender;
	
	private String job;
	private String preference;
	private String mood;
	private LocalDate join_dt;
	
	public MemberEntity(MemberVO vo) {
		this.id = vo.getId();
		this.pw = vo.getPw();
		this.birthdate = vo.getBirthdate();
		this.gender = vo.getGender();
		this.job = vo.getJob();
		this.preference = vo.getPreference();
		this.mood = vo.getMood();
		this.join_dt = LocalDate.now();
	}
	
}
