package model.BO;

import model.BEAN.Question;
import model.DAO.ExamDAO;

public class ExamBO {
	public boolean createQuestion(Question question) {
		return (new ExamDAO()).createQuestion(question);
	}
}
