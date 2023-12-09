package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://online-exam.mysql.database.azure.com:3306/online-exam?useSSL=true";
			conn=DriverManager.getConnection(url, "aedt3", "Toilagay123");
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

	public static Date convertStringToDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date date = new Date();
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
