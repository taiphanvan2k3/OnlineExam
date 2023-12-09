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
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
<link rel="stylesheet" href="./css/exam/exams.css" />
</head>
<body>
	<div class="container">
		<%@include file="sidebar.jsp"%>
		<%
		ArrayList<Exam> exams = (ArrayList<Exam>) request.getSession().getAttribute("exams");
		%>
		<div class="main-content">
			<table class="table table-dark table-hover">
				<tr>
					<th>Mã môn học</th>
					<th>Tên môn học</th>
					<th>Số câu hỏi</th>
					<th>Số phút</th>
					<th>Giờ bắt đầu</th>
					<th>Giảng viên</th>
					<th>Kiểm tra</th>
				</tr>
				<%
				for (Exam exam : exams) {
				%>
				<tr>
					<td><%=exam.getSubjectId()%></td>
					<td><%=exam.getName()%></td>
					<td><%=exam.getNumberOfQuestions()%></td>
					<td><%=exam.getTotalTime()%></td>
					<td><%=exam.getOpenAt()%></td>
					<td><%=exam.getTeacherName()%></td>
					<td>
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
	<script>
	        const dialog = document.getElementById('my-dialog');
	        const finalScore = <%=request.getSession().getAttribute("finalScore")%>;
	        const isWrongPassword = '<%=request.getSession().getAttribute("isWrongPassword")%>';
	        if (finalScore !== null && finalScore !== "" && finalScore !== undefined) {
	        	alert(`Bạn được ${finalScore} điểm`);
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
</body>
</html>