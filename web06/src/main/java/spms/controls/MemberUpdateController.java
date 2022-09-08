package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller {
	
	MemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
//		MemberDao memberDao = (MemberDao) model.get("memberDao");
		if (model.get("updateMember") == null) {
			model.put("member", memberDao.selectOne((int)model.get("no")));
			return "/member/MemberUpdateForm.jsp";
		} else {
			Member updateMember = (Member) model.get("updateMember");
			memberDao.update(updateMember);
			
			return "redirect:list.do";
		}
	}
}
