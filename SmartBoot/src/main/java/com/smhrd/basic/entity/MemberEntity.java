package com.smhrd.basic.entity;

import com.smhrd.basic.model.MemberVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 해당 클래스를 DB테이블처럼 구성하겠다
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity { // entity 객체는 DB를 위한 객체

	// 생성자 -- save(entity 객체)
	public MemberEntity(MemberVO vo) {
		this.email = vo.getEmail();
		this.pw = vo.getPw();
		tel = vo.getTel();
		address = vo.getAddress();
	}
	
	// ★★★ entity 구성시 필수사항 존재!!! ★★★
	// 반드시 pk가 존재
	// 해당 컬럼이 pk임
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idx;

	// tip!
	// 컬럼을 unique 로 하고싶음 --> email 컬럼에 작성
	// 컬럼을 not null 로 하고싶음 --> pw 컬럼에 작성
	// 컬럼 크기 키우고 싶음 --> address 컬럼에 작성
	// 컬럼을 auto_increase 하고싶음 --> idx 컬럼에 작성

	// @Column으로 시작
	// tip에 있는 기능을 구현하고싶은 변수 위에 작성

	@Column(unique = true)
	private String email;

	@Column(nullable = false)
	private String pw;
	private String tel;

	@Column(length = 100)
	private String address;
}
