<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Registration</title>
</head>
<body style="background-color: #e0f0ff">
	<jsp:include page="/jsp/navbar.jsp" />
	<hr>

	<h2 class="text-center">Регистрация</h2>

	<div class="w-25 mx-auto">
		<form action="" method="post">
			<div class="input-group mb-3">
				<span class="input-group-text" id="basic-addon1">Имя</span>
				<input name="login" type="text" class="form-control"  aria-describedby="basic-addon1">
			</div>

			<div class="input-group mb-3">
				<span class="input-group-text" id="basic-addon2">Пароль</span>
				<input name="password" type="password" class="form-control" aria-describedby="basic-addon2">
			</div>

			<button class="btn btn-primary">Зарегестрироваться</button>
		</form>
	</div>
</body>
</html>
