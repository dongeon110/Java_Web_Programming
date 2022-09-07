package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import spms.vo.Member;
import spms.controls.*;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		try {
			ServletContext sc = this.getServletContext();
			
			HashMap<String, Object> model = new HashMap<>();
			model.put("memberDao", sc.getAttribute("memberDao"));
			
			String pageControllerPath = null;
			Controller pageController = null;
			
			// 페이지 컨트롤러로 위임
			// 서블릿 경로에 따라 조건문을 사용하여 적절한 페이지 컨트롤러를 인클루딩
			if ("/member/list.do".equals(servletPath)) {
				pageController = new MemberListController();
//				pageControllerPath = "/member/list";
			} else if ("/member/add.do".equals(servletPath)) {
//				pageControllerPath = "/member/add";
				pageController = new MemberAddController();
				
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
							.setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password"))
							.setName(request.getParameter("name")));
				}
			} else if ("/member/update.do".equals(servletPath)) {
				pageControllerPath = "/member/update";
				if (request.getParameter("email") != null) {
					request.setAttribute("member", new Member()
							.setNo(Integer.parseInt(request.getParameter("no")))
							.setEmail(request.getParameter("email"))
							.setName(request.getParameter("name")));
				}
			} else if ("/member/delete.do".equals(servletPath)) {
				pageControllerPath = "/member/delete";
			} else if ("/auth/login.do".equals(servletPath)) {
				pageControllerPath = "/auth/login";
			} else if ("/auth/logout.do".equals(servletPath)) {
				pageControllerPath = "/auth/logout";
			}
			
//			// Controller
//			RequestDispatcher rd = request.getRequestDispatcher(pageControllerPath);
//			rd.include(request, response);
//			
//			String viewUrl = (String) request.getAttribute("viewUrl");
//			if (viewUrl.startsWith("redirect:")) { // redirect: 로 시작하면 인클루딩 대신 sendRedirect
//				response.sendRedirect(viewUrl.substring(9));
//				return;
//			} else {
//				rd = request.getRequestDispatcher(viewUrl);
//				rd.include(request, response);
//			}
			String viewUrl = pageController.execute(model);
			
			for (String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}
			
			if (viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
