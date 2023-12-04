package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BEAN.User;
import model.BO.LoginBO;
import util.Utils;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		switch (action) {
		case "login": {
			this.gotoLogin(request, response);
			break;
		}
		case "check-login":
			String username = (String) request.getParameter("username");
			String password = (String) request.getParameter("password");
			this.checkLogin(request, response, username, password);
			break;
		case "index": {
			this.gotoHome(request, response);
			break;
		}
		case "logout":
			this.logout(request, response);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void gotoLogin(HttpServletRequest request, HttpServletResponse response) {
		boolean isLoggin = (String) request.getSession().getAttribute("username") != null;
		if (!isLoggin) {
			Utils.redirectToPage(request, response, "/login.jsp");
		} else {
			this.gotoHome(request, response);
		}
	}

	private void gotoHome(HttpServletRequest request, HttpServletResponse response) {
		Utils.redirectToPage(request, response, "/index.jsp");
	}

	private void checkLogin(HttpServletRequest request, HttpServletResponse response, String username,
			String password) {
		User user = (new LoginBO()).checkLogin(username, password);
		String destination = "";
		if (user != null) {
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("fullName", user.getLastName() + " " + user.getFirstName());
			request.getSession().setAttribute("role", user.getRole());
			destination = "/index.jsp";
		} else {
			destination = "/login.jsp";
		}
		Utils.redirectToPage(request, response, destination);
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("fullName");
		request.getSession().removeAttribute("role");
		Utils.redirectToPage(request, response, "/login.jsp");
	}
}
