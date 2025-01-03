package com.smhrd.basic.entity;

import java.security.Timestamp;
import java.time.LocalDate;

import com.smhrd.basic.model.MemberVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
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

	    public void setId(String id) {
	        this.id = id;
	    }
	    
	    public void setPw(String pw) {
	        this.pw = pw;
	    }

	    public void setBirthdate(String birthdate) {
	        this.birthdate = birthdate;
	    }

	    public void setGender(String gender) {
	        this.gender = gender;
	    }

	    public void setJob(String job) {
	        this.job = job;
	    }

	    public void setPreference(String preference) {
	        this.preference = preference;
	    }

	    public void setMood(String mood) {
	        this.mood = mood;
	    }

	    public void setJoinDt(Timestamp joinDt, LocalDate join_dt) {
	        this.join_dt = join_dt;
	    }
	
}
