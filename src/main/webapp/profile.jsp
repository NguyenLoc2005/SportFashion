<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thông tin cá nhân</title>

<style>
.main {
    font-family: 'Times New Roman', Times, serif;
    font-size: 18px;
    width: 60%;
    margin: 40px auto;
}

.main h2 {
    margin-bottom: 20px;
    border-bottom: 1px solid #ccc;
    padding-bottom: 8px;
}

.main p {
    margin: 10px 0;
}

.main span {
    font-weight: bold;
}

.main a {
    display: inline-block;
    margin-top: 15px;
    text-decoration: none;
    color: #0066cc;
}

.main a:hover {
    text-decoration: underline;
}
</style>
</head>

<body>
    <jsp:include page="header.jsp" />

    <div class="main">
        <h2>Thông tin cá nhân</h2>

        <p><span>Tên đăng nhập:</span> ${sessionScope.user.userName}</p>
        <p><span>Vai trò:</span> ${sessionScope.user.role}</p>

        <a href="logout.jsp">Đăng xuất</a>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>
