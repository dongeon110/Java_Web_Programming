package spms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import spms.vo.Member;

public class MemberDao {

	Connection connection;
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public List<Member> selectList() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
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
		}
	}
	
	// MemberAddServlet
	public int insert(Member member) throws Exception {
		PreparedStatement stmt = null;
		
		try {
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
		}
	}
	
	// MemberDeleteServlet
	public int delete(Integer no) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(
					"DELETE FROM members WHERE MNO=?");
			stmt.setInt(1, no);
			
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
	}
	
	// MemberUpdateServlet - selectOne, update
	public Member selectOne(Integer no) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
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
		}
	}
	
	public int update(Member member) throws Exception {
		PreparedStatement stmt = null;
		try {
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
		}
	}
	
	// LoginServlet
	public Member exist(String email, String password) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
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
		}
	}
	
	
}
