<%@page contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login here</title>
	</head>
	
	<body>
		<h2>Sample Authentication Webapp</h2>
		<br/>
		Wellcome ${it.user}!, Expire at ${it.expire} <br/>
		<br/>
		<a href="${pageContext.request.contextPath}/services">Home Page</a><br/>
		<a href="${pageContext.request.contextPath}/services/freeresource">Free Page</a><br/>
		<a href="${pageContext.request.contextPath}/services/JugMilano/People/DomenicoBriganti">Presenter</a><br/>
		<a href="${pageContext.request.contextPath}/services/protectedresource1">Protected Page 1 Method</a><br/>
		<a href="${pageContext.request.contextPath}/services/protectedresource2">Protected Page 2 Class</a><br/>
		<a href="${pageContext.request.contextPath}/services/protectedresource3">Protected Page 3 Roles</a><br/>
		<a href="${pageContext.request.contextPath}/services/containterprotectedresources/4">Protected Page by container 4 Roles</a><br/>
		<a href="${pageContext.request.contextPath}/services/balance">Balance Page</a><br/>
		<a href="${pageContext.request.contextPath}/services/login">Login page</a><br/>
		<a href="${pageContext.request.contextPath}/services/logout">Logout</a><br/>
		<a href="${pageContext.request.contextPath}/services/transactionalresource1">Transactional resource 1 - Get from table</a><br/>
		<a href="${pageContext.request.contextPath}/services/transactionalresource2">Transactional resource 2 - Insert 1 into table, but ReadOnly Transaction</a><br/>
		<a href="${pageContext.request.contextPath}/services/transactionalresource3">Transactional resource 3 - Insert 1 into table</a><br/>
		<a href="${pageContext.request.contextPath}/services/transactionalresource4">Transactional resource 4 - Insert 1 into table and throw an exception</a><br/>
		<a href="${pageContext.request.contextPath}/services/transactionalresource6">Transactional resource 6 - Insert 1 into table call another insert that required a RequiredNew Transaction and throw an exception</a><br/>
		<br/>
		
		<c:if test="${empty it.user}">
			<div style="border: 1px black solid; padding: 5px; width: 250px;">
				<p style="font-weight: bold; vertical-align: middle; text-align: center;">AJAX login, Application Managed Security</p>
				Username: <input id="username" type="text" name="username"><br/>
				Password: <input id="password" type="password" name="password"><br/>
				<input type="button" value="login" onclick="sendRequest();">
				<label id="result"></label>
			</div>
		</c:if>
		
		<script type="text/javascript">
		
			var httpRequest;
			var result = document.getElementById("result");
			var username = document.getElementById("username");
			var password = document.getElementById("password");
	
			function alertContents() {
				if (httpRequest.readyState == 4) {
					if (httpRequest.status == 200) {
						result.innerHTML = "Logged!";
					} else if (httpRequest.status == 401) {
						result.innerHTML = "Unauthorzed!";
					} else {
						alert('There was a problem with the request.');
					}
				}
			}
	
			function sendRequest() {
	
				if (window.XMLHttpRequest) { // Mozilla, Safari, ...
					httpRequest = new XMLHttpRequest();
				} else if (window.ActiveXObject) { // IE
					httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				}
	
				httpRequest.onreadystatechange = alertContents;
				httpRequest.open('POST', '${pageContext.request.contextPath}/services/login', true);
				httpRequest.setRequestHeader("Accept", "application/json");
				httpRequest.send("username="+username.value+"&password="+password.value);
			}
		</script>
		
	</body>
	
</html>
