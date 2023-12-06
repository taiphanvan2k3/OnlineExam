package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BO.StudentExamBO;
import model.BO.SubjectBO;
import util.Utils;

@WebServlet("/StudentExamController")
public class StudentExamController extends HttpServlet {
	private final int PAGE_SIZE = 5;
	private static final long serialVersionUID = 1L;

	public StudentExamController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		String destination = "";
		switch (action) {
		case "do-exam":
			destination = "/do-exam.jsp";
			break;
		case "view-history":
			destination = this.viewHistory(request, response);
			break;
		}
		Utils.redirectToPage(request, response, destination);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private String viewHistory(HttpServletRequest request, HttpServletResponse response) {
		String subjectId = request.getParameter("subjectId");
		if (subjectId == null || subjectId.equals("all")) {
			subjectId = "";
		}
		String studentId = (String) request.getSession().getAttribute("username");
		String curentPageRequest = request.getParameter("current-page");
		int currentPage = 1;
		if (curentPageRequest != null) {
			currentPage = Integer.parseInt(curentPageRequest);
		}

		request.getSession().setAttribute("subjects", (new SubjectBO()).getAllSubjects());
		var temp = (new StudentExamBO()).getListHistoryDoExam(studentId, subjectId, currentPage, PAGE_SIZE);
		request.getSession().setAttribute("histories", temp.getHistories());
		request.getSession().setAttribute("totalPages", temp.getTotalPage());
		String destination = String.format("/view-history-do-exam.jsp?subjectId=%s&current-page=%d", subjectId,
				currentPage);
		return destination;
	}
}
