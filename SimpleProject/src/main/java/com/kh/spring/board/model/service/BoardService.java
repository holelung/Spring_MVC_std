package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

public interface BoardService {

	// 게시글 작성(파일첨부)
	void insertBoard(BoardDTO board, MultipartFile file, 
			HttpSession session); 
	/*
	 * insertBoard();
	 * save();
	 */
	
	
	
	// 게시글 목록조회
	Map<String, Object> selectBoardList(int currentPage);
	
	
	// 게시글 상세보기(댓글도 같이 조회) --> 새로운 머싯는 기술 써야지
	BoardDTO selectBoard(int boardNo); 
	/*
	 * selectBoard();
	 * findByXXX();
	 */
	
	// 게시글 수정
	BoardDTO updateBoard(BoardDTO board, MultipartFile file);
	
	// 게시글 삭제(delete인척 하고 없데이트 할것 STATUS컬럼값 N으로 바꿀껏)
	void deleteBoard(int boardNo);
	
	
	//---------1절
	
	// 댓글작성
	
	// 게시글 검색기능
	
	
}
