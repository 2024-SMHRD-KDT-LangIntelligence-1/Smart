package com.smhrd.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.MemberEntity;

@Repository
public interface MemberRepo extends JpaRepository<MemberEntity, String>{

	MemberEntity findByIdAndPw(String id, String pw);

}
