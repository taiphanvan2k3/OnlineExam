package model.BO;

import java.util.ArrayList;

import model.BEAN.Exam;
import model.BEAN.Question;
import model.BEAN.ResponseInfo;
import model.BEAN.Result;
import model.DAO.ExamDAO;

public class ExamBO {
	public boolean createQuestion(Question question) {
		return (new ExamDAO()).createQuestion(question);
	}

	public ResponseInfo createExam(Exam exam) {
		return (new ExamDAO()).createExam(exam);
	}
	
	public ArrayList<Exam> getListBaiKiemTraByTeacherId(String teacherId) {
		return (new ExamDAO()).getListBaiKiemTraByTeacherId(teacherId);
	}
	
	public ArrayList<Result> getListResultExamByExamId(String examId) {
		return (new ExamDAO()).getListResultExamByExamId(examId);
	}
}
