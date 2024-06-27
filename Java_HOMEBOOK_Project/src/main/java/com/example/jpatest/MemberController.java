package com.example.jpatest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;
	@Autowired
	private MemberValidator memberValidator;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<Member> members = memberService.getAllMembers();
		model.addAttribute("members", members);
		return "index_member";
	}
	
	@GetMapping("/{memberId}")
	public String getMemberById(@PathVariable("memberId") String memberId, Model model) {
		Member member = memberService.getMemberById(memberId);
		if (member == null) {
			throw new MemberNotFoundException(memberId);
		}
		model.addAttribute("member", member);
		return "detail_member";
	}

	@PostMapping("/update/{memberId}")
	public String updateMember(@PathVariable("memberId") String memberId, @ModelAttribute Member member) {
		member.setMemberId(memberId);
		 // 클라이언트에서 전송한 문자열을 LocalDateTime으로 변환
	    String joinDateStr = member.getJoinDate2(); // 클라이언트에서 전송한 날짜 문자열
	    LocalDate joinDate = LocalDate.parse(joinDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    member.setJoinDate(joinDate);
		
		
		memberService.updateMember(member);
		return "redirect:/member/"; // 리다이렉트 경로 수정
	}

	@PostMapping("/delete/{memberId}")
	public String deleteMember(@PathVariable String memberId) {
		memberService.deleteMember(memberId);
		return "redirect:/member/"; // 리다이렉트 경로 수정
	}

	@GetMapping("/new")
	public String newMemberForm(Model model) {
		model.addAttribute("member", new Member());
		return "new_member";
	}

	@PostMapping("/create")
	public String createMember(@ModelAttribute Member member,BindingResult bindingResult, Model model) {
		memberValidator.validate(member, bindingResult);
	    if (bindingResult.hasErrors()) {
	    	System.out.println("MemberController createMember() 메소드 부분 에러 발생!");
	    	System.out.println(member);
	    	// 아래 변칙 코딩 
	    	if (member.getJoinDate() ==null) 
	    		member.setJoinDate(LocalDate.now());
	    }
		
		memberService.addMember(member);
		return "redirect:/member/"; // 리다이렉트 경로 수정
	}


	@GetMapping("/edit/{memberId}")
	public String editMemberForm(@PathVariable("memberId") String memberId, Model model) {
		Member member = memberService.getMemberById(memberId);
		if (member != null) { // Member 객체가 null이 아닌 경우에만 모델에 추가
			model.addAttribute("member", member);
			return "edit_member";
		} else {
			return "member_not_found"; // 이 부분을 수정하여 필요한 처리를 수행하십시오.
		}
	}
	
	@PostMapping("/edit/{memberId}")
	public String updateProc(@ModelAttribute Member member, Model model) {
	    // member 객체에서 joinDate 값을 문자열로 받아서 LocalDate로 변환
	    String joinDateStr = member.getJoinDate2();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate joinDate = LocalDate.parse(joinDateStr, formatter);
	    member.setJoinDate(joinDate);

	    memberService.updateMember(member);
	    model.addAttribute("members", memberService.getAllMembers());
	    return "redirect:/member/index";
	}
	
	

}
