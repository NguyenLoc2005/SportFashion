<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sport Fashion</title>

<style>
body{
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	background-color: #f5f5f5;
}

.khung{
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
}

.login{
	text-align: center;
	border: 1px solid #ccc;
	font-family: 'Times New Roman', Times, serif;
	font-size: 19px;
	padding: 50px 60px;
	border-radius: 10px;
	background: #fff;
	box-shadow: 0 8px 20px rgba(0,0,0,0.1);
}

.login input{
	font-size: 15px;
	margin: 12px 0;
	padding: 8px 10px;
	width: 220px;
	border-radius: 5px;
	border: 1px solid #ccc;
}

.login button{
	font-size: 15px;
	margin-top: 10px;
	padding: 8px 25px;
	border-radius: 5px;
	border: none;
	background-color: #000;
	color: #fff;
	cursor: pointer;
}


.login a{
	display: block;
	margin-top: 15px;
	font-size: 16px;
	color: #555;
	text-decoration: none;
}
</style>

</head>

<body>
    <div class="khung">
        <div class="login">
            <form action="login" method="post">
                <h2>Đăng nhập</h2>

                <input type="text" name="userName" placeholder="Tài khoản"><br>
                <input type="password" name="password" placeholder="Mật khẩu"><br>
				<c:if test="${not empty err}">
    				<p style="color:red">${err}</p>
				</c:if>
                <button type="submit">Đăng nhập</button>
                <a href="clientHome">← Quay lại</a>
            </form>
        </div>
    </div>
</body>
</html>
    