package web.service.impl;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import web.dao.face.MemberDao;
import web.dao.impl.MemberDaoImpl;
import web.dto.Member;
import web.service.face.MemberService;

public class MemberServiceImpl implements MemberService {

	Connection conn = JDBCTemplate.getConnection();
	MemberDao memberDao = new MemberDaoImpl();
	
	@Override
	public Member getLoginMember(HttpServletRequest req) {
		System.out.println("MemberService - getLoginMember() 시작");
		Member member = new Member();
		
		member.setUserid(req.getParameter("userid"));
		member.setUserpw(req.getParameter("userpw"));
		
		System.out.println("MemberService - getLoginMember() 끝");
		return member;
	}

	@Override
	public boolean login(Member loginMember) {
		System.out.println("MemberService - login() 시작");
		
		//로그인 성공시 res=1, 그 경우 return true;
		int res =  memberDao.selectCntMemberByUseridUserpw(conn, loginMember);
		if(res>0) {
			return true;
		} else {
			System.out.println("MemberService - login() 끝");
			return false;
		}
		
	}

	@Override
	public Member info(Member loginMember) {
		System.out.println("MemberService - info() 시작");
		
		return memberDao.selectMemberByUserid(conn, loginMember);
	}

	@Override
	public Member getJoinMember(HttpServletRequest req) {
		System.out.println("MemberService - getJoinMember() 시작");
		
		return new Member(req.getParameter("userid"), req.getParameter("userpw"), req.getParameter("usernick"));
	}

	@Override
	public void join(Member member) {
		System.out.println("MemberService - join() 시작");
		
		if(memberDao.insert(conn, member) > 0) {
			JDBCTemplate.commit(conn);
			System.out.println("커밋완료(회원가입완료)");
		} else {
			JDBCTemplate.rollback(conn);
			System.out.println("롤백완료(회원가입실패");
		}
		
	}
	
	
	
}
