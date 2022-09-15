package spms.controls;

import java.util.Map;

import spms.dao.ProjectDao;
import spms.bind.DataBinding;
import spms.annotation.Component;

@Component("/project/delete.do")
public class ProjectDeleteController implements Controller, DataBinding{
	ProjectDao projectDao;
	
	public ProjectDeleteController setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	public Object [] getDataBinders() {
		return new Object[] {
				"no", Integer.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		projectDao.delete((int)model.get("no"));
		
		return "redirect:list.do";
	}
}
