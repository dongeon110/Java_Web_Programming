package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
		if (model.get("member") == null) { // 입력폼을 요청할 때
			return "/member/MemberForm.jsp";
		} else { // 등록을 요청할 때
			MemberDao memberDao = (MemberDao)model.get("memberDao");
			
			Member member = (Member) model.get("member");
			memberDao.insert(member);
			
			return "redirect:list.do";
		}
		
		/* MemberAddServlet 의 경우에는 HttpServlet 이었기 때문에 Get Request와 Post Request를
		 * 구분하여 처리 할 수 있었다.
		 * 하지만 얘는 안됨.
		 * 그래서 Member가 들어있으면 Post, 없으면 Get 요청으로 간주했다.
		 */
		
		
	}
}
