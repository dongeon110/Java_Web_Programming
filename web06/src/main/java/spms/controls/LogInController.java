package spms.controls;

import java.util.Map;

import spms.vo.Member;
import spms.dao.MemberDao;

import javax.servlet.http.HttpSession;

public class LogInController implements Controller {
	
	MemberDao memberDao;
	public LogInController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("email") == null) {
			return "/auth/LogInForm.jsp";
		} else {
//			MemberDao memberDao = (MemberDao) model.get("memberDao");
			Member member = memberDao.exist((String)model.get("email"), (String)model.get("password"));
			
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
