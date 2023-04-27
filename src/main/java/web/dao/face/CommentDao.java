package web.dao.face;

import java.sql.Connection;
import java.util.List;

import web.dto.Board;
import web.dto.Comment;

public interface CommentDao {

	/**
	 * 받아온 board객체에 있는 댓글을 select해오는 메소드
	 * 
	 * @param conn
	 * @param board
	 * @return - 댓글의 List객체
	 */
	public List<Comment> selectCommentByBoardNo(Connection conn, Board board);

	/**
	 * 다음 댓글의 번호를 받아오는 메소드
	 * 
	 * @return 다음댓글번호
	 */
	public int selectNextCommentNo(Connection conn);

	/**
	 * 입력한 댓글을 DB에 insert하는 메소드
	 * 
	 * @param conn
	 * @param insertComment
	 * @return - 실행결과(0 or 1)
	 */
	public int insertComment(Connection conn, Comment insertComment);

	/**
	 * 전달받은 comment객체를 기준으로 DB의 정보를 delete하는 메소드
	 * 
	 * @param conn
	 * @param comment
	 */
	public int deleteComment(Connection conn, Comment comment);

}
