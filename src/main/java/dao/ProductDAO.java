
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDAO {

	// Lọc tìm kiếm
	public List<Product> searchFilterProduct(String type) {
		List<Product> productList = new ArrayList<>();
		String sql = "select * from products where type like ?";
		try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, "%" + type + "%");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setColor(rs.getString("color"));
				product.setSize(rs.getString("size"));
				product.setType(rs.getString("type"));
				product.setPrice(rs.getInt("price"));
				product.setImageURL(rs.getString("imageURL"));

				productList.add(product);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}

	// Tìm kiếm
	public List<Product> searchProduct(String name) {
		List<Product> productList = new ArrayList<>();
		String sql = "select * from products where name like ?";
		try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, "%" + name + "%");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setColor(rs.getString("color"));
				product.setSize(rs.getString("size"));
				product.setType(rs.getString("type"));
				product.setPrice(rs.getInt("price"));
				product.setImageURL(rs.getString("imageURL"));

				productList.add(product);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;

	}
	
	//Xem toàn bộ sản phẩm
	public List<Product> getAllProducts() {
		List<Product> productList = new ArrayList<>();
		String sql = "select * from products ";
		try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setColor(rs.getString("color"));
				product.setSize(rs.getString("size"));
				product.setType(rs.getString("type"));
				product.setPrice(rs.getInt("price"));
				product.setImageURL(rs.getString("imageURL"));

				productList.add(product);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;

	}
	
	//Hàm xem chi tiết sản phẩm
	// Xem chi tiết sản phẩm
	public Product getProductById(int id) {
	    String sql = "SELECT * FROM products WHERE id = ?";
	    try (Connection conn = DatabaseConnect.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            Product product = new Product();
	            product.setId(rs.getInt("id"));
	            product.setName(rs.getString("name"));
	            product.setColor(rs.getString("color"));
	            product.setSize(rs.getString("size"));
	            product.setType(rs.getString("type"));
	            product.setPrice(rs.getInt("price"));
	            product.setImageURL(rs.getString("imageURL"));
	            return product;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
