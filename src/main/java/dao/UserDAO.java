package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Product;
import model.User;

public class UserDAO {
	
	//Hàm login
	// User h = new User()
	// h.Login(long123,lllll)
	//Login(1,2)
	//
	public User Login(String userName, String password) {
		String sql="select * from users where userName = ? and password = ?";
		
		try(Connection conn = DatabaseConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1,userName);
			ps.setString(2,password);
			
			ResultSet rs=ps.executeQuery();
			
			while (rs.next()) {
				User u = new User();
				
				u.setId(rs.getInt("id"));
				u.setUserName(rs.getString("userName"));
				u.setRole(rs.getString("role"));
				return u;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Hàm register
	
}
