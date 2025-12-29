package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.CartItem;
import model.Product;

public class CartDAO {

	//Hàm kiếm tra xem tồn tại không
    public boolean exists(int userId, int productId) {
        String sql = "select 1 from cart_items where user_id = ? and product_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //Hàm thêm 
    public void insert(int userId, int productId) {
        String sql = "insert into cart_items(user_id, product_id, quantity) values(?, ?, 1)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Đã có thì thêm 1
    public void increase(int userId, int productId) {
        String sql = "update cart_items set quantity = quantity + 1 where user_id = ? and product_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart(int userId, int productId) {
        if (exists(userId, productId)) increase(userId, productId);
        else insert(userId, productId);
    }

    public void updateQty(int userId, int productId, int qty) {
        if (qty <= 0) {
            remove(userId, productId);
            return;
        }

        String sql = "update cart_items set quantity = ? where user_id = ? and product_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, userId);
            ps.setInt(3, productId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(int userId, int productId) {
        String sql = "delete from cart_items where user_id = ? and product_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    //Lấy giỏ hàng 
    public List<CartItem> getItems(int userId) {
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
        			    "cart_items.quantity " +
        			    "from cart_items " +
        			    "join products on cart_items.product_id = products.id " +
        			    "where cart_items.user_id = ?";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
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

    public int getTotal(int userId) {
        String sql =
        		 "select sum(products.price * cart_items.quantity) " +
        				    "from cart_items " +
        				    "join products on cart_items.product_id = products.id " +
        				    "where cart_items.user_id = ?";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
