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

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: MediumSeaGreen">
			<div>
				<a href=<%=request.getContextPath()%>/list" class="navbar-brand">IH WEBAPP</a>
			</div>
		</nav>
	</header>
	<br>
	<div class="main">
	    <div class="col-md-6 col-sm-12">
	       <div class="login-form">
	          <form action="login">
	             <div class="form-group">
	                <label>Email</label>
	                <input type="email" class="form-control" name ="email" placeholder="User Name">
	             </div>
	             <div class="form-group">
	                <label>Username</label>
	                <input type="text" class="form-control" name="user" placeholder="Username">
	             </div>
	             <div class="form-group">
	                <label>Password</label>
	                <input type="password" class="form-control" name="pass" placeholder="Password">
	             </div>
	             <button type="submit" class="btn btn-success">Login</button>
	             <a href="user-register.jsp" class="btn btn-success">Register</a>      
	          </form>
	          <%if(request.getAttribute("error")=="true"){ %> <a>Errore durante il login</a>  <%} %> 
	       </div>
	    </div>
     </div>

</body>
</html>