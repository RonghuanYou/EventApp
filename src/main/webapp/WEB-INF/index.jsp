<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<!-- CSS only -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<meta charset="UTF-8">
	<title>Register-Login</title>
</head>
<body>
	<div class="container p-5">
		<h1>Welcome</h1>
		<div class="d-flex justify-content-between">
			<div class="register p-3">
				<h4>Register</h4>
				<p style="color:red;"><form:errors path="userObj.*"/></p>
				
				<form:form action="/registration" method="POST" modelAttribute="userObj">
					<p>
				        <form:label path="firstName">First Name</form:label>
				        <form:input path="firstName"/>
					</p>
					<p>
				        <form:label path="lastName">Last Name</form:label>
				        <form:input path="lastName"/>
					</p>
					<p>
				        <form:label path="email">Email</form:label>
				        <form:input type="email" path="email"/>
					</p>
					
					<p>
				        <form:label path="location">Location</form:label>
				        <form:input path="location"/>
				        
				        <form:select path="state">
				        	<form:option value="AL"/>
						  	<form:option value="AK"/>
							<form:option value="AZ"/>
							<form:option value="AR"/>
							<form:option value="CA"/>
							<form:option value="CO"/>
							<form:option value="CT"/>
							<form:option value="DE"/>
							<form:option value="DC"/>
							<form:option value="FL"/>
							<form:option value="GA"/>
							<form:option value="HI"/>
							<form:option value="ID"/>
							<form:option value="IL"/>
							<form:option value="IN"/>
							<form:option value="IA"/>
							<form:option value="KS"/>
							<form:option value="KY"/>
							<form:option value="LA"/>
							<form:option value="ME"/>
							<form:option value="MD"/>
							<form:option value="MA"/>
							<form:option value="MI"/>
							<form:option value="MN"/>
							<form:option value="MS"/>
							<form:option value="MO"/>
							<form:option value="MT"/>
							<form:option value="NE"/>
							<form:option value="NV"/>
							<form:option value="NH"/>
							<form:option value="NJ"/>
							<form:option value="NM"/>
							<form:option value="NY"/>
							<form:option value="NC"/>
							<form:option value="ND"/>
							<form:option value="OH"/>
							<form:option value="OK"/>
							<form:option value="OR"/>
							<form:option value="PA"/>
							<form:option value="RI"/>
							<form:option value="SC"/>
							<form:option value="SD"/>
							<form:option value="TN"/>
							<form:option value="TX"/>
							<form:option value="UT"/>
							<form:option value="VT"/>
							<form:option value="VA"/>
							<form:option value="WA"/>
							<form:option value="WV"/>
							<form:option value="WI"/>
							<form:option value="WY"/>
				        </form:select>
					</p>
					
					
					<p>
				        <form:label path="password">Password</form:label>
				        <form:input type="password" path="password"/>
					</p>
					
					<p>
				    	<form:label path="passwordConfirmation">Password Confirmation:</form:label>
				        <form:password path="passwordConfirmation"/>
				    </p>
					
					<button>Register</button>
				</form:form>
			</div>
			
			<div class="login p-3">
				<h4>Login</h4>
				<!-- SHOW ERROR MESSAGES -->
	    		<p style="color:red;"><c:out value="${ error }" /></p>
				<form action="/login" method="POST">
					<p>
						<label for="email">Email</label>
						<input type="email" name="email"/>
					</p>
					<p>
			            <label for="password">Password</label>
						<input type="password" name="password" />
					</p>
					
					<button>Login</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>