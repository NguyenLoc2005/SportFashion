<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<style>
main {
    text-align: center;
}

.grid-view {
    display: grid;
    grid-template-columns: repeat(4, 1fr); /* 4 cột */
    gap: 20px; /* khoảng cách giữa sản phẩm */
    margin: 30px 0;
}

.item {
    background: #fafafa;
    text-align: center;
    padding: 10px;
}

.item img {
    width: 100%;
}

.item .name {
    margin-top: 5px;
    text-align: left;
    
}

.item .price {
    margin-top: 5px;
    text-align: left;
}
</style>
<body>
<main>
		<div class="grid-view">
			<c:forEach var="product" items="${products}">
				<div class="item">
					<img src="${product.imageURL}" alt="${product.name }">
					<div class="name" style="font-size: 20px">
						<b>${product.name}</b>
					</div>
					<div class="price" style="font-size: 22px">
						<b>Giá: ${product.price }đ</b>
					</div>
					<a href="clientHome?action=detail-product&id=${product.id}">Xem chi tiết</a>
				</div>

			</c:forEach>
		</div>
	</main>
</body>
</html>