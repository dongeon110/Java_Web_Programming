package spms.controls;

import java.util.HashMap;
import java.util.Map;
import spms.dao.MariaDbMemberDao;
import spms.annotation.Component;
import spms.bind.DataBinding;

@Component("/member/list.do")
public class MemberListController implements Controller, DataBinding{
	
	MariaDbMemberDao memberDao;
	public MemberListController setMemberDao(MariaDbMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
				"orderCond", String.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
	
		// 외부에서 MemberDao 객체를 주입해 줄 것이기 때문에 더이상 꺼낼 필요가 없음.
//		// PageController 에서 사용할 객체를 Map에서 꺼내기
//		MemberDao memberDao = (MemberDao) model.get("memberDao");
		
		// PageController 가 작업한 결과물을 Map에 담기
		
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderCond", model.get("orderCond"));
		model.put("members", memberDao.selectList(paramMap));
		
		return "/member/MemberList.jsp";
	}
}
