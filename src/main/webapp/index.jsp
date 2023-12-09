<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" type="image/x-icon"
	href="./assets/favicon.jpg">
<title>Trang chủ - Trắc nghiệm online</title>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
</head>
<body>
	<div class="container">
		<%@include file="sidebar.jsp"%>
		<%
		request.getSession().removeAttribute("finalScore");
		%>
		<div class="main-content">
			<p>Đây là trang chủ</p>
		</div>
	</div>
</body>
</html>