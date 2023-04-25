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

@WebServlet("/member/join")
public class MemberJoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberService memberService = new MemberServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/member/join [GET]");
		HttpSession session = req.getSession();
		
		if(session.getAttribute("login") !=null ) {
			resp.sendRedirect("/main");
		}else {
			req.getRequestDispatcher("/WEB-INF/views/member/join.jsp").forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/member/join [POST]");
		
		Member member = memberService.getJoinMember(req);
		
		memberService.join(member);
		
		resp.sendRedirect("/main");
	}
	
}
