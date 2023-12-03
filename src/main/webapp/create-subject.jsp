<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Tạo môn học-Trắc nghiệm online</title>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/layout.css" />
<link rel="stylesheet" href="./css/subject/create.css" />
</head>
<body>
  <div class="container">
    <%@include file="sidebar.jsp"%>
    <div class="main-content">
      <div class="header"></div>
      <div class="content">
        <div>
          <h1>Tạo mới môn học</h1>
          <%
          ArrayList<String> subjectIds = (ArrayList<String>) request.getSession().getAttribute("subjectIds");
          Boolean isSuccess = (Boolean)request.getSession().getAttribute("status");
          request.getSession().removeAttribute("status");
          %>
          
          <form action="./SubjectController" method="post">
            <input type="hidden" name="action" value="create">
            <div class="row">
              <label>Mã môn học</label> 
              <input type="text" name="id" id="subjectId" onblur="checkValid()"/>
            </div>
            <div class="row">
              <label>Tên môn học</label> 
              <input type="text" name="name" />
            </div>
            <div class="btn-group">
              <button type="submit" id="btn-create">Tạo mới</button>
              <button type="reset">Huỷ bỏ</button>
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
  	
  	
	var subjectIds = [
  		<%for (int i = 0; i < subjectIds.size(); i++) {
  	       out.print("\"" + subjectIds.get(i) + "\"");
  	       if (i < subjectIds.size() - 1) {
  		      out.print(",");
  	       }
          }%>
  	];
	
	function checkValid() {
		const element = document.getElementById('subjectId');
		const buttonCreate = document.getElementById('btn-create');
		if (subjectIds.includes(element.value)) {
			buttonCreate.disabled = true;	
			alert('Đã tồn tại mã môn học này.');
		}else {
			buttonCreate.disabled = false;
		}
	}
	</script>
</body>
</html>