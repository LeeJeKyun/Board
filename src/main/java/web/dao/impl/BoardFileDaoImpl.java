package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.JDBCTemplate;
import web.dao.face.BoardFileDao;
import web.dto.Board;
import web.dto.BoardFile;

public class BoardFileDaoImpl implements BoardFileDao {
	
	PreparedStatement ps = null;
	ResultSet rs = null;

	@Override
	public int insert(Connection conn, BoardFile boardFile) {
		int res = 0;
		String sql = "";
		sql += "INSERT INTO boardfile (fileno, boardno, originname, storedname, filesize )";
		sql += " VALUES (?, ?, ?, ?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, boardFile.getFileno());
			ps.setInt(2, boardFile.getBoardno());
			ps.setString(3, boardFile.getOriginname());
			ps.setString(4, boardFile.getStoredname());
			ps.setLong(5, boardFile.getFilesize());
			
			res=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	
	@Override
	public int selectBoardFileseqNext(Connection conn) {
		String sql = "";
		sql += "SELECT boardfile_seq.nextval FROM dual";
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
	public int delete(Connection conn, Board board) {
		System.out.println("BoardFileDaoImpl - delete() 호출");
		String sql = "";
		sql += "DELETE boardfile";
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
	
	
	
}
