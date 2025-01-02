package com.smhrd.basic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryVO {
	
    private Long LIB_IDX;
    private String LIB_NM;
    private String LIB_ADDR;
    private Integer REGION_IDX;
    private String REGION_NM;
	
}
