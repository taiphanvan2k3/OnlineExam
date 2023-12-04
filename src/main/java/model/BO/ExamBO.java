package model.BO;

import model.BEAN.Exam;
import model.BEAN.Question;
import model.BEAN.ResponseInfo;
import model.DAO.ExamDAO;

public class ExamBO {
	public boolean createQuestion(Question question) {
		return (new ExamDAO()).createQuestion(question);
	}

	public ResponseInfo createExam(Exam exam) {
		return (new ExamDAO()).createExam(exam);
	}
}
