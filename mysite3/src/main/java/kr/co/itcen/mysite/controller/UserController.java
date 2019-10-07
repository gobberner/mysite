package kr.co.itcen.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.security.Auth;
import kr.co.itcen.mysite.security.AuthUser;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;	
	
	@RequestMapping(value="/join",method =RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	@RequestMapping(value="/join",method =RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo,BindingResult result,Model model) {
			
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
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
//	@RequestMapping(value="/login",method =RequestMethod.POST)
//	public String login(@ModelAttribute UserVo vo, HttpSession session,Model model) {
//		UserVo userVo = userService.getUser(vo);
//		
//		if(userVo == null) {
//			model.addAttribute("result","fail");
//			return "user/login";
//		}
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}
	@RequestMapping(value="/logout",method =RequestMethod.GET)
	public String logout(HttpSession session) {
		UserVo authUser =(UserVo)session.getAttribute("authUser");
		if(authUser != null) {
			session.removeAttribute("authUser");
			session.invalidate();
		}
		return "redirect:/";
	}

	@Auth(role="USER")
	@RequestMapping(value="/update",method =RequestMethod.GET)
	public String update(@ModelAttribute @AuthUser UserVo authUser) {
		
		//UserVo authUser = (UserVo)session.getAttribute("authUser");
		//Long no = authUser.getNo();
		authUser = userService.getUser(authUser.getNo());
		//model.addAttribute("userVo", userVo);
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
