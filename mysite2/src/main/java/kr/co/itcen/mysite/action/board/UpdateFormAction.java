package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class UpdateFormAction implements Action  {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userNo_ = request.getParameter("userNo");
		String no_ = request.getParameter("no");
		
		BoardVo boardVo = new BoardDao().view(Long.parseLong(no_));
		
		request.setAttribute("vo", boardVo);
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/modify.jsp");	
	}

}
