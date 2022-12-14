<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Posts</title>
</head>
<body style="background-color: #e0f0ff">
	<jsp:include page="/jsp/navbar.jsp" />
	<hr>
	<h2 class="text-center mb-5">Посты</h2>

	<form action="<c:url value="/posts"/>" method="get">
		<div class="input-group mb-3 w-25 mx-auto">
			<span class="input-group-text" id="basic-addon1">Поиск</span>
			<input name="query" type="text" class="form-control" aria-label="Username" aria-describedby="basic-addon1">
		</div>
	</form>


	<div class="container"  >

		<div class="row">
			<c:forEach var="post" items="${posts}">
		<div class="col-md-3 my-3">
			<div class="card w-100" style="height: 400px">
				<img src="http://localhost:8081/images/${post.getImageName()}" class="card-img-top" style="width: 304px; height: 280px"  alt="...">
				<div class="card-body">
					<h5 class="card-title">${post.getTitle()}</h5>
					<a href="/posts/${post.getId()}" style="background-color: #3b91fa; border-color: #3b91fa" class="btn btn-primary">Читать</a>
					</div>
			</div>
			</c:forEach>
	</div>


</body>
</html>
