<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
.main{
	font-family: 'Times New Roman', Times, serif;
    font-size: 19px; 
    margin-left:40px;
}
.main h3{
    margin-left: 50px;
}
.main a{
    text-decoration: none;
}
</style>
<body>
	<jsp:include page="header.jsp" />
	
	<div class="main">
		<h2>Thông tin cá nhân:</h2>
		<h3>Tên đăng nhập: ${sessionScope.user.userName }</h3>
		<h3>Vai trò: ${sessionScope.user.role }</h3>
		<a href="logout.jsp">Đăng xuất</a>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>