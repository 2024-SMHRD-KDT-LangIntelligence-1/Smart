package com.smhrd.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot1Application {
	// @SpringBootApplication 3개의 어노테이션이 합쳐진 어노테이션

	// 1. @SpringBootConfiguration
	// 		ㄴ spring boot의 전반적인 환경설정을 담당
	
	// 2. @EnableAutoConfiguration
	//		ㄴ 라이브러리 삽입시 pom.xml에 코드 작성이 되고 bean객체를 생성하여 환경설정 도움

	// 3. @ComponentScan
	//		ㄴ controller 를 사용하기 위해서 root-context에 scan bean 객체 생성 --> 자동으로 인식

	// 요약
	// @SpringBootApplication 하나로
	// Spring의 전반적인 환경설정, 라이브러리 설정, controller 설정까지 가능
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBoot1Application.class, args);
	}

}
