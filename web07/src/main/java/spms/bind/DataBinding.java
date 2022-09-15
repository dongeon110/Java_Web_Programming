package spms.bind;

// 프런트 컨트롤러가 페이지 컨트롤러를 실행하기 전에
// 원하는 데이터가 무엇인지 묻는 것에 대한 호출 규칙 인터페이스

public interface DataBinding {
	// 2개씩 한 쌍으로 "이름", 타입, "이름", 타입 으로 입력
	Object[] getDataBinders();
}
