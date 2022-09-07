<%@ page import="spms.vo.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="member"
	scope="session"
	class="spms.vo.Member"/>


<%
//Member member = (Member)session.getAttribute("member");
%>

<div style="background-color:#00008b;color:#ffffff;height:20px;padding:5px;">
	SPMS(Simple Project Management System)
	
	<c:if test="${member.email != null}" var="emailnull">
		<span style="float:right;">
		${member.name}
		<a style = "color:white;" href="<%=request.getContextPath()%>/auth/logout.do">로그아웃</a>
	</span>
	</c:if>

</div>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		
	</body>
</html>