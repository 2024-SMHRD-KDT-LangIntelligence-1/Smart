package com.smhrd.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.MemberEntity;

@Repository
public interface MemberRepo extends JpaRepository<MemberEntity, String>{
	
	// 로그인 및 회원가입
	MemberEntity findByIdAndPw(String id, String pw);
	
}
