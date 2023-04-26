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

@WebServlet("/comment/insert")
public class CommentInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BoardService boardService = new BoardServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//req에 있는 파라미터(게시글번호, 댓글내용), 세션(현재로그인아이디)로객체생성
		
		Comment insertComment = boardService.getComment(req);
		
//		System.out.println("CommentInsertController doGet() : " + insertComment); 
		
		boardService.commentInsert(insertComment);
		
		//매개변수로 쓰일 board객체(boardno만 존재해도 상관 X
		Board board = boardService.getBoardno(req);
		
		List<Comment> commentList = boardService.commentList(board);
		
		req.setAttribute("commentList", commentList);
		req.getRequestDispatcher("/WEB-INF/views/board/comment.jsp").forward(req, resp);
		
	}
	
}
