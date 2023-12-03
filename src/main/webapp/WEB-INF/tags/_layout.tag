<!DOCTYPE html>
<%@tag import="util.AppState"%>
<html lang="en">
<%@tag description="_layout" pageEncoding="UTF-8"%>
<%@attribute name="title"%>
<%@attribute name="body_area" fragment="true" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - Trắc nghiệm online</title>
    <link rel="stylesheet" href="./css/common.css" />
    <link rel="stylesheet" href="./css/layout.css" />
</head>
<body>
	<%
    	String currentUrl = request.getRequestURI();
    	request.getSession().setAttribute("previousUrl", currentUrl);
    	
    	String username = (String) request.getSession().getAttribute("username");
    	String role = (String) request.getSession().getAttribute("role");
    	String fullName = (String) request.getSession().getAttribute("fullName");
    	
    	if (username == null || role == null) {
    		response.sendRedirect("./LoginController?action=login");
    		return;
    	}
	%>
	
 	<div class="container">
 		<div class="side-bar">
	 		<div class="top-side-bar">
          		<p>Trắc nghiệm online</p>
        	</div>
        	<div class="div-info">
		       	<p>Xin chào, <%= fullName %></p>
	        </div>
        	<div class="nav">
	          <ul>
	            <%
	            	if(role.equals("sv")) {
	            %>
	            <li>
	                <a href="./ExamController?action=do-exam">Làm bài kiểm tra</a>
	            </li>
	           	<li>
	                  <a href="./ExamController?action=view-history">Lịch sử làm bài</a>
	           	</li>
	            <%
	            	}else if(role.equals("gv")) {
	            %>
	            <li>
	                  <a href="./ExamController?action=create-exam">Tạo bài kiểm tra</a>
	            </li>
	            <li>
	                  <a href="./ExamController?action=view-history">Xem lịch sử làm bài</a>
	            </li>
	            <% } else if(role.equals("admin")) {%>
	            <li>
	                  <a href="./AccountController?action=create">Tạo tài khoản</a>
	            </li>
	            <li>
	                  <a href="./SubjectController?action=create">Tạo môn học</a>
	            </li>
	            <% }%>
	            <li>
	            	<a href="./LoginController?action=logout">Đăng xuất</a>
	            </li>
	          </ul>
        	</div>
 		</div>
	    <div class="main-content">
	    	<div class="header">
		
	    	</div>
	    	 <div class="content">
	    	 	<jsp:invoke fragment="body_area"/>
	    	 </div>
	    </div>
 	</div>
 </body>

</html>