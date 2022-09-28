package exam.test11;

import java.text.SimpleDateFormat;
import java.util.Properties;

public class TireFactory {
	// 일반 클래스의 메서드를 Factory 기능을 하도록 하려면 반드시 static으로 선언해야 함.
	// 스프링 IoC 컨테이너의 규정임
	public static Tire createTire(String maker) {
		if (maker.equals("Hankook")) {
			return createHankookTire();
		} else {
			return createKumhoTire();
		}
	}
	
	private static Tire createHankookTire() {
		Tire tire = new Tire();
		tire.setMaker("Hankook");
		
		Properties specProp = new Properties();
		specProp.setProperty("width", "205");
		specProp.setProperty("ratio", "65");
		specProp.setProperty("rim.diameter", "14");
		tire.setSpec(specProp);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			tire.setCreatedDate(dateFormat.parse("2014-5-5"));
		} catch (Exception e) {}
		
		return tire;
	}
	
	private static Tire createKumhoTire() {
		Tire tire = new Tire();
		tire.setMaker("Kumho");
		
		Properties specProp = new Properties();
		specProp.setProperty("width", "185");
		specProp.setProperty("radio", "75");
		specProp.setProperty("rim.diameter", "16");
		tire.setSpec(specProp);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			tire.setCreatedDate(dateFormat.parse("2014-3-1"));
		} catch (Exception e) {}
		
		return tire;
	}
}
