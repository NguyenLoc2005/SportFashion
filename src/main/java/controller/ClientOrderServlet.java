package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.OrderDAO;
import model.CartItem;
import model.Order;
import model.User;

@WebServlet("/order")
public class ClientOrderServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // nếu có ?id=... => xem chi tiết đơn
        String idRaw = req.getParameter("id");
        if (idRaw != null && !idRaw.isEmpty()) {
            int orderId = Integer.parseInt(idRaw);

            List<CartItem> items = orderDAO.getOrderItemsWithProduct(orderId);
            int total = orderDAO.getOrderTotal(orderId);

            req.setAttribute("orderId", orderId);
            req.setAttribute("items", items);
            req.setAttribute("total", total);

            req.getRequestDispatcher("order-item.jsp").forward(req, resp);
            return;
        }

        // không có id => xem danh sách đơn
        List<Order> orders = orderDAO.getOrdersByUser(user.getId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String[] selected = req.getParameterValues("selectedIds");
        if (selected == null || selected.length == 0) {
            session.setAttribute("msg", "Bạn phải chọn ít nhất 1 sản phẩm để thanh toán!");
            resp.sendRedirect("cart");
            return;
        }

        List<Integer> productIds = new ArrayList<>();
        for (String s : selected) {
            try { productIds.add(Integer.parseInt(s)); } catch (Exception ignored) {}
        }

        int orderId = orderDAO.createOrderFromCartSelected(user.getId(), productIds);

        if (orderId == -1) {
            session.setAttribute("msg", "Tạo đơn hàng thất bại!");
            resp.sendRedirect("cart");
            return;
        }

        session.setAttribute("msg", "Tạo đơn hàng thành công! Mã đơn: " + orderId);
        // tạo xong chuyển thẳng qua chi tiết đơn
        resp.sendRedirect("order?id=" + orderId);
    }
}
