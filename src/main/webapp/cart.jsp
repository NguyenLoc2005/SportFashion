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
.cart h2 {
    text-align: center;
    margin-bottom: 20px;
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
.cart .total {
    text-align: right;
    margin-top: 15px;
    font-size: 18px;
}
.cart .actions {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}
.cart a {
    text-decoration: none;
}
</style>
</head>

<body>
    <jsp:include page="header.jsp" />

    <div class="cart">
        <h2>Giỏ hàng</h2>

        <c:if test="${empty items}">
            <p style="text-align:center;">Giỏ hàng trống.</p>
            <p style="text-align:center;"><a href="clientHome">← Tiếp tục mua sắm</a></p>
        </c:if>

        <c:if test="${not empty items}">
            <!-- FORM THANH TOÁN -->
            <form action="order" method="post" id="orderForm">
                <table>
                    <tr>
                        <th>Chọn</th>
                        <th>Ảnh</th>
                        <th>Tên</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                        <th>Hành động</th>
                    </tr>

                    <c:forEach var="it" items="${items}">
                        <tr>
                            <td>
                                <input type="checkbox" name="selectedIds" value="${it.product.id}">
                            </td>

                            <td><img src="${it.product.imageURL}" alt=""></td>
                            <td>${it.product.name}</td>
                            <td>${it.product.price}đ</td>
                            <td>${it.quantity}</td>
                            <td>${it.product.price * it.quantity}đ</td>

                            <td>
                                <!-- KHÔNG LỒNG FORM: dùng JS submit 1 form ẩn -->
                                <button type="button"
                                        onclick="deleteItem(${it.product.id})">
                                    Xóa
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <div class="total">
                    <b>Tổng tiền:</b> ${total}đ
                </div>

                <div class="actions">
                    <a href="clientHome">← Tiếp tục mua sắm</a>
                    <button type="submit" style="font-weight:bold;">Thanh toán</button>
                </div>
            </form>

            <!-- FORM ẨN để xóa item -->
            <form action="cart" method="post" id="deleteForm" style="display:none;">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="id" id="deleteId">
            </form>
        </c:if>
    </div>

    <jsp:include page="footer.jsp" />

<script>
(function(){
    const msg = "<%=(session.getAttribute("msg") != null ? session.getAttribute("msg") : "")%>";
    <% session.removeAttribute("msg"); %>
    if (msg && msg !== "null" && msg.trim() !== "") alert(msg);
})();

function deleteItem(productId){
    if(confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")){
        document.getElementById("deleteId").value = productId;
        document.getElementById("deleteForm").submit();
    }
}

document.getElementById("orderForm")?.addEventListener("submit", function(e){
    const checked = document.querySelectorAll('input[name="selectedIds"]:checked');
    if (checked.length === 0) {
        e.preventDefault();
        alert("Bạn phải chọn ít nhất 1 sản phẩm để thanh toán!");
    }
});
</script>

</body>
</html>
