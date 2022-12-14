<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<body style="background-color: #e0f0ff">
	<jsp:include page="/jsp/navbar.jsp" />
	<hr>
	<h2 class="text-center mb-5">Редактировать пост</h2>
	<form action="" method="post" enctype="multipart/form-data" class="w-75 mx-auto">
		<div class="mb-3">
			<label for="exampleFormControlInput1" class="form-label">Название</label>
			<input value="${post.getTitle()}" name="title" type="text" class="form-control" id="exampleFormControlInput1">
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Описание</label>
			<textarea name="text" class="form-control" id="exampleFormControlTextarea1" rows="3">
				${post.getText()}
			</textarea>
		</div>

		<div class="mb-3">
			<label for="formFile" class="form-label">Загрузите картинку</label>
			<input name="img" class="form-control" type="file" id="formFile">
		</div>

		<button class="btn btn-outline-primary mt-5">Сохранить</button>
	</form>


</body>
</html>
