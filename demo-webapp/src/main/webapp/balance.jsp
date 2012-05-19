<%@page contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Account balance</title>
	</head>
	
	<body>
		<h2>Account balance</h2>
		<br/>
	
		<c:set var="sum" value="0" scope="page" />
		<c:forEach var="entry" items="${it}">
			Name: ${entry.key} Value: ${entry.value} <br/>
			<c:set var="sum" value="${sum + entry.value}" />
		</c:forEach>		
		Sum: <c:out value="${sum}" />
		
		<br/>
		<br/>
		Move € 1 from user1 to user2
		<form action="${pageContext.request.contextPath}/services/moveamount" method="post">
			<label for="fromuser">From user:</label>
			<input name="fromuser" value="user1">
			<br/>
			<label for="touser">To user:</label>
			<input name="touser" value="user2">
			<br/>
			<label for="amount">Amount:</label>
			<input name="amount" value="1">
			<br/>
			<input type="submit" value="Move!">
		</form>
		
		<br/>
		
	</body>
	
	
</html>
