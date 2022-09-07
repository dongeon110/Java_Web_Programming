package spms.controls;

import java.util.Map;

public interface Controller {

	// execute는 FrontController가 PageController에게 일을 시키기 위해 호출하는 메서드
	String execute(Map<String, Object> model) throws Exception;

}
