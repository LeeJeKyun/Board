package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import web.dao.face.CommentDao;
import web.dto.Board;
import web.dto.Comment;

public class CommentDaoImpl implements CommentDao {
	
	PreparedStatement ps = null;
	ResultSet rs = null;

	@Override
	public List<Comment> selectCommentByBoardNo(Connection conn, Board board) {
		
		List<Comment> commentList = new ArrayList<>();
		
		String sql = "";
		sql += "SELECT commentno, boardno, userid, content, write_date FROM commenttb";
		sql += " WHERE boardno = ?";
		sql += " ORDER BY commentno DESC";
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, board.getBoardno());
			
			rs=ps.executeQuery();
			
			while( rs.next() ) {
				Comment comment = new Comment();
				comment.setCommentno(rs.getInt("commentno"));
				comment.setBoardno(rs.getInt("boardno"));
				comment.setUserid(rs.getString("userid"));
				comment.setContent(rs.getString("content"));
				comment.setWriteDate(rs.getDate("write_date"));
				
				commentList.add(comment);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return commentList;
	}

	@Override
	public int selectNextCommentNo(Connection conn) {
		
		String sql = "";
		sql += "SELECT commenttb_seq.nextval nextval FROM dual";
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			
			rs=ps.executeQuery();
			while(rs.next()) {
				
				res = rs.getInt("nextval");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public int insertComment(Connection conn, Comment insertComment) {
		
		String sql = "";
		sql += "INSERT INTO commenttb (";
		sql += " commentno, boardno, userid, content )";
		sql += " VALUES ( ?, ?, ?, ? )";
		
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1,insertComment.getCommentno());
			ps.setInt(2, insertComment.getBoardno());
			ps.setString(3, insertComment.getUserid());
			ps.setString(4, insertComment.getContent());
			
			res=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}

	
	
}
