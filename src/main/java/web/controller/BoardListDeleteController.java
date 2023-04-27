package web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Board;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/listDelete")
public class BoardListDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BoardService boardService = new BoardServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		System.out.println("BoardListDeleteController - doGet() : " + req.getParameter("boardno"));
		
//		System.out.println("BoardListDeleteController - doGet() : " + req.getParameterValues("boardno"));
		
		String[] strArr = req.getParameterValues("boardno");
//		for(String str : strArr) {
//			System.out.println(str);
//		}
		
		List<Board> deleteList = boardService.getDeleteList(strArr);
		
		boardService.deleteBoardList(deleteList);
		
		resp.sendRedirect("./list");
		
	}
	
}
