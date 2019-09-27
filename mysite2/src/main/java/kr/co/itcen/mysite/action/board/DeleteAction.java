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

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no=Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().view(no);
		System.out.println(vo);
		//new BoardDao().보드번호를 이용해서 유저 정보 뽑아오기
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
		if(authUser.getNo()!=vo.getUserNo()) {
			WebUtils.redirect(request,response,request.getContextPath()+"/board");
			return;
		}
		new BoardDao().updateStatus(no);
		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}

}
