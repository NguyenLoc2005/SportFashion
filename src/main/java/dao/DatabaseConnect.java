package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnect {

    private static final String URL = 
        "jdbc:mysql://localhost:3306/sportfashion";

    private static final String USER = "root";
    private static final String PASS = "2005";  

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
