package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVo vo = new BoardVo();
		String title =request.getParameter("title");
		String content =request.getParameter("content");
		vo.setTitle(title);
		vo.setContext(content);
		HttpSession session = request.getSession();
		if(session == null) {
		WebUtils.redirect(request,response,request.getContextPath()+"/board");
		return;
		}
		UserVo authUser =(UserVo)session.getAttribute("authUser");
			if(authUser == null) {
			WebUtils.redirect(request,response,request.getContextPath()+"/board");
			return;
		}
		vo.setUserNo(authUser.getNo());	
		new BoardDao().insert(vo);
		if("".equals(request.getParameter("no"))) {
			WebUtils.redirect(request, response, request.getContextPath()+"/board");
		}
		else {
			Long no=Long.parseLong(request.getParameter("no"));
			BoardVo newVo = new BoardDao().getSelect(no);
			vo.setG_no(newVo.getG_no());
			vo.setO_no(newVo.getO_no()+1);
			vo.setDepth(newVo.getDepth()+1);
			new BoardDao().updateRequest(vo);
			new BoardDao().insertRequest(vo);
			WebUtils.redirect(request, response, request.getContextPath()+"/board");
		}
	}

}
