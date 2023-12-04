package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BO.AccountBO;
import util.Utils;

@WebServlet("/AccountController")
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AccountController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		switch (action) {
		case "create": {
			ArrayList<String> usernames = (new AccountBO()).getAllUsernames();
			request.getSession().setAttribute("usernames", usernames);
			this.gotoCreateAccount(request, response);
			break;
		}
		case "create-account": {
			String username = (String) request.getParameter("username");
			String password = (String) request.getParameter("password");
			String role = (String) request.getParameter("role");
			String firstName = (String) request.getParameter("firstName");
			String lastName = (String) request.getParameter("lastName");
			this.createAccount(request, response, username, password, role, firstName, lastName);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void gotoCreateAccount(HttpServletRequest request, HttpServletResponse response) {
		Utils.redirectToPage(request, response, "/create-account.jsp");
	}

	private void createAccount(HttpServletRequest request, HttpServletResponse response, String username, String password, String role, String firstName, String lastName) {
		(new AccountBO()).createAccount(username, password, role, firstName, lastName);
		Utils.redirectToPage(request, response, "/index.jsp");
	}
}
