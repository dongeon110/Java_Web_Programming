package spms.controls;

import java.util.Map;

import spms.vo.Member;
import spms.dao.MariaDbMemberDao;
import spms.bind.DataBinding;
import spms.annotation.Component;

import javax.servlet.http.HttpSession;

@Component("/auth/login.do")
public class LogInController implements Controller, DataBinding {
	
	MariaDbMemberDao memberDao;
	public LogInController setMemberDao(MariaDbMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
				"loginInfo", spms.vo.Member.class
		};
	}
	
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member loginInfo = (Member)model.get("loginInfo");
		
		if (loginInfo.getEmail() == null) {
			return "/auth/LogInForm.jsp";
		} else {
//			MemberDao memberDao = (MemberDao) model.get("memberDao");

			Member member = memberDao.exist(
					loginInfo.getEmail(),
					loginInfo.getPassword());
			
			if (member != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LogInFail.jsp";
			}
		}
	}
}
