package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.JDBCTemplate;
import util.Paging;
import web.dao.face.BoardDao;
import web.dto.Board;
import web.dto.BoardFile;

public class BoardDaoImpl implements BoardDao {
	
	PreparedStatement ps = null;
	ResultSet rs = null;

	@Override
	public List<Map<String, Object>> selectAll(Connection conn) {
		System.out.println("BoardDao-selectAll() 시작");
		
		List<Map<String, Object>> list = new ArrayList<>();

		String sql = "";
		sql += "SELECT BOARDNO, TITLE, USERID, CONTENT, HIT, WRITE_DATE";
		sql += " , (SELECT COUNT(*) FROM recommend R WHERE R.boardno=B.boardno) AS recommend";
		sql += " FROM board B";
		sql += " ORDER BY boardno DESC";
		
		Board board = null;
		
		try {
			
			ps=conn.prepareStatement(sql);
			
			
			rs=ps.executeQuery();
			
			System.out.println(rs.getFetchSize());
			while( rs.next() ) {
				Map<String, Object> map = new HashMap<>();
				board = new Board(rs.getInt("boardno")
								, rs.getString("title")
								, rs.getString("userid")
								, rs.getString("content")
								, rs.getInt("hit")
								, rs.getDate("write_date"));
				int recommend = rs.getInt("recommend");
				map.put("b", board);
				map.put("c", recommend);
				
				list.add(map);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		System.out.println(list.get(0).get("board"));
		
		System.out.println("BoardDao-selectAll() 끝");
		return list;
	}

	@Override
	public Board selectBoardByBoardno(Connection conn, Board viewBoard) {
		System.out.println("BoardDao-selectBoardByBoardno() 시작");
		String sql = "";
		sql += "SELECT * FROM board";
		sql += " WHERE boardno=?";
		Board board = null;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, viewBoard.getBoardno());
			
			rs=ps.executeQuery();
			while(rs.next()) {
				board = new Board(rs.getInt("boardno")
						, rs.getString("title")
						, rs.getString("userid")
						, rs.getString("content")
						, rs.getInt("hit")
						, rs.getDate("write_date"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
//		System.out.println(board);
		System.out.println("BoardDao-selectBoardByBoardno() 끝");
		return board;
	}

	@Override
	public int updateHit(Connection conn, Board board) {
		String sql="";
		sql += "UPDATE board";
		sql += " SET hit=hit+1";
		sql += " WHERE boardno=?";
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, board.getBoardno());
			
			res=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	

	@Override
	public int insert(Connection conn, Board board) {
		System.out.println("BoardDao - insert() 시작");
		String sql = "";
		sql += "INSERT INTO board ( boardno, title, userid, content, hit)";
		sql += " VALUES (?, ?, ?, ?, 0)";
		
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1,board.getBoardno());
			ps.setString(2, board.getTitle());
			ps.setString(3, board.getUserid());
			ps.setString(4, board.getContent());
			
			res=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
//		System.out.println("BoardDao - insert() 끝 : " + res);
		return res;
	}



	@Override
	public int selectBoardseqNext(Connection conn) {
		String sql = "";
		sql += "SELECT board_seq.nextval FROM dual";
		int nextval = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				nextval=rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return nextval;
	}


	@Override
	public BoardFile selectBoardFileByBoardno(Connection conn, Board board) {
		System.out.println("boardDao - selectBoardFileByBoardno() : 시작");
		String sql = "";
		sql += "SELECT * FROM boardfile";
		sql += " WHERE boardno = ?";
		sql += " ORDER BY fileno DESC";
		BoardFile boardFile = null;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, board.getBoardno());
			
			rs=ps.executeQuery();
			
			if(rs.next()) {
				boardFile = new BoardFile();
				boardFile.setFileno(rs.getInt("fileno"));
				boardFile.setBoardno(rs.getInt("boardno"));
				boardFile.setOriginname(rs.getString("originname"));
				boardFile.setStoredname(rs.getString("storedname"));
				boardFile.setFilesize(rs.getLong("filesize"));
				boardFile.setWriteDate(rs.getDate("write_date"));
			} else {
				System.out.println("BoardDao - selectBoardFileByBoardno : 출력할 파일이 없습니다");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		System.out.println("boardDao - selectBoardFileByBoardno() boardFile : " + boardFile);
		return boardFile;
	}

	@Override
	public int update(Connection conn, Board selectedBoard) {
		System.out.println("BoardDaoImpl update() : 시작");
		String sql = "";
		sql += "UPDATE board";
		sql += " SET content = ?";
		sql += " WHERE boardno = ?";
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, selectedBoard.getContent());
			ps.setInt(2, selectedBoard.getBoardno());
			
			res = ps.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		System.out.println("boardDao - update() : " + res);
		
		return res;
	}

	@Override
	public int delete(Connection conn, Board board) {
		System.out.println("BoardDao - delete() : 호출");
		
		String sql = "";
		sql += "DELETE board";
		sql += " WHERE boardno = ?";
		
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, board.getBoardno());
			
			res=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}

	@Override
	public boolean selectRecommend(Connection conn, Board recommendBoard) {
		
		String sql = "";
		sql += "SELECT userid, boardno FROM recommend";
		sql += " WHERE userid=? AND boardno=?";
		
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, recommendBoard.getUserid());
			ps.setInt(2, recommendBoard.getBoardno());
			
			rs=ps.executeQuery();
			while( rs.next() ) {
				
				return false;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return true;
	}

	@Override
	public int insertRecommend(Connection conn, Board recommendBoard) {
		
		String sql = "";
		sql += "INSERT INTO recommend (userid, boardno)";
		sql += " VALUES ( ?, ? )";
		
		int res = 0;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, recommendBoard.getUserid());
			ps.setInt(2, recommendBoard.getBoardno());
			
			res=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		
		return res;
	}

