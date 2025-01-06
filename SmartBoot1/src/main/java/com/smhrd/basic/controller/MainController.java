package com.smhrd.basic.controller;

import java.util.ArrayList;
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

	// 추천도서
	@Autowired
	RecomRepo recomRepo;

	// 회원가입
	@PostMapping("member/join.do")
	public String join(MemberVO vo) {
		MemberEntity en = new MemberEntity(vo);
		repo.save(en);
		return "redirect:/";
	}

	// 로그인
	@PostMapping("member/login.do")
	public String login(HttpSession session, @RequestParam("id") String id, @RequestParam("pw") String pw) {
	    MemberEntity enti = repo.findByIdAndPw(id, pw);
	    if (enti != null) {
	        session.setAttribute("member", enti);
	        session.setAttribute("userId", enti.getId()); // 세션에 userId 저장
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
	    } else {
	        // 로그인 실패 처리 (예: 에러 메시지 표시)
	        return "redirect:/member/login.do?error=true";
	    }
	}
	
	@GetMapping("member/login.do")
	public String loginPage() {
	    return "login"; // 로그인 페이지 템플릿 이름
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

	// 회원 정보 수정
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
	// 지점 조회
	@Autowired
	LibraryRepo libraryRepo;
	@Autowired
	RetentionRepo retentionRepo;

//	@GetMapping("/")
//	public String getBooks(Model model, String id) {
//
//		// 인기도서
//		List<BookEntity> bestBooks = bookRepo.findByBestSeller("y");
//		model.addAttribute("bestBooks", bestBooks);
//
//		// 지역 선택
//		List<String> library = libraryRepo.findRegions();
//		model.addAttribute("library1", library);
//
//		return "main";
//	}
//	
//	
//	@GetMapping("/{id}")
//	public String getRecommendedBooksByUserId(@PathVariable String id, Model model) {
//	    // 추천 도서를 ID로 조회
//	    List<BookEntity> recommendedBooks = recomRepo.findRecommendedBooksByUserId(id);
//	    
//	    // 추천 도서를 모델에 담아서 HTML로 전달
//	    model.addAttribute("recommendedBooks", recommendedBooks);
//
//	    // 추천 도서 목록을 보여줄 HTML 페이지로 리턴
//	    return "recommendation";  // recommendation.html로 이동
//	}
	
	@GetMapping("/")
	public String getBooks(Model model, HttpSession session) {
	    // 인기도서 조회
	    List<BookEntity> bestBooks = bookRepo.findByBestSeller("y");
	    model.addAttribute("bestBooks", bestBooks);

	    // 사용자 ID 가져오기 (로그인된 경우)
	    String id = (String) session.getAttribute("userId");
	    if(id != null && !id.isEmpty()){
	        // 추천도서 조회
	        List<BookEntity> recommendedBooks = recomRepo.findRecommendedBooksByUserId(id);
	        model.addAttribute("recommendedBooks", recommendedBooks);
	    }

	    // 지역 선택 조회
	    List<String> library = libraryRepo.findRegions();
	    model.addAttribute("library1", library);

	    return "main"; // main.html 또는 main.jsp 등의 템플릿 파일
	}

	@GetMapping("/user/{id}")
	public String getBooksForUser(@PathVariable String id, Model model, HttpSession session){
	    // 인기도서 조회
	    List<BookEntity> bestBooks = bookRepo.findByBestSeller("y");
	    model.addAttribute("bestBooks", bestBooks);

	    // 세션에서 사용자 ID 확인
	    String sessionUserId = (String) session.getAttribute("id");
	    if(sessionUserId != null && sessionUserId.equals(id)){
	        // 추천도서 조회
	        List<BookEntity> recommendedBooks = recomRepo.findRecommendedBooksByUserId(id);
	        model.addAttribute("recommendedBooks", recommendedBooks);
	    } else {
	        // 세션의 사용자 ID와 요청된 ID가 일치하지 않을 때 처리
	        return "redirect:/member/login.do";
	    }

	    // 지역 선택 조회
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
	public List<Object[]> searchBooks(@RequestParam(required = false) String region,
			@RequestParam(required = false) Integer libIdx, @RequestParam String keyword) {
		if (libIdx != null) {
			// 도서관을 선택한 경우
			return retentionRepo.findBooksWithLibraryByLibraryId(libIdx, keyword);
		} else if (region != null && !region.isEmpty()) {
			// 지역만 선택한 경우
			return retentionRepo.findBooksWithLibraryByRegion(region, keyword);
		} else {
			// 지역이나 도서관을 선택하지 않은 경우 (전체 검색)
			return retentionRepo.findBooksWithLibraryByKeyword(keyword);
		}
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
	}
	
}
