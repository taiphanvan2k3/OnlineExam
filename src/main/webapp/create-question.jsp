<%@page import="model.BEAN.Subject"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" type="image/x-icon" href="./assets/favicon.jpg">
<title>Tạo môn học - Trắc nghiệm online</title>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
<link rel="stylesheet" href="./css/subject/create.css" />
<link rel="stylesheet" href="./css/exam/create-question.css" />
</head>
<body>
  <div class="container">
    <%@include file="sidebar.jsp"%>
    <div class="main-content">
      <div class="content">
        <h1>Tạo mới câu hỏi cho môn học</h1>
        <form action="./ExamController" method="post">
          <input type="hidden" name="action" value="create-question">
          <div class="row">
            <label>Môn học</label>
            <select name="subjectId">
              <%
              ArrayList<Subject> subjects = (ArrayList<Subject>) request.getSession().getAttribute("subjects");
              Boolean isSuccess = (Boolean) request.getSession().getAttribute("status");
              request.getSession().removeAttribute("status");
              if (subjects != null) {
              	for (Subject subject : subjects) {
              %>
              <option value="<%=subject.getId()%>"><%=subject.getName()%></option>
              <%
              }
              }
              %>
            </select>

          </div>
          <div class="row">
            <label>Tên câu hỏi</label>
            <textarea rows="1" name="question" required="required"></textarea>
          </div>
          <%
          for (int i = 1; i <= 4; i++) {
          %>
          <div class="row">
            <label>
              Đáp án
              <%=(char) ('A' + i - 1)%></label>
            <textarea rows="1" required="required" name="answers"></textarea>
          </div>
          <%
          }
          %>
          <div class="div-types">
            <label>Loại câu hỏi</label>
            <div class="types">
              <input type="radio" name="type" value="single" checked />
              <label>Một đáp án</label>
              <input type="radio" name="type" value="multiple" />
              <label>Nhiều đáp án</label>
            </div>
          </div>
          <div class="div-correct-answers">
            <label>Đáp án đúng</label>
            <div class="div-single">
              <%
              for (int i = 1; i <= 4; i++) {
              %>
              <input type="radio" name="answer-single" value="<%=(char) ('A' + i - 1)%>" />
              <label><%=(char) ('A' + i - 1)%></label>
              <%
              }
              %>
            </div>
            <div class="div-multiple" style="display: none;">
              <%
              for (int i = 1; i <= 4; i++) {
              %>
              <input type="checkbox" name="answer-multiple" value="<%=(char) ('A' + i - 1)%>" />
              <label><%=(char) ('A' + i - 1)%></label>
              <%
              }
              %>
            </div>
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
				const isSuccess = <%=isSuccess%>;
				if (isSuccess) {
					alert('Thêm mới thành công');
				}
			};
		</script>
  <script src="./js/create-question.js"></script>
</body>
</html>