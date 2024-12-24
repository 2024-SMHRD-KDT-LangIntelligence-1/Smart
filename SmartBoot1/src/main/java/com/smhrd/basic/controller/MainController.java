package com.smhrd.basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.smhrd.basic.entity.MemberEntity;
import com.smhrd.basic.model.MemberVO;
import com.smhrd.basic.repository.MemberRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	// 기존에 RequestMapping 이라고 작성 했으나
	// boot 에선 get/post/put/delete/patch 를 나누어서 mapping 코드 작성 권장
	// --> legacy 에서도 사용 가능
	
	@Autowired
	MemberRepo repo;
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	// 회원가입기능
	@PostMapping("member/join.do")
	public String join(MemberVO vo) {
		
		MemberEntity en = new MemberEntity(vo);
		
		repo.save(en);
		
		return "redirect:/";
	}
	
	// 로그인 기능
	@PostMapping("member/login.do")
	public String login( String email, String pw, HttpSession session) {
		
		// System.out.println("email : " + email);
		
		MemberEntity enti = repo.findByEmailAndPw(email, pw);
		
		// System.out.println("email : " + enti.getEmail());
		// System.out.println("password : " + enti.getPw());
		
		session.setAttribute("member", enti);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("member");
		
		return "redirect:/";
	}

}
