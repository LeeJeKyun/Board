package web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Board;
import web.dto.BoardFile;
import web.dto.Comment;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/view")
public class BoardViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getParameter("boardno")==null) {
			resp.sendRedirect("/board/list");
		} else {
		
		Board viewBoard = boardService.getBoardno(req);
		Board board = boardService.view(viewBoard);
		BoardFile boardFile = boardService.getBoardFile(board);
		
		Board recBoard = boardService.getBoard(req);
		Map<String,Object> viewBoardmap = boardService.viewMap(viewBoard);
		
		boolean isRecommended = boardService.isRecommended(recBoard);
		int recommendCnt = boardService.recommendCnt(recBoard);

		List<Comment> commentList = boardService.commentList(board);
		
//		System.out.println("BoardViewController - doGet : " + commentList);
		
		req.setAttribute("commentList", commentList);
		req.setAttribute("board", viewBoardmap.get("board"));
		req.setAttribute("usernick", viewBoardmap.get("nick"));
		req.setAttribute("boardFile", boardFile);
		req.setAttribute("recommended", isRecommended);
		req.setAttribute("recommendCnt", recommendCnt);
//		System.out.println("BoardViewController doGet() boardFile : " + boardFile);
		req.getRequestDispatcher(req.getContextPath()+"/WEB-INF/views/board/view.jsp").forward(req, resp);
		}
	}
	
}
