<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<body style="background-color: #e0f0ff">
	<jsp:include page="/jsp/navbar.jsp" />
	<hr>

	<div class="w-50 mx-auto">
		<h2 class="mb-5">${post.getTitle()}</h2>
		<div class="mb-5">
			<img style="width: 75%; border-radius: 5%" src="http://localhost:8081/images/${post.getImageName()}" alt="img">
		</div>
		<p class="mb-5">${post.getText()}</p>

		<c:if test="${post.getUserId().equals(authUser.getId()) || userService.isAdmin(authUser)}">
			<form action="<c:url value="/posts/update"/>" method="get" class="d-inline-block mb-5">
				<button name="postID" value="${post.getId()}" class="btn btn-outline-info">Редактировать</button>
			</form>
			<form action="<c:url value="/posts/delete"/>" method="post" class="d-inline-block">
				<button name="postID" value="${post.getId()}" class="btn btn-outline-danger">Удалить</button>
			</form>
		</c:if>

		<h3 class="text-center">Комментарии</h3>
		<hr>

		<ul class="list-group mb-5">
			<c:forEach var="comment" items="${comments}">
				<li class="list-group-item list-group-item-secondary mb-3">
					<b>${userService.findUserById(comment.getUserId()).getLogin()}</b>: ${comment.getText()}
				</li>
			</c:forEach>
		</ul>

		<c:if test="${authUser != null}">
			<form action="" method="post">
				<div class="input-group mb-3">
					<input name="text" type="text" class="form-control">
				</div>
				<button class="btn btn-success">Оставить комментарий</button>
			</form>
		</c:if>
	</div>

</body>
</html>
