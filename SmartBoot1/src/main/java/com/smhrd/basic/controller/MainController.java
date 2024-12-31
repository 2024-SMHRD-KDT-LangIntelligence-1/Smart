package com.smhrd.basic.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.basic.entity.BookEntity;
import com.smhrd.basic.entity.MemberEntity;
import com.smhrd.basic.entity.RecomEntity;
import com.smhrd.basic.model.MemberVO;
import com.smhrd.basic.repository.BookRepo;
import com.smhrd.basic.repository.MemberRepo;
import com.smhrd.basic.repository.RecomRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	private static final String String = null;
	@Autowired
	MemberRepo repo;
	
	@GetMapping("/mypage")
	public String mypage() {
		return "mypage";
	}
	
	// 회원가입
	@PostMapping("member/join.do")
	public String join(MemberVO vo) {
		MemberEntity en = new MemberEntity(vo);
		repo.save(en);
		return "redirect:/";
	}
	// 로그인
	@PostMapping("member/login.do")
	public String login(String id, String pw, HttpSession session) {
		MemberEntity enti = repo.findByIdAndPw(id, pw);
		session.setAttribute("member", enti);
		return "redirect:/";
	}
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("member");
		return "redirect:/";
	}

	// 인기도서
	@Autowired
	BookRepo bookRepo;
	
	@GetMapping("/")
    public String getBooks(Model model) {
		List<BookEntity> books = bookRepo.findByBestSeller("y");
        model.addAttribute("books", books);

        
        return "main";
    }
	
	// 추천도서
	@Autowired
	RecomRepo recomRepo;
	

}
