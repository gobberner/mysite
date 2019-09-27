package kr.co.itcen.mysite.action.board;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.PaginationUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String kwd = request.getParameter("kwd"); 
		 String pageStr = request.getParameter("page");
		 int page = Integer.parseInt((pageStr == null || pageStr.length() == 0) ? "1" : pageStr);
			
		BoardDao dao = new BoardDao();
		
		int totalCnt = dao.getListCount(kwd); // 전체 게시글 수
		PaginationUtil pagination = new PaginationUtil(page, totalCnt, 10, 5);
		
		List<BoardVo> list = dao.getList(kwd, pagination);
		request.setAttribute("list", list);
		request.setAttribute("pagination", pagination);
		
		
//		1. dao 에서 총 게시글의 수를 조회하는 쿼리 작성
//		int totalCnt = new BoardDao().getListCount(kwd);
//		PaginationUtil pUtil = new PaginationUtil(/*현재 페이지, 총 게시물의 수totalCnt, 한 페이지에 보여질 게시글의 수10, 페이지 블럭의 페이지 수 5*/);
		
//		2. 페이징을 포함한 getList 쿼리 수정
//		List<BoardVo> list = new BoardDao().getList(kwd, pUtil);
		
		
//		4. 어트리뷰트에 페이징 정보, 키워드 추가
//		request.setAttribute("kwd", kwd);
//		request.setAttribute("pagination", pUtil);
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
