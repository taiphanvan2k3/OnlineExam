package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.BEAN.Exam;
import model.BEAN.Question;
import model.BEAN.ResponseInfo;
import model.BEAN.Result;
import util.Utils;

public class ExamDAO {
	public boolean createQuestion(Question question) {
		try {
			Connection connection = Utils.getConnection();
			String correctAnswers = "";
			int isSingle = 1;
			if (question.getType().equals("multiple")) {
				correctAnswers = String.join(", ", question.getAnswerMultiple());
				isSingle = 0;
			} else {
				correctAnswers = question.getAnswerSingle();
			}
			String sql = String.format(
					"insert into cauhoi(question, answer1, answer2,answer3,answer4, correctAnswerIds, isSingle, subjectId)"
							+ " values('%s', '%s', '%s', '%s', '%s', '%s', %d, '%s')",
					question.getQuestion(), question.getAnswers()[0], question.getAnswers()[1],
					question.getAnswers()[2], question.getAnswers()[3], correctAnswers, isSingle,
					question.getSubjectId());
			Statement st = connection.createStatement();
			return st.executeUpdate(sql) > 0;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	public ResponseInfo createExam(Exam exam) {
		ResponseInfo response = new ResponseInfo();
		try {
			int totalNumberOfQuestion = this.getTotalNumberOfQuestion(exam.getSubjectId());
			if (totalNumberOfQuestion < exam.getNumberOfQuestions()) {
				response.setCode(500);
				response.setMessage("Môn học này không đủ số lượng câu hỏi mà bạn muốn tạo");
				return response;
			}

			Connection connection = Utils.getConnection();
			String sql = "insert into baikiemtra(name,numberQuestion, password, totalTime, openAt, maMH, createdBy)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, exam.getName());
			pst.setInt(2, exam.getNumberOfQuestions());
			pst.setString(3, exam.getPassword());
			pst.setDouble(4, exam.getTotalTime());
			pst.setTimestamp(5, exam.getOpenAt());
			pst.setString(6, exam.getSubjectId());
			pst.setString(7, exam.getCreateBy());
			pst.executeUpdate();

			this.addRecordsInExamQuestionTable(exam.getSubjectId(), exam.getNumberOfQuestions());
			response.setMessage("Tạo mới thành công");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			response.setMessage(e.getMessage());
			response.setCode(500);
		}
		return response;
	}

	private int getTotalNumberOfQuestion(String subjectId) throws SQLException {
		Connection connection = Utils.getConnection();
		String sql = "select count(*) as total from cauhoi where subjectId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, subjectId);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		}
		return 0;
	}

	private void addRecordsInExamQuestionTable(String subjectId, int totalQuestions) throws SQLException {
		Connection connection = Utils.getConnection();
		String query = "select id from baikiemtra order by id desc limit 1";
		PreparedStatement pst = connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		int examId = 0;
		if (!rs.next()) {
			return;
		}
		examId = rs.getInt("id");

		query = "select id from cauhoi where subjectId = ? ORDER by rand() limit ?";
		pst = connection.prepareStatement(query);
		pst.setString(1, subjectId);
		pst.setInt(2, totalQuestions);
		rs = pst.executeQuery();
		while (rs.next()) {
			String sql = "insert into baikiemtra_cauhoi values(?,?)";
			pst = connection.prepareStatement(sql);
			pst.setInt(1, examId);
			pst.setInt(2, rs.getInt("id"));
			pst.executeUpdate();
		}
	}
	
	public ArrayList<Exam> getListBaiKiemTraByTeacherId(String teacherId) {
		ArrayList<Exam> baiKiemTras = new ArrayList<>();
		try {
			Connection connection = Utils.getConnection();
			if (connection != null) {
				String query = "select * from baikiemtra where createdBy = '" + teacherId + "'";
				try {
					Statement st = connection.createStatement();
					ResultSet rs = st.executeQuery(query);
					while (rs.next()) {
						baiKiemTras.add(new Exam(rs.getString("id"), rs.getString("maMH"), rs.getString("name"),
								Integer.parseInt(rs.getString("numberQuestion")),
								Double.parseDouble(rs.getString("totalTime")), rs.getString("password"),
							 	Timestamp.valueOf(rs.getString("openAt")), rs.getString("createdBy")));
					}
				} catch (Exception e) { }
			}
		} catch (Exception e) { }
		return baiKiemTras;
	}
	
	public ArrayList<Result> getListResultExamByExamId(String examId) {
		ArrayList<Result> results = new ArrayList<>();
		try {
			Connection connection = Utils.getConnection();
			if (connection != null) {
				String query = "select studentId, CONCAT(u.lastName, ' ', u.firstName) AS fullname, correctQuestions, b.numberQuestion, b.name as examName "
						+ "from ketqua k "
						+ "join users u on k.studentId = u.username "
						+ "join baikiemtra b on k.examId = b.id "
						+ "where k.examId = '" + examId + "'";
				try {
					Statement st = connection.createStatement();
					ResultSet rs = st.executeQuery(query);
					while (rs.next()) {
						results.add(new Result(rs.getString("studentId"), rs.getString("fullname"),
								Integer.parseInt(rs.getString("correctQuestions")),
								Integer.parseInt(rs.getString("numberQuestion")),
								rs.getString("examName")));
					}
				} catch (Exception e) { }
			}
		} catch (Exception e) { }
		return results;
	}
}
