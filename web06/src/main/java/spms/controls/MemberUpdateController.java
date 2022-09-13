package spms.controls;

import java.util.Map;

import spms.dao.MariaDbMemberDao;
import spms.vo.Member;
import spms.bind.DataBinding;

public class MemberUpdateController implements Controller, DataBinding {
	
	MariaDbMemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MariaDbMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
				"no", Integer.class,
				"member", spms.vo.Member.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member updateMember = (Member) model.get("updateMember");
//		MemberDao memberDao = (MemberDao) model.get("memberDao");
		if (updateMember.getEmail() == null) {
			model.put("member", memberDao.selectOne((int)model.get("no")));
			return "/member/MemberUpdateForm.jsp";
		} else {
			
			memberDao.update(updateMember);
			
			return "redirect:list.do";
		}
	}
}
