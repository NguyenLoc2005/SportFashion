<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đơn hàng</title>
<style>
.container {
	width: 80%;
	margin: 30px auto;
}


table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: center;
}

a {
	text-decoration: none;
}

.actions {
	display: flex;
	justify-content: space-between;
	margin: 10px 0;
}

.badge {
	padding: 4px 10px;
	border: 1px solid #ccc;
	border-radius: 999px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" />

	<div class="container">

		
	

		<c:if test="${not empty orders}">
			<table>
				<tr>
					<th>Mã đơn</th>
					<th>Tổng tiền</th>
					<th>Trạng thái</th>
					<th>Chi tiết</th>
				</tr>

				<c:forEach var="o" items="${orders}">
					<tr>
						<td>#${o.id}</td>
						<td>${o.total}đ</td>
						<td><span class="badge">${o.status}</span></td>
						<td><a href="order?id=${o.id}">Xem</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>
