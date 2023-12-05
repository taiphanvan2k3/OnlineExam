package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BEAN.Exam;
import model.BEAN.Question;
import model.BEAN.ResponseInfo;
import model.BEAN.Result;
import model.BO.ExamBO;
import model.BO.SubjectBO;
import util.Utils;

/**
 * Servlet implementation class Exam
 */
@WebServlet("/ExamController")
public class ExamController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExamController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		String destination = "";
		switch (action) {
		case "create-question":
			request.getSession().setAttribute("subjects", (new SubjectBO()).getAllSubjects());
			destination = "/create-question.jsp";
			break;
		case "create-exam":
			request.getSession().setAttribute("subjects", (new SubjectBO()).getAllSubjects());
			destination = "/create-exam.jsp";
			break;
		case "do-exam":
			destination = "/do-exam.jsp";
			break;
		case "view-history-gv":
			String teacherId = (String) request.getSession().getAttribute("username");
			ArrayList<Exam> baiKiemTras = (new ExamBO()).getListBaiKiemTraByTeacherId(teacherId);
			ArrayList<Result> examDetails1 = new ArrayList<Result>();
			request.getSession().setAttribute("baiKiemTras", baiKiemTras);
			request.getSession().setAttribute("examDetails", examDetails1);
			destination = "/view-result-teacher.jsp";
			break;
		case "view-exam-detail":
			String examId = request.getParameter("id");
			System.out.println(examId);
			ArrayList<Result> examDetails = (new ExamBO()).getListResultExamByExamId(examId);
			request.getSession().setAttribute("examDetails", examDetails);
			destination = "/view-result-teacher.jsp";
			break;
		}
		Utils.redirectToPage(request, response, destination);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		switch (action) {
		case "create-question":
			this.createQuestion(request, response);
			break;
		case "create-exam":
			this.createExam(request, response);
			break;
		case "do-exam":
			break;
		}
		doGet(request, response);
	}

	private void createQuestion(HttpServletRequest request, HttpServletResponse response) {
		Question question = new Question();
		question.setSubjectId(request.getParameter("subjectId"));
		question.setQuestion(request.getParameter("question"));
		question.setAnswers(request.getParameterValues("answers"));
		question.setType(request.getParameter("type"));
		question.setAnswerSingle(request.getParameter("answer-single"));
		question.setAnswerMultiple(request.getParameterValues("answer-multiple"));
		boolean isSuccess = (new ExamBO()).createQuestion(question);
		request.getSession().setAttribute("status", isSuccess);
	}

	private void createExam(HttpServletRequest request, HttpServletResponse response) {
		Exam exam = new Exam();
		exam.setSubjectId(request.getParameter("subjectId"));
		exam.setCreateBy((String) request.getSession().getAttribute("username"));
		exam.setName(request.getParameter("name"));
		exam.setNumberOfQuestions(Integer.parseInt(request.getParameter("number")));
		exam.setPassword(request.getParameter("password"));
		exam.setTotalTime(Double.parseDouble(request.getParameter("total-time")));
		String openAt = request.getParameter("open-at");
		exam.setOpenAt(new java.sql.Timestamp(Utils.convertStringToDate(openAt).getTime()));

		ResponseInfo responseInfo = (new ExamBO()).createExam(exam);
		request.getSession().setAttribute("status", responseInfo.getMessage());
		if (responseInfo.getCode() != 200) {
			request.getSession().setAttribute("old-value", exam);
		}
	}
}
