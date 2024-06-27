package com.example.jpatest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	private final MemberService memberService;

	public MainController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/")
	public String bulma() {
		return "bulma";
	}

	@GetMapping("/board")
	public String board() {
		return "board/boardList";
	}

	// 로그인 페이지
	@GetMapping("/login")
	public String loginForm() {
		return "security/login"; // 로그인 페이지 템플릿
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model,
			HttpSession session) {
		Member member = memberService.findByMemberId(username);

		if (member != null && member.getPassword().equals(password)) {
			// 로그인 성공
			session.setAttribute("loggedInMember", member); // 세션에 사용자 정보 저장
			model.addAttribute("message", "로그인 성공");
			System.out.println(member.getMemberId()+"님이 로그인함!");
			return "redirect:/";
		} else {
			// 로그인 실패
			model.addAttribute("error", "로그인 실패");
			return "security/login"; // 로그인 페이지 템플릿
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 세션에서 로그인 정보 삭제
		session.removeAttribute("loggedInMember");
		return "redirect:/";
	}
}





//package com.example.jpatest;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class MainController {
//	private final MemberService memberService;
//
//	public MainController(MemberService memberService) {
//		this.memberService = memberService;
//	}
//
//	@GetMapping("/")
//	public String bulma() {
//		return "bulma";
//	}
//
//	@GetMapping("/board")
//	public String board() {
//		return "board/boardList";
//	}
//
//	// 로그인 페이지
//	@GetMapping("/login")
//	public String loginForm() {
//		return "security/login"; // 로그인 페이지 템플릿
//	}
//
//	// 로그인 처리
//	@PostMapping("/login")
//	public String login(@RequestParam String username, @RequestParam String password, Model model) {
//		Member member = memberService.findByMemberId(username);
//		System.out.println(member);
//
//		if (member != null && member.getPassword().equals(password)) {
//			// 로그인 성공
//			model.addAttribute("message", "로그인 성공");
//			return "bulma"; // 로그인 성공 페이지 템플릿
//		} else {
//			// 로그인 실패
//			model.addAttribute("error", "로그인 실패");
//			return "login"; // 로그인 페이지 템플릿
//		}
//	}
//
//	// 로그아웃 페이지
//	@GetMapping("/logout")
//	public String logout() {
//		// 로그아웃 처리 로직 추가
//		return "logout"; // 로그아웃 페이지 템플릿
//	}
//
//}