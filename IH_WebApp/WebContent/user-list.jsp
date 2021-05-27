<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>IH WEBAPP</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	
	<%
	
	if(session.getAttribute("username")==null){
		response.sendRedirect("user-login.jsp");
	}
	
	
	%>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark" style="background-color: MediumSeaGreen">
				<a href=<%=request.getContextPath()%>/list" class="navbar-brand">IH WEBAPP</a>
	            <a class="navbar-brand">Welcome ${username}!</a>
			<ul class="navbar-nav ml-auto">
	            <li class="nav-item">

	            </li>
	            <li class="nav-item">
	                <%if(session.getAttribute("username")!=null) { %><form action="logout"><input type="submit" value="Logout" class="btn btn-danger"></form><%} %>
	            </li>
        	</ul>
		</nav>
	</header>
	<br>

	<div class="row">

		<div class="container">
			<h3 class="text-center">Users List</h3>
			<hr>
			<div class="container text-left">

				<a href="<%=request.getContextPath()%>/new" class="btn btn-success">
					Add a New User</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Surname</th>
						<th>E-mail</th>
						<th>Country</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${listUser}">
						<tr>
							<td><c:out value="${user.id}" /></td>
							<td><c:out value="${user.nome}" /></td>
							<td><c:out value="${user.cognome}" /></td>
							<td><c:out value="${user.email}" /></td>
							<td><c:out value="${user.residenza}" /></td>
							<td><a href="edit?id=<c:out value='${user.id}' />" class="btn btn-success">Edit</a>
							&nbsp;&nbsp;&nbsp;&nbsp; 
							<a href="#myModal" class="btn btn-success" data-toggle="modal">Delete</a></td>
							<!---- "delete?id=<c:out value='${user.id}' />" ---->
						</tr>
						    <!-- Modal HTML -->
			    <div id="myModal" class="modal fade" tabindex="-1">
			        <div class="modal-dialog">
			            <div class="modal-content">
			                <div class="modal-header">
			                    <h5 class="modal-title">Confirmation</h5>
			                    <button type="button" class="close" data-dismiss="modal">&times;</button>
			                </div>
			                <div class="modal-body">
			                    <p>Do you want to <b style="color:red;">DELETE</b> this user?</p>
			                    <p class="text-secondary"><small>If you delete the user, your changes will be lost.</small></p>
			                </div>
			                <div class="modal-footer">
			                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
			                    <a href="delete?id=<c:out value='${user.id}' />" class="btn btn-danger">Delete</a>
			                </div>
			            </div>
			        </div>
			    </div>
					</c:forEach>
				</tbody>

			</table>
		</div>	
	</div>


</body>
</html>