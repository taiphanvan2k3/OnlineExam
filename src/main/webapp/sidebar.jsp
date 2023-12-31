<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
%>
<%
String currentUrl = request.getRequestURI();
request.getSession().setAttribute("previousUrl", currentUrl);

String username = (String) request.getSession().getAttribute("username");
String role = (String) request.getSession().getAttribute("role");
String fullName = (String) request.getSession().getAttribute("fullName");

if (username == null || role == null) {
	response.sendRedirect("./LoginController?action=login");
	return;
}
%>

<div class="side-bar">
  <div class="top-side-bar">
    <img alt="favicon" src="./assets/favicon.jpg" class="favicon">
    <p>Trắc nghiệm online</p>
  </div>
  <div class="div-info">
    <img alt="user-icon" src="./assets/user.png" class="user-icon">
    <p>
      <span class="hello">Xin chào</span>
      <br />
      <%=fullName%></p>
  </div>
  <div class="nav">
    <ul>
      <%
      if (role.equals("sv")) {
      %>
      <li>
        <a href="./ExamController?action=exams">Làm bài kiểm tra</a>
      </li>
      <li>
        <a href="./StudentExamController?action=view-history">Lịch sử làm bài</a>
      </li>
      <%
      } else if (role.equals("gv")) {
      %>
      <li>
        <a href="./ExamController?action=create-question">Tạo câu hỏi</a>
      </li>
      <li>
        <a href="./ExamController?action=create-exam">Tạo bài kiểm tra</a>
      </li>
      <li>
        <a href="./ExamController?action=view-history-gv">Danh sách bài kiểm tra đã tạo</a>
      </li>
      <%
      } else if (role.equals("admin")) {
      %>
      <li><a href="./AccountController?action=create">Tạo tài
          khoản</a></li>
      <li><a href="./SubjectController?action=create">Tạo môn
          học</a></li>
      <%
      }
      %>
      <li><a href="./LoginController?action=logout">Đăng xuất</a>
      </li>
    </ul>
  </div>
</div>