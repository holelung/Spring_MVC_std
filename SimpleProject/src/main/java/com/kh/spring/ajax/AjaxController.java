package com.kh.spring.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AjaxController {

	@GetMapping("ajax")
	public String forward() {
		
		
		return "ajax/ajax";
	}
	
	/*
	 * 내가반환하는 String 타입의 값이 View가 아니면
	 * MessageConverter로 이동
	 * 
	 */
	@ResponseBody
	@GetMapping(value = "test", produces = "text/html; charset=UTF-8")
	public String ajaxReturn(@RequestParam(name="input") String value) {
		
		log.info("ajax 요청을 통해 넘어온 VALUE값 : {}", value);
		
		String returnValue = value.equals("admin") ? "아이디 있어용" : "아이디 없어요!@";
		
		return returnValue;
	}

	private final BoardService boardService;
	
	@Autowired
	public AjaxController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@ResponseBody  
	@GetMapping(value = "study", produces ="application/json; charset=UTF-8")
	public ResponseEntity<BoardDTO> ajaxStudy(@RequestParam("replyNo") int boardNo) {
		
		return ResponseEntity.ok(boardService.selectBoard(boardNo)); 
	}
}
