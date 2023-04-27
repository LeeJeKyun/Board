package web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Paging;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Paging paging = boardService.getPaging(req);
		
		System.out.println("BoardListController - doGet() : " + req.getParameter("search"));
		
		req.setAttribute("paging", paging);
		
//		System.out.println("ListController - doGet() : " + paging);
		
		//서비스 -> Dao를 통해서 db데이터 받아오기
//		List<Map<String, Object>> list = boardService.getList();
		
			
		List<Map<String, Object>> list = boardService.getList(paging);
		
		System.out.println("BoardListController - doGet() : List =" + list);
		
		req.setAttribute("list", list);
		
		req.getRequestDispatcher(req.getContextPath()+"/WEB-INF/views/board/list.jsp").forward(req, resp);
		
	}
	
}
