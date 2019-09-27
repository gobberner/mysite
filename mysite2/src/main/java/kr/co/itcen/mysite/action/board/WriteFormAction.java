package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no=null;
		if(request.getParameter("no")!=null)
			no = Long.parseLong(request.getParameter("no"));
		request.setAttribute("no", no);
		WebUtils.forward(request, response, "/WEB-INF/views/board/write.jsp");
	}

}
