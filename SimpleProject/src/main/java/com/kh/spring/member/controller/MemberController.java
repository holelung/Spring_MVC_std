package com.kh.spring.member.controller;


import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor // 의존성주입 생성자를 생성해주는 애노테이션
public class MemberController {
	
	// @Autowired 1번
	private final MemberService memberService;
	
//	@Autowired 2번 setter(최악의방법)
//	public void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}

	/*
 	@Autowired  // 3번 (권장: Constructor injection)
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	
//	@RequestMapping(value="login")
//	public String login(HttpServletRequest request) {
//		String id = request.getParameter("id");
//		String pw = request.getParameter("pw");
//		
//		log.info("id : {}, pw : {}", id, pw);
//		return "main_page";
//	}
//	
	/*
	@RequestMapping("login") 
	public String login(@RequestParam(value="id", defaultValue="abcde") String id,
						@RequestParam(value="pw") String pw) {
		// defaultValue : 값이 안넘어왔을 때 기본으로 대입될 값
		log.info("이렇게도 넘어오나요? id : {}, pw : {}",id, pw);
		
		
		return "main_page";
	}
	*/
	
	/*
	// RequesetParam 쓰는거 권장
	@PostMapping("login")
	public String login(String id, String pw) {
	
		log.info("이렇게는!@?!@ id : {}, pw : {}",id, pw);
	
		MemberDTO member = new MemberDTO();
		member.setMemberId(id);
		member.setMemberPw(pw);

		
		
		return "main_page";
	}
	*/
	
	
	/**
	 * 커맨드 객체 방식
	 * 
	 * 1. 매개변수 자료형에 반드시 기본생성자가 존재할 것
	 * 2. 전달되는 KEY값과 객체의 필드명이 동일할 것
	 * 3. 필드에 Setter메서드가 반드시 존재할 것
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해서 생성한 후 
	 * 내부적으로 setter메서드를 찾아서 요청 시 전달값을 해당 필드에 대입해줌
	 * (Setter Injection)
	 */
//	@PostMapping("login")
//	public String login(MemberDTO member, HttpSession session, Model model) {
//		
//		MemberDTO loginMember = memberService.login(member);
//		
//		if( loginMember != null ) { // log.info("로그인 성공!");
//			session.setAttribute("loginMember", loginMember);
//			// sendRedirect 
//			return "redirect:/";
//			
//		} else { // log.info("로그인 실패..");
//			// requestScope 에러문구를 담아서 포워딩
//			// spring에서는 Model객체를 이용해서 RequestScope
//			model.addAttribute("message", "로그인 실패!");
//			
//			return "include/error_page";
//		}
//		
//	}
	
	// 두번째 방법 변환타입 ModelAndView로 돌아가기
	@PostMapping("login")
	public ModelAndView login(MemberDTO member, HttpSession session, ModelAndView mv) {
		
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("message","로그인 실패!")
			  .setViewName("include/error_page");
		}
		
		return mv;
	}
	
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session, ModelAndView mv) {
		
		session.removeAttribute("loginMember");
		mv.setViewName("redirect:/");
		return mv;
	}

	
	@GetMapping("signup-form")
	public String signupForm() {
		
		return "member/signup-form";
	}
	
	/**
	 * 
	 * @param member has memberId, memberPw, memberName, email 
	 * 
	 * @return 성공 시  main
	 */
	@PostMapping("signup")
	public String signUp(MemberDTO member) {
		
		//log.info("멤버:{}",member);
		memberService.signUp(member);
		
		return "main_page";	
	}
	
	@GetMapping("my-page")
	public String myPage() {
		return "member/my_page";
	}
	
	@PostMapping("update-member")
	public String update(MemberDTO member, HttpSession session) {
		// 1. Controller에서는 RequestMapping 애노테이션 및 요청 시 전달값이 잘 전달되는지 확인
		// log.info("사용자가 입력한 값 : {}", member);
			
		// 2. 이번에 실행할 SQL문을 생각
		// UPDATE ==> KH_MEMBER(MEMBER_ID)
		// SessionScpe에 loginMember 의 memberId값을 넘겨주어야 겠다.
		memberService.update(member, session);
//		log.info("updateMember 값 : {}" , updateMember);
		
		// 수행에 성공했을 경우 =>
		// my_page.jsp로 이동 + 갱신된 회원의 정보 출력
		// Id를 가지고 다시 조회
		
		// 수행에 실패했을 경우 =>
		// messag를 담아서 error_page로 포워딩
		// 예외 발생 => 예외처리기로 위임
		
		return "redirect:my-page";
		
	}
	
	
	// 탈퇴구현하기
	// 비밀번호를 받는다.
	// 비밀번호가 맞는지 검증 => 예외
	// DELETE 성공햇는지 검증 => 예외
	@PostMapping("delete")
	public String delete(MemberDTO member, HttpSession session) {
		
		memberService.delete(member, session);
		return "redirect:/";
	}
	
	@ResponseBody
	@GetMapping("id-check")
	public String idCheck(@RequestParam(name="memberId") String memberId) {
		//조회 결과가 있다/없다
		// NNNNY/NNNNN
		
		
		
		
		return memberService.idCheck(memberId);
	}
	
	
	
	
	
	
	
	
}

