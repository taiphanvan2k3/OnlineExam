package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	public static void redirectToPage(HttpServletRequest request, HttpServletResponse response, String destination) {
		try {
			response.sendRedirect(request.getContextPath() + destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
