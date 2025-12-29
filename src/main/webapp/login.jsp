<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sport Fashion</title>
</head>
<style>
.khung {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.login{
    border: 1px solid #ccc;
    width: fit-content;
    padding: 30px 40px;
    border-radius: 12px;
}

.login h2{
    text-align: center;
    margin-bottom: 20px;
}

.login button{
    display: block;
    margin: 0 auto;
    padding: 6px 20px;
    border-radius: 6px;
    cursor: pointer;
}

.login input{
    border-radius: 6px;
    padding: 6px 10px;
    width: 220px;
}

.login a{
    display: block;
    text-align: left;
   text-decoration: none;
}
.login p{
    text-align: center;
}

</style>

<body>
    <div class="khung">
        <div class="login">
            <form action="login" method="post">
                <h2>Đăng nhập</h2>

                <input type="text" name="userName" placeholder="Tài khoản"><br><br>
                <input type="password" name="password" placeholder="Mật khẩu"><br><br>
				<c:if test="${not empty err}">
    				<p style="color:red">${err}</p>
				</c:if><br>
                <button type="submit">Đăng nhập</button><br><br>
                <a href="clientHome">← Quay lại</a>
            </form>
        </div>
    </div>
</body>
</html>
    