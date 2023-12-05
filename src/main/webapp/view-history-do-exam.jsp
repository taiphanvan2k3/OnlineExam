<%@page import="model.BEAN.HistoryDoExam"%>
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
<title>Tạo môn học-Trắc nghiệm online</title>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
<link rel="stylesheet" href="./css/exam/view-history-do-exam.css" />
</head>
<body>
  <div class="container">
    <%@include file="sidebar.jsp"%>
    <div class="main-content">
      <div class="search-block">
        <h2 class="title">Điều kiện tìm kiếm</h2>
        <hr>
        <form action="./StudentExamController" method="get">
          <input type="hidden" name="action" value="view-history">
          <label>Môn học</label>
          <select name="subjectId">
            <option value="all">Tất cả</option>
            <%
            ArrayList<Subject> subjects = (ArrayList<Subject>) request.getSession().getAttribute("subjects");
            String subjectId = request.getParameter("subjectId");
            ArrayList<HistoryDoExam> histories = (ArrayList<HistoryDoExam>) request.getSession().getAttribute("histories");
            int currentPage = Integer.parseInt(request.getParameter("current-page"));
            if (subjects != null) {
            	for (Subject subject : subjects) {
            		String isSelected = subjectId != null && subjectId.equals(subject.getId()) ? "selected" : "";
            %>
            <option value="<%=subject.getId()%>" <%=isSelected%>><%=subject.getName()%></option>
            <%
            }
            }
            %>
          </select>
          <button class="btn-search">Tìm kiếm</button>
        </form>
      </div>
      <div class="content-block">
        <h2 class="title">Danh sách các bài kiểm tra đã làm</h2>
        <hr>
        <%
        if (histories.size() == 0) {
        	out.print("<p>Không tìm thấy kết quả nào</p>");
        } else {
        %>
        <table class="styled-table">
          <thead>
            <tr>
              <%
              if (subjectId.equals("")) {
              	out.print("<td class='text-center'>Môn học</td>");
              }
              %>
              <td class="text-center">Tên bài kiểm tra</td>
              <td class="text-center">Thời gian làm bài</td>
              <td class="text-center">Thời gian nộp bài</td>
              <td class="text-center">Tổng số câu hỏi</td>
              <td class="text-center">Số câu đúng</td>
              <td class="text-center">Điểm số</td>
            </tr>
          </thead>
          <tbody>
            <%
            for (HistoryDoExam history : histories) {
            %>
            <tr>
              <%
              if (subjectId.equals("")) {
              	out.print("<td class='text-center'>" + history.getSubjectName() + "</td>");
              }
              %>
              <td><%=history.getNameOfExam()%></td>
              <td class="text-center"><%=history.getOpenAt()%></td>
              <td class="text-center"><%=history.getSubmitAt()%></td>
              <td class="text-center"><%=history.getTotalQuestions()%></td>
              <td class="text-center"><%=history.getCorrectQuestions()%></td>
              <td class="text-center"><%=history.getCorrectQuestions() * 10.0 / history.getTotalQuestions()%></td>
            </tr>
            <%
            }
            %>
          </tbody>
        </table>

        <%
        int totalPages = (int) request.getSession().getAttribute("totalPages");
        %>
        <form action="./StudentExamController" class="div-pagination"
          method="get"
        >
          <input type="hidden" name="action" value="view-history">
          <ul class="pagination">
            <li class="prev">
              <button type="button">Prev</button>
            </li>
            <li class="current-page">
              <span>
                <%=currentPage%>
                /
                <%=totalPages%>
              </span>
            </li>
            <li class="next">
              <button type="button">Next</button>
            </li>
          </ul>
          <input type="hidden" name="subjectId" value="<%=subjectId%>">
          <input type="hidden" name="current-page"
            value="<%=currentPage%>"
          >
        </form>
        <input type="hidden" class="total-pages"
          value="<%=totalPages%>"
        >
        <%
        }
        %>
      </div>
    </div>
  </div>

  <script src="./js/view-history-do-exam.js"></script>
</body>
</html>