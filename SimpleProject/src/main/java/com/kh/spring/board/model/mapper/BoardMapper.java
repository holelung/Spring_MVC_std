package com.kh.spring.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.reply.model.dto.ReplyDTO;

@Mapper
public interface BoardMapper {
	
	
	int insertBoard(BoardDTO board);
	
	@Select("SELECT COUNT(*) FROM TB_SPRING_BOARD WHERE STATUS = 'Y'")
	int selectTotalCount();
	
	List<BoardDTO> selectBoardList(RowBounds rowBounds);

	BoardDTO selectBoard(int boardNo);
	
	int deleteBoard(int boardNo);

	// --- 여기까지 1절
	
	BoardDTO selectBoardAndReply(int boardNo);
	
	int updateBoard(BoardDTO board);
	
	List<ReplyDTO> selectReply(int boardNo); 
}
