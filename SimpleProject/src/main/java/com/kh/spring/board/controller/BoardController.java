package com.kh.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.exception.InvalidParameterException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	
	@GetMapping("boards")
	public String toBoardList(@RequestParam(name="page", defaultValue="1") int page,
							  Model model) {
		// 계획
		// 한 페이지에 5개 
		// 총 게시글 개수 == SELECT COUNT(*) FROM TB_SPRING_BOARD WHERE STATUS = 'Y'
		// 페이지 버튼 5개
		// 
		
		if(page<1) {
			throw new InvalidParameterException("어디 감히!");
			
		}
		Map<String, Object> map = boardService.selectBoardList(page);
		
		
		model.addAttribute("map",map);
		
		return "board/board_list";
	}
	
	@GetMapping("form.bo")
	public String goToForm() {
		return "board/insert_board";
	}
	
	@PostMapping("boards")
	public ModelAndView newBoard(ModelAndView mv
								, BoardDTO board
								, MultipartFile upfile
								, HttpSession session) {
		//log.info("게시글 정보: {}, 파일 정보 : {}", board, upfile);
		//첨부파일 존재유무
		// => 차이점 => MultipartFile타입의 filename 필드값으로 확인을 하겠다.
		
		//Insert INTO TB_SPRING_BOARD(BOARD_TITEL, BOARD_CONTENT, BOARD_WRITER)
		// 	         VALUES (#{boardTitle}, #{boardContent, #{boarderWireter}, #{changeName}	
		
		// 1. 권한있는 요청인가
		// 2. 값이 유효성이 있는가
		// 3. 전달된 파일이 존재할 경우  => 파일명 수정 서버에 올리고 BoardDTO의 changeName필ㄷ드에 을 대입
		boardService.insertBoard(board, upfile, session);
		
		mv.setViewName("redirect:boards");
		session.setAttribute("message", "게시글 등록 성공!");
		return mv;
		
	}
	
	@GetMapping("board")
	public ModelAndView goBoard(@RequestParam(name="boardNo") int boardNo,
								@RequestParam(name="page", defaultValue="1") int page,
								ModelAndView mv) {
		//log.info("게시글 번호: {}",boardNo);
		//1 절
		if(boardNo < 1) {
			throw new InvalidParameterException("비정상적인 접근입니다!@");
		}
		BoardDTO board = boardService.selectBoard(boardNo);
		mv.addObject("page",page);
		mv.addObject("board",board);
		mv.setViewName("board/board_detail");
		return mv;
	}
	
	@GetMapping("boardUpdate")
	public ModelAndView goUpdate(@RequestParam(name="boardNo") int boardNo,
								 @RequestParam(name="page", defaultValue="1") int page,
								 ModelAndView mv) {
		// 아  수정폼이 없네 귀차낭!~
		
		return mv;
	}
	
	
	@GetMapping("boardDelete")
	public ModelAndView doDelete(@RequestParam(name="boardNo") int boardNo,
								 HttpSession session,
								 ModelAndView mv) {
		if( boardNo <1) {
			throw new InvalidParameterException("비정상적인 접근입니다!");
		}
		if( session == null ) {
			throw new NullPointerException("로그인이나 하시죠");
		}
		// 글쓴이 맞는지도 확인해야되는데..ㅎㅎ
		log.info("보드본허: {}", boardNo);
		
		boardService.deleteBoard(boardNo);
		
		mv.setViewName("redirect:boards");
		session.setAttribute("message", "게시글 삭제 성공!");
		return mv;
	}
	
	@GetMapping("search")
	public ModelAndView doSearch(@RequestParam(name="condition") String condition,
								 @RequestParam(name="keyword") String keyword,
								 @RequestParam(name="page", defaultValue="1") int page,
								 ModelAndView mv) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("currentPage", String.valueOf(page));
		
		Map<String, Object> list = boardService.doSearch(map);
		list.put("condition", condition);
		list.put("keyword", keyword);
		
		mv.addObject("map", list).setViewName("board/board_list");
		
		return mv;
	}
	
}
