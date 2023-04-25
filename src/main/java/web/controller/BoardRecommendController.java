package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Board;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/recommend")
public class BoardRecommendController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BoardService boardService = new BoardServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/board/recommend [GET]");
		
		Board recommendBoard = boardService.getBoard(req);
		
//		System.out.println("/board/recommend doGet() recommendBoard : " + recommendBoard);
		
		//추천이 된 상태면 true, 추천이 안 된 상태면 false
		boolean isRecommended = boardService.recommend(recommendBoard);
		int recommendCnt = boardService.recommendCnt(recommendBoard);
		req.setAttribute("recommendCnt", recommendCnt);
		req.setAttribute("isRecommended", isRecommended);
		
		req.getRequestDispatcher("/WEB-INF/views/board/recommend.jsp").forward(req, resp);
		
	}
	
}
