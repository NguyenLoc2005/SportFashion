package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.DatabaseConnect;
import dao.OrderDAO;
import model.CartItem;
import model.Order;
import model.User;

@WebServlet("/order")
public class ClientOrderServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String idRaw = req.getParameter("id");

      
        if (idRaw != null && !idRaw.trim().isEmpty()) {

            int orderId;
            try {
                orderId = Integer.parseInt(idRaw.trim());
            } catch (Exception e) {
                resp.sendRedirect("order");
                return;
            }

            // 1) Check đơn có thuộc user không
            boolean isMine = false;
            String sqlCheck =
                    "select 1 " +
                    "from orders " +
                    "where orders.id = ? and orders.user_id = ?";

            try (Connection conn = DatabaseConnect.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlCheck)) {

                ps.setInt(1, orderId);
                ps.setInt(2, user.getId());
                ResultSet rs = ps.executeQuery();
                isMine = rs.next();

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!isMine) {
                session.setAttribute("msg", "Bạn không có quyền xem đơn này!");
                resp.sendRedirect("order");
                return;
            }

            // 2) Lấy danh sách sản phẩm + total
            List<CartItem> items = orderDAO.getOrderItemsWithProduct(orderId);
            int total = orderDAO.getOrderTotal(orderId);

            // 3) Lấy thông tin nhận hàng từ order_items (vì bạn lưu vào order_items)
            String receiverName = "";
            String receiverPhone = "";
            String receiverAddress = "";
            String note = "";

            String sqlReceiver =
                    "select order_items.receiver_name, order_items.receiver_phone, order_items.receiver_address, order_items.note " +
                    "from order_items " +
                    "where order_items.order_id = ? " +
                    "limit 1";

            try (Connection conn = DatabaseConnect.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlReceiver)) {

                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    receiverName = rs.getString("receiver_name");
                    receiverPhone = rs.getString("receiver_phone");
                    receiverAddress = rs.getString("receiver_address");
                    note = rs.getString("note");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // 4) Set attribute cho JSP
            req.setAttribute("orderId", orderId);
            req.setAttribute("items", items);
            req.setAttribute("total", total);

            req.setAttribute("receiverName", receiverName);
            req.setAttribute("receiverPhone", receiverPhone);
            req.setAttribute("receiverAddress", receiverAddress);
            req.setAttribute("note", note);

            req.getRequestDispatcher("order-item.jsp").forward(req, resp);
            return;
        }

        // ====== DANH SÁCH ĐƠN ======
        List<Order> orders = orderDAO.getOrdersByUser(user.getId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (!"checkout".equals(action)) {
            resp.sendRedirect("cart");
            return;
        }

        String receiverName = req.getParameter("receiverName");
        String receiverPhone = req.getParameter("receiverPhone");
        String receiverAddress = req.getParameter("receiverAddress");
        String note = req.getParameter("note");

        if (receiverName == null || receiverName.trim().isEmpty()
                || receiverPhone == null || receiverPhone.trim().isEmpty()
                || receiverAddress == null || receiverAddress.trim().isEmpty()) {

            session.setAttribute("msg", "Vui lòng nhập đầy đủ thông tin người nhận!");
            resp.sendRedirect("cart");
            return;
        }

        int orderId = orderDAO.createOrderFromCart(
                user.getId(),
                receiverName.trim(),
                receiverPhone.trim(),
                receiverAddress.trim(),
                (note == null ? "" : note.trim())
        );

        if (orderId == -1) {
            session.setAttribute("msg", "Giỏ hàng trống hoặc tạo đơn thất bại!");
            resp.sendRedirect("cart");
            return;
        }

        session.setAttribute("msg", "Tạo đơn hàng thành công! Mã đơn: " + orderId);
        resp.sendRedirect("order?id=" + orderId);
    }
}
