package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import org.reflections.Reflections;

import javax.naming.Context;
import javax.naming.InitialContext;

import spms.annotation.Component;

public class ApplicationContext {
	// 객체를 저장할 보관소 - 해시테이블
	// 해시 테이블에서 객체를 꺼낼 getter 메서드도 정의
	Hashtable<String, Object> objTable = new Hashtable<String, Object>();
	
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	// 프로퍼티 파일의 로딩
	public ApplicationContext(String propertiesPath) throws Exception {
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		prepareObjects(props);
		prepareAnnotationObjects();
		injectDependency();
	}
	
	private void prepareAnnotationObjects() throws Exception {
		Reflections reflector = new Reflections("");
		
		Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
		String key = null;
		
		for(Class<?> clazz : list) {
			key = clazz.getAnnotation(Component.class).value();
			objTable.put(key, clazz.newInstance());
		}
	}
	
	
	// 로딩후 그에 따라 객체를 준비
	private void prepareObjects(Properties props) throws Exception {
		// JNDI객체를 찾을 때 사용할 InitialContext
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		
		// 반복문을 통해 프로퍼티에 들어있는 정보를 꺼내 객체 생성
		for (Object item : props.keySet()) {
			key = (String) item;
			value = props.getProperty(key);
			if (key.startsWith("jndi.")) {
				objTable.put(key, ctx.lookup(value));
			} else {
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	// 톰캣 서버로부터 객체를 가져오거나(ex:DataSource)
	// 직접 생성했으면 (ex:MemberDao)
	// 각 객체가 필요로하는 의존 객체 할당
	private void injectDependency() throws Exception {
		for (String key:objTable.keySet()) {
			if (!key.startsWith("jndi.")) { // jndi. 로 시작하는 경우 톰캣 서버에서 제공하는 객체라서 의존객체 주입하면 안됨
				callSetter(objTable.get(key));
			}
		}
	}
	
	// callSetter 는 매개변수로 주어진 객체에 대해 셋터 메서드를 찾아서 호출하는 일을 함.
	private void callSetter(Object obj) throws Exception {
		Object dependency = null;
		for (Method m : obj.getClass().getMethods()) {
			if (m.getName().startsWith("set")) {
				dependency = findObjectByType(m.getParameterTypes()[0]);
				if (dependency != null) {
					m.invoke(obj, dependency);
				}
			}
		}
	}
	
	// 셋터 메서드를 호출할 때 넘겨줄 의존 객체를 찾는 일
	private Object findObjectByType(Class<?> type) {
		for (Object obj : objTable.values()) {
			if (type.isInstance(obj)) {
				return obj;
			}
		}
		
		return null;
	}
}
