<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<style>
body{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
header{
    display: flex;
    text-align: center;
    align-items: center;
    font-family: 'Times New Roman', Times, serif;
    font-size: 19px;
    border-bottom: solid;
}


.logo{
    flex: 1

}
.menu{
    flex:4;
    display: flex;
    justify-content: space-around;
}
.menu a{
    text-decoration: none;
    
}

.others{
    flex:4;
    display: flex;
    justify-content: flex-end;
    gap: 30px;
    padding-right: 30px;
}
.others input{
    font-size: 15px;
}

</style>
<body>
    <header>
        <div class="logo">
            <a href="clientHome"><img src="https://rubicmarketing.com/wp-content/uploads/2023/02/logo-man-city.jpg" width="150"></a>
        </div>
		
        <div class="menu">
            <a href="clientHome?action=filter&type=tshirtsport">Áo thể thao</a>
            <a href="clientHome?action=filter&type=trousersport">Quần thể thao</a>
            <a href="clientHome?action=filter&type=accessory">Phụ kiện</a>
        </div>

        <div class="others">
            <form action="clientHome" method="get">
            	<input type="hidden" name="action" value="search">
    			<input type="text" name="name" placeholder="Tìm kiếm" style="border-radius: 7px;">
			</form>
			
			<c:choose>
    			<c:when test="${not empty sessionScope.user}">
        			<a href="cart"><img src="https://cdn-icons-png.flaticon.com/128/3144/3144456.png" width="25"></a>
        			<a href="order"><img src="https://cdn-icons-png.flaticon.com/128/839/839860.png" width="25"></a>
        			<a href="profile.jsp"><img src="https://cdn-icons-png.flaticon.com/128/456/456212.png" width="25"></a>
   				</c:when>
    			<c:otherwise>
    				<a href="login.jsp"><img src="https://cdn-icons-png.flaticon.com/128/3144/3144456.png" width="25"></a>
        			<a href="login.jsp"><img src="https://cdn-icons-png.flaticon.com/128/839/839860.png" width="25"></a>
        			<a href="login.jsp"><img src="https://cdn-icons-png.flaticon.com/128/456/456212.png" width="25"></a>
    			</c:otherwise>
			</c:choose>
        </div>

    </header>
</body>
</html>