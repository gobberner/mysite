package kr.co.itcen.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.action.guestbook.GuestbookActionFactory;
import kr.co.itcen.mysite.action.main.MainActionFactory;
import kr.co.itcen.mysite.dao.GuestbookDao;
import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;


public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		
		ActionFactory actionFactory = new GuestbookActionFactory();
		Action action = actionFactory.getAction(actionName);
		action.execute(request, response);
			
//		if("insert".equals(action)) {
//		
//			String name = request.getParameter("name");
//			String password = request.getParameter("password");
//			String contents = request.getParameter("contents");
//
//			GuestbookVo vo = new GuestbookVo();
//			vo.setName(name);
//			vo.setPassword(password);
//			vo.setContents(contents);
//			
//			new GuestbookDao().insert(vo);
//			WebUtils.redirect(request, response, request.getContextPath() + "/guestbook");			
//
//		} else if("deleteform".equals(action)) {
//			//forwarding
//			WebUtils.forward(request, response, "/WEB-INF/views/guestbook/deleteform.jsp");
//					
//		} else if("delete".equals(action)) {
//			String no = request.getParameter("no");
//			String password = request.getParameter("password");
//			
//			GuestbookVo vo = new GuestbookVo();
//			vo.setNo(Long.parseLong(no));
//			vo.setPassword(password);
//			
//			new GuestbookDao().delete(vo);
//			
//			WebUtils.redirect(request, response, request.getContextPath() + "/guestbook");
//		} else {
//			/* index(list) */
//			
//			List<GuestbookVo> list = new GuestbookDao().getList();
//			
//			request.setAttribute("list", list);
//			
//			//forwarding
//			WebUtils.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
