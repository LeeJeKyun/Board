package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Board;
import web.dto.BoardFile;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/update")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/board/update [GET]");
		
//		System.out.println("/board/update - doGet : " + req.getParameter("boardno"));
//		System.out.println("/board/update - doGet : " + req.getAttribute("board"));
		
		Board upBoard = boardService.getBoardno(req);
		
		//조회수 증가로 사용 X
//		Board board = boardService.view(boardno);
		Board board = boardService.toUpdate(upBoard);
		BoardFile boardFile = boardService.getBoardFile(board);
		
		System.out.println("/board/update - doGet : " + board);
		
		req.setAttribute("board", board);
		req.setAttribute("boardFile", boardFile);
		
		req.getRequestDispatcher("/WEB-INF/views/board/update.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/board/update [POST]");
		
		req.setCharacterEncoding("UTF-8");
		
		boardService.update(req);
		
		resp.sendRedirect("/board/list");
		
	}
	
}
