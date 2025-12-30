<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Chi tiết đơn hàng</title>

<style>
.order-detail .container {
	width: 80%;
	margin: 30px auto;
}

.order-detail h2 {
	text-align: center;
	margin-bottom: 16px;
}

.order-detail .ship-box {
	border: 1px solid #ccc;
	border-radius: 8px;
	padding: 12px 14px;
	margin: 12px 0 18px 0;
}

.order-detail .ship-box h3 {
	margin: 0 0 10px 0;
	font-size: 18px;
}

.order-detail .ship-box p {
	margin: 6px 0;
}

.order-detail .ship-box span {
	font-weight: bold;
}

.order-detail table {
	width: 100%;
	border-collapse: collapse;
}

.order-detail th, .order-detail td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: center;
}

.order-detail table img {
	width: 70px;
	border-radius: 6px;
}

.order-detail .total {
	text-align: right;
	margin-top: 15px;
	font-size: 18px;
}

.order-detail .actions {
	display: flex;
	justify-content: space-between;
	margin-top: 20px;
}

.order-detail a {
	text-decoration: none;
	color: #1e90ff;
}

.order-detail a:hover {
	text-decoration: underline;
}

.order-detail .empty {
	text-align: center;
	padding: 30px;
	color: #666;
}
</style>
</head>

<body>
	<jsp:include page="header.jsp" />

	<div class="order-detail">
		<div class="container">

			<h2>Chi tiết đơn #${orderId}</h2>

			<!-- Không có sản phẩm -->
			<c:if test="${empty items}">
				<div class="empty">Không có sản phẩm trong đơn.</div>
				<div style="text-align: center;">
					<a href="order">← Quay lại đơn hàng</a>
				</div>
			</c:if>

			<!-- Có sản phẩm -->
			<c:if test="${not empty items}">

				<!-- THÔNG TIN NHẬN HÀNG -->
				<div class="ship-box">
					<h3>Thông tin nhận hàng</h3>
					<p>
						<span>Người nhận:</span> ${receiverName}
					</p>
					<p>
						<span>SĐT:</span> ${receiverPhone}
					</p>
					<p>
						<span>Địa chỉ:</span> ${receiverAddress}
					</p>

					<c:if test="${not empty note}">
						<p>
							<span>Ghi chú:</span> ${note}
						</p>
					</c:if>
				</div>

				<table>
					<tr>
						<th>Ảnh</th>
						<th>Tên</th>
						<th>Giá</th>
						<th>Số lượng</th>
						<th>Thành tiền</th>
					</tr>

					<c:forEach var="it" items="${items}">
						<tr>
							<td><img src="${it.product.imageURL}"
								alt="${it.product.name}"></td>
							<td>${it.product.name}</td>
							<td>${it.product.price}đ</td>
							<td>${it.quantity}</td>
							<td>${it.product.price * it.quantity}đ</td>
						</tr>
					</c:forEach>
				</table>

				<div class="total">
					<b>Tổng tiền:</b> ${total}đ
				</div>

				<div class="actions">
					<a href="order">← Quay lại đơn hàng</a> <a href="clientHome">Tiếp
						tục mua sắm →</a>
				</div>
			</c:if>

		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>
