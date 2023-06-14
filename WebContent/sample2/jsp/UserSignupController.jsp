<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>sample2_signup</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.css">
</head>
<body>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
<div class="container">
	<h2>SIGNUP</h2>
	<form action="<%= request.getContextPath() %>/sample2/signup" method="post">
		<div class="input-group mb-3">
			<span class="input-group-text">USERNAME</span>
			<input class="form-control" type="text" name="username" placeholder="USERNAME" minlength=3 maxlength=16 required>
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text">PASSWORD</span>
			<input class="form-control" type="password" name="password" placeholder="PASSWORD" minlength=6 maxlength=32 required>
		</div>
		<button class="btn btn-primary" type="submit">サインアップ</button>
		<a href="<%= request.getContextPath() %>/sample2"><button class="btn btn-secondary" type="button">キャンセル</button></a>
	</form>
</div>
</body>
</html>