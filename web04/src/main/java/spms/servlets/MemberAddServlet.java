package spms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>"); // action은 실행할 서블릿 URL주소
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>"); // input type 이 password면 **** 나타남
		out.println("<input type='submit' value='추가'>"); // input 의 submit 타입 = 서버에 요청
		out.println("<input type='reset' value='취소'>"); // input 의 reset 타입 = 입력폼 초기화
		out.println("</form>");
		out.println("</body></html>");
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); // Default ISO-8859-1 (ISO-Latin-1)
		// GET 이면 server.xml에서 Connector에 URIEncoding="UTF-8"을 준다
		
		
		Connection conn = null;
		PreparedStatement stmt = null;
		// Insert Query를 실행할 것이기 때문에 rs는 없다.
		
		try {
			DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mariadb://localhost/test",
					"root",
					"1234");
			stmt = conn.prepareStatement(
					"INSERT INTO members(EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE)"
					+ " VALUES (?, ?, ?, NOW(), NOW())");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			stmt.setString(3, request.getParameter("name"));
			stmt.executeUpdate(); // 결과 레코드를 만들지 않는 DDL이나 DML종류의 SQL문을 실행할 때는 executeUpdate()를 호출한다
			
			response.sendRedirect("list");
			
			
			/* Redirect는 HTML을 출력하지 않는다.
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원등록결과</title>");
			
			out.println("<meta http-equiv='Refresh' content='1; url=list'>"); // meta 태그 추가
			out.println("</head>");
			out.println("<body>");
			out.println("<p>등록 성공입니다<p>");
			out.println("</body></html>");
			
			// Refresh 정보를 응답 헤더에 추가
//			response.addHeader("Refresh", "1;url=list"); // html head태그 안에 meta태그 추가
			*/
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {if (stmt != null) stmt.close();} catch (Exception e) {}
			try {if (conn != null) conn.close();} catch (Exception e) {}
		}
	}
}
