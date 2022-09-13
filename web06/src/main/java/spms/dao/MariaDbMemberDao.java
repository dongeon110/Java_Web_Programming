package spms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;


import spms.vo.Member;
import spms.util.DBConnectionPool;

public class MariaDbMemberDao implements MemberDao {

	// DB Connection pool 적용을 위해 주석처리
//	Connection connection;
//	
//	public void setConnection(Connection connection) {
//		this.connection = connection;
//	}
	
//	DBConnectionPool connPool;
	DataSource ds;
	
	// DI
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	
//	public void setDbConnectionPool(DBConnectionPool connPool) {
//		this.connPool = connPool;
//	}
	
	
	
	public List<Member> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
//			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO, MNAME, EMAIL, CRE_DATE" +
					" FROM MEMBERS" + 
					" ORDER BY MNO ASC");
			
			ArrayList<Member> members = new ArrayList<Member>();
			
			while (rs.next()) {
				members.add(new Member()
						.setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"))
						.setCreatedDate(rs.getDate("CRE_DATE")) );
			}
			
			return members;
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			if (connection != null) connPool.returnConnection(connection);
			try {if (connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	// MemberAddServlet
	public int insert(Member member) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
//			connection = connPool.getConnection();
			stmt = connection.prepareStatement(
					"INSERT INTO members(EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE)"
					+ " VALUES (?, ?, ?, NOW(), NOW())");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			if (connection != null) connPool.returnConnection(connection);
			try {if (connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	// MemberDeleteServlet
	public int delete(int no) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ds.getConnection();
//			connection = connPool.getConnection();
			stmt = connection.prepareStatement(
					"DELETE FROM members WHERE MNO=?");
			stmt.setInt(1, no);
			
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			if (connection != null) connPool.returnConnection(connection);
			try {if (connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	// MemberUpdateServlet - selectOne, update
	public Member selectOne(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
//			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO, EMAIL, MNAME, CRE_DATE FROM members"
					+ " WHERE MNO=" + no);
			rs.next();
			
			Member member = new Member();
			member	.setNo(rs.getInt("MNO"))
					.setEmail(rs.getString("EMAIL"))
					.setName(rs.getString("MNAME"))
					.setCreatedDate(rs.getDate("CRE_DATE"));
			return member;
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			if (connection != null) connPool.returnConnection(connection);
			try {if (connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	public int update(Member member) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ds.getConnection();
//			connection = connPool.getConnection();
			stmt = connection.prepareStatement(
					"UPDATE MEMBERS SET EMAIL=?, MNAME=?, MOD_DATE=now() WHERE MNO=?");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			if (connection != null) connPool.returnConnection(connection);
			try {if (connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	// LoginServlet
	public Member exist(String email, String password) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
//			connection = connPool.getConnection();
			stmt = connection.prepareStatement(
					"SELECT MNAME, EMAIL FROM MEMBERS WHERE EMAIL=? AND PWD=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				Member member = new Member()
					.setName(rs.getString("MNAME"))
					.setEmail(rs.getString("EMAIL"));
				return member;
			}
			return null;
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			if (connection != null) connPool.returnConnection(connection);
			try {if (connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	
}
