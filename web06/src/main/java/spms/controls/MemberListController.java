package spms.controls;

import java.util.Map;
import spms.dao.MemberDao;

public class MemberListController implements Controller {
	
	MemberDao memberDao;
	public MemberListController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
	
		// 외부에서 MemberDao 객체를 주입해 줄 것이기 때문에 더이상 꺼낼 필요가 없음.
//		// PageController 에서 사용할 객체를 Map에서 꺼내기
//		MemberDao memberDao = (MemberDao) model.get("memberDao");
		
		// PageController 가 작업한 결과물을 Map에 담기
		model.put("members", memberDao.selectList());
		
		return "/member/MemberList.jsp";
	}
}
