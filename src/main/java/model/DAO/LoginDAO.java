package model.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import model.BEAN.User;
import util.Utils;

public class LoginDAO {
	public User checkLogin(String username, String password) {
		User user = null;
		try {
			Connection connection = Utils.getConnection();
			if (connection != null) {
				String query = "select *from users where username = '" + username + "' and password = '" + password
						+ "'";
				try {
					Statement st = connection.createStatement();
					ResultSet rs = st.executeQuery(query);
					if (rs.next()) {
						user = new User();
						user.setUsername(rs.getString("username"));
						int role = rs.getInt("role");
						user.setRole(role == 0 ? "admin" : role == 1 ? "gv" : "sv");
						user.setFirstName(rs.getString("firstName"));
						user.setLastName(rs.getString("lastName"));
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
}
