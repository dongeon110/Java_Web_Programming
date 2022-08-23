package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;


@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// JDBC 객체 주소를 보관할 참조 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// 데이터베이스 관련 코드를 위한 try~ catch~
		try {
			DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mariadb://localhost/test", // test는 DB명
					"root",
					"1234");
			stmt = conn.createStatement(); // createStatement() 가 반환하는 것은 java.sql.Statement 인터페이스의 구현체. 이 객체를 통해 DB에 SQL문을 보낼 수 있음.
			rs = stmt.executeQuery(
					"select MNO, MNAME, EMAIL, CRE_DATE" +
					" from MEMBERS order by MNO ASC");
			
			// HTML 페이지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			out.println("<p><a href='add'>신규 회원</a></p>");
			while(rs.next()) { // row 별로 next()로 다음 row가져옴. 받으면 true 없으면 false
				out.println(
						rs.getInt("MNO") + ", " +
						"<a href='update?no=" + rs.getInt("MNO") + "'>" +
						rs.getString("MNAME") + "</a>, " +
						rs.getString("EMAIL") + ", " +
						rs.getDate("CRE_DATE") + "<br>");
			}
			out.println("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			// 자원 해제 - 자원해제는 역순으로 rs - stmt - conn // 오류나도 특별히 할 작업이 없어서 여기서는 빈 블록{}
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
			}
	}
}
