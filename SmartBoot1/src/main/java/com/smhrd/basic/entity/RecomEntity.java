package com.smhrd.basic.entity;

import com.smhrd.basic.model.RecomVO;

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
@Table(name = "tb_recommendation")
public class RecomEntity {
	
	@Id
    private Integer recoIdx;
	private String id;
	private Integer bookIdx;

    public RecomEntity(RecomVO param)
    {
        this.recoIdx = param.getRecoIdx();
        this.id = param.getId();
        this.bookIdx = param.getBookIdx();
    }

}
