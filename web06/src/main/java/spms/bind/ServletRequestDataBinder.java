package spms.bind;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

public class ServletRequestDataBinder {
	public static Object bind(
			ServletRequest request, Class<?> dataType, String dataName) throws Exception {
		
		// 기본타입인지 아닌지 검사
		if (isPrimitiveType(dataType)) {
			return createValueObject(dataType, request.getParameter(dataName));
		}
		
		// 매개변수 이름과 값을 맵 객체에 담아서 반환
		Set<String> paramNames = request.getParameterMap().keySet();
		Object dataObject = dataType.newInstance();
		Method m = null;
		
		// Setter 메서드 찾아서 호출하기
		for (String paramName : paramNames) {
			m = findSetter(dataType, paramName); // Setter 메서드 찾아서 반환
			if (m != null) { // 찾았으면 호출
				m.invoke(dataObject, createValueObject(m.getParameterTypes()[0],
						request.getParameter(paramName)));
			}
		}
		
		return dataObject;
	}
	
	// int, long, float, double, boolean, Date, String
	// 에 대해서 기본타입으로 간주. 만약 byte, short도 하고 싶으면 조건문 추가
	private static boolean isPrimitiveType(Class<?> type) {
		if (type.getName().equals("int") || type == Integer.class ||
				type.getName().equals("long") || type == Long.class ||
				type.getName().equals("float") || type == Float.class ||
				type.getName().equals("double") || type == Double.class ||
				type.getName().equals("boolean") || type == Boolean.class ||
				type == Date.class || type == String.class) {
			return true;
		}
		return false;
	}
	
	
	// 기본타입의 경우 Setter 메서드가 없기 때문에 값을 할당 할 수 없음.
	// 보통 생성사를 호출할 때 값을 할당함. 그래서 createValueObject 메서드를 만듦
	// 이 메서드는 Setter로 값을 할당 할 수 없는 기본 타입에 대해 객체를 생성하는 메서드
	private static Object createValueObject(Class<?> type, String value) {
		if (type.getName().equals("int") || type == Integer.class) {
			return new Integer(value);
		} else if (type.getName().equals("float") || type == Float.class) {
			return new Float(value);
		} else if (type.getName().equals("double") || type == Double.class) {
			return new Double(value);
		} else if (type.getName().equals("boolean") || type == Boolean.class ) {
			return new Boolean(value);
		} else if (type == Date.class) {
			return java.sql.Date.valueOf(value);
		} else {
			return value;
		}
	}
	
	// 메서드 이름이 set으로 시작하는 메서드에 대해 작업 수행
	private static Method findSetter(Class<?> type, String name) {
		Method[] methods = type.getMethods();
		
		String propName = null;
		for (Method m : methods) {
			if (!m.getName().startsWith("set")) continue; // set으로 시작 안하면 무시
			propName = m.getName().substring(3); // set은 제외
			if (propName.toLowerCase().equals(name.toLowerCase())) {
				return m;
			}
		}
		return null;
	}
	
}
