package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.PaginationUtil;

@Service
public class BoardService {
	@Autowired
	BoardDao boardDao;
	
	public int getListCount(String kwd) {
		int count = boardDao.getListCount(kwd);
		
		return count;
	}

	public List<BoardVo> getList(String kwd, PaginationUtil pagination) {
		List<BoardVo> list = boardDao.getList(kwd, pagination);
		
		return list;
	}
}
