<%@page import="model.BEAN.Result"%>
<%@page import="model.BEAN.Exam"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Xem kết quả kiểm tra các lớp</title>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
<link rel="stylesheet" href="./css/exam/view-result-teacher.css" />
</head>
<body>
  <div class="container">
    <%@include file="sidebar.jsp"%>

    <%
    ArrayList<Exam> baiKiemTras = (ArrayList<Exam>) request.getSession().getAttribute("baiKiemTras");
    ArrayList<Result> examDetails = (ArrayList<Result>) request.getSession().getAttribute("examDetails");
    Boolean isClicked = (Boolean) request.getSession().getAttribute("isClicked");
    request.getSession().removeAttribute("examDetails");
    request.getSession().removeAttribute("isClicked");
    if (baiKiemTras.size() == 0) {
    	out.print("<h1 class='text-center' style='flex-grow:1'>Bạn chưa tạo bài kiểm tra nào</h1>");
    } else {
    %>
    <div class="table-container scrollbar">
      <h1 class="text-center fs-20px">Danh sách các bài kiểm tra
        đã tạo</h1>
      <table class="styled-table">
        <thead class="thead-dark">
          <tr>
            <th class="text-center">Tên bài kiểm tra</th>
            <th class="text-center">Môn học</th>
            <th class="text-center">Số lượng câu hỏi</th>
            <th class="text-center">Tổng thời gian</th>
            <th class="text-center">Thời gian mở</th>
            <th class="text-center">Xem</th>
          </tr>
        </thead>
        <tbody>
          <%
          for (Exam exam : baiKiemTras) {
          %>
          <tr class="active-row">
            <td><%=exam.getName()%></td>
            <td><%=exam.getSubjectName()%></td>
            <td class="text-center"><%=exam.getNumberOfQuestions()%></td>
            <td class="text-center"><%=exam.getTotalTime()%></td>
            <!-- Format the date and time using Bootstrap 'small' class -->
            <td><small><%=exam.getOpenAt()%></small></td>
            <td class="text-center"><a
                href="./ExamController?action=view-exam-detail&id=<%=exam.getExamId()%>"
              >Xem chi tiết</a></td>
          </tr>
          <%
          }
          %>
        </tbody>
      </table>
    </div>

    <%
    if (isClicked != null) {
    %>
    <div id="myModal" class="modal">
      <div class="modal-content scrollbar">
        <span class="close">&times;</span>
        <%
        if (examDetails != null && examDetails.size() > 0) {
        %>
        <h2 class="text-center">
          <%
          out.print(examDetails.get(0).getExamName());
          %>
        </h2>
        <div class="table-container">
          <table class="styled-table">
            <thead class="thead-dark">
              <tr>
                <th>Mã sinh viên</th>
                <th>Tên sinh viên</th>
                <th class="text-center">Số câu trả lời đúng</th>
              </tr>
            </thead>
            <tbody>
              <%
              for (Result examDetail : examDetails) {
              %>
              <tr class="active-row">
                <td><%=examDetail.getStudentId()%></td>
                <td><%=examDetail.getStudentName()%></td>
                <td class="text-center"><%=examDetail.getCorrectQuestions()%>/10</td>
              </tr>
              <%
              }
              %>
            </tbody>
          </table>
        </div>
        <%
        } else {
        %>
        <h1 class='text-center fs-20px'>Chưa có sinh viên nào làm
          bài kiểm tra này.</h1>
        <%
        }
        %>
      </div>
    </div>
    <%
    }
    }
    %>
  </div>
  <script src="./js/view-history-create-exam.js"></script>
</body>
</html>
