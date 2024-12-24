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

// 해당 클래스를 DB 테이블처럼 구성하겠다
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {// entity 객체는 DB를 위한 객체
	
	// 생성자 -- save(entity 객체)
	public MemberEntity(MemberVO vo) {
		this.email = vo.getEmail();
		this.pw = vo.getPw();
		tel = vo.getTel();
		address = vo.getAddress();
	}
	
	// ★★★entity 구성시 필수 사항이 존재!!!!!
	// 반드시 pk가 존재
	
	// 해당 컬럼이 pk에요~~
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idx;
	
	// tip!
	// 컬럼을 uique 값으로 잡고 싶습니다  -- email 컬럼에 작성
	// 컬럼을 not null로 하고 싶어요 -- pw 에 작성
	// 컬럼의 크기를 키우고 싶어요 -- address 에 작성
	// 컬럼을 auto_increase 하고 싶어요 -- idx 에 작성
	
	// @Column 으로 시작
	// tip 에 있는 기능을 구현하고 싶은 변수 위에 작성
	
	@Column(unique = true)	
	private String email;
	
	@Column(nullable = false)
	private String pw;
	private String tel;
	
	@Column(length = 100)
	private String address;

}
