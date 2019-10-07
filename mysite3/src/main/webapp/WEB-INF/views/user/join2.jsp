<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src= "${pageContext.servletContext.contextPath}/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
	$(function(){
			$("#input-email").change(function(){
			$("#btn-check-email").show();
			$("#img-checked").hide();
		});
		
		//var $btnCheckEmail = $("#btn-check-email");
		$("#btn-check-email").click(function(){
			var email = $("#input-email").val();
			if(email == "")
				{
					return;
				}
			// ajax 통신
			$.ajax({
						url:"${pageContext.servletContext.contextPath }/api/user/checkemail?email="+email,
						type:"get",
						dataType:"json",
						data:"",
						success:function(response){
							if(response.result == "fail"){
								console.error(response.message);
								return;
							}
							if(response.data == true){
								alert("이미존재하는 메일입니다");
								$("#input-email").val("");
								$("#input-email").focus();
								return;
							}
						$("#btn-check-email").hide();
						$("#img-checked").show();
						
					},
					error:function(xhr,error){
						console.error("error:" + error);
					}
				});
			});
	});
		
	
	
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<spring:message code="NotEmpty.userVo.name"></spring:message>
				<form id="join-form" name="joinForm" method="post" action="${pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="">
					<spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("name")}'></c:if>
						<br>
						<strong>${errors.getFieldError("name").defaultMessage }</strong>
					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label>
					<form:input path="email"/>
					<input id="input-email" name="email" type="text" value="">
					<input id="btn-check-email" type="button" value="중복확인">
					<img id = "img-checked" style ="width:20px; display:none"src="${pageContext.servletContext.contextPath }/assets/images/check.jpg">
					<form:error path="email"></form:error>
					<spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("email")}'>
<%-- 							<spring:message code ='${errors.getfieldErrors("email")[0]}' text='${errors.getFieldError("email").defaultMessage }'/> --%>
						</c:if>
						<br>
					<%-- <strong>${errors.getFieldError("name").defaultMessage }</strong> --%>
					</spring:hasBindErrors>
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<legend><spring:message code="user.joinform.gendertext"/></legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					<form:radiobuttons items=${genders } path="gender"/>
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>