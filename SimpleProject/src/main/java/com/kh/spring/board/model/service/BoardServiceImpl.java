package com.kh.spring.board.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.mapper.BoardMapper;
import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.reply.model.dto.ReplyDTO;
import com.kh.spring.util.model.dto.PageInfo;
import static com.kh.spring.util.template.Pagination.getPageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	
	private final BoardMapper boardMapper;
	
	@Override
	public void insertBoard(BoardDTO board, MultipartFile file, HttpSession session) {
		
		// 1. 권한 체크
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		if(loginMember != null && !loginMember.getMemberId().equals(board.getBoardWriter())) {
			throw new AuthenticationException("권한 없는 접근입니다.");
		}
		
		// 2. 유효성검사
		if(board.getBoardTitle() == null || board.getBoardTitle().trim().isEmpty() ||
		   board.getBoardContent() == null || board.getBoardContent().trim().isEmpty() ||
		   board.getBoardWriter() == null || board.getBoardWriter().trim().isEmpty()) {
			throw new InvalidParameterException("유효하지 않은 요청입니다.");
		}
		
		// 2-2)
		
		// 3) 파일 유무 체크 // 이름 바꾸기 + 저장 
		if( !file.getOriginalFilename().isEmpty() ) {

			// 이름 바꾸기
			// KH_현재시간+랜덤숫자+원본파일확장자
		
			StringBuilder sb = new StringBuilder();
			sb.append("KH_");
			
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			sb.append(currentTime);
			sb.append("_");
			
			int random = (int)(Math.random() * 900) + 100;
			sb.append(random);
			
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			sb.append(ext);
			
			// log.info("바뀐파일명 : {}", sb);
			
			ServletContext application = session.getServletContext();
			
			String savePath = application.getRealPath("/resources/upload_files/");
		
			try {
				file.transferTo(new File(savePath + sb.toString()));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			board.setChangeName("/spring/resources/upload_files/" + sb.toString());
		}
		
		
		boardMapper.insertBoard(board);
		
	}
	

	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		
		List<BoardDTO> boards = new ArrayList<BoardDTO>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		int count = boardMapper.selectTotalCount();
		PageInfo pi = getPageInfo(count, currentPage, 5, 5);
		
		if(count != 0) {
			
			RowBounds rb = new RowBounds((currentPage -1 ) * 5, 5);
			boards = boardMapper.selectBoardList(rb);
		}
		
		map.put("boards", boards);
		map.put("pageInfo", pi);

		return map;
	}

	@Override
	public BoardDTO selectBoard(int boardNo) {
//		// 1절
//		BoardDTO board = boardMapper.selectBoard(boardNo);
//		
//		// 2절
//		List<ReplyDTO> replyList = boardMapper.selectReply(boardNo);
//		board.setReplyList(replyList);

		// 3절
		BoardDTO board = boardMapper.selectBoardAndReply(boardNo);
		if(board == null) {
			throw new InvalidParameterException("엥? 없는데요?");
		}		
		
		return board;
	}

	@Override
	public BoardDTO updateBoard(BoardDTO board, MultipartFile file) {
		
		return null;
	}

	@Override
	public void deleteBoard(int boardNo) {
		
		int result = boardMapper.deleteBoard(boardNo);
		if(result != 1) {
			throw new AuthenticationException("뭔진 모르겠는데 문제가 일어났어요. 다시 시도해주세요.");
		}
	}

}
