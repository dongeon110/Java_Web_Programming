package spms.controls;

import spms.dao.ProjectDao;
import spms.vo.Project;
import spms.bind.DataBinding;

import java.util.Map;

import spms.annotation.Component;

@Component("/project/update.do")
public class ProjectUpdateController implements Controller, DataBinding {
	
	ProjectDao projectDao;
	
	public ProjectUpdateController setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
				"no", Integer.class,
				"project", spms.vo.Project.class
		};
	}
	
	
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Project project = (Project) model.get("project");
		
		if (project.getTitle() == null) { // 입력폼 요청
			model.put("project", projectDao.selectOne((int)model.get("no")));
			return "/project/ProjectUpdateForm.jsp";
		} else {
			projectDao.update(project);
			
			return "redirect:list.do";
		}
		
	}
}
