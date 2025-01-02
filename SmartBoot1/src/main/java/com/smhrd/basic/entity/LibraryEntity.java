package com.smhrd.basic.entity;

import com.smhrd.basic.model.LibraryVO;

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
@Table(name = "tb_smart_library") // 테이블 이름과 매핑
public class LibraryEntity {
	
	@Id
	private Long libIdx;
	private String libNm;
	private String libAddr;
	private Integer regionIdx;
	private String regionNm;

    public LibraryEntity(LibraryVO param) {
        this.libIdx = param.getLIB_IDX();
        this.libNm = param.getLIB_NM();
        this.libAddr = param.getLIB_ADDR();
        this.regionIdx = param.getREGION_IDX();
        this.regionNm = param.getREGION_NM();
    }

}
