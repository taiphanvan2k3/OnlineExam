<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon"
  href="./assets/favicon.jpg"
>
<title>Login - Trắc nghiệm online</title>
<link rel="stylesheet" type="text/css" href="css/login/login.css">
</head>
<body>
  <div class="login-div">
    <h1 class="title">ĐĂNG NHẬP</h1>
    <form action="LoginController" method="POST" name="formLogin">
      <input type="hidden" name="action" value="check-login" />
      <div class="content">
        <div class="row form-group">
          <input type="text" name="username"
            placeholder="Tên đăng nhập"
          />
        </div>
        <div class="row form-group">
          <input type="password" name="password"
            placeholder="Mật khẩu"
          />
        </div>
        <div class="row btn-group">
          <button type="submit">Đăng nhập</button>
        </div>
      </div>
    </form>
  </div>
</body>
</html>