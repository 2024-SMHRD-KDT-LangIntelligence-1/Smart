package com.smhrd.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.MemberEntity;

// @Mapper가 없는 이유 --> mybatis
// JPA에서 사용하는 어노테이션
@Repository	// repository == mapper
public interface MemberRepo extends JpaRepository<MemberEntity, Long>{
	// 인터페이스 복습 3 인터페이스 간 상속 키워드는 extends
	// <> (제너릭스) 안에 타입 작성 시 <> 레퍼런스 long(X) -> Long(O)
	
	// jpa는 java persistence api의 줄임말
	
	// jpa 는 java 코드로 테이블을 만들고() 메소드를 실행시켜 crud(Create Read Update Delete)를 하자~ ()
	
	// 1. 테이블 만들기 --> entity 객체 != VO 객체
	
	// 2. 메소드를 실행시켜 crud 작업 진행
	//	  --> 이름 바꿔주기 -- mapper -> repository
	// interface를 하나 더 확장
	// JpaRepository<T == entity, ID == T에서 작성한 entity의 pk 타입>
	
	// 끝
	// 기본적으로 제공하고 있는 crud 작업
	
	// 1. findAll()
	//	ㄴ select * from 테이블
	// 2. findById(pk값)
	//	ㄴ select * from 테이블 where pk 컬럼명 = pk 값
	// 3. save(매개변수 or entity)
	//	ㄴ insert into 테이블 values(매개변수 or entity)
	// 4. delete(매개변수)
	//	ㄴ delete from 테이블 where pk 컬럼명 = 매개변수
	
	// 이 이외에도 sql 사용하기 위해 사용자 정의로 만들 수 있습니다.
	// 규칙이 존재
	
	// 작성하고자 하는 sql
	// select * from 테이블명 where email =? and password =?
	
	// 매개변수 필요한 것 email, password
	// select 메소드 작명의 규칙 -- ★반드시 카멜기법으로 작성할 것!
	// 시작은 find + (테이블명) + By + 컬럼명 + And(Or) + 컬럼명
	
	// 접근제한자 생략
	// select 결곽밧은 entity 객체
	// 매개변수는 순서대로 동작(변수명, 이름으로 찾아가는 것 아님!!)
	MemberEntity findByEmailAndPw(String email, String pw);

}
