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

/* form thanh toán đơn giản */
.pay-form {
	margin-top: 18px;
	border: 1px solid #ccc;
	border-radius: 8px;
	padding: 14px;
	display: none;
}

.pay-form h3 {
	margin: 0 0 12px 0;
}

.pay-form .row {
	margin: 10px 0;
	text-align: left;
}

.pay-form label {
	display: block;
	margin-bottom: 6px;
	font-weight: bold;
}

.pay-form input, .pay-form textarea {
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 6px;
}

.pay-form .btns {
	display: flex;
	justify-content: flex-end;
	gap: 10px;
	margin-top: 10px;
}

.pay-form .btns button {
	padding: 8px 14px;
}

.readonly {
	background: #f5f5f5;
}
</style>
</head>

<body>
	<jsp:include page="header.jsp" />

	<div class="cart">
		<c:if test="${empty items}">
			<div class="empty" style="height: 50vh;">Giỏ hàng của bạn đang trống.</div>
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
							<form action="cart" method="post">
								<input type="hidden" name="action" value="remove"> <input
									type="hidden" name="id" value="${it.product.id}">
								<button type="submit">Xóa</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>

			<div class="actions">
				<!-- nút chỉ để hiện form -->
				<button type="button" onclick="togglePayForm()"
					style="font-weight: bold;">Thanh toán</button>
			</div>

			<!-- FORM THANH TOÁN HIỆN NGAY TRANG -->
			<div id="payForm" class="pay-form">
				<h3>Thông tin nhận hàng</h3>

				<form action="order" method="post">
					<div class="row">
						<label>Tên đăng nhập</label> <input class="readonly" type="text"
							value="${sessionScope.user.userName}" readonly>
					</div>

					<div class="row">
						<label>Tên người nhận</label> <input type="text"
							name="receiverName" required>
					</div>

					<div class="row">
						<label>Số điện thoại</label> <input type="text"
							name="receiverPhone" required>
					</div>

					<div class="row">
						<label>Địa chỉ nhận hàng</label>
						<textarea name="receiverAddress" rows="3" required></textarea>
					</div>

					<div class="row">
						<label>Ghi chú (không bắt buộc)</label>
						<textarea name="note" rows="2"></textarea>
					</div>

					<input type="hidden" name="action" value="checkout">

					<div class="btns">
						<button type="button" onclick="togglePayForm()">Hủy</button>
						<button type="submit" style="font-weight: bold;">Xác nhận
							đặt hàng</button>
					</div>
				</form>
			</div>
		</c:if>
	</div>

	<script>
		function togglePayForm() {
			var f = document.getElementById("payForm");
			if (f.style.display === "block")
				f.style.display = "none";
			else
				f.style.display = "block";
		}
	</script>

	<jsp:include page="footer.jsp" />
</body>
</html>
