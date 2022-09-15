<!-- JSP전용 태그 - 지시자 
- page 지시자
JSP 페이지와 관련된 속성으 ㄹ정의할 때 사용하는 태그
language - 작성할 프로그래밍 언어, 생략시 기본 java
contentType - 출력할 데이터 MIME 타입과 문자 집합
pageEncoding - 출력할 데이터 문자 집합, 기본값 ISO-8859-1
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
    
    
<!-- 스크립트릿 - 자바코드 넣을 때 -->
<%
String v1 = "";
String v2 = "";
String result = "";
String[] selected = {"", "", "", ""};

// 값이 있을 때만 꺼낸다.
if (request.getParameter("v1") != null) {
	v1 = request.getParameter("v1");
	v2 = request.getParameter("v2");
	String op = request.getParameter("op");
	
	result = calculate(
			Integer.parseInt(v1),
			Integer.parseInt(v2),
			op);
	
	if ("+".equals(op)) {
		selected[0] = "selected";
	} else if ("-".equals(op)) {
		selected[1] = "selected";
	} else if ("*".equals(op)) {
		selected[2] = "selected";
	} else if ("/".equals(op)) {
		selected[3] = "selected";
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>계산기</title>
</head>
<body>
<h2>JSP 계산기</h2>
<form action="Calculator.jsp" method="get">
	<input type="text" name="v1" size="4" value="<%=v1%>">
	<select name="op">
		<option value="+" <%=selected[0]%>>+</option>
		<option value="-" <%=selected[1]%>>-</option>
		<option value="*" <%=selected[2]%>>*</option>
		<option value="/" <%=selected[3]%>>/</option>
	</select>
	<input type="text" name="v2" size="4" value="<%=v2%>">
	<input type="submit" value="=">
	<input type="text" size="8" value="<%=result%>"><br>
</form>

</body>
</html>

<%!
private String calculate(int a, int b, String op) {
	int r=0;
	
	if ("+".equals(op)) {
		r = a + b;
	} else if ("-".equals(op)) {
		r = a - b;
	} else if ("*".equals(op)) {
		r = a * b;
	} else if ("/".equals(op)) {
		r = a / b;
	}
	
	return Integer.toString(r);
}
%>