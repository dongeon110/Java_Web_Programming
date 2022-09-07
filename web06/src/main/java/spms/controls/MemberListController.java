package spms.controls;

import java.util.Map;
import spms.dao.MemberDao;

public class MemberListController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
		// PageController 에서 사용할 객체를 Map에서 꺼내기
		MemberDao memberDao = (MemberDao) model.get("memberDao");
		
		// PageController 가 작업한 결과물을 Map에 담기
		model.put("members", memberDao.selectList());
		
		return "/member/MemberList.jsp";
	}
}