	@Override
	public int deleteRecommend(Connection conn, Board recommendBoard) {
		
		String sql = "";
		sql += "DELETE recommend";
		sql += " WHERE userid= ? AND boardno = ?";
		
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, recommendBoard.getUserid());
			ps.setInt(2, recommendBoard.getBoardno());
			
			res=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}

	@Override
	public int selectRecommendCnt(Connection conn, Board recommendBoard) {

		String sql = "";
		sql += "SELECT count(*) cnt FROM recommend";
		sql += " WHERE boardno = ?";
		
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, recommendBoard.getBoardno());
			
			rs=ps.executeQuery();
			
			while( rs.next() ) {
				
				res=rs.getInt(1);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return res;
	}

	@Override
	public int selectCntAll(Connection conn, String keyword) {
		
		String sql = "";
		sql += "SELECT count(*) FROM board";
		sql += " WHERE title LIKE ?";
		
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, "%"+keyword+"%");
			
			rs=ps.executeQuery();
			while( rs.next() ) {
				res = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return res;
	}

	@Override
	public List<Map<String, Object>> selectAll(Connection conn, Paging paging) {

		String sql ="";
		sql += "SELECT * FROM (";
		sql += " SELECT rownum rnum, S.* FROM ( SELECT";
		sql += " boardno, title, userid, content, hit, write_date ";
		sql += " , (SELECT COUNT(*) FROM recommend R WHERE R.boardno=B.boardno)";
		sql += " AS recommend";
		sql += " FROM board B WHERE title LIKE ?";
		sql += " ORDER BY boardno DESC ) S";
		sql += " )Board";
		sql += " WHERE rnum BETWEEN ? AND ?";
		
		List<Map<String, Object>> list = new ArrayList<>();
		Board board = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, "%"+paging.getSearch()+"%");
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				Map<String, Object> map = new HashMap<>();
				board = new Board(rs.getInt("boardno")
								, rs.getString("title")
								, rs.getString("userid")
								, rs.getString("content")
								, rs.getInt("hit")
								, rs.getDate("write_date"));
				int recommend = rs.getInt("recommend");
				map.put("b", board);
				map.put("c", recommend);
				
				list.add(map);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		for(Map<String,Object> map : list) {
			System.out.println(map);
		}
		
		return list;
	}
	
	
	
}
