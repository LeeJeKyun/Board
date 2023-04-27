package web.service.face;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.Paging;
import web.dto.Board;
import web.dto.BoardFile;
import web.dto.Comment;

public interface BoardService {

	/**
	 * Dao를 통해 받아온 데이터를 Board의 list로 만들어서 반환
	 * 
	 * @return DB의 List데이터 반환
	 */
	
	public List<Map<String, Object>> getList();

	/**
	 * req에 있는 파라미터를 담아서 Board 객체로 반환
	 * 
	 * @param req
	 * @return Board객체
	 */
	public Board getBoardno(HttpServletRequest req);

	/**
	 * 받아온 viewBoard에 있는 글 번호로 게시글 조회 와 hit++
	 * 
	 * @param viewBoard
	 * @return 조회해온 Board객체
	 */
	public Board view(Board viewBoard);
	
	/**
	 * update하기 위해 객체를 DB에서 조회해오는 메소드
	 * 
	 * @param upBoard
	 * @return - 조회해온 Board객체
	 */
	public Board toUpdate(Board upBoard);

	/**
	 * 받아온 폼필드와 파일을 각각 저장하는 메소드
	 * 
	 * @param req
	 */
	public void write(HttpServletRequest req);

	
	/**
	 * 받아온 객체를 이용해 BoardFile을 조회한 후 반환하는 메소드
	 * 
	 * @param board
	 * @return
	 */
	public BoardFile getBoardFile(Board board);

	
	/**
	 * 게시글을 수정하는 메소드
	 * 
	 * @param req
	 * @return
	 */
	public void update(HttpServletRequest req);

	/**
	 * 게시글 삭제 메소드
	 * 
	 * @param board
	 */
	public void delete(Board board);

	/**
	 * 게시글번호와 세션의 id를 받아서 객체로 반환
	 * 
	 * @param req
	 * @return
	 */
	public Board getBoard(HttpServletRequest req);

	/**
	 * 게시글의 추천수를 올리는 메소드
	 * 
	 * @param recommendBoard
	 */
	public boolean recommend(Board recommendBoard);

	/**
	 * 게시글이 추천된 적 있는지 확인하는 메소드
	 * 
	 * @param board
	 * @return 추천된 적이 있다면 false, 아니면 true
	 */
	public boolean isRecommended(Board board);

	/**
	 * 추천된횟수를 조회하여 반환하는 메소드
	 * 
	 * @param recommendBoard
	 * @return
	 */
	public int recommendCnt(Board recommendBoard);

	/**
	 * 페이징 객체를 반환하는 메소드
	 * 
	 * @param req
	 * @return req에있는 curPage와 DB에있는 총게시글수를 담은 페이징객체
	 */
	public Paging getPaging(HttpServletRequest req);

	/**
	 * Dao를 통해 받아온 데이터를 List<Map>형태로 반환
	 * 
	 * @param paging
	 * @return
	 */
	public List<Map<String, Object>> getList(Paging paging);

	/**
	 * 조회대상인 Board의 댓글을 List로 담아서 반환하는 메소드
	 * 
	 * @param board
	 * @return 댓글List객체
	 */
	public List<Comment> commentList(Board board);

	/**
	 * req객체에 있는 매개변수로 comment객체 생성 반환
	 * 
	 * @param req
	 * @return - comment객체 반환
	 */
	public Comment getComment(HttpServletRequest req);

	/**
	 * comment객체를 DB에 삽입하는 메소드
	 * 
	 * @param insertComment
	 */
	public void commentInsert(Comment insertComment);

	/**
	 * req에 있는 commentno을 기준으로 DB의 코멘트를 삭제하는 메소드
	 * 
	 * @param req
	 */
	public void commentDelete(HttpServletRequest req);



}
