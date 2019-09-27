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

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.PaginationUtil;
import kr.co.itcen.mysite.vo.UserVo;

@Repository
public class BoardDao {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	

	public Boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert",vo);
		return count == 1;

	}

	public List<BoardVo> getList(String kwd, PaginationUtil pagination) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();
			
			String sql = "select b.no as no, title, name, contents, hit, date_format(reg_date,'%Y-%m-%d %h:%i:%s') as reg_date, depth, status" +
			        "       from user u, board b" +
					"      where u.no = b.user_no" +
			        "        and (b.title like ? or b.contents like ?)" +
			        "   order by g_no desc, o_no asc" +
			        "      limit ?, ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%" + ((kwd == null) ? "" : kwd) + "%");
			pstmt.setString(2, "%" + ((kwd == null) ? "" : kwd) + "%");
			pstmt.setInt(3, (pagination.getCurrentPage() - 1) * pagination.getListSize());
			pstmt.setInt(4, pagination.getListSize());
//			5. limit 수정
//			ex ) limit (보여줄 페이지 - 1) * 한 페이지에 보여줄 게시글의 수, 한 페이지에 보여줄 게시글의 수
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo boardVo = new BoardVo();
				
				boardVo.setNo(rs.getLong("no"));
				boardVo.setTitle(rs.getString("title"));
				boardVo.setName(rs.getString("name"));
				boardVo.setContents("contents");
				boardVo.setHit(rs.getLong("hit"));
				boardVo.setRegDate(rs.getString("reg_date"));
				boardVo.setDepth(rs.getLong("depth"));
				boardVo.setStatus(rs.getString("status"));
				
				result.add(boardVo);
			}
						
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public UserVo get(Long no) {

		UserVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
				String sql = "select name,email,gender from user where no= ? ";
				pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, no);
	
				rs = pstmt.executeQuery();
				if (rs.next()) {
					String name = rs.getString(1);
					String email = rs.getString(2);
					String gender = rs.getString(3);
	
					result = new UserVo();
					result.setName(name);
					result.setEmail(email);
					result.setGender(gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public BoardVo view(Long no) {

		BoardVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
	
				String sql = "select title,contents from board where no= ? ";
				pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, no);
	
				rs = pstmt.executeQuery();
				if (rs.next()) {
					
					String title = rs.getString(1);
					String context = rs.getString(2);
					result = new BoardVo();
					result.setTitle(title);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Boolean update(UserVo vo) {

		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {

			String sql = "update board set name=?,password =?,gender =? ,status='modify' where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setLong(4, vo.getNo());
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Boolean updateStatus(Long no) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update board set status='delete' where no=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public int getListCount(String kwd) {
		int count = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();
			
			String sql = "select count(*) as 'cnt'" +
			        "       from user u, board b" +
					"      where u.no = b.user_no" +
			        "        and (b.title like ? or b.contents like ?)" +
			        "   order by g_no desc, o_no asc";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%" + ((kwd == null) ? "" : kwd) + "%");
			pstmt.setString(2, "%" + ((kwd == null) ? "" : kwd) + "%");
			
//			5. limit 수정
//			ex ) limit (보여줄 페이지 - 1) * 한 페이지에 보여줄 게시글의 수, 한 페이지에 보여줄 게시글의 수
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("cnt");
			}
						
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
}


