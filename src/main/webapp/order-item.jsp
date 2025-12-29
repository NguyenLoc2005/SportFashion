<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Chi tiết đơn hàng</title>

<style>
/* ===== Scope toàn bộ CSS cho trang này ===== */
.order-detail .container {
    width: 80%;
    margin: 30px auto;
}



.order-detail table {
    width: 100%;
    border-collapse: collapse;
}

.order-detail th,
.order-detail td {
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
</style>
</head>

<body>
    <!-- HEADER -->
    <jsp:include page="header.jsp" />

    <!-- CONTENT -->
    <div class="order-detail">
        <div class="container">
           

            <!-- Không có sản phẩm -->
            <c:if test="${empty items}">
                <p style="text-align: center;">Không có sản phẩm trong đơn.</p>
                <p style="text-align: center;">
                    <a href="order">← Quay lại đơn hàng</a>
                </p>
            </c:if>

            <!-- Có sản phẩm -->
            <c:if test="${not empty items}">
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
                            <td>
                                <img src="${it.product.imageURL}" alt="${it.product.name}">
                            </td>
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
                    <a href="order">← Quay lại đơn hàng</a>
                    <a href="clientHome">Tiếp tục mua sắm →</a>
                </div>
            </c:if>
        </div>
    </div>

    <!-- FOOTER -->
    <jsp:include page="footer.jsp" />
</body>
</html>
