package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;
import spms.dao.MemberDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		
		try {
			ServletContext sc = this.getServletContext();
			
//			Class.forName(sc.getInitParameter("driver"));
//			conn = DriverManager.getConnection(
//					sc.getInitParameter("url"),
//					sc.getInitParameter("username"),
//					sc.getInitParameter("password")); // 초기화 매개변수를 이요하여 데이터베이스 연결
			
//			conn = (Connection) sc.getAttribute("conn");
//			MemberDao memberDao = new MemberDao();
//			memberDao.setConnection(conn);
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
			
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(
//					"select MNO, EMAIL, MNAME, CRE_DATE from MEMBERS" +
//					" where MNO=" + request.getParameter("no"));
//			rs.next();
//			Member member = new Member();
//			member	.setNo(rs.getInt("MNO"))
//					.setEmail(rs.getString("EMAIL"))
//					.setName(rs.getString("MNAME"))
//					.setCreatedDate(rs.getDate("CRE_DATE"));
			request.setAttribute("member", memberDao.selectOne(Integer.parseInt(request.getParameter("no"))));

			
//			// Change Page Controller
//			response.setContentType("text/html; charset=UTF-8");
////			PrintWriter out = response.getWriter();
////			out.println("<html><head><title>회원정보</title></head>");
////			out.println("<body><h1>회원정보</h1>");
////			out.println("<form action='update' method='post'>"); // 회원 상세페이지에서 바로 값을 변경할 수 있도록 입력폼 형태로 만듦
////			out.println("번호: <input type='text' name='no' value='" + request.getParameter("no") + "'readonly><br>"); // PK라서 변경할 수 없기 때문에 readonly
////			out.println("이름: <input type='text' name='name' value='" + rs.getString("MNAME") + "'><br>");
////			out.println("이메일: <input type='text' name='email' value='" + rs.getString("EMAIL") + "'><br>");
////			out.println("가입일: " + rs.getDate("CRE_DATE") + "<br>");
////			out.println("<input type='submit' value='저장'>");
////			out.println(
////					"<input type='button' value='삭제' onclick='location.href=" +
////					"\"delete?no=" + request.getParameter("no") + "\"'>");
////			out.println("<input type='button' value='취소' onclick='location.href=\"list\"'>");
////			out.println("</form>");
////			out.println("</body></html>");
//			
//			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
//			rd.include(request, response);
			request.setAttribute("viewUrl", "/member/MemberUpdateForm.jsp");
			
			
		} catch (Exception e) {
//			throw new ServletException(e);
//			e.printStackTrace();
//			request.setAttribute("error", e);
//			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
//			rd.forward(request, response);
			throw new ServletException(e);
		}
//		} finally {
//			try {if (rs != null) rs.close();} catch(Exception e) {}
//			try {if (stmt != null) stmt.close();} catch(Exception e) {}
////			try {if (conn != null) conn.close();} catch(Exception e) {}
//		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
//		Connection conn = null;
//		PreparedStatement stmt = null;
		try {
			ServletContext sc = this.getServletContext();
//			Class.forName(sc.getInitParameter("driver"));
//			conn = DriverManager.getConnection(
//					sc.getInitParameter("url"),
//					sc.getInitParameter("username"),
//					sc.getInitParameter("password"));
//			conn = (Connection) sc.getAttribute("conn");
//			MemberDao memberDao = new MemberDao();
//			memberDao.setConnection(conn);
			
			
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
			Member updateMember = new Member()
							.setName(request.getParameter("name"))
							.setEmail(request.getParameter("email"))
							.setNo(Integer.parseInt(request.getParameter("no")));
			memberDao.update(updateMember);
			
//			stmt = conn.prepareStatement(
//					"UPDATE MEMBERS SET EMAIL=?, MNAME=?, MOD_DATE=now() WHERE MNO=?");
//			stmt.setString(1, request.getParameter("email"));
//			stmt.setString(2, request.getParameter("name"));
//			stmt.setInt(3, Integer.parseInt(request.getParameter("no")));
//			stmt.executeUpdate();
			
			
//			response.sendRedirect("list");
			request.setAttribute("viewUrl", "redirect:list.do");
			
		} catch (Exception e) {
//			throw new ServletException(e);
//			e.printStackTrace();
//			request.setAttribute("error", e);
//			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
//			rd.forward(request, response);
			throw new ServletException(e);
		}
//		} finally {
//			try {if (stmt != null) stmt.close();} catch(Exception e) {}
////			try {if (conn != null) conn.close();} catch(Exception e) {}
//		}
	}
}
