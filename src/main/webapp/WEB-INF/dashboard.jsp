<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<!-- CSS only -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<meta charset="UTF-8">
	<title>Events</title>
</head>
<body>
	<div class="container">
		<div class="d-flex justify-content-between align-items-center">
			<h1>Welcome, <c:out value="${ user.firstName }"/></h1>
			<a href="/logout">Log out</a>
		</div>
		
		<h4>Here are some of the events in your state:</h4>
		<table class="table">
			<thead>
				<tr>
					<th>Name</th>
					<th>Date</th>
					<th>Location</th>
					<th>Host</th>
					<th>Action / Status</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="event" items="${ events }">
					<c:if test = "${ event.state == user.state }">
						<tr>
							<td><a href="/events/${ event.id }">${ event.name }</a></td>
							<td><fmt:formatDate type="date" value="${ event.date }" /></td>
							<td>${ event.location }</td>
							<td>${ event.host }</td>		
												
							<c:choose>
								<c:when test="${ event.creator.id == user_id }">
									<td>
										<a href="/events/${event.id}/edit">Edit</a>
										<a href="/events/${event.id}/delete">Delete</a>
									</td>
								</c:when>
								<c:when test="${ event.attendees.contains(user)}">									
									<td>
										Joining
										<a href="/events/${ event.id }/cancel">Cancel</a>
									</td>
								</c:when> 
						        <c:otherwise>
						        	<td>
						        		<a href="/events/${ event.id }/join">Join</a>
						        	</td>
								</c:otherwise>
							</c:choose>
						</tr>
			    	</c:if>
				</c:forEach>
			</tbody>
		</table>
		<br />
		
		<h4>Here are some of the events in other states:</h4>
		<table class="table">
			<thead>
				<tr>
					<th>Name</th>
					<th>Date</th>
					<th>Location</th>
					<th>Host</th>
					<th>Action / Status</th>
				</tr>
			</thead>
						
			<tbody>
				<c:forEach var="event" items="${ events }">
					<c:if test = "${ event.state != user.state }">
						<tr>
							<td><a href="/events/${ event.id }">${ event.name }</a></td>
							  
							
							<td><fmt:formatDate type="date" value="${event.date}" /></td>
							<td>${ event.location }</td>
							<td>${ event.host }</td>
							<c:choose>
								<c:when test="${ event.creator.id == user_id }">
									<td>
										<a href="/events/${event.id}/edit">Edit</a>
										<a href="/events/${event.id}/delete">Delete</a>
									</td>
								</c:when>
								<c:when test="${ event.attendees.contains(user)}">									
									<td>
										Joining
										<a href="/events/${ event.id }/cancel">Cancel</a>
									</td>
								</c:when> 
						        <c:otherwise>
						        	<td><a href="/events/${ event.id }/join">Join</a></td>
								</c:otherwise>
							</c:choose>
						</tr>
			    	</c:if>
				</c:forEach>
			</tbody>
		</table>
		
			
		<br />
		<h4>Create an Event</h4>
		<p style="color:red;"><form:errors path="eventObj.*"/></p>
		
		<form:form action="/events" method="POST" modelAttribute="eventObj">
			<form:input path="host" value="${ user.firstName }" type="hidden"/>
			<form:input path="creator" value="${ user.id }" type="hidden"/>
			
	 		<p>
				Name
				<form:input path="name"/>
			</p>
			
			<p>
				Date
				<form:input type="date" path="date"/>
			</p>
		
		
			<p>
				Location
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
			<button>Create</button>
		</form:form>

	</div>
</body>
</html>