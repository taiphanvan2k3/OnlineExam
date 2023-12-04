<%@page import="model.BEAN.Exam"%>
<%@page import="java.util.Date"%>
<%@page import="model.BEAN.Subject"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Tạo môn học - Trắc nghiệm online</title>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
<link rel="stylesheet" href="./css/subject/create.css" />
</head>
<body>
  <div class="container">
    <%@include file="sidebar.jsp"%>
    <div class="main-content">
      <div class="content">
        <h1>Tạo bài kiểm tra</h1>
        <form action="./ExamController" method="post">
          <input type="hidden" name="action" value="create-exam">
          <div class="row">
            <label>Môn học</label>
            <select name="subjectId">
              <%
              ArrayList<Subject> subjects = (ArrayList<Subject>) request.getSession().getAttribute("subjects");
              String status = (String) request.getSession().getAttribute("status");
              Exam exam = (Exam) request.getSession().getAttribute("old-value");
              if (exam == null)
              	exam = new Exam();
              request.getSession().removeAttribute("status");
              request.getSession().removeAttribute("old-value");

              if (subjects != null) {
              	for (Subject subject : subjects) {
              		String isSelected = exam.getSubjectId().equals(subject.getId()) ? "selected" : "";
              %>
              <option value="<%=subject.getId()%>" <%=isSelected%>><%=subject.getName()%></option>
              <%
              }
              }
              %>
            </select>

          </div>
          <div class="row">
            <label>Tên bài kiểm tra</label>
            <input type="text" name="name" value="<%=exam.getName()%>" required />
          </div>
          <div class="row">
            <label>Số câu hỏi</label>
            <input type="number" name="number" min="1" value="<%=exam.getNumberOfQuestions()%>" />
          </div>
          <div class="row">
            <label>Mật khẩu làm bài</label>
            <input type="text" name="password" value="<%=exam.getPassword()%>" required />
          </div>
          <div class="row">
            <label>Thời gian làm bài (phút)</label>
            <input type="number" name="total-time" class="total-time" step="0.1" min="1.0"
              value="<%=exam.getTotalTime()%>"
            />
          </div>
          <div class="row">
            <label>Thời gian bắt đầu</label>
            <input type="datetime-local" name="open-at" value="<%=exam.getOpenAt()%>" />
          </div>
          <div class="btn-group">
            <button type="submit">Tạo</button>
            <button type="reset">Huỷ bỏ</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <script>
	window.onload = function() {
		const message = '<%=status == null ? "" : status%>';
		if (message !== '') {
			alert(message);
		}
	};
  </script>
</body>
</html>