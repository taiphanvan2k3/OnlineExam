package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Utils {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online-exam", "root", "");
		} catch (Exception e) {
			System.out.println("Fail");
		}
		return conn;
	}
}
