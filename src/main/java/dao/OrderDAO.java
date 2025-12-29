package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.CartItem;
import model.Order;
import model.Product;

public class OrderDAO {
	public int createOrderFromCartSelected(int userId, List<Integer> productIds) {
		if (productIds == null || productIds.isEmpty())
			return -1;

		String inSql = buildInPlaceholders(productIds.size());

		String sqlSelect = "SELECT ci.product_id, ci.quantity, p.price "
				+ "FROM cart_items ci JOIN products p ON ci.product_id = p.id "
				+ "WHERE ci.user_id = ? AND ci.product_id IN (" + inSql + ")";

		String sqlInsertOrder = "INSERT INTO orders(user_id, total, status) VALUES(?, ?, 'PENDING')";

		String sqlInsertItem =
				// nếu DB bạn KHÔNG có subtotal thì bỏ subtotal ra
				"INSERT INTO order_items(order_id, product_id, price, quantity, subtotal) VALUES(?, ?, ?, ?, ?)";

		String sqlDeleteCart = "DELETE FROM cart_items WHERE user_id = ? AND product_id IN (" + inSql + ")";

		Connection conn = null;
		try {
			conn = DatabaseConnect.getConnection();
			conn.setAutoCommit(false);

			List<int[]> items = new ArrayList<>(); // {productId, qty, price}
			int total = 0;

			try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
				int idx = 1;
				ps.setInt(idx++, userId);
				for (Integer pid : productIds)
					ps.setInt(idx++, pid);

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						int pid = rs.getInt("product_id");
						int qty = rs.getInt("quantity");
						int price = rs.getInt("price");
						items.add(new int[] { pid, qty, price });
						total += price * qty;
					}
				}
			}

			if (items.isEmpty()) {
				conn.rollback();
				return -1;
			}

			int orderId;
			try (PreparedStatement ps = conn.prepareStatement(sqlInsertOrder, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, userId);
				ps.setInt(2, total);
				ps.executeUpdate();

				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (!keys.next()) {
						conn.rollback();
						return -1;
					}
					orderId = keys.getInt(1);
				}
			}

			try (PreparedStatement ps = conn.prepareStatement(sqlInsertItem)) {
				for (int[] it : items) {
					int pid = it[0], qty = it[1], price = it[2];
					int subtotal = price * qty;

					ps.setInt(1, orderId);
					ps.setInt(2, pid);
					ps.setInt(3, price);
					ps.setInt(4, qty);
					ps.setInt(5, subtotal);
					ps.addBatch();
				}
				ps.executeBatch();
			}

			try (PreparedStatement ps = conn.prepareStatement(sqlDeleteCart)) {
				int idx = 1;
				ps.setInt(idx++, userId);
				for (Integer pid : productIds)
					ps.setInt(idx++, pid);
				ps.executeUpdate();
			}

			conn.commit();
			return orderId;

		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ignored) {
			}
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.setAutoCommit(true);
			} catch (Exception ignored) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ignored) {
			}
		}

		return -1;
	}

	
	public List<Order> getOrdersByUser(int userId) {
		List<Order> list = new ArrayList<>();
		String sql = "SELECT id, user_id, total, status FROM orders WHERE user_id = ? ORDER BY id DESC";
		try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Order o = new Order();
					o.setId(rs.getInt("id"));
					o.setUserId(rs.getInt("user_id"));
					o.setTotal(rs.getInt("total"));
					o.setStatus(rs.getString("status"));
					list.add(o);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	public List<CartItem> getOrderItemsWithProduct(int orderId) {
		List<CartItem> list = new ArrayList<>();

		String sql = "SELECT p.*, oi.quantity " + "FROM order_items oi JOIN products p ON oi.product_id = p.id "
				+ "WHERE oi.order_id = ?";

		try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, orderId);
			try (ResultSet rs = ps.executeQuery()) {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	
	public int getOrderTotal(int orderId) {
		String sql = "SELECT total FROM orders WHERE id = ?";
		try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, orderId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private String buildInPlaceholders(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append("?");
			if (i < n - 1)
				sb.append(",");
		}
		return sb.toString();
	}
}
