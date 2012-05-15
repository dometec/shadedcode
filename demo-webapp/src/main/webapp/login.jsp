<%@page contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login here</title>
	</head>
	
	<body>
		<br/>
		${it.message}
		<br/>
		<h2>Login here (application managed security)</h2><br/>
		<br/>
		<form action="${pageContext.request.contextPath}/services/login" method="post">
			Username: <input type="text" name="username"><br/>
			Password: <input type="password" name="password"><br/>
			<input type="submit">
		</form>
		<br/>
		<h2>Login here (container managed security)</h2><br/>
		<br/>
		<form method="POST" action="j_security_check">
			Username: <input type="text" name="j_username"><br/>
			Password: <input type="password" name="j_password"><br/>
			<input type="submit">
		</form>

	</body>
	
</html>