package exam.test18;

import java.text.SimpleDateFormat;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test18/beans.xml");
		
		Car car1 = (Car) ctx.getBean("car1");
		System.out.println(car1.getEngine());
		
		// Engine 빈을 선언하지 않았기 때문에 예외가 발생함
		
		/* Error creating bean with name 'car1': 
		 * car1 빈 생성 실패
		 * 
		 * Injection of autowired dependencies failed // Unsatisfied dependency expressed through method 'setEngine' parameter 0; ~
		 * 의존객체 자동 주입 실패
		 * 
		 * Could not autowire method:
		 * public void exam.test18.Car.estEngine(exam.test18.Engine)
		 * 요기서 실패
		 * 
		 * Car.setEngine Annotation에 (required=false)를 주어 에러발생하지 않음.
		 */
	}
}
