package model.BO;

import java.util.ArrayList;
import java.util.Map;

import model.BEAN.Exam;
import model.BEAN.Question;
import model.BEAN.ResponseInfo;
import model.BEAN.Subject;
import model.BEAN.Result;
import model.DAO.ExamDAO;
import model.DAO.SubjectDAO;

public class ExamBO {
	public boolean createQuestion(Question question) {
		return (new ExamDAO()).createQuestion(question);
	}

	public ResponseInfo createExam(Exam exam) {
		return (new ExamDAO()).createExam(exam);
	}
	
	public ArrayList<Exam> getValidExams(String username) {
		return (new ExamDAO()).getValidExams(username);
	}
	
	public boolean checkPasswordExam(String examId, String password) {
		return (new ExamDAO()).checkPasswordExam(examId, password);
	}
	
	public ArrayList<Question> getListQuestionsByExamId(String examId) {
		return (new ExamDAO()).getListQuestionsByExamId(examId);
	}
	
	public double getFinalScore(Map<String, String[]>selectedAnswersMap, String examId) {
		return (new ExamDAO()).getFinalScore(selectedAnswersMap, examId);
	}
	
	public ArrayList<Exam> getListBaiKiemTraByTeacherId(String teacherId) {
		return (new ExamDAO()).getListBaiKiemTraByTeacherId(teacherId);
	}
	
	public ArrayList<Result> getListResultExamByExamId(String examId) {
		return (new ExamDAO()).getListResultExamByExamId(examId);
	}
}
