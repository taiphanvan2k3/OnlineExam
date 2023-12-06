<%@page import="model.BEAN.Result"%>
<%@page import="model.BEAN.Exam"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

		<div class="table-container scrollbar">
			<table class="styled-table">
				<thead class="thead-dark">
					<tr>
						<th>Tên bài kiểm tra</th>
						<th>Số lượng câu hỏi</th>
						<th>Tổng thời gian</th>
						<th>Thời gian mở</th>
						<th>Xem</th>
					</tr>
				</thead>
				<tbody>
					<%
					ArrayList<Exam> baiKiemTras = (ArrayList<Exam>) request.getSession().getAttribute("baiKiemTras");
					ArrayList<Result> examDetails = (ArrayList<Result>) request.getSession().getAttribute("examDetails");
					for (Exam exam : baiKiemTras) {
					%>
					<tr class="active-row">
						<td><%=exam.getName()%></td>
						<td class="text-center"><%=exam.getNumberOfQuestions()%></td>
						<td class="text-center"><%=exam.getTotalTime()%></td>
						<!-- Format the date and time using Bootstrap 'small' class -->
						<td><small><%=exam.getOpenAt()%></small></td>
						<td class="text-center"><a
							href="./ExamController?action=view-exam-detail&id=<%=exam.getExamId()%>">Xem
								chi tiết</a></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>

		<!-- The Modal -->
		<div id="myModal" class="modal">
			<!-- Modal content -->
			<div class="modal-content">
				<span class="close">&times;</span>
				<h2 class="text-center"><% out.print(examDetails.get(0).getExamName()); %></h2>
				<div class="table-container scrollbar">
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
								<td class="text-center"><%=examDetail.getCorrectQuestions()%>/<%=examDetail.getNumQuestions()%></td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script>
		// Kiểm tra nếu danh sách examDetail không rỗng, thì mở modal
		var a =
	<%out.print(examDetails.size());%>
		var examDetailList =
	<%if (examDetails.size() > 0) {
		out.print("true");
	} else {
		out.print("false");
	}%>
		if (examDetailList) {
			var modal = document.getElementById("myModal");
			modal.style.display = "block";
		}

		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];

		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
			modal.style.display = "none";
		}

		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}
	</script>
</body>
</html>
