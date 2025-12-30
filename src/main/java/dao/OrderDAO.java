package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.CartItem;
import model.Order;
import model.Product;

public class OrderDAO {

    // Tạo order từ toàn bộ cart của user
    public int createOrderFromCart(int userId) {

        // 1) Tính total
        String sqlTotal =
                "select sum(products.price * cart_items.quantity) as total " +
                "from cart_items " +
                "join products on cart_items.product_id = products.id " +
                "where cart_items.user_id = ?";

        // 2) Insert orders
        String sqlInsertOrder =
                "insert into orders(user_id, total, status) values(?, ?, 'PENDING')";

        // 3) Insert order_items (tạo từ cart)
        String sqlInsertItems =
                "insert into order_items(order_id, product_id, price, quantity, subtotal) " +
                "select ?, cart_items.product_id, products.price, cart_items.quantity, (products.price * cart_items.quantity) " +
                "from cart_items " +
                "join products on cart_items.product_id = products.id " +
                "where cart_items.user_id = ?";

        // 4) Xóa cart sau khi thanh toán
        String sqlDeleteCart =
                "delete from cart_items where cart_items.user_id = ?";

        try (Connection conn = DatabaseConnect.getConnection()) {

            // A) Lấy total
            int total = 0;
            try (PreparedStatement ps = conn.prepareStatement(sqlTotal)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) total = rs.getInt("total");
            }

            // Cart trống
            if (total <= 0) return -1;

            // B) Tạo order và lấy orderId
            int orderId = -1;
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertOrder, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setInt(2, total);
                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) orderId = keys.getInt(1);
            }

            if (orderId == -1) return -1;

            // C) Insert items từ cart vào order_items
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertItems)) {
                ps.setInt(1, orderId);
                ps.setInt(2, userId);
                ps.executeUpdate();
            }

            // D) Xóa cart
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteCart)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
            }

            return orderId;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // Lấy danh sách orders của user
    public List<Order> getOrdersByUser(int userId) {
        List<Order> list = new ArrayList<>();

        String sql =
                "select orders.id, orders.user_id, orders.total, orders.status " +
                "from orders " +
                "where orders.user_id = ? " +
                "order by orders.id desc";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setTotal(rs.getInt("total"));
                o.setStatus(rs.getString("status"));
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy item trong 1 order + product info
    public List<CartItem> getOrderItemsWithProduct(int orderId) {
        List<CartItem> list = new ArrayList<>();

        String sql =
                "select " +
                        "products.id, " +
                        "products.name, " +
                        "products.color, " +
                        "products.size, " +
                        "products.type, " +
                        "products.price, " +
                        "products.imageURL, " +
                        "order_items.quantity " +
                "from order_items " +
                "join products on order_items.product_id = products.id " +
                "where order_items.order_id = ?";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setColor(rs.getString("color"));
                p.setSize(rs.getString("size"));
                p.setType(rs.getString("type"));
                p.setPrice(rs.getInt("price"));
                p.setImageURL(rs.getString("imageURL"));

                CartItem item = new CartItem(p, rs.getInt("quantity"));
                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy total của 1 order
    public int getOrderTotal(int orderId) {
        String sql =
                "select orders.total from orders where orders.id = ?";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    public int createOrderFromCart(int userId, String receiverName, String receiverPhone, String receiverAddress, String note) {

        String sqlTotal =
                "select sum(products.price * cart_items.quantity) as total " +
                "from cart_items " +
                "join products on cart_items.product_id = products.id " +
                "where cart_items.user_id = ?";

        String sqlInsertOrder =
                "insert into orders(user_id, total, status) values(?, ?, 'PENDING')";

        // thêm receiver vào order_items
        String sqlInsertItems =
                "insert into order_items(order_id, product_id, price, quantity, subtotal, receiver_name, receiver_phone, receiver_address, note) " +
                "select ?, cart_items.product_id, products.price, cart_items.quantity, (products.price * cart_items.quantity), ?, ?, ?, ? " +
                "from cart_items " +
                "join products on cart_items.product_id = products.id " +
                "where cart_items.user_id = ?";

        String sqlDeleteCart =
                "delete from cart_items where cart_items.user_id = ?";

        try (Connection conn = DatabaseConnect.getConnection()) {

            int total = 0;
            try (PreparedStatement ps = conn.prepareStatement(sqlTotal)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) total = rs.getInt("total");
            }

            if (total <= 0) return -1;

            int orderId = -1;
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertOrder, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setInt(2, total);
                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) orderId = keys.getInt(1);
            }

            if (orderId == -1) return -1;

            try (PreparedStatement ps = conn.prepareStatement(sqlInsertItems)) {
                ps.setInt(1, orderId);
                ps.setString(2, receiverName);
                ps.setString(3, receiverPhone);
                ps.setString(4, receiverAddress);
                ps.setString(5, note);
                ps.setInt(6, userId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteCart)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
            }

            return orderId;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    public String[] getReceiverInfo(int orderId) {

        String sql =
                "select order_items.receiver_name, order_items.receiver_phone, order_items.receiver_address, order_items.note " +
                "from order_items " +
                "where order_items.order_id = ? " +
                "limit 1";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new String[] {
                    rs.getString("receiver_name"),
                    rs.getString("receiver_phone"),
                    rs.getString("receiver_address"),
                    rs.getString("note")
                };
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[]{"", "", "", ""};
    }


}
