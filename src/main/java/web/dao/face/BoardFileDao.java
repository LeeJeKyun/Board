package web.dao.face;

import java.sql.Connection;

import web.dto.Board;
import web.dto.BoardFile;

public interface BoardFileDao {

	public int insert(Connection conn, BoardFile boardFile);

	
	/**
	 * 다음 게시글의 boardfileno nextval을 가져오는 메소드
	 * 
	 * @param conn
	 * @return
	 */
	public int selectBoardFileseqNext(Connection conn);


	/**
	 * 선택한 게시글의 boardno을 이용해 해당 boardno의 게시글을 삭제하는 메소드
	 * 
	 * @param conn
	 * @param board - boardno을 포함하고 있는 board객체
	 */
	public int delete(Connection conn, Board board);

	
	
}
