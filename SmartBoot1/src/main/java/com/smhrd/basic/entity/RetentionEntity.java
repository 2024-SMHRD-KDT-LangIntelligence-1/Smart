package com.smhrd.basic.entity;

import com.smhrd.basic.model.RetentionVO;

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
@Table(name = "TB_RETENTION")
public class RetentionEntity {
	
	@Id
    private Integer rtIdx;
	private Integer bookIdx;
	private Integer libIdx;
	
    public RetentionEntity(RetentionVO param) {
        this.rtIdx = param.getRT_IDX();
        this.bookIdx = param.getBOOK_IDX();
        this.libIdx = param.getLIB_IDX();
    }

}
