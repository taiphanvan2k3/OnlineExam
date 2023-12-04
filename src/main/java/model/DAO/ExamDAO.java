package model.DAO;

import java.sql.Connection;
import java.sql.Statement;

import model.BEAN.Question;
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
}
