<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<p class="jr-success">
					회원가입을 축하합니다.
					<br><br>
					<a href="<%=request.getContextPath() %>/user?a=loginform">로그인하기</a>
				</p>				
			</div>
		</div>
		<c:import page="/WEB-INF/views/includes/navigation.jsp" />
		<c:import page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>