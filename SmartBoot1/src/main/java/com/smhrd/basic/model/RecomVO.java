package com.smhrd.basic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecomVO {

    // 책 제목 (Primary Key와 매핑)
    private String bookTitle;

    // 사용자 ID
    private String userId;

    // 도서 인덱스
    private Integer bookIndex;

    // 빈 필드
    private String reservedField;

    // Getters & Setters 생성 (롬복의 @Data가 자동 생성)
}
