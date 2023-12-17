package model.BO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import model.BEAN.Exam;
import model.BEAN.Question;
import model.BEAN.ResponseInfo;
import model.BEAN.Result;
import model.BEAN.ResultAfterCheck;
import model.BEAN.SimpleExam;
import model.DAO.ExamDAO;

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

	public SimpleExam getExamInfo(int examId) {
		return (new ExamDAO()).getExamInfo(examId);
	}

	public ArrayList<Question> getListQuestionsByExamId(String examId) {
		return (new ExamDAO()).getListQuestionsByExamId(examId);
	}

	public ResultAfterCheck getFinalScore(Map<String, String[]> selectedAnswersMap, String examId) {
		return (new ExamDAO()).getFinalScore(selectedAnswersMap, examId);
	}

	public ArrayList<Exam> getListBaiKiemTraByTeacherId(String teacherId) {
		return (new ExamDAO()).getListBaiKiemTraByTeacherId(teacherId);
	}

	public ArrayList<Result> getListResultExamByExamId(String examId) {
		return (new ExamDAO()).getListResultExamByExamId(examId);
	}

	public void saveResultExam(String studentId, String examId, double correctQuestions, LocalDateTime openAt,
			LocalDateTime submitAt) {
		(new ExamDAO()).saveResultExam(studentId, examId, correctQuestions, openAt, submitAt);
	}
}
