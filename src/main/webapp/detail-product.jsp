<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chi tiết sản phẩm</title>
</head>
<style>
.detail{
	display: flex;
	gap: 50px;
	text-align: left;
	padding: 50px;
}
.detail .infor a{
	text-decoration: none;
	padding: 4px;
	border: solid;
	margin-right: 10px;
	border-radius: 5px;
}
</style>
<body>
	<jsp:include page="header.jsp" />
	
	<div class="detail">
	<c:if test="${product != null}">
		<div class="img">
	    <img src="${product.imageURL}" width="300">
	    </div>
	    
	    <div class="infor">
		    <p>Tên sản phẩm: ${product.name}</p>
		    <p>Giá: ${product.price}đ</p>
		    <p>Màu sắc: ${product.color}</p>
		    <p>Size: ${product.size}</p>
		    <p>Loại: ${product.type}</p>
		    
		    <a href="clientHome">Quay lại</a>
			<a href="cart?action=add&id=${product.id}">Thêm vào giỏ hàng</a>
			<a href="">Mua ngay</a>
	    </div>
	</c:if>
	
	
	</div>
	
	<jsp:include page="footer.jsp" />

</body>
</html>
