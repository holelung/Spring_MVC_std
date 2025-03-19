package com.kh.spring.member.model.service;



import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberDAO memberDao;
	private final SqlSessionTemplate sqlSession;
	private final BCryptPasswordEncoder passwordEncoder;
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
		
		validator.validatedLoginMember(member);
		
		
		// 1. Table에 아이디 존재해야함
		// 2. 비밀번호가 일치해야됨
		// 3. 둘다 통과하면 정상적으로 조회
		
		MemberDTO loginMember =memberDao.login(sqlSession, member);
		
		// 1. loginMember == null -> 아이디가 존재하지 않다
		if(loginMember == null) {
			throw new MemberNotFoundException("존재하지 않는 아이디 입니다.");
		}
		// 2. 비밀번호 검증 과정
		if(passwordEncoder.matches(
				member.getMemberPw(), loginMember.getMemberPw())) {
			return loginMember;
		} else {
			
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다."); 
		}
	}

	@Override
	public void signUp(MemberDTO member) {
		
		if(member == null || 
				   member.getMemberId() == null || 
				   member.getMemberId().trim().isEmpty() ||
				   member.getMemberPw() == null ||
				   member.getMemberPw().trim().isEmpty()) {
					return;
				}
		int result = memberDao.checkId(sqlSession, member.getMemberId());
		if(result > 0) {
			return;
		}
		// 암호화 하는법
		
		//log.info("평문을 암호문으로 바꾼것 : {}", passwordEncoder.encode(member.getMemberPw()));
		// INSERT 해야지~ 
		String encPwd = passwordEncoder.encode(member.getMemberPw());
		member.setMemberPw(encPwd);
		int consequence = memberDao.signUp(sqlSession, member);
		
		if(consequence > 0) {
			return;
		}else {
			return;
		}
				
	}

	@Override
	public MemberDTO update(MemberDTO member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(MemberDTO member) {
		// TODO Auto-generated method stub
		return 0;
	}

	 
}
