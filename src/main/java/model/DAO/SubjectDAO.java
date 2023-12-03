package model.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import util.Utils;

public class SubjectDAO {
	public ArrayList<String> getAllSubjectIds() {
		ArrayList<String> subjectIds = new ArrayList<>();
		try {
			Connection connection = Utils.getConnection();
			String query = "select id from monhoc";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				subjectIds.add(rs.getString("id"));
			}
		} catch (Exception e) {
		}
		return subjectIds;
	}

	public boolean createSubject(String id, String name) {
		try {
			Connection connection = Utils.getConnection();
			String query = String.format("insert into monhoc values('%s', '%s')", id, name);
			Statement st = connection.createStatement();
			return st.executeUpdate(query) > 0;
		} catch (Exception e) {
		}
		return false;
	}
}
