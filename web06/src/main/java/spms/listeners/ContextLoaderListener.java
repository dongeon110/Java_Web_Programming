package spms.listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.dbcp2.BasicDataSource;

import spms.dao.MemberDao;
import spms.util.DBConnectionPool;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
//	Connection conn;
//	DBConnectionPool connPool;
//	BasicDataSource ds;

	
	// contextInitialized - AppInitServlet 클래스에 있던 DB 커넥션 객체를 준비하는 코드를 가져옴
	// 이 리스너의 핵심은 웹 애플리케이션이 실행될 때 MemberDao객체를 준비하여 ServletContext에 보관하는 것
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();
			
////			Class.forName(sc.getInitParameter("driver"));
////			conn = DriverManager.getConnection(
////					sc.getInitParameter("url"),
////					sc.getInitParameter("username"),
////					sc.getInitParameter("password"));
//			connPool = new DBConnectionPool(
//					sc.getInitParameter("driver"),
//					sc.getInitParameter("url"),
//					sc.getInitParameter("username"),
//					sc.getInitParameter("password"));
			
//			ds = new BasicDataSource();
//			ds.setDriverClassName(sc.getInitParameter("driver"));
//			ds.setUrl(sc.getInitParameter("url"));
//			ds.setUsername(sc.getInitParameter("username"));
//			ds.setPassword(sc.getInitParameter("password"));
			
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup(
					"java:comp/env/jdbc/test");
			
			
			
			// MemberDao 객체를 ServletContext에 보관
			MemberDao memberDao = new MemberDao();
////			memberDao.setConnection(conn);
//			memberDao.setDbConnectionPool(connPool);
			memberDao.setDataSource(ds);
			
			sc.setAttribute("memberDao", memberDao);
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
////		try {
////			conn.close();
////		} catch (Exception e) {}
//		connPool.closeAll();
//		try { if(ds != null) ds.close(); } catch (SQLException e) {}
		
	}
}
