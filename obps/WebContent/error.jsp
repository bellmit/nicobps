<%@ page import="java.util.HashMap,java.util.Map"%>
<%
 	Map<String,String> msg=new HashMap<>();
 	msg.put("REPORT_STATUS_MISSING","Status code missing");
 	msg.put("REPORT_PARAM_MISSING","Parameter missing");
%>
<html>
	<head>
		<title>Error</title>
	</head>
	<body>
		<center><h1>Due to technical problem request can't be process.Please try again later...!</h1></center>
		<span><%= (request.getParameter("msg")!=null)?msg.get(request.getParameter("msg").toString()):"" %></span>
	</body>
</html>