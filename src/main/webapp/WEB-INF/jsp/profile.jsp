<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Profile</title>
</head>
<body style="background-color: #e0f0ff">
	<jsp:include page="/jsp/navbar.jsp" />
	<hr>
	<h2 class="text-center">Ваш профиль</h2>
	<div>
		<form action="" method="post" class="w-50 mx-auto">
			<div class="mb-3">
				<label for="login" class="form-label">Логин</label>
				<input value="${login}" name="login" type="text" class="form-control" id="login">
			</div>
			<div class="mb-3">
				<label for="info" class="form-label">Информация о пользователе</label>
				<textarea name="info" class="form-control" id="info" rows="3">
					${info}
				</textarea>
			</div>

			<button class="btn btn-outline-success">Сохранить изменения</button>
		</form>
	</div>
</body>
</html>
