package com.kh.spring.member.model.service;

import javax.servlet.http.HttpSession;

import com.kh.spring.member.model.dto.MemberDTO;

public interface MemberService { // 계약서 

	// 로그인
	MemberDTO login(MemberDTO member);
	
	// 회원가입
	// 좋은 방법 : 가입된 회원의 정보를 반환해준다.(Hibernate)
	// 일반적인 방법:	정수값을 반환하거나 
	//            	값을 반환하지 않는다. (MyBatis)
	void signUp(MemberDTO member);
	
	
	// 회원정보수정
	void update(MemberDTO member, HttpSession session);
	

	// 회원 탈퇴
	void delete(MemberDTO member, HttpSession session);

	// 1절끝
	
	// 아이디 중복체크
	String idCheck(String memberid);
	
	// 2절
	
	// 이메일인증... 시간없는데.. ㅠㅠ
	
	// 3절

	
}
