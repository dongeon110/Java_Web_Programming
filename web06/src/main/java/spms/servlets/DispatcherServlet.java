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
import javax.servlet.http.HttpSession;

import spms.vo.Member;
import spms.controls.*;
import spms.listeners.ContextLoaderListener;
import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.context.ApplicationContext;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		try {
//			ServletContext sc = this.getServletContext();
			ApplicationContext ctx = ContextLoaderListener.getApplicationContext();
			
			HashMap<String, Object> model = new HashMap<>();
			// IoC하면서 memberDao 따로 필요없어짐
//			model.put("memberDao", sc.getAttribute("memberDao"));
			model.put("session", request.getSession());
			
			// pageController는 ServletContext에 저장되어있음.
//			String pageControllerPath = null;
//			Controller pageController = (Controller) sc.getAttribute(servletPath);
			Controller pageController = (Controller) ctx.getBean(servletPath);
			
			if (pageController == null) {
				throw new Exception("요청한 서비스를 찾을 수 없습니다.");
			}
			
			if (pageController instanceof DataBinding) {
				prepareRequestData(request, model, (DataBinding)pageController);
			}
			
			
			
//			// 페이지 컨트롤러로 위임
//			// 서블릿 경로에 따라 조건문을 사용하여 적절한 페이지 컨트롤러를 인클루딩
//			if ("/member/list.do".equals(servletPath)) {
////				pageController = new MemberListController();
////				pageControllerPath = "/member/list";
//			} else if ("/member/add.do".equals(servletPath)) {
////				pageControllerPath = "/member/add";
////				pageController = new MemberAddController();
//				
//				if (request.getParameter("email") != null) {
//					model.put("member", new Member()
//							.setEmail(request.getParameter("email"))
//							.setPassword(request.getParameter("password"))
//							.setName(request.getParameter("name")));
//				}
//			} else if ("/member/update.do".equals(servletPath)) {
////				pageControllerPath = "/member/update";
////				pageController = new MemberUpdateController();
//				
////				model.put("no", Integer.parseInt(request.getParameter("no")));
//				
//				if (request.getParameter("email") != null) {
//					model.put("updateMember", new Member()
//							.setNo(Integer.parseInt(request.getParameter("no")))
//							.setEmail(request.getParameter("email"))
//							.setName(request.getParameter("name")));
//				}
//			} else if ("/member/delete.do".equals(servletPath)) {
////				pageControllerPath = "/member/delete";
////				pageController = new MemberDeleteController();
//				
//				model.put("no", Integer.parseInt(request.getParameter("no")));
//			} else if ("/auth/login.do".equals(servletPath)) {
////				pageControllerPath = "/auth/login";
////				pageController = new LogInController();
//				
//				if (request.getParameter("email") != null) {
//					model.put("loginInfo", new Member()
//							.setEmail(request.getParameter("email"))
//							.setPassword(request.getParameter("password")));
//				}
//				
//			} else if ("/auth/logout.do".equals(servletPath)) {
////				pageControllerPath = "/auth/logout";
////				pageController = new LogOutController();
//				
//			}
			
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
	
	private void prepareRequestData(
			HttpServletRequest request,
			HashMap<String, Object> model,
			DataBinding dataBinding) throws Exception {
		Object[] dataBinders = dataBinding.getDataBinders();
		String dataName = null;
		Class<?> dataType = null;
		Object dataObj = null;
		
		for (int i = 0; i < dataBinders.length; i+=2) {
			dataName = (String) dataBinders[i];
			dataType = (Class<?>) dataBinders[i+1];
			dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
			model.put(dataName, dataObj);
		}
	}
	
}
