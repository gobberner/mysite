package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;
	
	
	public void delete(GuestbookVo vo) {
		sqlSession.delete("guestbook.delete",vo);
	}	
	
	public Boolean insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert",vo);
		return count == 1;
	}
	public List<GuestbookVo> getList() {
		List<GuestbookVo> result = sqlSession.selectList("guestbook.getList");
		return result;

	}	
//	public List<GuestbookVo> getList() {
//		List<GuestbookVo> result = new ArrayList<GuestbookVo>();
//		
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			
//			String sql = 
//				"   select no, name, contents, date_format(reg_date, '%Y-%m-%d %h:%i:%s')" +
//				"     from guestbook" + 
//				" order by reg_date desc";
//			pstmt = connection.prepareStatement(sql);
//			
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()){
//				Long no = rs.getLong(1);
//				String name = rs.getString(2);
//				String contents = rs.getString(3);
//				String regDate = rs.getString(4);
//				
//				GuestbookVo vo= new GuestbookVo();
//				vo.setNo(no);
//				vo.setName(name);
//				vo.setContents(contents);
//				vo.setRegDate(regDate);
//				
//				result.add(vo);
//			}
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return result;
//	}	
	
//	private Connection getConnection() throws SQLException {
//		Connection connection = null;
//		
//		try {
//			Class.forName("org.mariadb.jdbc.Driver");
//		
//			String url = "jdbc:mariadb://192.168.1.66:3306/webdb?characterEncoding=utf8";
//			connection = DriverManager.getConnection(url, "webdb", "webdb");
//		
//		} catch (ClassNotFoundException e) {
//			System.out.println("Fail to Loading Driver:" + e);
//		}
//		
//		return connection;
//	}
	
}
