package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.mvc.PaginationUtil;

public class BoardDao {

	private Connection getConnection() throws SQLException {
		Connection connection = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.1.66:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}

		return connection;
	}

	public Boolean insert(BoardVo vo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "insert into board values(null,?,?,0,date_format(now(),'%Y-%m-%d %h:%i:%s'),ifnull((select max(g_no) from board as b)+1,1) , 1 , 0 , ? , 'insert' )";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContext());
			pstmt.setLong(3,vo.getUserNo());
			int count = pstmt.executeUpdate();
			result = (count == 1);

			stmt = connection.createStatement();
			rs = stmt.executeQuery("select last_insert_id()");
			if (rs.next()) {
				Long no = rs.getLong(1);
				vo.setNo(no);
			}

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

//	public List<BoardVo> getList(String kwd, PaginationUtil pUtil) {
	public List<BoardVo> getList(String kwd) {

		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select board.no,board.title,board.contents,board.hit,board.reg_Date,board.g_no,board.o_no,board.depth,board.user_no,user.name,board.status from board,user where user.no= board.user_no and (board.status='insert' or board.status='modify') and (board.title like ?  or board.contents like ?) order by g_no desc,o_no asc";
//			3. limit 설정 limit x, y x부터 y개 조회
//			lilmit (보여줄 페이지 - 1) * 한 페이지에 보여질 게시글의 수, 한 페이지에 보여질 게시글의 수
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setString(2, "%"+kwd+"%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String reg_date = rs.getString(5);
				Long g_no = rs.getLong(6);
				Long o_no = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long user_no = rs.getLong(9);
				String name = rs.getString(10);
				String status=rs.getString(11);
				BoardVo vo= new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContext(contents);
				vo.setHit(hit);
				vo.setRegDate(reg_date);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				vo.setName(name);		
				vo.setStatus(status);
				list.add(vo);	
							
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

		return list;
	}

	public BoardVo getSelect(Long no) {

		BoardVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select g_no,o_no,depth from board where no= ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Long g_no = rs.getLong(1);
				Long o_no = rs.getLong(2);
				Long depth = rs.getLong(3);

				result = new BoardVo();
				result.setG_no(g_no);
				result.setO_no(o_no);
				result.setDepth(depth);
				
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
			connection = getConnection();

			String sql = "select title,contents,no,user_no from board where no= ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				String title = rs.getString(1);
				String context = rs.getString(2);
				Long no1 = rs.getLong(3);
				Long usr=rs.getLong(4);
				result = new BoardVo();
				result.setTitle(title);
				result.setContext(context);
				result.setNo(no1);
				result.setUserNo(usr);
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

	public Boolean update(BoardVo vo) {

		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "update board set status='modify', title = ?, contents = ? where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContext());
			pstmt.setLong(3, vo.getNo());
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
			connection = getConnection();

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
	public Boolean updateRequest(BoardVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      
	      try {
	         connection = getConnection();
	         String sql = "update board set o_no=o_no+1 where g_no = ? and o_no >= ?";
	         pstmt = connection.prepareStatement(sql);
	         
	         pstmt.setLong(1, vo.getG_no());
	         pstmt.setLong(2, vo.getO_no());
	         int count = pstmt.executeUpdate();
	         result = (count==1);
	         
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
	            }if(stmt!=null) {
	            	stmt.close();
	            }if(connection != null){
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	      return result;
	}
	public Boolean insertRequest(BoardVo vo) {
		Connection connection = null;
	    PreparedStatement pstmt = null;
	    Statement stmt = null;
	    Boolean result = false;
	    ResultSet rs =null;
	      try {
	         connection = getConnection();
	         String sql = "insert into board values(null,?,?,0,now(),?,?,?,?,'insert')";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, vo.getTitle());
	         pstmt.setString(2, vo.getContext());
	         pstmt.setLong(3, vo.getG_no());
	         pstmt.setLong(4,vo.getO_no());
	         pstmt.setLong(5, vo.getDepth());
	         pstmt.setLong(6, vo.getUserNo());
	         int count = pstmt.executeUpdate();
	         result = (count==1);
	         stmt=connection.createStatement();
	         rs =stmt.executeQuery("select last_insert_id()");//본래는 메소드 한번에 쿼리 하나씩이지만 이건 특이한 케이스이다.
	         if(rs.next()) {
	        	Long no=rs.getLong(1);
	         	vo.setNo(no);
	         }
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
	            }if(stmt!=null) {
	            	stmt.close();
	            }if(connection != null){
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
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
			connection = getConnection();
			
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

	public List<BoardVo> getList(String kwd, PaginationUtil pagination) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			
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
				boardVo.setContext(rs.getString("contents"));
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
}


