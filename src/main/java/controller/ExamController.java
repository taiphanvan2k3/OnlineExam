package controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.BEAN.Exam;
import model.BEAN.Question;
import model.BEAN.ResponseInfo;
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
			String examId = (String) request.getParameter("exam-id");
			request.getSession().setAttribute("examId", examId);
			String timeout = (String) request.getParameter("exam-timeout");
			request.getSession().setAttribute("examTimeout", timeout);
			String password = (String) request.getParameter("password");
			if ((new ExamBO()).checkPasswordExam(examId, password)) {
				request.getSession().setAttribute("examInfo", (new ExamBO()).getExamInfo(Integer.parseInt(examId)));
				request.getSession().setAttribute("examQuestion", (new ExamBO()).getListQuestionsByExamId(examId));
				destination = "/do-exam.jsp";
			} else {
				request.getSession().setAttribute("isWrongPassword", "true");
				destination = "/exams.jsp";
			}
			break;
		case "exams":
			String username = (String) request.getSession().getAttribute("username");
			ArrayList<Exam> exams = (new ExamBO()).getValidExams(username);
			request.getSession().setAttribute("exams", exams);
			destination = "/exams.jsp";
			break;
		case "send-result":
			this.submitResult(request, response);
			username = (String) request.getSession().getAttribute("username");
			exams = (new ExamBO()).getValidExams(username);
			request.getSession().setAttribute("exams", exams);
			destination = "/exams.jsp";
			break;
		case "view-history-gv":
			String teacherId = (String) request.getSession().getAttribute("username");
			request.getSession().setAttribute("baiKiemTras", (new ExamBO()).getListBaiKiemTraByTeacherId(teacherId));
			destination = "/view-result-teacher.jsp";
			break;
		case "view-exam-detail":
			examId = request.getParameter("id");
			request.getSession().setAttribute("examDetails", (new ExamBO()).getListResultExamByExamId(examId));
			request.getSession().setAttribute("isClicked", true);
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

	private void submitResult(HttpServletRequest request, HttpServletResponse response) {
		String examId = (String) request.getSession().getAttribute("examId");
		String selectedAnswers = request.getParameter("selectedAnswers");
		String timeExam = ((int) Double.parseDouble(request.getParameter("timeExam"))) + ":00";
		String examTimeout = request.getParameter("examTimeout");
		if (examTimeout.equals(request.getParameter("timeExam"))) {
			examTimeout = timeExam;
		}
		LocalDateTime timeSubmit = LocalDateTime.now();
		String[] timeExamComponents = timeExam.split(":");
		String[] examTimeoutComponents = examTimeout.split(":");
		Duration duration1 = Duration.ofMinutes(Long.parseLong(timeExamComponents[0]))
				.plusSeconds(Long.parseLong(timeExamComponents[1]));
		Duration duration2 = Duration.ofMinutes(Long.parseLong(examTimeoutComponents[0]))
				.plusSeconds(Long.parseLong(examTimeoutComponents[1]));

		// Tính hiệu của hai Duration
		Duration timeDoExam = duration1.minus(duration2);
		LocalDateTime timeStartExam = timeSubmit.minus(timeDoExam);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
		double finalScore = 0;
		if (selectedAnswers.equals("")) {
			finalScore = 0;
			request.getSession().setAttribute("finalScore", finalScore);
		} else {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String[]> selectedAnswersMap;
			try {
				selectedAnswersMap = mapper.readValue(selectedAnswers, new TypeReference<Map<String, String[]>>() {
				});
				finalScore = (new ExamBO()).getFinalScore(selectedAnswersMap, examId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		(new ExamBO()).saveResultExam((String) request.getSession().getAttribute("username"),
				(String) request.getSession().getAttribute("examId"), finalScore, timeStartExam, timeSubmit);
		request.getSession().setAttribute("finalScore", finalScore);
		request.getSession().setAttribute("timeSubmit", timeSubmit.format(formatter));
		request.getSession().setAttribute("timeStartExam",
				timeDoExam.toMinutes() + " phút " + timeDoExam.toSecondsPart() + " giây");
	}
}
