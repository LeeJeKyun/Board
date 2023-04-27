package web.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import common.JDBCTemplate;
import util.Paging;
import web.dao.face.BoardDao;
import web.dao.face.BoardFileDao;
import web.dao.face.CommentDao;
import web.dao.impl.BoardDaoImpl;
import web.dao.impl.BoardFileDaoImpl;
import web.dao.impl.CommentDaoImpl;
import web.dto.Board;
import web.dto.BoardFile;
import web.dto.Comment;
import web.service.face.BoardService;

public class BoardServiceImpl implements BoardService {
	
	Connection conn = JDBCTemplate.getConnection();
	BoardDao boardDao = new BoardDaoImpl();
	BoardFileDao boardFileDao = new BoardFileDaoImpl();
	CommentDao commentDao = new CommentDaoImpl();
	
	@Override
	public List<Map<String, Object>> getList() {
		System.out.println("BoardService-getList() 시작");
		List<Map<String, Object>> list = null;
		
		list = boardDao.selectAll(conn);
		
		System.out.println("BoardService-getList() 끝");
		return list;
	}

	@Override
	public Board getBoardno(HttpServletRequest req) {
		System.out.println("BoardService-getBoardno 시작");
		
		Board viewBoard = new Board();
		viewBoard.setBoardno(Integer.parseInt(req.getParameter("boardno")));
		
//		System.out.println(viewBoard.getBoardno());
		
		System.out.println("BoardService-getBoardno 끝");
		return viewBoard;
	}

	@Override
	public Board view(Board viewBoard) {
		System.out.println("BoardService-view() 시작");
		
		int res = boardDao.updateHit(conn, viewBoard);
		if(res>0) {
			JDBCTemplate.commit(conn);
			System.out.println("Hit 커밋완료");
		} else {
			JDBCTemplate.rollback(conn);
			System.out.println("Hit 롤백완료");
		}
		Board board = boardDao.selectBoardByBoardno(conn, viewBoard);
		
		System.out.println("BoardService-view() 끝");
		return board;
	}

	
	
	@Override
	public Board toUpdate(Board upBoard) {
		
		return boardDao.selectBoardByBoardno(conn, upBoard);
	}

