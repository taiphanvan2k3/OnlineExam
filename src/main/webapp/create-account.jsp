<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="shortcut icon" type="image/x-icon" href="./assets/favicon.jpg">
<title>Tạo tài khoản-Trắc nghiệm online</title>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
<link rel="stylesheet" href="./css/account/account.css" />
</head>
<body>
	<div class="container">
		<%@include file="sidebar.jsp"%>
		<div class="main-content">
			<div class="header"></div>
			<div class="content">
				<div>
					<h1>Tạo mới tài khoản</h1>
					<%
			          ArrayList<String> usernames = (ArrayList<String>) request.getSession().getAttribute("usernames");
			          Boolean isSuccess = (Boolean)request.getSession().getAttribute("status");
			          request.getSession().removeAttribute("status");
			        %>
					<form action="AccountController" method="POST"
						name="formCreateAccount">
						<input type="hidden" name="action" value="create-account" />
						<div class="content">
							<div class="row form-group">
								<label for="">Tài khoản</label> <input id="username" type="text" name="username" onblur="checkValid()">
							</div>
							<span id="username-error" style="color: red"></span>
							<div class="row form-group">
								<label for="">Mật khẩu</label> <input type="password" name="password" />
							</div>
							<div class="row form-group">
								<label for="">Mật khẩu xác nhận</label> <input type="password" />
							</div>
							<div class="row form-group">
								<label for="">Vai trò</label> <select name="role">
									<option value="teacher">Giảng viên</option>
									<option value="student">Sinh viên</option>
								</select>
							</div>
							<div class="row form-group">
								<label for="">Họ</label> <input type="text" name="firstName" />
							</div>
							<div class="row form-group">
								<label for="">Tên</label> <input type="text" name="lastName" />
							</div>
							<div class="row btn-group">
								<button id="btn-create" type="submit">Tạo</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
    window.onload = function() {
        const isSuccess = <%= isSuccess %>;
        if (isSuccess) {
            alert('Thêm mới thành công');
        }
    };
  	
  	
	var usernames = [
  		<%for (int i = 0; i < usernames.size(); i++) {
  	       out.print("\"" + usernames.get(i) + "\"");
  	       if (i < usernames.size() - 1) {
  		      out.print(",");
  	       }
          }%>
  	];
	
	function checkValid() {
		const element = document.getElementById("username");
		const usernameError = document.getElementById("username-error");
		const btnCreate = document.getElementById("btn-create");
		if (usernames.includes(element.value)) {
			btnCreate.disabled = true;
			usernameError.innerText = "Tên người dùng đã tồn tại";
		}else {
			btnCreate.disabled = false;
			usernameError.innerText = "";
		}
	}
	</script>
</body>
</html>
