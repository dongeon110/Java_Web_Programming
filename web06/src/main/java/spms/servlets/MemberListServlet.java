package spms.servlets;

import spms.dao.MariaDbMemberDao;
import spms.vo.Member;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// UI 출력 코드를 제거하고 UI생성 및 출력을 JSP에게 위임한다.
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
		
		try {
			ServletContext sc = this.getServletContext();
//			// ServletContext 보관소에서 저장된 DB를 꺼내쓰기 위해 제거
//			Class.forName(sc.getInitParameter("driver"));
//			conn = DriverManager.getConnection(
//					sc.getInitParameter("url"),
//					sc.getInitParameter("username"),
//					sc.getInitParameter("password"));
			
			
			// Listener 호출 위해 주석처리
//			conn = (Connection) sc.getAttribute("conn");
//			
//			// 사용하기 전에 셋터를 호출하여 ServletContext에서 DB커넥션 객체 주입
//			MemberDao memberDao = new MemberDao();
			// Listener에서 불러옴
			MariaDbMemberDao memberDao = (MariaDbMemberDao)sc.getAttribute("memberDao");
//			memberDao.setConnection(conn);
			
			request.setAttribute("members", memberDao.selectList());
			

//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(
//					"select MNO, MNAME, EMAIL, CRE_DATE" +
//					" from MEMBERS order by MNO ASC");
			
			
			
//			// JSP로 출력을 위임하는 것을 FrontController 에게 위임하기 위하여 주석처리.
//			response.setContentType("text/html; charset=UTF-8");
//			
////			ArrayList<Member> members= new ArrayList<>();
////			
////			while(rs.next()) {
////				members.add(new Member()
////										.setNo(rs.getInt("MNO"))
////										.setName(rs.getString("MNAME"))
////										.setEmail(rs.getString("EMAIL"))
////										.setCreatedDate(rs.getDate("CRE_DATE"))
////										);
////			}
////			
////			// request 에 회원 목록 데이터 보관
////			request.setAttribute("members",  members);
//			
//			// JSP로 출력을 위임
//			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp"); // 경로가 /로 시작하면 웹 애플리케이션 루트
//			// RequestDispatcher 객체를 얻었으면 포워드하거나 인클루드 하면 됨.
//			// 포워드는 해당 서블릿으로 제어권이 넘어가고 다시 돌아오지 않고
//			// 인클루드는 해당 서블릿에게 제어권을 넘긴 후 작업을 끝내면 다시 제어권이 돌아옴
//			rd.include(request, response);
			
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
			
			
		} catch (Exception e) {
//			throw new ServletException(e);
//			// 오류가 발생해도 FrontController가 대신 해줌
//			e.printStackTrace();
//			request.setAttribute("error",  e);
//			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
//			rd.forward(request, response);
			
			// DAO 오류는 여기서 던지도록!
			throw new ServletException(e);
		}
//		} finally {
//			try {if (rs != null) rs.close();} catch(Exception e) {}
//			try {if (stmt != null) stmt.close();} catch(Exception e) {}
////			try {if (conn != null) conn.close();} catch(Exception e) {}
//			}
	}
}