	@Override
	public void write(HttpServletRequest req) {
		System.out.println("BoardService-write() 시작");
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		
		if( !isMultipart ) return;
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		int maxMem = 1 * 1024 * 1024;
		factory.setSizeThreshold(maxMem);
		
		//실제 서버 경로 가져오기(경로/files)
		ServletContext context = req.getServletContext();
		String path = context.getRealPath("files");
//		System.out.println("BoardService - write() 의 getRealPath : " + path);
		
		File filesRepository = new File(path);
		
		filesRepository.mkdir();
		factory.setRepository(filesRepository);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		int maxFile = 10 * 1024 * 1024;
		upload.setFileSizeMax(maxFile);
		
		List<FileItem> items = null;
		
		try {
			items=upload.parseRequest(req);
//			for(FileItem item : items) {
//				System.out.println("BoardService - write() item : " + item);
//			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		
		Board board = new Board();
		int boardno = 0;
		boardno = boardDao.selectBoardseqNext(conn);
		board.setBoardno(boardno);
		board.setUserid((String)req.getSession().getAttribute("userid"));
		BoardFile boardFile = null;
		
		Iterator<FileItem> iter = items.iterator();
		
		while ( iter.hasNext() ) {
			
			FileItem item = iter.next();
			
			if( item.getSize() <= 0) {
				
				continue;
			}
			
			if( item.isFormField() ) {
				String key = item.getFieldName();
				String value = null;
				
				try {
					value = item.getString("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				if("title".equals(key)) {
					board.setTitle(value);
				} else if("content".equals(key)) {
					board.setContent(value);
				} 
				
			}
			
			if( !item.isFormField() ) {
				boardFile = new BoardFile();
				int fileno = boardFileDao.selectBoardFileseqNext(conn);
				boardFile.setFileno(fileno);
				boardFile.setBoardno(boardno);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
				String rename = sdf.format(new Date());
				
				File uploadFolder = new File( context.getRealPath("upload"));
				uploadFolder.mkdir();
				
				File up = new File(uploadFolder, rename);
				
				try {
					item.write(up);
					
					item.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				boardFile.setOriginname(item.getName());
				boardFile.setStoredname(rename);
				boardFile.setFilesize(item.getSize());
				
			}//if END
			
			
		}// while END
		
		System.out.println("boardService write() : 마무리 "+board);
		System.out.println("boardService write() : 마무리 "+boardFile);
		
		int res = 0;
		
		res += boardDao.insert(conn, board);
		if(boardFile != null) {
			res += boardFileDao.insert(conn, boardFile);
		}
		
		if(res == 0) {
			JDBCTemplate.rollback(conn);
		} else if(res>=1) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
	}

	@Override
	public BoardFile getBoardFile(Board board) {
		
		
		
		return boardDao.selectBoardFileByBoardno(conn, board);
	}

	@Override
	public void update(HttpServletRequest req) {
		System.out.println("BoardService - update() 시작");
		
		boolean Multipart = ServletFileUpload.isMultipartContent(req);
		
		if(!Multipart) return;
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		int maxMem = 1 * 1024 * 1024;
		
		factory.setSizeThreshold(maxMem);
		
		ServletContext context = req.getServletContext();
		String path = context.getRealPath("files");
		
		File fileRepository = new File(path);
		fileRepository.mkdir();
		factory.setRepository(fileRepository);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		int maxFile=10*1024*1024;
		upload.setSizeMax(maxFile);
		
		Board board = new Board();
		int boardno = 0;
		BoardFile boardFile = null;
		
		List<FileItem> items = null;
		
		try {
			items=upload.parseRequest(req);
			
			Iterator<FileItem> iter = items.iterator();
			
			while(iter.hasNext()) {
				FileItem item = iter.next();
				
				if( item.getSize() <= 0 ){
					continue;
				}
				
				if(item.isFormField()) {
					String key = item.getFieldName();
					String value = null;
					
					try {
						value=item.getString("UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
					if("boardno".equals(key)) {
						boardno = Integer.parseInt(value);
						board.setBoardno(boardno); //boardno이 0일때 상황 생각하기
					} else if("content".equals(key)) {
						board.setContent(value);
					} 
					
				}
				
				if( !item.isFormField() ) {
					boardFile = new BoardFile();
					int fileno = boardFileDao.selectBoardFileseqNext(conn);
					boardFile.setFileno(fileno);
					boardFile.setBoardno(boardno);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
					String rename = sdf.format(new Date());
					
					File uploadFolder = new File( context.getRealPath("upload"));
					uploadFolder.mkdir();
					
					File up = new File(uploadFolder, rename);
					
					try {
						item.write(up);
						
						item.delete();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					boardFile.setOriginname(item.getName());
					boardFile.setStoredname(rename);
					boardFile.setFilesize(item.getSize());
					
				} //if END
				
			}// while END
			
//			System.out.println("boardService update() : 마무리 " + board);
//			System.out.println("boardService update() : 마무리 " + boardFile);
			
			//board와 boardfile모두 update하기
			System.out.println("boardService update() : 마무리 " + board);
			System.out.println("boardService update() : 마무리 " + boardFile);
			
			int res = 0;
			res += boardDao.update(conn, board);
			
			if(boardFile != null) {
				res += boardFileDao.insert(conn, boardFile);
			}
			
			if(res>=1) {
				JDBCTemplate.commit(conn);
				System.out.println("커밋완료");
			} else {
				JDBCTemplate.rollback(conn);
				System.out.println("커밋완료");
			}
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Board board) {
		System.out.println("BoardService - delete() : 호출");
		
		int res = 0;
		res += boardDao.delete(conn, board);
		res += boardFileDao.delete(conn, board);
		
		if(res>0) {
			JDBCTemplate.commit(conn);
			System.out.println("BoardService - delete() 커밋완료");
		} else {
			JDBCTemplate.rollback(conn);
			System.out.println("BoardService - delete() 롤백완료");
		}
		
		
		
	}

	@Override
	public Board getBoard(HttpServletRequest req) {
		Board board = new Board();
		board.setBoardno(Integer.parseInt(req.getParameter("boardno")));
		board.setUserid((String) req.getSession().getAttribute("userid"));
		
		return board;
	}

	@Override
	public boolean recommend(Board recommendBoard) {
		
		//게시글의 추천상태를 확인한 후에 DB에서 추천수 추가 혹은 제거
		boolean isRecommended = boardDao.selectRecommend(conn, recommendBoard);
		
		System.out.println("BoardService - recommend() - isRecommended : " + isRecommended);
		
		int res = 0;
		int result = 0;
		
		if(isRecommended) {
			res += boardDao.insertRecommend(conn, recommendBoard);
			System.out.println("추천 추가 완료");
		} else {
			res += boardDao.deleteRecommend(conn, recommendBoard);
			System.out.println("추천 취소 완료");
		}
		
		if( res > 0) {
			JDBCTemplate.commit(conn);
			System.out.println("BoardService - recommend() : 커밋완료");
		} else {
			JDBCTemplate.rollback(conn);
			System.out.println("BoardService - recommend() : 롤백완료");
		}
		
		return isRecommended;
		
	}

	@Override
	public boolean isRecommended(Board board) {
		
		boolean isRecommended = boardDao.selectRecommend(conn, board);
		
//		System.out.println("BoardService isRecommended = " + isRecommended);
		
		return isRecommended;
	}

	@Override
	public int recommendCnt(Board recommendBoard) {
		
		return boardDao.selectRecommendCnt(conn, recommendBoard);
	}

	@Override
	public Paging getPaging(HttpServletRequest req) {
		
		String param = req.getParameter("curPage");
		String keyword = req.getParameter("search");
		if(keyword == null) {
			keyword = "";
		}
		int curPage = 0;
		if( param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		} else {
			System.out.println("[WARN] BoardService - getPaging() : curPage값이 null이거나 비어있음");
		}
		
		//search파라미터가 비어있을경우와 아닌경우 나누기
		
		Paging paging = null;
		
		//비어있을경우 : selectCntAll, Paging그대로진행하기
		int totalCount = boardDao.selectCntAll( conn , keyword );
		paging = new Paging(totalCount, curPage, 10, 5);
//		System.out.println("BoardService getPaging() - : " + totalCount);
		paging.setSearch(keyword);
		
		return paging;
	}

	@Override
	public List<Map<String, Object>> getList(Paging paging) {
		
		
		return boardDao.selectAll(conn, paging);
	}

	@Override
	public List<Comment> commentList(Board board) {
		
		return commentDao.selectCommentByBoardNo(conn, board);
	}

	@Override
	public Comment getComment(HttpServletRequest req) {
		
		Comment comment = new Comment();
		
		
		
		
		comment.setBoardno( Integer.parseInt( req.getParameter("boardno") ) );
		comment.setContent( req.getParameter("content") );
		comment.setUserid( (String)req.getSession().getAttribute("userid") );
		
		return comment;
	}

	@Override
	public void commentInsert(Comment insertComment) {
		
		insertComment.setCommentno(commentDao.selectNextCommentNo(conn));
		
//		System.out.println("boardService - commentInsert() comment : " + insertComment);
		
		int res = commentDao.insertComment(conn, insertComment);
		if( res > 0 ) {
			System.out.println("commentInsert - 커밋완료");
			JDBCTemplate.commit(conn);
		} else {
			System.out.println("commentInsert - 롤백완료");
			JDBCTemplate.rollback(conn);
		}
		
	}

	@Override
	public void commentDelete(HttpServletRequest req) {
		
		Comment comment = new Comment();
		comment.setCommentno(Integer.parseInt( req.getParameter("commentno")));
		
		int res = commentDao.deleteComment(conn, comment);
		
		if( res > 0 ) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
	}

	@Override
	public List<Board> getDeleteList(String[] boardnoArr) {
		
		List<Board> deleteList = new ArrayList<>();
		for(String str : boardnoArr) {
			
			Board board = new Board();
			board.setBoardno(Integer.parseInt(str));
			
			deleteList.add(board);
		}
		
		return deleteList;
	}

	@Override
	public void deleteBoardList(List<Board> deleteList) {
		
		Iterator<Board> iter = deleteList.iterator();
		
		while(iter.hasNext()) {
			Board board = (Board)iter.next();
			
			int res = boardDao.delete(conn, board);
			if(res > 0 ) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}

			int res1 = commentDao.deleteCommentByBoardno(conn, board);
			if(res1 > 0 ) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
			
		}
		
	}

	@Override
	public Map<String, Object> viewMap(Board viewBoard) {
		
		return boardDao.selectBoardJoinUsernick(conn, viewBoard);
	}
	
	
	
	
	
}
