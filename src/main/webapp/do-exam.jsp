<%@page import="model.BEAN.SimpleExam"%>
<%@page import="model.BEAN.Question"%>
<%@page import="java.util.Date"%>
<%@page import="model.BEAN.Subject"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" type="image/x-icon"
  href="./assets/favicon.jpg"
>
<title>Làm bài trắc nghiệm online</title>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
<link rel="stylesheet" href="./css/exam/do-exam.css" />
</head>
<body>
  <div class="container">
    <%
    ArrayList<Question> examQuestion = (ArrayList<Question>) request.getSession().getAttribute("examQuestion");
    int index = 1;
    %>
    <div class="main-content display-timeout">
      <div class="custom-scroll list-question">
        <form action="./ExamController" method="post">
          <input type="hidden" name="action" value="send-result">
          <input type="hidden" name="timeExam"
            value="<%=request.getSession().getAttribute("examTimeout")%>"
          >
          <input type="hidden" name="examTimeout" id="examTimeout"
            value="<%=request.getSession().getAttribute("examTimeout")%>"
          >
          <%
          SimpleExam examInfo = (SimpleExam) request.getSession().getAttribute("examInfo");
          %>
          <div class="exam-info">
            <div class="exam-name">
              <label>Tên bài kiểm tra: </label>
              <span><%=examInfo.getExamName()%></span>
            </div>
            <div class="exam-info-body">
              <div class="row">
                <div>
                  <label>Môn học: </label>
                  <span><%=examInfo.getSubjectName()%></span>
                </div>
                <div class="time">
                  <label>Tổng thời gian: </label>
                  <span><%=examInfo.getTotalTime()%> (phút)
                  </span>
                </div>
              </div>
              <div class="row">
                <div>
                  <label>Mã sinh viên: </label>
                  <span><%=(String) request.getSession().getAttribute("username")%></span>
                </div>
                <div>
                  <label>Họ tên sinh viên: </label>
                  <span><%=(String) request.getSession().getAttribute("fullName")%></span>
                </div>
              </div>
            </div>
          </div>
          <%
          for (Question question : examQuestion) {
          %>
          <h3 style="margin-bottom: 10px;">
            Câu
            <%=index + "/" + examQuestion.size() + ": " + question.getQuestion()%></h3>
          <%
          for (int i = 0; i < question.getAnswers().length; i++) {
          %>
          <%
          if (question.getType().equals("multiple")) {
          %>
          <div
            id="checkbox<%=index%><%=String.valueOf((char) (65 + i))%>"
            class="question"
            onclick="triggerInputClick(this)"
          >
            <input
              id="checkbox<%=index%><%=String.valueOf((char) (65 + i))%>"
              type="checkbox" name="answer<%=index%>""
              value="<%=question.getId()%>;<%=String.valueOf((char) (65 + i))%>"
              onclick="event.stopPropagation(); updateSelectedAnswers('<%=question.getId()%>', '<%=String.valueOf((char) (65 + i))%>', 'checkbox', this.id, '')"
            >
            <label for="option<%=i%>"><%=String.valueOf((char) (65 + i)) + ") " + question.getAnswers()[i]%></label>
            <br>
          </div>
          <%
          } else {
          %>
          <div class="question radio<%=index%>" onclick="triggerInputClick(this)">
            <input
              id="radio<%=index%><%=String.valueOf((char) (65 + i))%>"
              type="radio" name="answer<%=index%>"
              value="<%=question.getId()%>;<%=String.valueOf((char) (65 + i))%>"
              onclick="event.stopPropagation(); updateSelectedAnswers('<%=question.getId()%>', '<%=String.valueOf((char) (65 + i))%>', 'radio', this.id, 'radio<%=index%>')"
            >
            <label for="option<%=i%>"><%=String.valueOf((char) (65 + i)) + ") " + question.getAnswers()[i]%></label>
            <br>
          </div>
          <%
          }
          %>
          <%
          }
          %>
          <%
          index++;
          }
          %>
          <input type="hidden" name="selectedAnswers"
            id="selected-answers"
          >
          <button type="submit" class="btn-submit" onclick="localStorage.removeItem('selectedAnswers')">Nộp bài</button>
        </form>
      </div>
      <div class="timeout-container">
        <p class="title-timeout">Thời gian còn lại</p>
        <p id="timeout"><%=request.getSession().getAttribute("examTimeout")%></p>
      </div>
    </div>
  </div>
  <script>
    var startingMinutes = parseInt('<%=request.getSession().getAttribute("examTimeout")%>');
  </script>
  <script src="./js/do-exam.js"></script>
  <script src="./js/view-history-create-exam.js"></script>
</body>
</html>