<%@page import="model.BEAN.Exam"%>
<%@page import="java.util.Date"%>
<%@page import="model.BEAN.Subject"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Danh sách bài kiểm tra</title>
<link rel="shortcut icon" type="image/x-icon"
  href="./assets/favicon.jpg"
>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
<link rel="stylesheet" href="./css/exam/exams.css" />
</head>
<body>
	<div class="container">
		<%@include file="sidebar.jsp"%>
		<%
		ArrayList<Exam> exams = (ArrayList<Exam>) request.getSession().getAttribute("exams");
        if(exams == null) {
        	response.sendRedirect("./ExamController?action=exams"); 
            return; 
        }
        request.getSession().removeAttribute("exams");
		Double finalScore = (Double) request.getSession().getAttribute("finalScore");
		String timeSubmit = (String) request.getSession().getAttribute("timeSubmit");
		String timeStartExam = (String) request.getSession().getAttribute("timeStartExam");
		%>
		<div class="main-content">
			<table class="styled-table">
				<thead class="thead-dark">
					<tr>
						<th class="text-center">Mã môn học</th>
						<th class="text-center">Tên môn học</th>
						<th class="text-center">Số câu hỏi</th>
						<th class="text-center">Số phút</th>
						<th class="text-center">Giờ bắt đầu</th>
						<th class="text-center">Giảng viên</th>
						<th class="text-center">Kiểm tra</th>
					</tr>
				</thead>
				<%
				for (Exam exam : exams) {
				%>
				<tr>
					<td class="text-center"><%=exam.getSubjectId()%></td>
					<td><%=exam.getName()%></td>
					<td class="text-center"><%=exam.getNumberOfQuestions()%></td>
					<td class="text-center"><%=exam.getTotalTime()%></td>
					<td class="text-center"><%=exam.getOpenAt()%></td>
					<td class="text-center"><%=exam.getTeacherName()%></td>
					<td class="text-center">
						<button>
							<a class="dialog-btn" href="#my-dialog"
								data-exam-id="<%=exam.getExamId()%>"
								data-exam-timeout="<%=exam.getTotalTime()%>">Làm bài</a>
						</button>
					</td>
				</tr>
				<%
				}
				%>
			</table>
		</div>
	</div>
	<form action="./ExamController" method="post">
		<input type="hidden" name="action" value="do-exam"> <input
			type="hidden" name="exam-id" id="exam-id-input"> <input
			type="hidden" name="exam-timeout" id="exam-timeout-input"> <input
			type="hidden" name="exam-openAt" id="exam-openAt-input">
		<div class="dialog overlay" id="my-dialog">
			<a href="#" class="overlay-close"></a>
			<div class="dialog-body">
				<a class="dialog-close-btn" href="#">&times;</a>
				<h3>Nhập mật khẩu để làm bài</h3>
				<input type="password" name="password">
				<button type="submit">Submit</button>
			</div>
		</div>
	</form>
	<%
	if (finalScore != null) {
	%>
	<div id="myModal" class="modal">
		<div class="modal-content scrollbar">
			<span class="close">&times;</span>
			<h2 class="text-center">Kết quả thi</h2>
			<div class="table-container">
				<table class="styled-table">
					<thead class="thead-dark">
						<tr>
							<th class="text-center">Điểm</th>
							<th class="text-center">Thời gian làm bài</th>
							<th class="text-center">Thời gian nộp bài</th>
						</tr>
					</thead>
					<tbody>
						<tr class="active-row">
							<td class="text-center"><%=finalScore%></td>
							<td class="text-center"><%=timeSubmit%></td>
							<td class="text-center"><%=timeStartExam%></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%
	}
	%>
	<script>
	        const dialog = document.getElementById('my-dialog');
	        const finalScore = <%=finalScore%>;
	        const isWrongPassword = '<%=request.getSession().getAttribute("isWrongPassword")%>';
	        if (finalScore !== null && finalScore !== "" && finalScore !== undefined) {
	        	//alert(`Bạn được ${finalScore} điểm`);
	        	<%request.getSession().removeAttribute("finalScore");%>
	        }
	        else if (isWrongPassword === "true") {
	        	alert('Bạn đã nhập sai mật khẩu');
	        	<%request.getSession().removeAttribute("isWrongPassword");%>
	        }
	        const dialogButtons = document.querySelectorAll('.dialog-btn');
		    const examIdInput = document.getElementById('exam-id-input');
		    const examTimeoutInput = document.getElementById('exam-timeout-input');
		    const examOpenAtInput = document.getElementById('exam-openAt-input');
		    dialogButtons.forEach(button => {
		        button.addEventListener('click', () => {
		            const examId = button.getAttribute('data-exam-id');
		            const examTimeout = button.getAttribute('data-exam-timeout');
		            const examOpenAt = new Date();
		            examIdInput.value = examId;
		            examTimeoutInput.value = examTimeout;
		            examOpenAtInput.value = examOpenAt;
		        });
		    });
		</script>
	<script src="./js/view-history-create-exam.js"></script>
</body>
</html>