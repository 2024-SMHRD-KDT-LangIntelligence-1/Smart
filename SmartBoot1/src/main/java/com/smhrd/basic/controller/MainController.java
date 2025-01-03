package com.smhrd.basic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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
		
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        String flaskUrl = "http://localhost:5000/user_info"; // Flask 앱의 URL 및 엔드포인트
	        Map<String, Object> data = new HashMap<>();
	        data.put("id", enti.getId());
	        data.put("birthdate", enti.getBirthdate());
	        data.put("gender", enti.getGender());
	        data.put("job", enti.getJob());
	        data.put("preference", enti.getPreference());
	        data.put("mood", enti.getMood());
	        data.put("join_dt", enti.getJoin_dt().toString());

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<Map<String, Object>> request = new HttpEntity<>(data, headers);

	        restTemplate.postForObject(flaskUrl, request, String.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return "redirect:/";
	}

	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
	    MemberEntity member = (MemberEntity) session.getAttribute("member");
	    
	    if (member != null) {
	        try {
	            RestTemplate restTemplate = new RestTemplate();
	            String flaskUrl = "http://localhost:5000/logout"; // Flask 앱의 로그아웃 엔드포인트
	            
	            // 요청에 전송할 데이터 생성
	            Map<String, Object> data = new HashMap<>();
	            data.put("id", member.getId()); // 사용자 ID를 전송
	            
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_JSON);
	            HttpEntity<Map<String, Object>> request = new HttpEntity<>(data, headers);
	            
	            // Flask 로그아웃 엔드포인트 호출
	            restTemplate.postForObject(flaskUrl, request, String.class);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		
		session.removeAttribute("member");
		return "redirect:/";
	}
	//회원 정보 수정
	 @Autowired
	    private MemberRepo memberRepo; // 또는 MemberService와 연결

	 @PostMapping("/update")
	 public ResponseEntity<?> updateMemberPreferences(@RequestBody Map<String, Object> payload) {
	     try {
	         // JSON에서 데이터 추출
	         String id = (String) payload.get("id");
	         List<String> preferenceList = (List<String>) payload.get("preference");
	         String step = String.valueOf(payload.get("step"));

	         // 쉼표로 구분된 preference 문자열 생성
	         String preference = String.join(",", preferenceList);

	         System.out.println("Received ID: " + id);
	         System.out.println("Received Preferences: " + preference);
	         System.out.println("Step: " + step);

	         // ID로 회원 검색
	         Optional<MemberEntity> optionalMember = memberRepo.findById(id);
	         if (optionalMember.isPresent()) {
	             MemberEntity member = optionalMember.get();
	             System.out.println("Existing Preference: " + member.getPreference());

	             // DB 업데이트
	             member.setPreference(preference);
	             memberRepo.save(member);

	             return ResponseEntity.ok(Map.of("success", true));
	         } else {
	             return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                     .body(Map.of("success", false, "message", "회원 정보 없음"));
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                 .body(Map.of("success", false, "message", e.getMessage()));
	     }
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

		// 추천도서
//		List<BookEntity> bestBook1s = recomRepo.findRecommendedBooks("y");
//		model.addAttribute("recomBooks", recomBooks);

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
