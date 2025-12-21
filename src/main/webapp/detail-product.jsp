<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chi tiết sản phẩm</title>
</head>
<body>

<jsp:include page="header.jsp" />

<c:if test="${product != null}">
    <h2>${product.name}</h2>
    <img src="${product.imageURL}" width="300">
    <p>Giá: ${product.price}đ</p>
    <p>Màu sắc: ${product.color}</p>
    <p>Size: ${product.size}</p>
    <p>Loại: ${product.type}</p>
</c:if>

<a href="clientHome">← Quay lại</a>
<a href="">Thêm vào giỏ hàng</a>
<a href="">Mua ngay</a>

<jsp:include page="footer.jsp" />

</body>
</html>
