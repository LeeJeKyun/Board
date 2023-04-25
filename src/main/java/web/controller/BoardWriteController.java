package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/write")
public class BoardWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/board/write [GET]");
		
		HttpSession session = req.getSession();
		if(session.getAttribute("login") != null) {
			req.getRequestDispatcher("/WEB-INF/views/board/write.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("/main");
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/board/write [POST]");
		
		req.setCharacterEncoding("UTF-8");
		
		boardService.write(req);
		
		resp.sendRedirect("/board/list");
		
	}
	
}
