package spms.controls;

import java.util.Map;

import spms.dao.MariaDbProjectDao;
import spms.vo.Project;
import spms.bind.DataBinding;
import spms.annotation.Component;

@Component("/project/add.do")
public class ProjectAddController implements Controller, DataBinding {
	
	MariaDbProjectDao projectDao;
	
	public ProjectAddController setProjectDao(MariaDbProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object [] {
				"project", spms.vo.Project.class
		};
	}
	
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Project project = (Project) model.get("project");
		
		if (project.getTitle() == null) { // GET 입력폼 요청
			return "/project/ProjectForm.jsp";
		} else { // POST 등록 요청
			projectDao.insert(project);
			
			return "redirect:list.do";
		}
	}
}
