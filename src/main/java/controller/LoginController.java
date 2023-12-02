package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BEAN.User;
import model.BO.LoginBO;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		if (action.equals("check-login")) {
			String username = (String) request.getParameter("username");
			String password = (String) request.getParameter("password");
			System.out.println(username + " " + password);
			this.checkLogin(request, response, username, password);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void checkLogin(HttpServletRequest request, HttpServletResponse response, String username,
			String password) {
		User user = (new LoginBO()).checkLogin(username, password);
		String destination = "";
		System.out.println(user != null);
		if (user != null) {
			response.addCookie(new Cookie("username", username));
			destination = "/views/index.jsp";
		} else {
			destination = "/login.jsp";
		}
		try {
			response.sendRedirect(request.getContextPath() + destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
