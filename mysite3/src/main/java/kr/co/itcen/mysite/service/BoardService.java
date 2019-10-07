package kr.co.itcen.mysite.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
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

	public Boolean insert(BoardVo vo) {
		return boardDao.insert(vo);
	}

	public BoardVo getSelect(Long vo) {
		BoardVo vo1 = boardDao.getSelect(vo);
		return vo1;
	}

	public boolean insertRequest(BoardVo vo) {
		boolean result = boardDao.insertRequest(vo); 
		return result;
	}

	public Boolean updateRequest(BoardVo vo) {
		 Boolean result = boardDao.updateRequest(vo);
		 return result;
	}

	public BoardVo view(Long no) {
		return boardDao.view(no);
	}

	public void delete(Long no) {
		 boardDao.delete(no);
	}

	public Boolean update(BoardVo vo) {
		return boardDao.update(vo);
	}
	
	
}
