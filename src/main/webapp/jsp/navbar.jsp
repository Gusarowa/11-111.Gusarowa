<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
<script src="<c:url value="/js/bootstrap.bundle.min.js"/>"></script>

<style>
	body{}
	.nav-item:hover {
		background-color: #6c757d;
		color: white;
		border-radius: 5px;
        cursor: pointer;
	}
</style>
<ul class="nav" style="margin-top: 1rem; padding-left: .5rem">
	<li class="nav-item">
		<a style="color: #034181" class="nav-link active" aria-current="page" href="<c:url value="/home"/>">HOME</a>
	</li>

	<li class="nav-item">
		<a style="color: #034181" class="nav-link" href="<c:url value="/reg"/>">SIGN UP</a>
	</li>

	<li class="nav-item">
		<a style="color: #034181" class="nav-link" href="<c:url value="/auth"/>">LOG IN</a>
	</li>

	<li class="nav-item">
		<a style="color: #034181" class="nav-link" href="<c:url value="/posts"/>">POSTS</a>
	</li>

	<c:if test="${authUser != null}">
		<li class="nav-item">
			<a class="nav-link" style="color: #034181"  href="<c:url value="/posts/add"/>">ADD</a>
		</li>

		<li class="nav-item">
			<a class="nav-link" style="color: #034181" href="<c:url value="/profile"/>">PROFILE</a>
		</li>
	</c:if>
</ul>