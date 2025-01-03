package com.smhrd.basic.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smhrd.basic.DTO.BookWithLibraryNameDTO;
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
	public List<BookWithLibraryNameDTO> getBooksByLibraryId(@PathVariable Integer libIdx) {
	    List<Object[]> results = bookRepo.findBooksByLibraryIdWithLibraryName(libIdx);

	    // 반환할 리스트 객체에 도서관 이름과 도서 정보를 포함
	    List<BookWithLibraryNameDTO> booksWithLibrary = new ArrayList<>();
	    for (Object[] result : results) {
	        BookEntity book = (BookEntity) result[0];
	        String libraryName = (String) result[1];
	        booksWithLibrary.add(new BookWithLibraryNameDTO(book, libraryName));
	    }

	    return booksWithLibrary;
	}
	// 도서 검색 (도서관명 포함)
	@GetMapping("/searchBooks")
	@ResponseBody
	public List<Object[]> searchBooks(@RequestParam String keyword) {
		return retentionRepo.findBooksWithLibraryByKeyword(keyword);
	}
	
	@GetMapping("/mypage")
	   public String showMypage(HttpSession session, Model model) {
	       MemberEntity currentMember = (MemberEntity) session.getAttribute("member");

	       if (currentMember != null) {
	           MemberEntity member = repo.findById(currentMember.getId()).orElse(null);
	           model.addAttribute("member", member);
	       } else {
	           model.addAttribute("error", "로그인이 필요합니다.");
	       }
	       
	       return "mypage";
	   }
	   
	   @PostMapping("/mypage/update")
	    public String updateMemberInfo(MemberEntity updatedMember, HttpSession session, Model model) {
	        // 현재 세션에서 사용자 정보 가져오기
	        MemberEntity currentMember = (MemberEntity) session.getAttribute("member");

	        if (currentMember != null) {
	            // DB에서 현재 사용자의 정보를 가져옵니다.
	            MemberEntity existingMember = repo.findById(currentMember.getId()).orElse(null);

	            if (existingMember != null) {
	                // 사용자 정보를 업데이트
	                existingMember.setBirthdate(updatedMember.getBirthdate());
	                existingMember.setGender(updatedMember.getGender());
	                existingMember.setJob(updatedMember.getJob());
	                existingMember.setPreference(updatedMember.getPreference());
	                existingMember.setMood(updatedMember.getMood());
	                
	                // 변경된 정보를 저장
	                repo.save(existingMember);

	                // 세션 정보 업데이트
	                session.setAttribute("member", existingMember);

	                model.addAttribute("success", "회원정보가 성공적으로 수정되었습니다.");
	            } else {
	                model.addAttribute("error", "사용자 정보를 찾을 수 없습니다.");
	            }
	        } else {
	            model.addAttribute("error", "로그인이 필요합니다.");
	        }
	        return "redirect:/mypage"; // 수정 결과를 마이페이지로 반환
	    }}


