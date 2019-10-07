package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.mysite.vo.PaginationUtil;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping({"","/list"})
	public String boardList(@RequestParam(value = "kwd", required = false ,defaultValue="") String kwd, //@RequestParam 받을 시 null 일 때, required = false 면 오류 없이 진행
				  		 	@RequestParam(value = "page", required = false, defaultValue = "1") int page,
				  		 	Model model) { //코드 간추리기 생각해볼 것

			
		int totalCnt = boardService.getListCount(kwd);
		
		PaginationUtil pagination = new PaginationUtil(page, totalCnt, 10, 5);
		
		List<BoardVo> list = boardService.getList(kwd, pagination);
		
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "board/list";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session) {
		if(session==null)
			return "redirect:/board";
		UserVo authUser= (UserVo)session.getAttribute("authUser");
		if(authUser==null)
			return "redirect:/board";
		return "board/write";
	}
	@RequestMapping(value="/write/{no}", method=RequestMethod.GET)
	public String write(HttpSession session,Model model,@PathVariable("no") Long no) {
		if(session==null)
			return "redirect:/board";
		UserVo authUser= (UserVo)session.getAttribute("authUser");
		if(authUser==null)
			return "redirect:/board";
		model.addAttribute("no",no);
		return "board/write";
	}
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(BoardVo vo,HttpSession session) {
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		vo.setUserNo(authUser.getNo());
		System.out.println(vo);
		if(vo.getNo()==null) {//글쓰기 작업
			boardService.insert(vo);
		}else {//답글쓰기
			BoardVo newVo = boardService.getSelect(vo.getNo());
			vo.setG_no(newVo.getG_no());
			vo.setO_no(newVo.getO_no()+1);
			vo.setDepth(newVo.getDepth()+1);
			boardService.insertRequest(vo);
			boardService.updateRequest(vo);
		}
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/view/{no}",method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no,Model model) {
		BoardVo vo= boardService.view(no);
		
		vo.setNo(no);
		model.addAttribute("vo",vo);
		return "board/view";
	}	
	
	@RequestMapping(value = "/delete/{no}", method =RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		boardService.delete(no);
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/update/{no}", method =RequestMethod.GET)
	public String update(@PathVariable("no") Long no,Model model) {
		//1.no값을 통한 디비 select=>where no=?
		//2.model을 통해 포워드된 페이지로 전송
		
		BoardVo vo = boardService.view(no);
		vo.setNo(no);
		model.addAttribute("vo",vo);
		return "board/modify";
	}
	@RequestMapping(value = "/update/{no}", method =RequestMethod.POST)
	public String update(@PathVariable("no") Long no,BoardVo vo) {
				 
		//1.BoardVo 에 받은 값 세팅
		//2.vo를 매게변수로 받는 업데이트 서비스 생성
		//3.redirect view
		boardService.update(vo);
		
		return "redirect:/board/view/"+no;
	}
}

