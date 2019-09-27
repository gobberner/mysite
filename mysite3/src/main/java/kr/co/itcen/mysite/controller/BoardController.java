package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.PaginationUtil;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String boardList(@RequestParam(value = "kwd", required = false) String kwd, //@RequestParam 받을 시 null 일 때, required = false 면 오류 없이 진행
				  		 	@RequestParam(value = "page", required = false, defaultValue = "1") String pageStr,
				  		 	Model model) { //코드 간추리기 생각해볼 것

		if(pageStr.length() == 0) {
			pageStr = "1";
		}
		
		int page = Integer.parseInt(pageStr);
		
		int totalCnt = boardService.getListCount(kwd);
		
		PaginationUtil pagination = new PaginationUtil(page, totalCnt, 10, 5);
		
		List<BoardVo> list = boardService.getList(kwd, pagination);
		
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "board/list";
	}
	
	@RequestMapping(value="write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String write(BoardVo vo) {
		
		
		return "board/write";
	}
	
	
}
