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
	<title>Read Event</title>
</head>
<body>
	<div class="container">
		<h1><c:out value="${ event.name }"/></h1>
		
		<div class="d-flex justify-content-between">
			<div>
				<p> Host: <c:out value="${ event.host }"/></p>
				<p> Date: <fmt:formatDate type="date" value="${event.date}" /></p>
				<p> Location: <c:out value="${ event.location }"/></p>
				<p> People who are attending this event: ${ event.attendees.size() }</p><br>	
			
				<table class="table">
					<thead>
						<tr>
							<th>Name</th>
							<th>Location</th>
						</tr>
					</thead>
					
					<tbody>
						
						<tr>
							<c:forEach var="attendees" items="${ event.attendees }">
								<th><c:out value="${ attendees.firstName }"/></th>
								<th><c:out value="${ attendees.location }"/></th>
							</c:forEach>
							
						</tr>
					</tbody>
								
				</table>		
			</div>
			
			
			<div>
				<h1>Message Wall</h1>
				
				<c:forEach var="comment" items="${ event.comments }">
					<p>
						<c:out value=" ${comment.author.firstName }"/> says: <c:out value=" ${ comment.message }"/><br />
						----------------------------------------------------------------
					</p>
				</c:forEach>
				
				<h4>Add Comment:</h4>
				<form:form action="/comment/create" method="POST" modelAttribute="commentObj">
					<form:input value="${ user_id }" path="author" type="hidden"/>
					<form:input value="${ event.id }" path="event" type="hidden"/>
					<p>
						<form:input path="message"/>
					</p>
					<button>Submit</button>
				</form:form>
			</div>
		
		
		</div>
	</div>
</body>
</html>