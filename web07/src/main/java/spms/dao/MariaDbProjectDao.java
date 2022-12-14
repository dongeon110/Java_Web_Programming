package spms.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import spms.annotation.Component;
import spms.vo.Project;

@Component("projectDao")
public class MariaDbProjectDao implements ProjectDao {
	
	SqlSessionFactory sqlSessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
//	public List<Project> selectList() throws Exception {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		try {
//			return sqlSession.selectList("spms.dao.ProjectDao.selectList");
//		} finally {
//			sqlSession.close();
//		}
//	}
	public List<Project> selectList(HashMap<String, Object> paramMap) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.selectList("spms.dao.ProjectDao.selectList", paramMap);
		} finally {
			sqlSession.close();
		}
	}
	
	public int insert(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.insert("spms.dao.ProjectDao.insert", project);
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}
	}
	
	public Project selectOne(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.selectOne("spms.dao.ProjectDao.selectOne", no);
		} finally {
			sqlSession.close();
		}
	}
	
//	public int update(Project project) throws Exception {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		try {
//			int count = sqlSession.update("spms.dao.ProjectDao.update", project);
//			sqlSession.commit();
//			return count;
//		} finally {
//			sqlSession.close();
//		}
//	}
	
	public int update(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			Project original = sqlSession.selectOne(
					"spms.dao.ProjectDao.selectOne", project.getNo());
			
			Hashtable<String, Object> paramMap = new Hashtable<>();
			
			if (!project.getTitle().equals(original.getTitle())) {
				paramMap.put("title", project.getTitle());
			}
			if (!project.getContent().equals(original.getContent())) {
				paramMap.put("content", project.getContent());
			}
			if (!project.getStartDate().equals(original.getStartDate())) {
				paramMap.put("startDate", project.getStartDate());
			}
			if (!project.getEndDate().equals(original.getEndDate())) {
				paramMap.put("endDate", project.getEndDate());
			}
			if (project.getState() != original.getState()) {
				paramMap.put("state", project.getState());
			}
			if (!project.getTags().equals(original.getTags())) {
				paramMap.put("tags", project.getTags());
			}
			
			if(paramMap.size() > 0) {
				paramMap.put("no", project.getNo());
				int count = sqlSession.update("spms.dao.ProjectDao.update", paramMap);

				sqlSession.commit();
				return count;
			} else {
				return 0;
			}
			
			
		} finally {
			sqlSession.close();
		}
	}
	
	public int delete(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.delete("spms.dao.ProjectDao.delete", no);
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}
	}
	
	
	// myBatis ????????? ?????? ?????????
//	DataSource ds;
//	public void setDataSource(DataSource ds) {
//		this.ds = ds;
//	}
//	
//	public List<Project> selectList() throws Exception {
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = ds.getConnection();
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(
//					"SELECT PNO, PNAME, STA_DATE, END_DATE, STATE"
//					+ " FROM PROJECTS"
//					+ " ORDER BY PNO DESC");
//			
//			
//			ArrayList<Project> projects = new ArrayList<Project>();
//			
//			while(rs.next()) {
//				projects.add(new Project()
//						.setNo(rs.getInt("PNO"))
//						.setTitle(rs.getString("PNAME"))
//						.setStartDate(rs.getDate("STA_DATE"))
//						.setEndDate(rs.getDate("END_DATE"))
//						.setState(rs.getInt("STATE")));
//			}
//			return projects;
//			
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			try {if (rs != null) rs.close();} catch(Exception e) {}
//			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			try {if (conn != null) conn.close();} catch(Exception e) {}
//		}
//	}
//	
//	
//	public int insert(Project project) throws Exception {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		
//		try {
//			conn = ds.getConnection();
//			stmt = conn.prepareStatement(
//					"INSERT INTO projects"
//					+ " (pname, content, sta_date, end_date, state, cre_date, tags)"
//					+ " VALUES (?, ?, ?, ?, 0, NOW(), ?)");
//			stmt.setString(1, project.getTitle());
//			stmt.setString(2, project.getContent());
//			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
//			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
//			stmt.setString(5, project.getTags());
//			
//			return stmt.executeUpdate();
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			try {if (conn != null) conn.close();} catch(Exception e) {}
//		}
//	}
//	
//	public Project selectOne(int no) throws Exception {
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = ds.getConnection();
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(
//					"SELECT PNO, PNAME, STA_DATE, END_DATE, STATE"
//					+ " FROM PROJECTS WHERE PNO=" + no);
//			rs.next();
//			
//			Project project = new Project()
//					.setNo(rs.getInt("PNO"))
//					.setTitle(rs.getString("PNAME"))
//					.setStartDate(rs.getDate("STA_DATE"))
//					.setEndDate(rs.getDate("END_DATE"))
//					.setState(rs.getInt("STATE"));
//			return project;
//			
//		} catch(Exception e) {
//			throw new Exception("?????? ????????? ??????????????? ?????? ??? ????????????.");
//		} finally {
//			try {if (rs != null) rs.close();} catch(Exception e) {}
//			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			try {if (conn != null) conn.close();} catch(Exception e) {}
//		}
//	}
//	
//	public int update(Project project) throws Exception {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		
//		try {
//			conn = ds.getConnection();
//			stmt = conn.prepareStatement(
//					"UPDATE PROJECTS SET"
//					+ " PNAME=?, CONTENT=?, STA_DATE=?, END_DATE=?, STATE=?, TAGS=?"
//					+ " WHERE PNO=?");
//			stmt.setString(1, project.getTitle());
//			stmt.setString(2, project.getContent());
//			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
//			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
//			stmt.setInt(5, project.getState());
//			stmt.setString(6, project.getTags());
//			stmt.setInt(7, project.getNo());
//			return stmt.executeUpdate();
//			
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			try {if (conn != null) conn.close();} catch(Exception e) {}
//		}
//	}
//	
//	
//	public int delete(int no) throws Exception {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		
//		try {
//			conn = ds.getConnection();
//			stmt = conn.prepareStatement(
//					"DELETE FROM PROJECTS WHERE PNO=?");
//			stmt.setInt(1, no);
//			
//			return stmt.executeUpdate();
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			try {if (conn != null) conn.close();} catch(Exception e) {}
//		}
//		
//	}
}
