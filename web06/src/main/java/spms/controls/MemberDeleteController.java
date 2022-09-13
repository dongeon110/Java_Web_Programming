package spms.controls;

import java.util.Map;

import spms.dao.MariaDbMemberDao;
import spms.bind.DataBinding;

public class MemberDeleteController implements Controller, DataBinding {
	
	MariaDbMemberDao memberDao;
	
	public MemberDeleteController setMemberDao(MariaDbMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
				"no", Integer.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
//		MemberDao memberDao = (MemberDao) model.get("memberDao");
		memberDao.delete((int)model.get("no"));
		
		return "redirect:list.do";
	}
}
