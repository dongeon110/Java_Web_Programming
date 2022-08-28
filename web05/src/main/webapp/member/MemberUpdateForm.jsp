<%@ page import = "spms.vo.Member" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원정보</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>

<h1>회원정보</h1>
<jsp:useBean id="members"
	scope="request"
	class="java.util.ArrayList"
	type="java.util.ArrayList<spms.vo.Member>"/>
	
<form action='update' method='post'>
번호: <input type='text' name='no' value='<%member.getNo() %>' readonly><br>
이름: <input type='text' name='name' value='<%member.getName() %>'><br>
이메일: <input type='text' name='email' vlaue'<%member.getEmail() %>'><br>
가입일: <%member.getCreatedDate() %> <br>
<input type='submit' value='저장'>
<input type='button' value='삭제' onclick='location.href="delete?no=<%member.getNo() %>"'>
</form>

</body>
</html>

