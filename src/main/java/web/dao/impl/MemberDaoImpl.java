package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.JDBCTemplate;
import web.dao.face.MemberDao;
import web.dto.Member;

public class MemberDaoImpl implements MemberDao {
	
	PreparedStatement ps = null;
	ResultSet rs = null;

	@Override
	public int selectCntMemberByUseridUserpw(Connection conn, Member loginMember) {
		System.out.println("MemberDao-selectCntMemberByUseridUserpw() 시작");
		String sql = "";
		sql += "SELECT count(*) cnt FROM member";
		sql += " WHERE userid=?";
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, loginMember.getUserid());
			
			rs=ps.executeQuery();
			rs.next();
			res=rs.getInt("cnt");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		System.out.println("MemberDao-selectCntMemberByUseridUserpw() 끝");
		return res;
	}

	@Override
	public Member selectMemberByUserid(Connection conn, Member loginMember) {
		System.out.println("MemberDao-selectMemberByUserid() 시작");
		String sql = "";
		sql += "SELECT * FROM member";
		sql += " WHERE userid=?";
		Member member = null;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, loginMember.getUserid());
			
			rs=ps.executeQuery();
			while(rs.next()) {
				member = new Member(rs.getString("userid")
									, rs.getString("userpw")
									, rs.getString("usernick"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		System.out.println("MemberDao-selectMemberByUserid() 끝");
		return member;
	}

	@Override
	public int insert(Connection conn, Member member) {
		System.out.println("MemberDao - insert() 시작");
		String sql = "";
		sql += "INSERT INTO member( userid, userpw, usernick )";
		sql += " VALUES( ?, ?, ? )";
		
		
		int res = 0;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, member.getUserid());
			ps.setString(2, member.getUserpw());
			ps.setString(3, member.getUsernick());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}

	
}
