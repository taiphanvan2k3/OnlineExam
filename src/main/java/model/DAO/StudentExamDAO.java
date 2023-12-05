package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.BEAN.HistoryDoExam;
import model.BEAN.ListOfHistoryDoExam;
import util.Utils;

public class StudentExamDAO {

	private int getTotalPages(String studentId, String subjectId, int pageSize) {
		int totalPage = 0;
		try {
			Connection connection = Utils.getConnection();
			String query = "select count(*) as total from ketqua kq join baikiemtra kt on kq.examId = kt.id "
					+ "join monhoc mh on kt.maMH = mh.id" + " where kq.studentId = ?";

			if (!subjectId.equals("")) {
				query += " and kt.maMH = ?";
			}
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, studentId);
			if (!subjectId.equals("")) {
				pst.setString(2, subjectId);
			}

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				int totalRecords = rs.getInt("total");
				totalPage = (int) Math.ceil(totalRecords * 1.0 / pageSize);
			}
			connection.close();
		} catch (Exception e) {
		}
		return totalPage;
	}

	public ListOfHistoryDoExam getListHistoryDoExam(String studentId, String subjectId, int currentPage, int pageSize) {
		ListOfHistoryDoExam result = new ListOfHistoryDoExam(currentPage);
		try {
			result.setTotalPage(this.getTotalPages(studentId, subjectId, pageSize));

			Connection connection = Utils.getConnection();
			String query = "select kt.name as examName,mh.name as subjectName, kq.correctQuestions, "
					+ "kq.openAt as studentOpenAt, kq.submitAt, kt.numberQuestion as totalQuestions, kt.totalTime "
					+ "from ketqua kq join baikiemtra kt on kq.examId = kt.id " + "join monhoc mh on kt.maMH = mh.id"
					+ " where kq.studentId = ?";
			if (!subjectId.equals("")) {
				query += " and kt.maMH = ?";
			}

			query += " order by kt.openAt DESC limit ? offset ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, studentId);
			int paramIndex = 2;
			if (!subjectId.equals("")) {
				pst.setString(2, subjectId);
				paramIndex++;
			}
			pst.setInt(paramIndex++, pageSize);
			pst.setInt(paramIndex, (currentPage - 1) * pageSize);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				HistoryDoExam history = new HistoryDoExam();
				history.setNameOfExam(rs.getString("examName"));
				history.setSubjectName(rs.getString("subjectName"));
				history.setCorrectQuestions(rs.getInt("correctQuestions"));
				history.setOpenAt(rs.getTimestamp("studentOpenAt"));
				history.setSubmitAt(rs.getTimestamp("submitAt"));
				history.setTotalQuestions(rs.getInt("totalQuestions"));
				history.setTotalTimeOfExam(rs.getDouble("totalTime"));
				result.getHistories().add(history);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return result;
	}
}
