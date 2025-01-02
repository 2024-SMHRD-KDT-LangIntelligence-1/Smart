package com.smhrd.basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smhrd.basic.entity.BookEntity;
import com.smhrd.basic.entity.LibraryEntity;
import com.smhrd.basic.entity.MemberEntity;
import com.smhrd.basic.model.MemberVO;
import com.smhrd.basic.repository.BookRepo;
import com.smhrd.basic.repository.LibraryRepo;
import com.smhrd.basic.repository.MemberRepo;
import com.smhrd.basic.repository.RecomRepo;
import com.smhrd.basic.repository.RetentionRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

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
	// 추천도서
	@Autowired
	RecomRepo recomRepo;
	// 지점 조회
	@Autowired
	LibraryRepo libraryRepo;
	//
	@Autowired
	RetentionRepo retentionRepo;

	@GetMapping("/")
	public String getBooks(Model model) {

		// 인기도서
		List<BookEntity> bestBooks = bookRepo.findByBestSeller("y");
		model.addAttribute("bestBooks", bestBooks);

		// 지역 선택
		List<String> library = libraryRepo.findRegions();
		model.addAttribute("library1", library);

      return "main";
   }
   
   @GetMapping("/book/{bookIdx}")
   @ResponseBody
   public BookEntity getBookDetails(@PathVariable Long bookIdx) {
	   return bookRepo.findById(bookIdx).orElse(null);
   }

	// 지역을 기준으로 도서관 반환
	@GetMapping("/getLibrariesByRegion")
	@ResponseBody
	public List<LibraryEntity> getLibrariesByRegion(@RequestParam String regionNm) {
		return libraryRepo.findByRegionNm(regionNm);
	}

	// 도서관 ID로 해당 도서관의 보유 도서 목록 반환
	@GetMapping("/library/{libIdx}/books")
	@ResponseBody
	public List<BookEntity> getBooksByLibraryId(@PathVariable Integer libIdx) {
		return bookRepo.findBooksByLibraryId(libIdx);
	}

}
