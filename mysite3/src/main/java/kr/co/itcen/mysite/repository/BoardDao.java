package kr.co.itcen.mysite.repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.PaginationUtil;


@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	

	public Boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert",vo);
		return count == 1;

	}
	public List<BoardVo> getList(String kwd, PaginationUtil pagination) {
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("kwd", kwd);
		int start=(pagination.getCurrentPage()-1)*pagination.getListSize();
		map.put("start", start);
		int end = (pagination.getListSize());
		map.put("end", end);
		List<BoardVo> result = sqlSession.selectList("board.getList",map);
		return result;

//			5. limit 수정
//			ex ) limit (보여줄 페이지 - 1) * 한 페이지에 보여줄 게시글의 수, 한 페이지에 보여줄 게시글의 수
	}
	public Boolean insertRequest(BoardVo vo) {
		int count = sqlSession.insert("board.insertRequest",vo);
		return count == 1;
		//본래는 메소드 한번에 쿼리 하나씩이지만 이건 특이한 케이스이다.
	}
	public Boolean delete(Long no) {
		int count=sqlSession.update("board.delete",no);
		return count==1;
	}

	public BoardVo view(Long no) {

		BoardVo vo =sqlSession.selectOne("board.view",no);
		return vo;
	}

	public Boolean update(BoardVo vo) {
		int count = sqlSession.update("board.modify",vo);
		return count ==1;
	}

	
	public Boolean updateRequest(BoardVo vo) {
		int count = sqlSession.update("board.updateRequest",vo);
		return count>=0;
		
	}

	public int getListCount(String kwd) {
		int count = sqlSession.selectOne("board.getListCount",kwd);
		return count;
	}

	public BoardVo getSelect(Long no) {
		BoardVo vo = sqlSession.selectOne("board.getSelect",no);
		return vo;
	}

	
}


