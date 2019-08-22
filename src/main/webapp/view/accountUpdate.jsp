<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
		<link href="css/accCrud.css" rel="stylesheet">	
	</head>
	<body>
		<div class="top">
			<h1>The <span style="color:#FFBF00;">Golden</span> Bank</h1>
		</div>
	        
	    <ul>
	  		<li><a href="employeeDash">Home</a></li>
			<li><a href="customerCrud">Customer Management</a></li>
			<li><a class="active" href="accountCrud">Account Management</a></li>
			<li><a href="transaction">Transaction Management</a></li>
			<li style="float:right"><a href="index">Logout</a></li>
		</ul>
	      
		<div class="content">
			<br />
			<form:form action="UpdateAccount"  method ="POST" modelAttribute="accModel">
				Account No.:
					<form:input type="number" path="accNo" readonly="true"/> <br>
				Customer ID:
					<form:input type="number" path="cusID" readonly="true"/> <br>
				Branch: 
					<form:input type="text" path="branch"/> <br>
				Amount: 
					<form:input type="number" path="amount"/> <br>
				Account Type: 
					<form:input type="text" path="accType"/> <br>
				Account Nominee: 
					<form:input type="text" path="accNominee"/> <br>
					
					<form:input type="submit" path="" value="submit"/>
			</form:form>
		</div>
	</body>
</html>