package com.kh.spring.member.model.service;

import org.springframework.stereotype.Component;

import com.kh.spring.exception.DuplicateIdException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {

	private final MemberMapper mapper;
	
	private void validatedLength(MemberDTO member) {
		if(member.getMemberId().length() > 10 ) {
			throw new TooLargeValueException("아이디가 너무 깁니다. 10자 이내로 작성해주세요");
		}
	}
	
	private void validatedValue(MemberDTO member) {
		if(member == null || 
				   member.getMemberId() == null || 
				   member.getMemberId().trim().isEmpty() ||
				   member.getMemberPw() == null ||
				   member.getMemberPw().trim().isEmpty()) {
			throw new InvalidParameterException("인자가 비었다.");
		}
	}
	
	private void validateUpdateValue(MemberDTO member) {
		if(member == null ||
				 member.getMemberId() == null ||
				 member.getMemberId().trim().isEmpty() ||
				 member.getMemberName() == null ||
				 member.getMemberName().trim().isEmpty()) {
			// email 검증도 할거임
			throw new InvalidParameterException("빈칸이 있어요");
		}
	}
	
	private void validateDuplicateId(MemberDTO member) {
		MemberDTO existingMember = mapper.login(member);
		if(existingMember != null && member.getMemberId().equals(existingMember.equals(existingMember.getMemberId()))) {
			
			throw new DuplicateIdException("이미 존재하는 회원아이디 입니다.");
		}
	}
	
	public void validatedLoginMember(MemberDTO member) {
		validatedLength(member);
		validatedValue(member);
	}
	
	public void validateJoinMember(MemberDTO member) {
		validatedLoginMember(member);
		validateDuplicateId(member);
		
	}
	
	public void validatedUpdateMember(MemberDTO member) {
		validateUpdateValue(member);
		validateMemberExists(member);
		
	}
	
	
	
	public MemberDTO validateMemberExists(MemberDTO member) {
		MemberDTO loginMember = mapper.login(member);
		if(loginMember !=  null) {
			return loginMember;
		}
		throw new MemberNotFoundException("존재하지 않는 아이디 입니다.");
	}
	
	
	
	

}
