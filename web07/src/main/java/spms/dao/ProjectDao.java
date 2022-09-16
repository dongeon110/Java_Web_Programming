package spms.dao;

import java.util.List;
import java.util.HashMap;
import spms.vo.Project;

public interface ProjectDao {
//	List<Project> selectList() throws Exception;
	List<Project> selectList(HashMap<String, Object> paramMap) throws Exception;
	int insert(Project project) throws Exception;
	Project selectOne(int no) throws Exception;
	int update(Project project) throws Exception;
	int delete(int no) throws Exception;
}
