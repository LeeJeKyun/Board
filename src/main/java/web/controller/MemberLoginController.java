package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.dto.Member;
import web.service.face.MemberService;
import web.service.impl.MemberServiceImpl;

@WebServlet("/member/login")
public class MemberLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberService memberService = new MemberServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		if(session.getAttribute("login") !=null ) {
			resp.sendRedirect("/main");
		}else {
			req.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(req, resp);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Member loginMember = memberService.getLoginMember(req);
		
		boolean login = memberService.login(loginMember);
		
		HttpSession session = req.getSession();
		
		if(login) {	//로그인 성공시
			Member member = memberService.info(loginMember);
			session.setAttribute("login", true);
			session.setAttribute("userid", member.getUserid());
			session.setAttribute("userpw", member.getUserpw());
			resp.sendRedirect("/main");
		} else {	//로그인 실패시
			resp.sendRedirect("/main");
		}
		
	}
	
}
