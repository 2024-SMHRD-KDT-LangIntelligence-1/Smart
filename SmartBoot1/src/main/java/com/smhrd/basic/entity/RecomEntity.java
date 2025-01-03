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
    private String bookTitle;   // 테이블의 primary key와 매핑
    
    private String userId;      // 사용자 ID 필드
    private Integer bookIndex;  // 책 인덱스 필드
    private String reservedField; // reserve 필드 (널 허용)

    // RecomVO 객체를 이용한 생성자
    public RecomEntity(RecomVO param) {
        this.bookTitle = param.getBookTitle();
        this.userId = param.getUserId();
        this.bookIndex = param.getBookIndex();
        this.reservedField = param.getReservedField();
    }
    
    
}