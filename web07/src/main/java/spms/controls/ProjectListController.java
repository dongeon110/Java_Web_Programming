package spms.controls;

import java.util.Map;
import java.util.HashMap;

import spms.dao.MariaDbProjectDao;
import spms.annotation.Component;
import spms.bind.DataBinding;

@Component("/project/list.do")
public class ProjectListController implements Controller, DataBinding {
	
	MariaDbProjectDao projectDao;
	public ProjectListController setProjectDao(MariaDbProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
				"orderCond", String.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderCond", model.get("orderCond"));
		model.put("projects", projectDao.selectList(paramMap));
		
		return "/project/ProjectList.jsp";
	}
}
