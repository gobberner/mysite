package kr.co.itcen.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;	
	
	@RequestMapping(value="/join",method =RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	@RequestMapping(value="/join",method =RequestMethod.POST)
	public String join(@ModelAttribute UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	@RequestMapping(value="/joinsucess",method =RequestMethod.GET)
	public String joinsucess() {
		return "user/joinsucess";
	}
	@RequestMapping(value="/login",method =RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	@RequestMapping(value="/login",method =RequestMethod.POST)
	public String login(@ModelAttribute UserVo vo, HttpSession session,Model model) {
		UserVo userVo = userService.getUser(vo);
		
		if(userVo == null) {
			model.addAttribute("result","fail");
			return "user/login";
		}
		session.setAttribute("authUser", userVo);
		return "redirect:/";
	}
	@RequestMapping(value="/logout",method =RequestMethod.GET)
	public String logout(HttpSession session) {
		UserVo authUser =(UserVo)session.getAttribute("authUser");
		if(authUser != null) {
			session.removeAttribute("authUser");
			session.invalidate();
		}
		return "redirect:/";
	}

	@RequestMapping(value="/update",method =RequestMethod.GET)
	public String update() {
		return "user/update";
	}
	
	@RequestMapping(value="/update",method =RequestMethod.POST)
	public String update(@ModelAttribute UserVo vo, HttpSession session) {
		UserVo authuser=(UserVo) session.getAttribute("authUser");
		vo.setNo(authuser.getNo());
		userService.update(vo);
		
		return "redirect:/";
	}
	
	@ExceptionHandler(UserDaoException.class)
	public String handlerException() {
		return "error/exception";
	}
}
