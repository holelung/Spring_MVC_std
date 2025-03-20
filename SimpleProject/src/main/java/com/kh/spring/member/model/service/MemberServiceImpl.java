package com.kh.spring.member.model.service;




import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

//	private final MemberDAO memberDao;
//	private final SqlSessionTemplate sqlSession;
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	private final MemberValidator validator;
	/* 이거 롬복으로 대체된당!@!
	@Autowired
	public MemberServiceImpl(MemberDAO memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
	*/
	
	
	
	@Override
	public MemberDTO login(MemberDTO member) {
		// 아이디가 10자넘으면 안돼
		// 아이디/비밀번호 : 빈 문자열 또는 null이면 안되겠네?
		
		
		
		
		// 1. Table에 아이디 존재해야함
		// 2. 비밀번호가 일치해야됨
		// 3. 둘다 통과하면 정상적으로 조회
		/*
		MemberDTO loginMember = memberMapper.login(member);
		
		// 1. loginMember == null -> 아이디가 존재하지 않다
		if(loginMember == null) {
			throw new MemberNotFoundException("존재하지 않는 아이디 입니다.");
		}
		*/
		
		validator.validatedLoginMember(member);
		MemberDTO loginMember = validator.validateMemberExists(member);
		
		// 2. 비밀번호 검증 과정(얘도 validator 로 빼부리)
		if(passwordEncoder.matches(
				member.getMemberPw(), loginMember.getMemberPw())) {
			return loginMember;
		} else {
			
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다."); 
		}
		
	}

	@Override
	public void signUp(MemberDTO member) {
		/*
		if(member == null || 
				   member.getMemberId() == null || 
				   member.getMemberId().trim().isEmpty() ||
				   member.getMemberPw() == null ||
				   member.getMemberPw().trim().isEmpty()) {
					return;
				}
				*/
//		validator.validatedLoginMember(member);
		
//		MemberDTO loginMember = memberMapper.login(member);
		
//		if(result > 0) {
//			return;
//		}
		// 암호화 하는법
		
		//log.info("평문을 암호문으로 바꾼것 : {}", passwordEncoder.encode(member.getMemberPw()));
		
		// INSERT 해야지~ 
		validator.validateJoinMember(member);
		member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
		memberMapper.signUp(member);
		
		/*
		if(consequence > 0) {
			return;
		}else {
			return;
		}
		*/
				
	}

	
	
	@Override
	public void update(MemberDTO member, HttpSession session) {
		MemberDTO sessionMember = (MemberDTO)session.getAttribute("loginMember");
		// 사용자 검증
		if(sessionMember == null) {
			throw new NullPointerException("로그인이 안되있다");
		}
		
		if( !member.getMemberId().equals(sessionMember.getMemberId())) {
			throw new AuthenticationException("권한없는 접근");
		}
		
		
		// 입력값 검증 & 존재하는 아이디인지 검증
		validator.validatedUpdateMember(member);
		//validator.validateMemberExists(member);
			
		// SQL 수행결과 검증
		int result = memberMapper.update(member);
		if(result != 1) {
			throw new AuthenticationException("뭔진 모르겠는데 문제가 일어났어요. 다시 시도해주세요.");
		}
		
		sessionMember.setMemberName(member.getMemberName());
		sessionMember.setEmail(member.getEmail());
		
	}

	@Override
	public void delete(MemberDTO member, HttpSession session) {
		MemberDTO sessionMember = (MemberDTO)session.getAttribute("loginMember");
		
		if(sessionMember == null) {
			throw new NullPointerException("로그인이 안되있습니다.");
		}
		
		member.setMemberId(sessionMember.getMemberId());
		validator.validateMemberExists(member);
		
		int result = memberMapper.delete(member);
		if(result != 1) {
			throw new AuthenticationException("에러 발생. 다시 시도해주세요.");
		}
		
		session.removeAttribute("loginMember");
		session.setAttribute("message", "회원탈퇴가 완료되었습니다.");
		
		
	}

	 
}
