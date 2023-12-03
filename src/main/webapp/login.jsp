<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login - Trắc nghiệm online</title>
<link rel="stylesheet" type="text/css" href="css/login/login.css">
</head>
<body>
  <h1>Đăng nhập</h1>
  <form action="LoginController" method="POST" name="formLogin">
    <input type="hidden" name="action" value="check-login" />
    <div class="content">
      <div class="row form-group">
        <label for="">Tài khoản</label> <input type="text" name="username" />
      </div>
      <div class="row form-group">
        <label for="">Mật khẩu</label> <input type="password" name="password" />
      </div>
      <div class="row btn-group">
        <button type="submit">Đăng nhập</button>
      </div>
    </div>
  </form>
</body>
</html>