package spms.controls;

import java.util.Map;

import spms.dao.MariaDbProjectDao;
import spms.annotation.Component;

@Component("/project/list.do")
public class ProjectListController implements Controller {
	
	MariaDbProjectDao projectDao;
	public ProjectListController setProjectDao(MariaDbProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		model.put("projects", projectDao.selectList());
		
		return "/project/ProjectList.jsp";
	}
}
