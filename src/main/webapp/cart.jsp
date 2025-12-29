<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Giỏ hàng</title>
<style>
.cart {
	width: 80%;
	margin: 30px auto;
}

.cart table {
	width: 100%;
	border-collapse: collapse;
}

.cart th, .cart td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: center;
}

.cart img {
	width: 70px;
	border-radius: 6px;
}

.cart button {
	padding: 6px 12px;
	border-radius: 6px;
	border: 1px solid #ccc;
	cursor: pointer;
}

.cart .actions {
	margin-top: 20px;
	text-align: right;
}

.empty {
	text-align: center;
	padding: 30px;
	color: #666;
}
</style>
</head>

<body>
	<jsp:include page="header.jsp" />

	<div class="cart">
		<c:if test="${empty items}">
			<div class="empty">Giỏ hàng của bạn đang trống.</div>
		</c:if>

		<c:if test="${not empty items}">
			<table>
				<tr>
					<th>Ảnh</th>
					<th>Tên</th>
					<th>Giá</th>
					<th>Số lượng</th>
					<th>Thành tiền</th>
					<th>Xóa</th>
				</tr>

				<c:forEach var="it" items="${items}">
					<tr>
						<td><img src="${it.product.imageURL}"></td>
						<td>${it.product.name}</td>
						<td>${it.product.price}đ</td>
						<td>${it.quantity}</td>
						<td>${it.product.price * it.quantity}đ</td>
						<td>
							<!-- FORM XÓA (RIÊNG, KHÔNG LỒNG) -->
							<form action="cart" method="post">
								<input type="hidden" name="action" value="remove"> <input
									type="hidden" name="id" value="${it.product.id}">
								<button type="submit">Xóa</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>

			<!-- FORM THANH TOÁN (RIÊNG) -->
			<div class="actions">
				<form action="order" method="post">
					<button type="submit" style="font-weight: bold;">Thanh
						toán</button>
				</form>
			</div>
		</c:if>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>
