package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BO.SubjectBO;
import util.Utils;

@WebServlet("/SubjectController")
public class SubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SubjectController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		if (action.equals("create")) {
			ArrayList<String> subjectIds = (new SubjectBO()).getAllSubjectIds();
			request.getSession().setAttribute("subjectIds", subjectIds);
			Utils.redirectToPage(request, response, "/create-subject.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		if (action.equals("create")) {
			this.createSubject(request, response);
		}
		doGet(request, response);
	}

	private void createSubject(HttpServletRequest request, HttpServletResponse response) {
		String id = (String) request.getParameter("id");
		String name = (String) request.getParameter("name");
		boolean isSuccess = (new SubjectBO()).createSubject(id, name);
		request.getSession().setAttribute("status", isSuccess);
	}
}
