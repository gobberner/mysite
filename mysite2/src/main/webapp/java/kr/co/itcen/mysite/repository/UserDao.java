package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
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
	public Boolean insert(UserVo vo) throws UserDaoException{
		int count = sqlSession.insert("user.insert",vo);
		return count == 1;
	}
	
	public Boolean update(UserVo vo) {
		Boolean result = false;
		int count = sqlSession.update("user.update",vo);
		return result;
		
	}
	
//	public Boolean insert(UserVo vo) throws UserDaoException{
//		Boolean result = false;
//		
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			
//			String sql = "inser into user values(null, ?, ?, ?, ?, now())";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setString(1, vo.getName());
//			pstmt.setString(2, vo.getEmail());
//			pstmt.setString(3, vo.getPassword());
//			pstmt.setString(4, vo.getGender());
//			int count = pstmt.executeUpdate();
//			result = (count == 1);
//			
//			stmt = connection.createStatement();
//			rs = stmt.executeQuery("select last_insert_id()");
//			if(rs.next()) {
//				Long no = rs.getLong(1);
//				vo.setNo(no);
//			}
//			
//		} catch (SQLException e) {
//			new UserDaoException(e.getMessage());
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(stmt != null) {
//					stmt.close();
//				}
//				
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				
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
	public UserVo get(Long no) {
		//return get(vo.getEmail(),vo.getPassword());
		return sqlSession.selectOne("user.getByNo",no);
	}	
	public UserVo get(UserVo vo) {
		//return get(vo.getEmail(),vo.getPassword());
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword1",vo);
		return result;
	}
	public UserVo get(String email, String password) {
		
		Map<String,String> map =new HashMap<String, String>();
		map.put("email",email);
		map.put("password",password);
		
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword2",map);
		return result;
	}
	
//	public UserVo get(String email, String password) {
//	
//		UserVo result = null;
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			
//			String sql = "select no, name from user where email = ? and password = ?";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setString(1, email);
//			pstmt.setString(2, password);
//			
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				Long no = rs.getLong(1);
//				String name = rs.getString(2);
//				
//				result = new UserVo();
//				result.setNo(no);
//				result.setName(name);
//			}
//			
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
//	public UserVo get(Long no) {
//		
//		UserVo result = null;
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			
//			String sql = "select name,email,gender from user where no= ? ";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setLong(1, no);
//				
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				String name = rs.getString(1);
//				String email = rs.getString(2);
//				String gender = rs.getString(3);
//				
//				result = new UserVo();
//				result.setName(name);
//				result.setEmail(email);
//				result.setGender(gender);
//			}
//			
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
//		return result;		
//	}

//	public Boolean update(UserVo vo) {
//
//		Boolean result = false;
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			
//			String sql = "update user set name=?,password =?,gender =?where no = ? ";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setString(1, vo.getName());
//			pstmt.setString(2, vo.getPassword());
//			pstmt.setString(3, vo.getGender());
//			pstmt.setLong(4, vo.getNo());
//			int count = pstmt.executeUpdate();
//			result = (count == 1);
//				
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(stmt != null) {
//					stmt.close();
//				}
//				
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				
//				if(connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;	
//	}
//
}
