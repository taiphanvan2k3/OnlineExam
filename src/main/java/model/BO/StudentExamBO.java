package model.BO;

import model.BEAN.ListOfHistoryDoExam;
import model.DAO.StudentExamDAO;

public class StudentExamBO {
	public ListOfHistoryDoExam getListHistoryDoExam(String studentId, String subjectId, int currentPage,
			int maxRowOnPage) {
		return (new StudentExamDAO()).getListHistoryDoExam(studentId, subjectId, currentPage, maxRowOnPage);
	}
}
