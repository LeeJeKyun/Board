package web.dao.face;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import web.dto.Board;
import web.dto.BoardFile;

public interface BoardDao {
	
	/**
	 * DB에 있는 게시판 정보를 모두 가져오는 메소드
	 * 
	 * @param conn - DB 연결객체
	 * @return - DB에 있는 정보를 모두 가지고 있는 List객체
	 */
	public List<Map<String, Object>> selectAll(Connection conn);

	
	/**
	 * 보드넘버로 DB조회하는 메소드
	 * 
	 * @param conn
	 * @param viewBoard
	 * @return board객체
	 */
	public Board selectBoardByBoardno(Connection conn, Board viewBoard);

	/**
	 * 보드넘버로 DB를 조회하고 Hit을 1 더하는 메소드
	 * 
	 * @param conn
	 * @param Board
	 * @return - 제대로 실행되었는지 확인하는 int값
	 */
	public int updateHit(Connection conn, Board Board);

	/**
	 * 작성한 게시글의 Board객체를 DB에 insert하는 메소드
	 * 
	 * @param conn
	 * @param board
	 */
	public int insert(Connection conn, Board board);

	/**
	 * 작성한 게시글의 BoarFile객체를 DB에 insert하는 메소드
	 * 
	 * @param conn
	 * @param boardFile
	 * @return
	 */

	
	/**
	 * 다음 게시글의 boardno nextval을 가져오는 메소드
	 * 
	 * @param conn
	 * @return board_seq.nextval
	 */
	public int selectBoardseqNext(Connection conn);
	
	/**
	 * 조회된 Board를 통해 BoardFilew정보를 가져오는 메소드
	 * 
	 * @param conn
	 * @param board
	 * @return BoardFile
	 */
	
	public BoardFile selectBoardFileByBoardno(Connection conn, Board board);


	/**
	 * 선택된 보드를 update하는 메소드
	 * 
	 * @param conn
	 * @param selectedBoard
	 * @return - 성공했는지 0/1로 구분, 트랜젝션에 사용한다.
	 */
	public int update(Connection conn, Board selectedBoard);


	/**
	 * 선택된 보드를 DELETE하는 메소드
	 * 
	 * @param conn
	 * @param board
	 * @return - 성공했는지를 구분, 트랜젝션에 사용한다.
	 */
	public int delete(Connection conn, Board board);

	/**
	 * 동일한 boardno과 userid로 추천된 기록이 있는지 확인한다
	 * 
	 * @param conn
	 * @param recommendBoard
	 * @return 있을경우 true, 없을경우 false
	 */
	public boolean selectRecommend(Connection conn, Board recommendBoard);

	/**
	 * 추천을 insert하는 메소드
	 * 
	 * @param conn
	 * @param recommendBoard
	 * @return 성공여부 0 or 1
	 */
	public int insertRecommend(Connection conn, Board recommendBoard);

	/**
	 * 추천을 delete하는 메소드
	 * 
	 * @param conn
	 * @param recommendBoard
	 * @return 성공여부 0 or 1
	 */
	public int deleteRecommend(Connection conn, Board recommendBoard);


	/**
	 * 게시글에 대한 추천수를 조회하여 반환하는 메소드
	 * 
	 * @param conn
	 * @param recommendBoard
	 * @return 추천수
	 */
	public int selectRecommendCnt(Connection conn, Board recommendBoard);

	
	
}
