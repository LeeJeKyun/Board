package web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Board;
import web.dto.Comment;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/comment/delete")
public class CommentDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	BoardService boardService = new BoardServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("CommentDeleteController - doGet() : " + req.getParameter("commentno"));
//		System.out.println("CommentDeleteController - doGet() : " + req.getParameter("boardno"));
		
		boardService.commentDelete(req);
		
		Board board = boardService.getBoardno(req);
		
		List<Comment> commentList = boardService.commentList(board);
		
		req.setAttribute("commentList", commentList);
		req.getRequestDispatcher("/WEB-INF/views/board/comment.jsp").forward(req, resp);
		
	}
	
}
