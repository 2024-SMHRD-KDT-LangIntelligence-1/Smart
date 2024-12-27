package com.smhrd.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.basic.entity.MemberEntity;

public interface MemberRepo extends JpaRepository<MemberEntity, Long>{

	MemberEntity findByIdAndPw(String id, String pw);

}
