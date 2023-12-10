<%@page import="model.BEAN.Question"%>
<%@page import="java.util.Date"%>
<%@page import="model.BEAN.Subject"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" type="image/x-icon" href="./assets/favicon.jpg">
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
		<div class="main-content display-timeout" style="height: 90vh; overflow-y: auto;">
			<div class="custom-scroll list-question">
				<form action="./ExamController" method="post">
					<input type="hidden" name="action" value="send-result"> 
					<input type="hidden" name="timeExam" 
						value="<%=request.getSession().getAttribute("examTimeout")%>">
					<input type="hidden" name="examTimeout" id="examTimeout"
						value="<%=request.getSession().getAttribute("examTimeout")%>">
					<%
					for (Question question : examQuestion) {
					%>
						<h3 style="margin-bottom: 10px;">Câu <%=index + "/" + examQuestion.size() + ": " + question.getQuestion()%></h3>
						<% for (int i = 0; i < question.getAnswers().length; i++) { %>
							<%if (question.getType().equals("multiple")) { %>
								<div id="checkbox<%= index%><%= String.valueOf((char) (65 + i)) %>" class="question">									
									<input id="checkbox<%= index%><%= String.valueOf((char) (65 + i)) %>" type="checkbox" name="answer<%= index%>" value="<%= String.valueOf((char) (65 + i)) %>" onclick="updateSelectedAnswers('<%= question.getId() %>', '<%= String.valueOf((char) (65 + i)) %>', 'checkbox', this.id, '')">
									<label for="option<%= i %>"><%= String.valueOf((char) (65 + i)) + ") " + question.getAnswers()[i] %></label><br>
								</div>
							<%} else { %>
								<div class="question radio<%= index%>">
									<input id="radio<%= index%><%= String.valueOf((char) (65 + i)) %>" type="radio" name="answer<%= index%>" value="<%= String.valueOf((char) (65 + i)) %>" onclick="updateSelectedAnswers('<%= question.getId() %>', '<%= String.valueOf((char) (65 + i)) %>', 'radio', this.id, 'radio<%= index%>')">
									<label for="option<%= i %>"><%= String.valueOf((char) (65 + i)) + ") " + question.getAnswers()[i] %></label><br>
								</div>
							<%} %>
						<% } %>
					<%
						index++;
					}
					%>
					<input type="hidden" name="selectedAnswers" id="selected-answers">
					<button type="submit" class="btn-submit">Nộp bài</button>
				</form>
			</div>
			<div class="timeout-container">		
				<p class="title-timeout">Thời gian còn lại</p>
				<p id="timeout"><%= request.getSession().getAttribute("examTimeout") %></p>
			</div>
		</div>
	</div>
	<script>
		document.addEventListener('keydown', function(event) {
		     event.preventDefault();
		});
		const startingMinutes = parseInt('<%= request.getSession().getAttribute("examTimeout") %>');
		let time = startingMinutes * 60;
		const timeoutElement = document.getElementById('timeout');
		const timeDoExamElement = document.getElementById('examTimeout');

		setInterval(updateTimeout, 1000);
		function updateTimeout() {
			const minutes = Math.floor(time / 60);
			let seconds = time % 60;
			timeoutElement.innerHTML = String(minutes).padStart(2, '0') + ':' + String(seconds).padStart(2, '0');
			timeDoExamElement.value = timeoutElement.innerHTML;
			if (time <= 0) {
		        document.forms[0].submit();
		    }
			time--;
		}
		var selectedAnswers = {};
		function updateSelectedAnswers(questionId, answerValue, type, id, className) {
			if (!selectedAnswers.hasOwnProperty(questionId)) {
				selectedAnswers[questionId] = [];
			}
			if (type === 'checkbox') {
				var answerIndex = selectedAnswers[questionId].indexOf(answerValue);
				if (answerIndex !== -1) {
					selectedAnswers[questionId].splice(answerIndex, 1);
				} else {
					selectedAnswers[questionId].push(answerValue);
				}
				const divElement = document.getElementById(id);
				if (divElement.classList.contains("choose")) {
					divElement.classList.remove("choose");
				}
				else {
					divElement.classList.add("choose");
				}
			} else {
				selectedAnswers[questionId] = [answerValue];
				const divElements = document.getElementsByClassName(className);
				Array.from(divElements).forEach(function(element) {
					element.classList.remove('choose');
				});
				const divElement = document.getElementById(id).closest("div");
				divElement.classList.add("choose");
			}
			const answers = document.getElementById('selected-answers');
			answers.value = JSON.stringify(selectedAnswers);
		}
	</script>
	<script src="./js/view-history-create-exam.js"></script>
</body>
</html>