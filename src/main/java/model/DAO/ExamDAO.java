package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import model.BEAN.Exam;
import model.BEAN.Question;
import model.BEAN.ResponseInfo;
import model.BEAN.Result;
import model.BEAN.SimpleExam;
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

	public ArrayList<Exam> getValidExams(String username) {
		ArrayList<Exam> exams = new ArrayList<>();
		try {
			Connection connection = Utils.getConnection();
			if (connection != null) {
				String sql = "SELECT baikiemtra.*, users.firstName, users.lastName FROM baikiemtra JOIN users ON baikiemtra.createdBy = users.username WHERE openAt > NOW() AND NOT EXISTS (SELECT * FROM ketqua kq WHERE baikiemtra.id = kq.examId AND kq.studentId = ?)";
				try {
					PreparedStatement pst = connection.prepareStatement(sql);
					pst.setString(1, username);
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						exams.add(new Exam(rs.getString("id"), rs.getString("maMH"), rs.getString("name"),
								rs.getInt("numberQuestion"), rs.getDouble("totalTime"), rs.getString("password"),
								rs.getTimestamp("openAt"), rs.getString("lastName") + " " + rs.getString("firstName")));
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
		}
		return exams;
	}

	public boolean checkPasswordExam(String examId, String password) {
		try {
			Connection connection = Utils.getConnection();
			String sql = "SELECT * FROM baikiemtra WHERE id = ? AND password = ?";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, examId);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	public SimpleExam getExamInfo(int examId) {
		SimpleExam exam = null;
		try {
			Connection connection = Utils.getConnection();
			String sql = "SELECT kt.name as examName, mh.name as subjectName, totalTime from baikiemtra kt "
					+ "join monhoc mh on mh.id = kt.maMH where kt.id = ?";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setInt(1, examId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				exam = new SimpleExam(rs.getString("examName"), rs.getString("subjectName"), rs.getDouble("totalTime"));
			}
		} catch (Exception e) {
		}
		return exam;
	}

	public ArrayList<Question> getListQuestionsByExamId(String examId) {
		ArrayList<Question> questions = new ArrayList<>();
		try {
			Connection connection = Utils.getConnection();
			String sql = "SELECT ch.* FROM baikiemtra_cauhoi bc " + "INNER JOIN cauhoi ch ON bc.maCauHoi = ch.id "
					+ "WHERE bc.maBaiKiemTra = ?";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(examId));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String[] correctAnswerIds = rs.getString("correctAnswerIds").split(",\s");
				String type = rs.getBoolean("isSingle") ? "single" : "multiple";
				questions.add(new Question(rs.getInt("id"), rs.getString("subjectId"), rs.getString("question"),
						new String[] { rs.getString("answer1"), rs.getString("answer2"), rs.getString("answer3"),
								rs.getString("answer4") },
						type, correctAnswerIds));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return questions;
	}

	public double getFinalScore(Map<String, String[]> selectedAnswersMap, String examId) {
		double finalScore = 0;
		try {
			double totalScore = 0;
			int numberQuestion = 0;
			Connection connection = Utils.getConnection();
			String sql = "SELECT bkt.numberQuestion, ch.* FROM baikiemtra_cauhoi bc "
					+ "INNER JOIN cauhoi ch ON bc.maCauHoi = ch.id "
					+ "INNER JOIN baikiemtra bkt ON bc.maBaiKiemTra = bkt.id " + "WHERE bc.maBaiKiemTra = ?";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(examId));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String idCauHoi = Integer.toString(rs.getInt("id"));
				String[] myAnswers = selectedAnswersMap.get(idCauHoi);
				if (myAnswers == null) {
					continue;
				}
				boolean isSingle = rs.getBoolean("isSingle");
				numberQuestion = rs.getInt("numberQuestion");
				String[] correctAnswerIds = rs.getString("correctAnswerIds").split(", ");
				double ammountTrueAnswer = countCommonElements(correctAnswerIds, myAnswers);
				if (isSingle) {
					totalScore += ammountTrueAnswer;
				} else {
					totalScore += ammountTrueAnswer < myAnswers.length
							? Math.max(2 * ammountTrueAnswer - myAnswers.length, 0) / correctAnswerIds.length
							: ammountTrueAnswer / correctAnswerIds.length;
				}
			}
			finalScore = totalScore * 10 / numberQuestion;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return finalScore;
	}

	private int countCommonElements(String[] array1, String[] array2) {
		int count = 0;
		for (String element : array1) {
			if (Arrays.asList(array2).contains(element)) {
				count++;
			}
		}
		return count;
	}

	public ArrayList<Exam> getListBaiKiemTraByTeacherId(String teacherId) {
		ArrayList<Exam> baiKiemTras = new ArrayList<>();
		try {
			Connection connection = Utils.getConnection();
			if (connection != null) {
				String query = "SELECT kt.*, mh.name as tenMH from baikiemtra kt "
						+ "join monhoc mh on kt.maMH = mh.id " + "WHERE kt.createdBy = '" + teacherId + "'";
				try {
					Statement st = connection.createStatement();
					ResultSet rs = st.executeQuery(query);
					while (rs.next()) {
						Exam exam = new Exam(rs.getString("id"), rs.getString("maMH"), rs.getString("name"),
								Integer.parseInt(rs.getString("numberQuestion")),
								Double.parseDouble(rs.getString("totalTime")), rs.getString("password"),
								Timestamp.valueOf(rs.getString("openAt")), rs.getString("createdBy"));

						exam.setSubjectName(rs.getString("tenMH"));
						baiKiemTras.add(exam);
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
		return baiKiemTras;
	}

	public ArrayList<Result> getListResultExamByExamId(String examId) {
		ArrayList<Result> results = new ArrayList<>();
		try {
			Connection connection = Utils.getConnection();
			if (connection != null) {
				String query = "select studentId, CONCAT(u.lastName, ' ', u.firstName) AS fullname, correctQuestions, b.numberQuestion, b.name as examName "
						+ "from ketqua k " + "join users u on k.studentId = u.username "
						+ "join baikiemtra b on k.examId = b.id " + "where k.examId = '" + examId + "'";
				try {
					Statement st = connection.createStatement();
					ResultSet rs = st.executeQuery(query);
					while (rs.next()) {
						results.add(new Result(rs.getString("studentId"), rs.getString("fullname"),
								Integer.parseInt(rs.getString("correctQuestions")),
								Integer.parseInt(rs.getString("numberQuestion")), rs.getString("examName")));
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
		return results;
	}

	public void saveResultExam(String studentId, String examId, Double correctQuestions, LocalDateTime openAt,
			LocalDateTime submitAt) {
		ArrayList<Result> results = new ArrayList<>();
		try {
			Connection connection = Utils.getConnection();
			PreparedStatement preparedStatement = null;
			if (connection != null) {
				String query = "INSERT INTO ketqua (studentId, examId, correctQuestions, openAt, submitAt) VALUES (?, ?, ?, ?, ?)";

				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, studentId);
				preparedStatement.setInt(2, Integer.parseInt(examId));
				preparedStatement.setDouble(3, correctQuestions);
				preparedStatement.setObject(4, openAt);
				preparedStatement.setObject(5, submitAt);

				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
		}
	}
}
