package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import util.Utils;

public class AccountDAO {
	public ArrayList<String> getAllUsernames() {
		ArrayList<String> usernames = new ArrayList<>();
		try {
			Connection connection = Utils.getConnection();
			if (connection != null) {
				String query = "select username from users";
				try {
					Statement st = connection.createStatement();
					ResultSet rs = st.executeQuery(query);
					while (rs.next()) {
						usernames.add(rs.getString("username"));
					}
				} catch (Exception e) { }
			}
		} catch (Exception e) { }
		return usernames;
	}

	public void createAccount(String username, String password, String role, String firstName, String lastName) {
		try {
			Connection connection = Utils.getConnection();
			if (connection != null) {
				String query = "INSERT INTO users (username, password, firstName, lastName, `role`) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, firstName);
				preparedStatement.setString(4, lastName);
				preparedStatement.setInt(5, role.equals("teacher") ? 1 : 2);

				// Thực thi câu lệnh INSERT
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
