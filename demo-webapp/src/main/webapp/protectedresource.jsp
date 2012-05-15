<%@page contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Protected resource</title>
	</head>
	
	<body>
		<h2>Protected resource</h2>
		<br/>
		Wellcome ${it.user}<br/>
		<br/>
		<a href="${pageContext.request.contextPath}/services/protectedresource2">Protected Page 1</a><br/>
		<a href="${pageContext.request.contextPath}/services/protectedresource1">Protected Page 2</a><br/>
		<a href="${pageContext.request.contextPath}/services/login">Login page</a><br/>
		<br/>
	</body>
	
	
</html>
